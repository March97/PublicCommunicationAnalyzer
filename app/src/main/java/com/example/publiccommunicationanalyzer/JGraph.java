package com.example.publiccommunicationanalyzer;

import org.jgrapht.Graph;
import org.jgrapht.alg.scoring.BetweennessCentrality;
import org.jgrapht.alg.scoring.ClosenessCentrality;
import org.jgrapht.alg.scoring.HarmonicCentrality;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.jgrapht.graph.WeightedMultigraph;

import java.util.ArrayList;

public class JGraph {

    DirectedWeightedMultigraph<String, DefaultWeightedEdge> graphTime = new DirectedWeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    DirectedWeightedMultigraph<String, DefaultWeightedEdge> graphDistance = new DirectedWeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
//    WeightedMultigraph<String, DefaultWeightedEdge> graphTime = new WeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
//    WeightedMultigraph<String, DefaultWeightedEdge> graphDistance = new WeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    //Bliskość
    ClosenessCentrality<String, DefaultWeightedEdge> cT;
    ClosenessCentrality<String, DefaultWeightedEdge> cD;
    //Pośrednictwo
    BetweennessCentrality<String, DefaultWeightedEdge> bT;
    BetweennessCentrality<String, DefaultWeightedEdge> bD;
    //Harmonic
    HarmonicCentrality<String, DefaultWeightedEdge> hT;
    HarmonicCentrality<String, DefaultWeightedEdge> hD;

    public ClosenessCentrality<String, DefaultWeightedEdge> getcT() {
        return cT;
    }

    public ClosenessCentrality<String, DefaultWeightedEdge> getcD() {
        return cD;
    }

    public BetweennessCentrality<String, DefaultWeightedEdge> getbT() {
        return bT;
    }

    public BetweennessCentrality<String, DefaultWeightedEdge> getbD() {
        return bD;
    }

    public HarmonicCentrality<String, DefaultWeightedEdge> gethT() {
        return hT;
    }

    public HarmonicCentrality<String, DefaultWeightedEdge> gethD() {
        return hD;
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
        cT = new ClosenessCentrality<>(graphTime);
        cD = new ClosenessCentrality<>(graphDistance);

        //Pośrednictwo
        bT = new BetweennessCentrality<>(graphTime);
        bD = new BetweennessCentrality<>(graphDistance);

        //Harmonic
        hT = new HarmonicCentrality<>(graphTime);
        hD = new HarmonicCentrality<>(graphDistance);
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
            System.out.println("Stopien: " + graphTime.degreeOf(graphTime.getEdgeSource(e)) + " Bliskość: " + cT.getVertexScore(graphTime.getEdgeSource(e)) + " Pośrednictwo: " + bT.getVertexScore(graphTime.getEdgeSource(e)) + " Harmonia: " + hT.getVertexScore(graphTime.getEdgeSource(e))) ;
        }
    }

    public void clearGraph() {
        graphTime = new DirectedWeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        graphDistance = new DirectedWeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

//        graphTime = new WeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
//        graphDistance = new WeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    }
}