package org.VGN.timetable;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.*;
import android.widget.TextView;
import android.widget.Toast;

import org.VGN.timetable.fragments.BasicFragment;

import java.util.LinkedList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private List<BasicFragment> basicFragmentList;
    private FragmentTransaction fragmentTransaction;
    private FloatingActionButton fab;
    private BasicFragment currentFragment;
    private static SharedPreferences sharedPreferences;
    private static SharedPreferences.Editor editor;
    private static TextView user;

    public static DBController dbController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View hView = navigationView.getHeaderView(0);
        user = (TextView) hView.findViewById(R.id.HeaderText);

        sharedPreferences = getSharedPreferences("FirstLaunch", Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        if (sharedPreferences.getString("UserName", null) == null) {
            FragmentManager manager = getFragmentManager();
            UserNameEnterDialog userNameEnterDialog = new UserNameEnterDialog();
            userNameEnterDialog.show(manager, "User name enter dialog");
        } else {
            user.setText(sharedPreferences.getString("UserName", null));
        }

        if (!sharedPreferences.getBoolean("IsLaunched", false)) {
            editor.putBoolean("IsLaunched", true);
            editor.apply();

            FragmentManager manager = getFragmentManager();
            InstructionSetDialog instructionSetDialog = new InstructionSetDialog();
            instructionSetDialog.show(manager, "Instruction dialog");
        }

        basicFragmentList = new LinkedList<>();

        for (int i = 0; i < 7; i++) {
            basicFragmentList.add(new BasicFragment());
            basicFragmentList.get(i).setFragmentId(i);
        }

        basicFragmentList.get(0).setHeaderString("понедельник");
        basicFragmentList.get(0).setTableName(DBController.TABLE_CHECKBOXES_MONDAY);
        basicFragmentList.get(1).setHeaderString("вторник");
        basicFragmentList.get(1).setTableName(DBController.TABLE_CHECKBOXES_TUESDAY);
        basicFragmentList.get(2).setHeaderString("среду");
        basicFragmentList.get(2).setTableName(DBController.TABLE_CHECKBOXES_WEDNESDAY);
        basicFragmentList.get(3).setHeaderString("четверг");
        basicFragmentList.get(3).setTableName(DBController.TABLE_CHECKBOXES_THURSDAY);
        basicFragmentList.get(4).setHeaderString("пятницу");
        basicFragmentList.get(4).setTableName(DBController.TABLE_CHECKBOXES_FRIDAY);
        basicFragmentList.get(5).setHeaderString("субботу");
        basicFragmentList.get(5).setTableName(DBController.TABLE_CHECKBOXES_SATURDAY);
        basicFragmentList.get(6).setHeaderString("воскресенье");
        basicFragmentList.get(6).setTableName(DBController.TABLE_CHECKBOXES_SUNDAY);

        setCurrentFragment(DateController.getDayOfWeek());
        fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.container, currentFragment);
        fragmentTransaction.commit();

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currentFragment.addNewCheckbox();
            }
        });
    }

    public static void saveName (String name) {
        editor.putString("UserName", name);
        editor.apply();
        user.setText(sharedPreferences.getString("UserName", null));
    }

    private void setCurrentFragment(String currentDayOfWeek) {
        switch (currentDayOfWeek){
            case "понедельник":
                currentFragment = basicFragmentList.get(0);
                break;
            case "вторник":
                currentFragment = basicFragmentList.get(1);
                break;
            case "среда":
                currentFragment = basicFragmentList.get(2);
                break;
            case "четверг":
                currentFragment = basicFragmentList.get(3);
                break;
            case "пятница":
                currentFragment = basicFragmentList.get(4);
                break;
            case "суббота":
                currentFragment = basicFragmentList.get(5);
                break;
            case "воскресенье":
                currentFragment = basicFragmentList.get(6);
                break;
             default:
                 break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.clearOption) {
            currentFragment.clear();
            return true;
        } else if (id == R.id.instructionOption) {
            FragmentManager manager = getFragmentManager();
            InstructionSetDialog instructionSetDialog = new InstructionSetDialog();
            instructionSetDialog.show(manager, "Instruction dialog");
            return true;
        } else if (id == R.id.NameChangeOption) {
            FragmentManager manager = getFragmentManager();
            UserNameEnterDialog userNameEnterDialog = new UserNameEnterDialog();
            userNameEnterDialog.show(manager, "User name enter dialog");
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        fragmentTransaction = getFragmentManager().beginTransaction();

        int id = item.getItemId();

        if (id == R.id.monday) {
            fragmentTransaction.replace(R.id.container, basicFragmentList.get(0));
            currentFragment = basicFragmentList.get(0);
        } else if (id == R.id.tuesday) {
            fragmentTransaction.replace(R.id.container, basicFragmentList.get(1));
            currentFragment = basicFragmentList.get(1);
        } else if (id == R.id.wednesday) {
            fragmentTransaction.replace(R.id.container, basicFragmentList.get(2));
            currentFragment = basicFragmentList.get(2);
        } else if (id == R.id.thursday) {
            fragmentTransaction.replace(R.id.container, basicFragmentList.get(3));
            currentFragment = basicFragmentList.get(3);
        } else if (id == R.id.friday) {
            fragmentTransaction.replace(R.id.container, basicFragmentList.get(4));
            currentFragment = basicFragmentList.get(4);
        } else if (id == R.id.saturday) {
            fragmentTransaction.replace(R.id.container, basicFragmentList.get(5));
            currentFragment = basicFragmentList.get(5);
        } else if (id == R.id.sunday) {
            fragmentTransaction.replace(R.id.container, basicFragmentList.get(6));
            currentFragment = basicFragmentList.get(6);
        }
        fragmentTransaction.commit();

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
