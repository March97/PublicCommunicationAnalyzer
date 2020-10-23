package com.example.publiccommunicationanalyzer;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class VertexRepository {

    private VertexDao vertexDao;
    private LiveData<List<Vertex>> listVertex;

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
