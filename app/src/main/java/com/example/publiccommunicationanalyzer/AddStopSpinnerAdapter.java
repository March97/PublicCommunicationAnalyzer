package com.example.publiccommunicationanalyzer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class AddStopSpinnerAdapter extends ArrayAdapter {

    ArrayList<Vertex> objects;

    public AddStopSpinnerAdapter(@NonNull Context context, int resource, @NonNull ArrayList<Vertex> objects) {
        super(context, resource, objects);

        this.objects = objects;
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_stop, parent, false);

        TextView id = (TextView) itemView.findViewById(R.id.tvStopIds);
        id.setText(String.valueOf(objects.get(position).getId()));

        TextView address = (TextView) itemView.findViewById(R.id.tvAddress);
        address.setText(objects.get(position).getAddress());

        TextView x = (TextView) itemView.findViewById(R.id.tvX);
        x.setText(String.valueOf(objects.get(position).getX()));

        TextView y = (TextView) itemView.findViewById(R.id.tvY);
        y.setText(String.valueOf(objects.get(position).getY()));

        return itemView;
    }

    // It gets a View that displays in the drop down popup the data at the specified position
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    // It gets a View that displays the data at the specified position
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }
}
