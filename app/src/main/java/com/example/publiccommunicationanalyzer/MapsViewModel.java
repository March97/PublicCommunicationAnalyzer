package com.example.publiccommunicationanalyzer;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class MapsViewModel extends AndroidViewModel {

    private VertexRepository vertexRepository;
    private EdgeRepository edgeRepository;

    private LiveData<List<Vertex>> vertices;
    private LiveData<List<Edge>> edges;
    private LiveData<List<String>> lines;
    private LiveData<List<String>> days;
    private LiveData<List<String>> services;
    private LiveData<List<Edge>> selectedEdges;
    private LiveData<Vertex> vertex;

    public MapsViewModel(@NonNull Application application) throws ExecutionException, InterruptedException {
        super(application);

        vertexRepository = new VertexRepository(application);
        edgeRepository = new EdgeRepository(application);
        //csvReader = new CsvReader();

        vertices = vertexRepository.getListVertex();
        edges = edgeRepository.getListEdge();
        lines = edgeRepository.getLines();
    }

    public void insertVertex(Vertex vertex) {
        vertexRepository.insert(vertex);
    }
    public void updateVertex(Vertex vertex) {
        vertexRepository.update(vertex);
    }
    public void deleteVertex(Vertex vertex) {
        vertexRepository.delete(vertex);
    }
    public void deleteAllVertex() {
        vertexRepository.deleteAll();
    }

    public LiveData<List<Vertex>> getAllVertex() {
        return vertices;
    }

    public void insertEdge(Edge edge) {
        edgeRepository.insert(edge);
    }
    public void updateEdge(Edge edge) {
        edgeRepository.update(edge);
    }
    public void deleteEdge(Edge edge) {
        edgeRepository.delete(edge);
    }
    public void deleteAllEdge() {
        edgeRepository.deleteAll();
    }

    public LiveData<List<Edge>> getAllEdge() {
        return edges;
    }
    public LiveData<List<String>> getLines() throws ExecutionException, InterruptedException {
        return edgeRepository.getLines();
    }

    public LiveData<List<String>> getDays(String line) throws ExecutionException, InterruptedException {
        days = edgeRepository.getDays(line);
        return days;
    }

    public LiveData<List<String>> getServices(String line, String day) throws ExecutionException, InterruptedException {
        services = edgeRepository.getServices(line, day);
        return services;
    }

    public LiveData<List<Edge>> getSelectedEdges(String service) throws ExecutionException, InterruptedException {
        selectedEdges = edgeRepository.getSelectedEdges(service);
        return selectedEdges;
    }

    public LiveData<Vertex> getVertex(int id) throws ExecutionException, InterruptedException {
        vertex = vertexRepository.getVertex(id);
        return vertex;
    }
}