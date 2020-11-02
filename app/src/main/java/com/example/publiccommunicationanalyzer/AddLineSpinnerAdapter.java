package com.example.publiccommunicationanalyzer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class AddLineSpinnerAdapter extends ArrayAdapter {

    //String[] objects;
    ArrayList<String> objects;

    public AddLineSpinnerAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);

        this.objects = objects;
    }

    public View getCustomView(int position, View convertView,
                              ViewGroup parent) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_line, parent, false);

        TextView lineNumber = (TextView) itemView.findViewById(R.id.tvLine);
        lineNumber.setText(objects.get(position));

        ImageView img = (ImageView) itemView.findViewById(R.id.ivLine);

        if (objects.get(position).contains("S") || objects.get(position).contains("R")
                || objects.get(position).contains("W"))
            img.setImageResource(R.drawable.ic_baseline_train_24);
        else if (objects.get(position).contains("L"))
            img.setImageResource(R.drawable.ic_baseline_directions_bus_24);
        else if (objects.get(position).contains("N"))
            img.setImageResource(R.drawable.ic_baseline_directions_bus_24);
        else if (objects.get(position).contains("C1") || objects.get(position).contains("C4")
                || objects.get(position).contains("C6"))
            img.setImageResource(R.drawable.ic_baseline_tram_24);
        else if (objects.get(position).contains("C"))
            img.setImageResource(R.drawable.ic_baseline_directions_bus_24);
        else if (objects.get(position).contains("E"))
            img.setImageResource(R.drawable.ic_baseline_directions_bus_24);
        else if (objects.get(position).contains("M"))
            img.setImageResource(R.drawable.ic_baseline_subway_24);
        else if (Integer.parseInt(objects.get(position)) < 100)
            img.setImageResource(R.drawable.ic_baseline_tram_24);
        else
            img.setImageResource(R.drawable.ic_baseline_directions_bus_24);

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
