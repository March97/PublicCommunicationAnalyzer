package com.example.publiccommunicationanalyzer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;


import androidx.appcompat.app.AppCompatDialogFragment;
import java.util.concurrent.ExecutionException;

public class AddSelectorDialog extends AppCompatDialogFragment {

    private AddSelectorDialogListener listener;
    int choice = 0;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.add_selector, null);

        builder.setView(view)
                .setTitle(R.string.dialog_add_selector)
                .setNegativeButton("wyjdź", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

        Button btnLine = view.findViewById(R.id.btnLine);
        btnLine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice = 1;
                try {
                    listener.applyIntSelector(choice);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
                dismiss();
            }
        });

        Button btnAddress = view.findViewById(R.id.btnAddress);
        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choice = 0;
                try {
                    listener.applyIntSelector(choice);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dismiss();
            }
        });

        return builder.create();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            listener = (AddSelectorDialogListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ExampleDialogListener");
        }
    }
    public interface AddSelectorDialogListener {
        void applyIntSelector(int choice) throws ExecutionException, InterruptedException;
    }
}