package org.VGN.timetable;

import android.app.*;
import android.content.*;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import org.VGN.timetable.fragments.BasicFragment;

public class UserNameEnterDialog extends android.app.DialogFragment {

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Введите ваше имя");
        final EditText input = new EditText(getActivity());
        builder.setView(input);

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MainActivity.saveName(input.getText().toString());
                dialogInterface.cancel();
            }
        });
        return builder.create();
    }
}