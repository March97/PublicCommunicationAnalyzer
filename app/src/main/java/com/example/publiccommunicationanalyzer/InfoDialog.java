package com.example.publiccommunicationanalyzer;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialogFragment;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class InfoDialog extends AppCompatDialogFragment {

    private JGraph jGraph;
    private List<String> selectedServices;

    public InfoDialog(JGraph jGraph, ArrayList<String> selectedServices) {
        this.jGraph = jGraph;
        this.selectedServices = selectedServices;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        View view = inflater.inflate(R.layout.info_dialog, null);

        builder.setView(view)
                .setTitle(R.string.dialog_info)
                .setPositiveButton("wyjdź", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                    }
                });

        TextView verticesAmount = (TextView) view.findViewById(R.id.tvVerticesAmount);
        verticesAmount.setText(String.valueOf(jGraph.getGraphTime().vertexSet().size()));

        TextView edgesAmount = (TextView) view.findViewById(R.id.tvEdgesAmount);
        edgesAmount.setText(String.valueOf(jGraph.getGraphTime().edgeSet().size()));

        TextView maxDegree = (TextView) view.findViewById(R.id.tvMaxDegree);
        maxDegree.setText(jGraph.maxDegree());

        TextView maxAlphaTime = (TextView) view.findViewById(R.id.tvALphaTimeMax);
        maxAlphaTime.setText(jGraph.getMax(jGraph.getaT().getScores()));

//        TextView minAlphaTime = (TextView) view.findViewById(R.id.tvAlphaTimeMin);
//        minAlphaTime.setText(jGraph.getMin(jGraph.getaT().getScores()));

//        TextView maxCloseTime = (TextView) view.findViewById(R.id.tvCloseTimeMax);
//        maxCloseTime.setText(jGraph.getMax(jGraph.getcT().getScores()));

//        TextView minCloseTime = (TextView) view.findViewById(R.id.tvCloseTimeMin);
//        minCloseTime.setText(jGraph.getMin(jGraph.getcT().getScores()));

        TextView maxBetweenTime = (TextView) view.findViewById(R.id.tvBetweenTimeMax);
        maxBetweenTime.setText(jGraph.getMax(jGraph.getbT().getScores()));

//        TextView minBetweeneTime = (TextView) view.findViewById(R.id.tvBetweenTimeMin);
//        minBetweeneTime.setText(jGraph.getMin(jGraph.getbT().getScores()));

//        TextView maxHarmonyTime = (TextView) view.findViewById(R.id.tvHarmonyTimeMax);
//        maxHarmonyTime.setText(jGraph.getMax(jGraph.gethT().getScores()));

//        TextView minHarmonyTime = (TextView) view.findViewById(R.id.tvHarmonyTimeMin);
//        minHarmonyTime.setText(jGraph.getMin(jGraph.gethT().getScores()));

        TextView maxPageRankTime = (TextView) view.findViewById(R.id.tvPageRankTimeMax);
        maxPageRankTime.setText(jGraph.getMax(jGraph.getPrT().getScores()));

//        TextView minPageRankTime = (TextView) view.findViewById(R.id.tvPageRankTimeMin);
//        minPageRankTime.setText(jGraph.getMin(jGraph.getPrT().getScores()));

        TextView maxAlphaDistance = (TextView) view.findViewById(R.id.tvAlphaDistanceMax);
        maxAlphaDistance.setText(jGraph.getMax(jGraph.getaD().getScores()));

//        TextView minAlphaDistance = (TextView) view.findViewById(R.id.tvAlphaDistanceMin);
//        minAlphaDistance.setText(jGraph.getMin(jGraph.getaD().getScores()));

//        TextView maxCloseDistance = (TextView) view.findViewById(R.id.tvCloseDistanceMax);
//        maxCloseDistance.setText(jGraph.getMax(jGraph.getcD().getScores()));

//        TextView minCloseDistance = (TextView) view.findViewById(R.id.tvCloseDistanceMin);
//        minCloseDistance.setText(jGraph.getMin(jGraph.getcD().getScores()));

        TextView maxBetweenDistance = (TextView) view.findViewById(R.id.tvBetweenDistanceMax);
        maxBetweenDistance.setText(jGraph.getMax(jGraph.getbD().getScores()));

//        TextView minBetweenDistance = (TextView) view.findViewById(R.id.tvBetweenDistanceMin);
//        minBetweenDistance.setText(jGraph.getMin(jGraph.getbD().getScores()));

//        TextView maxHarmonyDistance = (TextView) view.findViewById(R.id.tvHarmonyDistanceMax);
//        maxHarmonyDistance.setText(jGraph.getMax(jGraph.gethD().getScores()));

//        TextView minHarmonyDistance = (TextView) view.findViewById(R.id.tvHarmonyDistanceMin);
//        minHarmonyDistance.setText(jGraph.getMin(jGraph.gethD().getScores()));

        TextView maxPageRankDistance = (TextView) view.findViewById(R.id.tvPageRankDistanceMax);
        maxPageRankDistance.setText(jGraph.getMax(jGraph.getPrD().getScores()));

//        TextView minPageRankDistance = (TextView) view.findViewById(R.id.tvPageRankDistanceMin);
//        minPageRankDistance.setText(jGraph.getMin(jGraph.getPrD().getScores()));

        System.out.println("SERVICES SIZE: " + selectedServices.size());

        ListView lvServices = (ListView) view.findViewById(R.id.lvServices);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getContext(), R.layout.item_service_info, selectedServices);
//        for (String s : selectedServices) {
//            arrayAdapter.add(s);
//        }
        System.out.println("ADAPTER SIZE: " + arrayAdapter.getCount());
        lvServices.setAdapter(arrayAdapter);
        setListViewHeightBasedOnItems(lvServices);

        return builder.create();
    }

    private static boolean setListViewHeightBasedOnItems(ListView listView) {

        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter != null) {

            int numberOfItems = listAdapter.getCount();

            // Get total height of all items.
            int totalItemsHeight = 0;
            for (int itemPos = 0; itemPos < numberOfItems; itemPos++) {
                View item = listAdapter.getView(itemPos, null, listView);
                float px = 500 * (listView.getResources().getDisplayMetrics().density);
                item.measure(View.MeasureSpec.makeMeasureSpec((int) px, View.MeasureSpec.AT_MOST),
                        View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED));
                totalItemsHeight += item.getMeasuredHeight();
            }

            // Get total height of all item dividers.
            int totalDividersHeight = listView.getDividerHeight() * (numberOfItems - 1);
            // Get padding
            int totalPadding = listView.getPaddingTop() + listView.getPaddingBottom();

            // Set list height.
            ViewGroup.LayoutParams params = listView.getLayoutParams();
            params.height = totalItemsHeight + totalDividersHeight + totalPadding;
            listView.setLayoutParams(params);
            listView.requestLayout();
            //setDynamicHeight(listView);
            return true;

        } else {
            return false;
        }
    }
}
