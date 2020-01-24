package org.VGN.timetable;

import android.app.*;
import android.content.*;
import android.os.Bundle;
import android.widget.Toast;

import org.VGN.timetable.fragments.BasicFragment;

public class ChoiceDialog extends android.app.DialogFragment {

    BasicFragment targetFragment;
    int targetFragmentId;
    int tempId;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("        Выберите действие");

        builder.setPositiveButton("Редактировать задачу             ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();

                FragmentManager manager = getFragmentManager();
                EditDialog editDialog = new EditDialog();
                editDialog.show(manager, "Choice dialog" + targetFragmentId);
                editDialog.setFragment(targetFragment, targetFragmentId);
                editDialog.setTempId(tempId);
            }
        });
        builder.setNegativeButton("Удалить задачу                    ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                targetFragment.delete(tempId);
            }
        });

        builder.setNeutralButton("Отмена                              ", new DialogInterface.OnClickListener() {
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