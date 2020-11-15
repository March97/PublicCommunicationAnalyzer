package com.example.publiccommunicationanalyzer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;

import androidx.appcompat.app.AppCompatDialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class InfoDialog extends AppCompatDialogFragment {

    private ArrayList<ArrayList<Vertex>> vertices;
    private ArrayList<ArrayList<Edge>> edges;

    public InfoDialog(ArrayList<ArrayList<Vertex>> vertices, ArrayList<ArrayList<Edge>> edges) {
        this.vertices = vertices;
        this.edges = edges;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.info_dialog, null);

        RecyclerView recyclerView = view.findViewById(R.id.rvInfo);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setHasFixedSize(true);

        builder.setView(view)
                .setTitle(R.string.dialog_info)
                .setNegativeButton("wyjdź", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                })
                .setPositiveButton("dalej", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

        return builder.create();

    }
}
