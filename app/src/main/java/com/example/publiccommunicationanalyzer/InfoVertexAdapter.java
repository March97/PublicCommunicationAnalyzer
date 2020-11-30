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
import java.text.NumberFormat;
import java.util.ArrayList;

public class InfoVertexAdapter implements GoogleMap.InfoWindowAdapter {

    Context context;
    private View view;
    private String name, vicinity;

    private  Double aT;
    private  Double aD;

    private Double cT;
    private Double cD;
    //Pośrednictwo
    private Double bT;
    private Double bD;
    //Harmonic
    private Double hT;
    private Double hD;
    //wektory wlasne
    private Double prT;
    private Double prD;

    private final JGraph jGraph;

//    public InfoVertexAdapter(@NonNull Context context, ArrayList<ArrayList<Vertex>> vertices, ArrayList<ArrayList<Edge>> edges) {
//        jGraph.setGraph(vertices, edges);
//        this.context = context;
//
//    }

    public InfoVertexAdapter(@NonNull Context context, JGraph jGraph) {
        this.context = context;
        this.jGraph = jGraph;
    }

    @Override
    public View getInfoContents(Marker marker) {
        View view = LayoutInflater.from(context).inflate(R.layout.info_vertex, null);

        TextView id = (TextView) view.findViewById(R.id.tvIdVertex);
        id.setText("ID: " + marker.getTitle());

        TextView dt = (TextView) view.findViewById(R.id.tvDegreeTime);
        dt.setText(String.valueOf(jGraph.getGraphTime().degreeOf(marker.getTitle())));

        NumberFormat formatter = new DecimalFormat("0.###E0");
        TextView aTt = (TextView) view.findViewById(R.id.tvAlphaTime);
        aT = jGraph.getaT().getVertexScore(marker.getTitle());
        aTt.setText(formatter.format(aT));

        TextView aDt = (TextView) view.findViewById(R.id.tvAlphaDistance);
        aD = jGraph.getaD().getVertexScore(marker.getTitle());
        aDt.setText(formatter.format(aD));

        TextView cTt = (TextView) view.findViewById(R.id.tvCloseTime);
        cT = jGraph.getcT().getVertexScore(marker.getTitle());
        cTt.setText(formatter.format(cT));

        TextView cDt = (TextView) view.findViewById(R.id.tvCloseDistance);
        cD = jGraph.getcD().getVertexScore(marker.getTitle());
        cDt.setText(formatter.format(cD));

        TextView bTt = (TextView) view.findViewById(R.id.tvBetweenTime);
        bT = jGraph.getbT().getVertexScore(marker.getTitle());
        bTt.setText(formatter.format(bT));

        TextView bDt = (TextView) view.findViewById(R.id.tvBetweenDistance);
        bD = jGraph.getbD().getVertexScore(marker.getTitle());
        bDt.setText(formatter.format(bD));

        TextView hTt = (TextView) view.findViewById(R.id.tvHarmonyTime);
        hT = jGraph.gethT().getVertexScore(marker.getTitle());
        hTt.setText(formatter.format(hT));

        TextView hDt = (TextView) view.findViewById(R.id.tvHarmonyDistance);
        hD = jGraph.gethD().getVertexScore(marker.getTitle());
        hDt.setText(formatter.format(hD));

        TextView prTt = (TextView) view.findViewById(R.id.tvPageRankTime);
        prT = jGraph.getPrT().getVertexScore(marker.getTitle());
        prTt.setText(formatter.format(prT));

        TextView prDt = (TextView) view.findViewById(R.id.tvPageRankDistance);
        prD = jGraph.getPrD().getVertexScore(marker.getTitle());
        prDt.setText(formatter.format(prD));

        System.out.println("Alpha time: " + jGraph.getaT().getVertexScore(marker.getTitle()));
        System.out.println("Alpha distance: " + jGraph.getaD().getVertexScore(marker.getTitle()));

        return view;
    }

    @Override
    public View getInfoWindow(Marker marker) {
        // TODO Auto-generated method stub
        return null;
    }

}