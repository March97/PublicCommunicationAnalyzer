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
import java.util.concurrent.ExecutionException;

public class AddLineDialog extends AppCompatDialogFragment {

    public Spinner s;
    public ArrayList<String> linesList;
    public int pos;
    private AddLineDialogListener listener;

    public AddLineDialog(ArrayList<String> linesList) {
        this.linesList = linesList;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.layout_dialog, null);

        builder.setView(view)
                .setTitle(R.string.dialog_line)
                .setNegativeButton("wyjdź", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("dalej", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String line = linesList.get(pos);
                        try {
                            listener.applyTextsLine(line);
                        } catch (ExecutionException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                });
        s = view.findViewById(R.id.dialog_spinner);
        s.setAdapter(new AddLineSpinnerAdapter(getContext(), R.layout.item_line, linesList));
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
            listener = (AddLineDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }
    public interface AddLineDialogListener {
        void applyTextsLine(String line) throws ExecutionException, InterruptedException;
    }
}
