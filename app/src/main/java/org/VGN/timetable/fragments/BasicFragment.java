package org.VGN.timetable.fragments;

import android.annotation.SuppressLint;
import android.app.FragmentManager;
import android.content.*;
import android.content.res.ColorStateList;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.*;
import android.widget.*;

import org.VGN.timetable.DBController;
import org.VGN.timetable.ChoiceDialog;
import org.VGN.timetable.MainActivity;
import org.VGN.timetable.TextInputDialog;
import org.VGN.timetable.R;

public class BasicFragment extends android.app.Fragment implements View.OnClickListener {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;
    private OnFragmentInteractionListener mListener;

    private LinearLayout llMain;
    private LinearLayout.LayoutParams lParams;
    private BasicFragment fragment;
    private int fragmentId;
    private TextView header;
    private String headerString;
    private String tableName;

    public SQLiteDatabase database;
    public ContentValues contentValues;

    public BasicFragment() {
        fragment = this;
    }

    public LinearLayout getLinerLayout () {
        return  this.llMain;
    }

    public void setHeaderString(String dayOfWeek) {
        headerString = " Задачи на " + dayOfWeek;
    }

    public void setTableName (String tableName) {
        this.tableName = tableName;
    }

    public String getTableName () {
        return this.tableName;
    }

    public void setFragmentId (int id) {
        this.fragmentId = id;
    }

    public static BasicFragment newInstance(String param1, String param2) {
        BasicFragment fragment = new BasicFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.basic_fragment, container, false);

        llMain = (LinearLayout) v.findViewById(R.id.llMain);

        lParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lParams.gravity = Gravity.LEFT;

        header = v.findViewById(R.id.BasicFragmentHeader);
        header.setText(headerString);

        MainActivity.dbController = new DBController(getActivity());

        reload();

        MainActivity.dbController.close();

        return v;
    }

    public void addNewCheckbox() {

        FragmentManager manager = getFragmentManager();
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.show(manager, "dialog" + fragmentId);
        textInputDialog.setFragment(fragment, fragmentId);
    }

    ColorStateList changeColor () {
        ColorStateList colorStateList = new ColorStateList(
                new int[][] {
                        new int[] { -android.R.attr.state_checked },
                        new int[] { android.R.attr.state_checked }
                },
                new int[] {
                        0xffffbb33,
                        0xffffbb33
                }
        );
        return colorStateList;
    }

    @SuppressLint("RestrictedApi")
    public void reload () {

        database = MainActivity.dbController.getWritableDatabase();
        Cursor cursor = database.query(tableName,
                null, null, null, null, null, null);

        if (cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex(DBController.KEY_ID);
            int textIndex = cursor.getColumnIndex(DBController.KEY_TEXT);
            int statelIndex = cursor.getColumnIndex(DBController.KEY_STATE);
            do {
                AppCompatCheckBox cbNew = new AppCompatCheckBox(this.getActivity());
                cbNew.setSupportButtonTintList(changeColor());

                cbNew.setText(cursor.getString(textIndex));
                cbNew.setChecked(intToBoolean(cursor.getInt(statelIndex)));

                final int tempId = cursor.getInt(idIndex);

                updateCheckBoxState(cbNew, tempId);

                cbNew.setOnCheckedChangeListener((compoundButton, b) -> {
                    updateCheckBoxState(cbNew, tempId);
                });

                cbNew.setOnLongClickListener(view -> {
                    FragmentManager manager = getFragmentManager();
                    ChoiceDialog choiceDialog = new ChoiceDialog();
                    choiceDialog.show(manager, "dialog" + fragmentId);
                    choiceDialog.setFragment(fragment, fragmentId);
                    choiceDialog.setTempId(tempId);
                    return false;
                });

                llMain.addView(cbNew, lParams);
            } while (cursor.moveToNext());
        }

        cursor.close();
    }

    boolean intToBoolean (int i) {
        if (i == 1) {
            return true;
        } else {
            return false;
        }
    }

    int booleanToInt (boolean b) {
        if (b) {
            return 1;
        } else {
            return 0;
        }
    }

    public void edit (int idx, String newText) {
        database = MainActivity.dbController.getWritableDatabase();

        llMain.removeAllViews();
        database.execSQL("update " + tableName + " set " + DBController.KEY_TEXT +
                " = " + "'" + newText + "'" + " where " + DBController.KEY_ID + " = " + idx + ";");
        reload();
    }

    public void delete (int idx) {

        database = MainActivity.dbController.getWritableDatabase();

        llMain.removeAllViews();
        database.execSQL("delete from " + tableName + " where " + DBController.KEY_ID + " = " + idx + ";");
        reload();
    }

    public void clear () {
        llMain.removeAllViews();
        database = MainActivity.dbController.getWritableDatabase();
        database.delete(tableName, null, null);
    }

    void updateCheckBoxState (AppCompatCheckBox checkBox, int idx) {
        if (checkBox.isChecked()) {
            checkBox.setPaintFlags(checkBox.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            checkBox.setPaintFlags(checkBox.getPaintFlags() & (~Paint.STRIKE_THRU_TEXT_FLAG));
        }

        database = MainActivity.dbController.getWritableDatabase();

        database.execSQL("update " + tableName + " set " + DBController.KEY_STATE +
         " = " + booleanToInt(checkBox.isChecked()) + " where " + DBController.KEY_ID + " = " + idx + ";");
    }

    @Override
    public void onClick(View view) {}

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }
}