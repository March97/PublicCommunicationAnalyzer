package com.example.publiccommunicationanalyzer;

import org.jgrapht.Graph;
import org.jgrapht.alg.scoring.AlphaCentrality;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.alg.scoring.ClosenessCentrality;
import org.jgrapht.alg.scoring.HarmonicCentrality;
import org.jgrapht.alg.scoring.PageRank;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.jgrapht.graph.WeightedMultigraph;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class JGraph {

    private DirectedWeightedMultigraph<String, DefaultWeightedEdge> graphTime = new DirectedWeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    private DirectedWeightedMultigraph<String, DefaultWeightedEdge> graphDistance = new DirectedWeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
//    WeightedMultigraph<String, DefaultWeightedEdge> graphTime = new WeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
//    WeightedMultigraph<String, DefaultWeightedEdge> graphDistance = new WeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    //Bliskość
//    private ClosenessCentrality<String, DefaultWeightedEdge> cT;
//    private ClosenessCentrality<String, DefaultWeightedEdge> cD;
    //Pośrednictwo
    private BetweennessCentrality<String, DefaultWeightedEdge> bT;
    private BetweennessCentrality<String, DefaultWeightedEdge> bD;
    //Harmonic
//    private HarmonicCentrality<String, DefaultWeightedEdge> hT;
//    private HarmonicCentrality<String, DefaultWeightedEdge> hD;

    private PageRank<String, DefaultWeightedEdge> prT;
    private PageRank<String, DefaultWeightedEdge> prD;

    private AlphaCentrality<String, DefaultWeightedEdge> aT;
    private AlphaCentrality<String, DefaultWeightedEdge> aD;

    private Map<String, Double> closenessCentralityTimeMap;
    private  Map<String, Double> closenessCentralityDistanceMap;
    private Map<String, Double> betweennessCentralityTimeMap;
    private  Map<String, Double> betweennessCentralityDistanceMap;
    private Map<String, Double> harmonicCentralityTimeMap;
    private  Map<String, Double> harmonicCentralityDistanceMap;
    private Map<String, Double> pageRankTimeMap;
    private Map<String, Double> pageRankDistanceMap;

//    public ClosenessCentrality<String, DefaultWeightedEdge> getcT() {
//        return cT;
//    }
//
//    public ClosenessCentrality<String, DefaultWeightedEdge> getcD() {
//        return cD;
//    }

    public BetweennessCentrality<String, DefaultWeightedEdge> getbT() {
        return bT;
    }

    public BetweennessCentrality<String, DefaultWeightedEdge> getbD() {
        return bD;
    }

//    public HarmonicCentrality<String, DefaultWeightedEdge> gethT() {
//        return hT;
//    }
//
//    public HarmonicCentrality<String, DefaultWeightedEdge> gethD() {
//        return hD;
//    }

    public PageRank<String, DefaultWeightedEdge> getPrT() {
        return prT;
    }

    public PageRank<String, DefaultWeightedEdge> getPrD() {
        return prD;
    }

    public AlphaCentrality<String, DefaultWeightedEdge> getaT() {
        return aT;
    }

    public AlphaCentrality<String, DefaultWeightedEdge> getaD() {
        return aD;
    }

    public DirectedWeightedMultigraph<String, DefaultWeightedEdge> getGraphTime() {
        return graphTime;
    }

    public DirectedWeightedMultigraph<String, DefaultWeightedEdge> getGraphDistance() {
        return graphDistance;
    }

//    public Map<String, Double> getClosenessCentralityTimeMap() {
//        return closenessCentralityTimeMap;
//    }
//
//    public Map<String, Double> getClosenessCentralityDistanceMap() {
//        return closenessCentralityDistanceMap;
//    }

    public Map<String, Double> getBetweennessCentralityTimeMap() {
        return betweennessCentralityTimeMap;
    }

    public Map<String, Double> getBetweennessCentralityDistanceMap() {
        return betweennessCentralityDistanceMap;
    }

//    public Map<String, Double> getHarmonicCentralityTimeMap() {
//        return harmonicCentralityTimeMap;
//    }
//
//    public Map<String, Double> getHarmonicCentralityDistanceMap() {
//        return harmonicCentralityDistanceMap;
//    }

    public Map<String, Double> getPageRankTimeMap() {
        return pageRankTimeMap;
    }

    public Map<String, Double> getPageRankDistanceMap() {
        return pageRankDistanceMap;
    }

    private void addVertices(ArrayList<ArrayList<Vertex>> vertices) {
        for (ArrayList<Vertex> vertexList : vertices) {
            for (Vertex vertex : vertexList) {
                if (!graphTime.containsVertex(String.valueOf(vertex.getId())))
                    graphTime.addVertex(String.valueOf(vertex.getId()));

                if (!graphDistance.containsVertex(String.valueOf(vertex.getId())))
                    graphDistance.addVertex(String.valueOf(vertex.getId()));
            }
        }
    }

    private void addEdges(ArrayList<ArrayList<Vertex>> vertices, ArrayList<ArrayList<Edge>> edges) {
        for (int j = 0; j < vertices.size(); j++) {
            for (int k = 0; k < edges.get(j).size(); k++) {
                Edge edge = new Edge(edges.get(j).get(k));
                if (edge.getV1() != 0 && edge.getV1() != edge.getV2() ) {
                    graphTime.addEdge(String.valueOf(edge.getV1()), String.valueOf(edge.getV2()));
                    graphTime.setEdgeWeight(String.valueOf(edge.getV1()), String.valueOf(edge.getV2()), edge.getTime_from_last());
                    graphDistance.addEdge(String.valueOf(edge.getV1()), String.valueOf(edge.getV2()));
                    graphDistance.setEdgeWeight(String.valueOf(edge.getV1()), String.valueOf(edge.getV2()), edge.getDistance());
                }
            }
        }
    }

    public void setGraph(ArrayList<ArrayList<Vertex>> vertices, ArrayList<ArrayList<Edge>> edges) {
        clearGraph();
        addVertices(vertices);
        addEdges(vertices, edges);

        //Centralność

        //Bliskość
//        cT = new ClosenessCentrality<>(graphTime);
//        cD = new ClosenessCentrality<>(graphDistance);

        //Pośrednictwo
        bT = new BetweennessCentrality<>(graphTime);
        bD = new BetweennessCentrality<>(graphDistance);

        //Harmonic
//        hT = new HarmonicCentrality<>(graphTime);
//        hD = new HarmonicCentrality<>(graphDistance);

        prT = new PageRank<>(graphTime);
        prD = new PageRank<>(graphDistance);

        aT = new AlphaCentrality<>(graphTime);
        aD = new AlphaCentrality<>(graphDistance);

//        closenessCentralityTimeMap = cT.getScores();
//        closenessCentralityDistanceMap = cD.getScores();
        betweennessCentralityTimeMap = bT.getScores();
        betweennessCentralityDistanceMap = bD.getScores();
//        harmonicCentralityTimeMap = hT.getScores();
//        harmonicCentralityDistanceMap = hD.getScores();
        pageRankTimeMap = prT.getScores();
        pageRankDistanceMap = prD.getScores();
    }

    public void printEdges() {
        System.out.println("GRAPH DISTANCE:");
        int i = 0;
        for (DefaultWeightedEdge e : graphDistance.edgeSet()) {
            i++;
            System.out.println(i + ". " + graphDistance.getEdgeSource(e) + " --> " + graphDistance.getEdgeTarget(e) + " " + graphDistance.getEdgeWeight(e));
        }

        i = 0;
        System.out.println("GRAPH:");

        for (DefaultWeightedEdge e : graphTime.edgeSet()) {
            i++;
            System.out.println(i + ". " + graphTime.getEdgeSource(e) + " --> " + graphTime.getEdgeTarget(e) + " " + graphTime.getEdgeWeight(e));
            System.out.println("Stopien: " + graphTime.degreeOf(graphTime.getEdgeSource(e)) + " Pośrednictwo: " + bT.getVertexScore(graphTime.getEdgeSource(e))) ;
        }
    }

    public void clearGraph() {
        graphTime = new DirectedWeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        graphDistance = new DirectedWeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

//        graphTime = new WeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
//        graphDistance = new WeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    }

    public String getMax(Map<String, Double> map) {

        String key = "";
        double toCompare = 0;
        for(Map.Entry<String, Double> entry : map.entrySet()) {
            if(entry.getValue() >= toCompare) {
                toCompare = entry.getValue();
                key = entry.getKey();
            }
        }
//        Map<String, Double> toReturn = new HashMap<>();
//        toReturn.put(key, toCompare);
        NumberFormat formatter = new DecimalFormat("0.###E0");
        return formatter.format(toCompare) + " : " + key;
    }

    public String getMin(Map<String, Double> map) {

        String key = "";
        double toCompare = 0;

        for(Map.Entry<String, Double> entry : map.entrySet()) {
            if(key.compareTo("") == 0) {
                toCompare = entry.getValue();
                key = entry.getKey();
            }

            if(entry.getValue() < toCompare) {
                toCompare = entry.getValue();
                key = entry.getKey();
            }
        }
//        Map<String, Double> toReturn = new HashMap<>();
//        toReturn.put(key, toCompare);
        NumberFormat formatter = new DecimalFormat("0.###E0");
        return formatter.format(toCompare) + " : " + key;
    }

    public String maxDegree() {

        int degree = 0;
        String key = "";

        for(String vertex : graphTime.vertexSet()) {
            if(graphTime.degreeOf(vertex) >= degree) {
                degree = graphTime.degreeOf(vertex);
                key = vertex;
            }
        }
        return degree + " : " + key;
    }
}