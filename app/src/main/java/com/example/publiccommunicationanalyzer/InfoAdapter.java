package com.example.publiccommunicationanalyzer;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class InfoAdapter extends RecyclerView.Adapter<InfoAdapter.InfoHolder> {

    private ArrayList<ArrayList<Vertex>> vertices;
    private ArrayList<ArrayList<Edge>> edges;

    private AdapterView.OnItemClickListener listener;

    @NonNull
    @Override
    public InfoAdapter.InfoHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_line_info, parent, false);
        return new InfoHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull InfoAdapter.InfoHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    class InfoHolder extends RecyclerView.ViewHolder {

        private TextView number;
        private TextView service;
        private TextView amountOfVertices;
        private TextView time;
        private TextView distance;

        public InfoHolder(View view) {
            super(view);
            number = view.findViewById(R.id.tvNumberInfo);
            service = view.findViewById(R.id.tvServiceInfo);
            amountOfVertices = view.findViewById(R.id.tvAmountOfVerticesInfo);
            time = view.findViewById(R.id.tvTimeInfo);
            distance = view.findViewById(R.id.tvDistanceInfo);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
//                    if (listener != null && position != RecyclerView.NO_POSITION)
////                        listener.onItemClick(vertices.get(position));
                }
            });
        }
    }
}
