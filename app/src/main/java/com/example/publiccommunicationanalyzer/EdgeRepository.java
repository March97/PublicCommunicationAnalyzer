package com.example.publiccommunicationanalyzer;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;

public class EdgeRepository {

    private EdgeDao edgeDao;
    private LiveData<List<Edge>> listEdge;

    public EdgeRepository(Application application) {
        GraphDatabase db = GraphDatabase.getInstance(application);
        edgeDao = db.edgeDao();
        listEdge = edgeDao.getAll();
    }

    public void insert(Edge edge) {
        new EdgeRepository.InsertEdgeAsyncTask(edgeDao).execute(edge);
    }

    public void update(Edge edge) {
        new EdgeRepository.UpdateEdgeAsyncTask(edgeDao).execute(edge);
    }

    public void delete(Edge edge) {
        new EdgeRepository.DeleteEdgeAsyncTask(edgeDao).execute(edge);
    }

    public void deleteAll() {
        new EdgeRepository.DeleteAllEdgeAsyncTask(edgeDao).execute();
    }

    public LiveData<List<Edge>> getListEdge() {
        return listEdge;
    }

    private static class InsertEdgeAsyncTask extends AsyncTask<Edge, Void, Void> {

        private EdgeDao edgeDao;
        private InsertEdgeAsyncTask(EdgeDao edgeDao) {
            this.edgeDao = edgeDao;
        }

        @Override
        protected Void doInBackground(Edge... edges) {
            edgeDao.insert(edges[0]);
            return null;
        }
    }

    private static class UpdateEdgeAsyncTask extends AsyncTask<Edge, Void, Void> {

        private EdgeDao edgeDao;
        private UpdateEdgeAsyncTask(EdgeDao edgeDao) {
            this.edgeDao = edgeDao;
        }

        @Override
        protected Void doInBackground(Edge... edges) {
            edgeDao.update(edges[0]);
            return null;
        }
    }

    private static class DeleteEdgeAsyncTask extends AsyncTask<Edge, Void, Void> {

        private EdgeDao edgeDao;
        private DeleteEdgeAsyncTask(EdgeDao edgeDao) {
            this.edgeDao = edgeDao;
        }

        @Override
        protected Void doInBackground(Edge... edges) {
            edgeDao.delete(edges[0]);
            return null;
        }
    }

    private static class DeleteAllEdgeAsyncTask extends AsyncTask<Edge, Void, Void> {

        private EdgeDao edgeDao;
        private DeleteAllEdgeAsyncTask(EdgeDao edgeDao) {
            this.edgeDao = edgeDao;
        }

        @Override
        protected Void doInBackground(Edge... edges) {
            edgeDao.deleteAll();
            return null;
        }
    }
}
