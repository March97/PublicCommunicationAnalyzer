package com.example.publiccommunicationanalyzer;

import org.jgrapht.Graph;
import org.jgrapht.alg.scoring.ClosenessCentrality;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedGraph;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.DirectedWeightedMultigraph;
import org.jgrapht.graph.SimpleDirectedWeightedGraph;

import java.util.ArrayList;

public class JGraph {

    DirectedWeightedMultigraph<String, DefaultWeightedEdge> graphTime = new DirectedWeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    DirectedWeightedMultigraph<String, DefaultWeightedEdge> graphDistance = new DirectedWeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);

    Graph<String, DefaultEdge> graph = new DefaultUndirectedGraph<String, DefaultEdge>(DefaultEdge.class);

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
                if (edge.getV1() != 0) {
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
        ClosenessCentrality<String, DefaultWeightedEdge> c = new ClosenessCentrality<>(graphTime);
        for (DefaultWeightedEdge e : graphTime.edgeSet()) {
            i++;
            System.out.println(i + ". " + graphTime.getEdgeSource(e) + " --> " + graphTime.getEdgeTarget(e) + " " + graphTime.getEdgeWeight(e));
            System.out.println("Stopien: " + graphTime.inDegreeOf(graphTime.getEdgeSource(e)) + " Bliskość: " + c.getVertexScore(graphTime.getEdgeSource(e))) ;
        }
    }

    public void clearGraph() {
        graphTime = new DirectedWeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
        graphDistance = new DirectedWeightedMultigraph<String, DefaultWeightedEdge>(DefaultWeightedEdge.class);
    }
}