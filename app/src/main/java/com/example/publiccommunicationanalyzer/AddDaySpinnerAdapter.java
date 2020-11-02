package com.example.publiccommunicationanalyzer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class AddDaySpinnerAdapter  extends ArrayAdapter {
    
    //String[] objects;
    ArrayList<String> objects;

    public AddDaySpinnerAdapter(@NonNull Context context, int resource, @NonNull ArrayList<String> objects) {
        super(context, resource, objects);

        this.objects = objects;
    }

    public View getCustomView(int position, View convertView,
                              ViewGroup parent) {

        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_day, parent, false);

        TextView day = (TextView) itemView.findViewById(R.id.tvDay);
        day.setText(objects.get(position));

        TextView dayDescription = (TextView) itemView.findViewById(R.id.tvDayDescription);
        //{"DP", "DS", "N7", "NO", "NP", "NS", "SB"};
        if(objects.get(position).contains("DP"))
            dayDescription.setText(R.string.DP);
        else if(objects.get(position).contains("DS"))
            dayDescription.setText(R.string.DS);
        else if(objects.get(position).contains("NO"))
            dayDescription.setText(R.string.NO);
        else if(objects.get(position).contains("NP"))
            dayDescription.setText(R.string.NP);
        else if(objects.get(position).contains("NS"))
            dayDescription.setText(R.string.NS);
        else if(objects.get(position).contains("SB"))
            dayDescription.setText(R.string.SB);
        else if(objects.get(position).contains("N"))
            dayDescription.setText(R.string.N1);
        else if(objects.get(position).contains("D"))
            dayDescription.setText(R.string.D1);
        else
            dayDescription.setText("");

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
