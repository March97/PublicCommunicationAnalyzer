package com.example.publiccommunicationanalyzer;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class VertexRepository {

    private VertexDao vertexDao;
    private LiveData<List<Vertex>> listVertex;
    private LiveData<Vertex> vertex;

    public VertexRepository(Application application) {
        GraphDatabase db = GraphDatabase.getInstance(application);
        vertexDao = db.vertexDao();
        listVertex = vertexDao.getAll();
    }

    public void insert(Vertex vertex) {
        new VertexRepository.InsertVertexAsyncTask(vertexDao).execute(vertex);
    }

    public void update(Vertex vertex) {
        new VertexRepository.UpdateVertexAsyncTask(vertexDao).execute(vertex);
    }

    public void delete(Vertex vertex) {
        new VertexRepository.DeleteVertexAsyncTask(vertexDao).execute(vertex);
    }

    public void deleteAll() {
        new VertexRepository.DeleteAllVertexAsyncTask(vertexDao).execute();
    }

    public LiveData<List<Vertex>> getListVertex() {
        return listVertex;
    }

    public LiveData<Vertex> getVertex(int id) throws ExecutionException, InterruptedException {
        return new VertexRepository.GetVertexAsyncTask(vertexDao).execute(id).get();
    }

    public LiveData<List<String>> getAddresses() throws ExecutionException, InterruptedException {
        return new  VertexRepository.GetAddressesAsyncTask(vertexDao).execute().get();
    }

    private static class GetAddressesAsyncTask extends AsyncTask<Void, Void, LiveData<List<String>>> {

        private VertexDao vertexDao;
        private GetAddressesAsyncTask(VertexDao vertexDao) {
            this.vertexDao = vertexDao;
        }

        @Override
        protected LiveData<List<String>> doInBackground(Void... voids) {
            return vertexDao.getAddresses();
        }
    }

    public LiveData<List<Vertex>> getByAddresses(String address) throws ExecutionException, InterruptedException {
        return new  VertexRepository.GetByAddressesAsyncTask(vertexDao).execute(address).get();
    }

    private static class GetByAddressesAsyncTask extends AsyncTask<String, Void, LiveData<List<Vertex>>> {

        private VertexDao vertexDao;
        private GetByAddressesAsyncTask(VertexDao vertexDao) {
            this.vertexDao = vertexDao;
        }

        @Override
        protected LiveData<List<Vertex>> doInBackground(String... addresses) {
            return vertexDao.getByAddresses(addresses[0]);
        }
    }

    private static class GetVertexAsyncTask extends AsyncTask<Integer, Void, LiveData<Vertex>> {

        private VertexDao vertexDao;
        private GetVertexAsyncTask(VertexDao vertexDao) {
            this.vertexDao = vertexDao;
        }

        @Override
        protected LiveData<Vertex> doInBackground(Integer... integers) {
            return vertexDao.getVertex(integers[0]);
        }
    }

    private static class InsertVertexAsyncTask extends AsyncTask<Vertex, Void, Void> {

        private VertexDao vertexDao;
        private InsertVertexAsyncTask(VertexDao vertexDao) {
            this.vertexDao = vertexDao;
        }

        @Override
        protected Void doInBackground(Vertex... vertices) {
            vertexDao.insert(vertices[0]);
            return null;
        }
    }

    private static class UpdateVertexAsyncTask extends AsyncTask<Vertex, Void, Void> {

        private VertexDao vertexDao;
        private UpdateVertexAsyncTask(VertexDao vertexDao) {
            this.vertexDao = vertexDao;
        }

        @Override
        protected Void doInBackground(Vertex... vertices) {
            vertexDao.update(vertices[0]);
            return null;
        }
    }

    private static class DeleteVertexAsyncTask extends AsyncTask<Vertex, Void, Void> {

        private VertexDao vertexDao;
        private DeleteVertexAsyncTask(VertexDao vertexDao) {
            this.vertexDao = vertexDao;
        }

        @Override
        protected Void doInBackground(Vertex... vertices) {
            vertexDao.delete(vertices[0]);
            return null;
        }
    }

    private static class DeleteAllVertexAsyncTask extends AsyncTask<Vertex, Void, Void> {

        private VertexDao vertexDao;
        private DeleteAllVertexAsyncTask(VertexDao vertexDao) {
            this.vertexDao = vertexDao;
        }

        @Override
        protected Void doInBackground(Vertex... vertices) {
            vertexDao.deleteAll();
            return null;
        }
    }
}
