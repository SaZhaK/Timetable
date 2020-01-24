package org.VGN.timetable;

import android.app.*;
import android.content.*;
import android.os.Bundle;
import android.widget.*;

import org.VGN.timetable.fragments.BasicFragment;

public class TextInputDialog extends android.app.DialogFragment {

    BasicFragment targetFragment;
    int targetFragmentId;
    LinearLayout targetLinearLayout;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("  Создание новой задачи");
        final EditText input = new EditText(getActivity());
        builder.setView(input);

        targetLinearLayout = targetFragment.getLinerLayout();

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                targetFragment.database = MainActivity.dbController.getWritableDatabase();

                targetFragment.contentValues = new ContentValues();

                targetFragment.contentValues.put(DBController.KEY_TEXT, input.getText().toString());
                targetFragment.contentValues.put(DBController.KEY_STATE, false);

                targetFragment.database.insert(targetFragment.getTableName(),
                        null, targetFragment.contentValues);

                targetLinearLayout.removeAllViews();
                targetFragment.reload();
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
}
