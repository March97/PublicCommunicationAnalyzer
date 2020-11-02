package com.example.publiccommunicationanalyzer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.util.ArrayList;

public class AddDayDialog extends AppCompatDialogFragment {

    public Spinner s;
    public ArrayList<String> daysList;
    public int pos;
    private AddDayDialogListener listener;

    public AddDayDialog(ArrayList<String> daysList) {
        this.daysList = daysList;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle(R.string.dialog_day)
                .setNegativeButton("wyjdź", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("dalej", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String line = daysList.get(pos);
                        listener.applyTextsDay(line);
                    }
                });
        s = view.findViewById(R.id.dialog_spinner);
        s.setAdapter(new AddDaySpinnerAdapter(getContext(), R.layout.item_day, daysList));
        s.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        return builder.create();
    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (AddDayDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }
    public interface AddDayDialogListener {
        void applyTextsDay(String day);
    }
}
