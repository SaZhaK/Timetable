package org.VGN.timetable;

import android.app.*;
import android.content.*;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import org.VGN.timetable.fragments.BasicFragment;

public class EditDialog extends android.app.DialogFragment {

    BasicFragment targetFragment;
    int targetFragmentId;
    int tempId;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("Введите ноую задачу");
        final EditText input = new EditText(getActivity());
        builder.setView(input);

        builder.setPositiveButton("Изменить", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                targetFragment.edit(tempId, input.getText().toString());
            }
        });
        builder.setNegativeButton("Отмена", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        return builder.create();
    }

    public void setFragment (BasicFragment fragment, int fragmentId) {
        this.targetFragment = fragment;
        this.targetFragmentId = fragmentId;
    }

    public void setTempId (int tempId) {
        this.tempId = tempId;
    }
}