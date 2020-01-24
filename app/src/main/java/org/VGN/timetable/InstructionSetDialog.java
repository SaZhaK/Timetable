package org.VGN.timetable;

import android.app.*;
import android.content.*;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;

import org.VGN.timetable.fragments.BasicFragment;

public class InstructionSetDialog extends android.app.DialogFragment {

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Добро пожаловать в планировщик задач!\n\n" +
                "Для создания новой задачи нажмите “+” в правом нижнем углу\n\n" +
                "Для редактирования или удаления конкретной задачи нажмите на неё и удерживайте в течение нескольких секунд\n\n" +
                "Для удаления всех задач выбранного дня выберите кнопку “меню” в правом верхнем углу и нажмите “Очистить всё”\n");

        builder.setPositiveButton("Ok                                   ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        return builder.create();
    }
}