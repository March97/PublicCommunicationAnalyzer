package com.example.publiccommunicationanalyzer;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;

import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.alg.scoring.ClosenessCentrality;
import org.jgrapht.alg.scoring.HarmonicCentrality;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class InfoVertexAdapter implements GoogleMap.InfoWindowAdapter {

    Context context;
    private View view;
    private String name, vicinity;

    private Double cT;
    private Double cD;
    //Pośrednictwo
    private Double bT;
    private Double bD;
    //Harmonic
    private Double hT;
    private Double hD;

    private final JGraph jGraph = new JGraph();

    public InfoVertexAdapter(@NonNull Context context, ArrayList<ArrayList<Vertex>> vertices, ArrayList<ArrayList<Edge>> edges) {
        jGraph.setGraph(vertices, edges);
        this.context = context;

    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = LayoutInflater.from(context).inflate(R.layout.info_vertex, null);

        TextView id = (TextView) view.findViewById(R.id.tvIdVertex);
        id.setText("ID: " + marker.getTitle());

        TextView dt = (TextView) view.findViewById(R.id.tvDegreeTime);
        dt.setText(String.valueOf(jGraph.graphTime.degreeOf(marker.getTitle())));

        TextView dd = (TextView) view.findViewById(R.id.tvDegreeDistance);
        dd.setText(String.valueOf(jGraph.graphDistance.degreeOf(marker.getTitle())));

        TextView cTt = (TextView) view.findViewById(R.id.tvCloseTime);
        cT = jGraph.getcT().getVertexScore(marker.getTitle());
        cTt.setText(String.valueOf(cT));

        TextView cDt = (TextView) view.findViewById(R.id.tvCloseDistance);
        cD = jGraph.getcD().getVertexScore(marker.getTitle());
        cDt.setText(String.valueOf(cD));

        TextView bTt = (TextView) view.findViewById(R.id.tvBetweenTime);
        bT = jGraph.getbT().getVertexScore(marker.getTitle());
        bTt.setText(String.valueOf(bT));

        TextView bDt = (TextView) view.findViewById(R.id.tvBetweenDistance);
        bD = jGraph.getbD().getVertexScore(marker.getTitle());
        bDt.setText(String.valueOf(bD));

        TextView hTt = (TextView) view.findViewById(R.id.tvHarmonyTime);
        hT = jGraph.gethT().getVertexScore(marker.getTitle());
        hTt.setText(String.valueOf(hT));

        TextView hDt = (TextView) view.findViewById(R.id.tvHarmonyDistance);
        hD = jGraph.gethD().getVertexScore(marker.getTitle());
        hDt.setText(String.valueOf(hD));

        return view;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        // TODO Auto-generated method stub
        return null;
    }

}