package com.example.publiccommunicationanalyzer;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class EdgeRepository {

    private EdgeDao edgeDao;
    private LiveData<List<Edge>> listEdge;
    private LiveData<List<String>> lines;
    private LiveData<List<String>> days;
    private LiveData<List<String>> services;
    private LiveData<List<Edge>> selectedEdges;

    public EdgeRepository(Application application) throws ExecutionException, InterruptedException {
        GraphDatabase db = GraphDatabase.getInstance(application);
        edgeDao = db.edgeDao();
        listEdge = edgeDao.getAll();
        //lines = edgeDao.getLines();
        lines = getLines();
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

    public LiveData<List<String>> getLines() throws ExecutionException, InterruptedException {
        return new EdgeRepository.GetLinesAsyncTask(edgeDao).execute().get();
        //return lines;
    }

    public LiveData<List<String>> getDays(String line) throws ExecutionException, InterruptedException {
//        days = edgeDao.getDays(line);
//        return days;
        return new EdgeRepository.GetDaysAsyncTask(edgeDao).execute(line).get();
    }

    public LiveData<List<String>> getServices(String line, String day) throws ExecutionException, InterruptedException {
//        services = edgeDao.getServiceByLineDay(line, day);
//        return services;
        return new EdgeRepository.GetServicesAsyncTask(edgeDao).execute(line, day).get();
    }

    public LiveData<List<Edge>> getSelectedEdges(String service) throws ExecutionException, InterruptedException {
//        selectedEdges = edgeDao.getEdgesByService(service);
//        return selectedEdges;
        return new EdgeRepository.GetSelectedEdgesAsyncTask(edgeDao).execute(service).get();
    }

    private static class GetLinesAsyncTask extends AsyncTask<Void, Void, LiveData<List<String>>> {

        private EdgeDao edgeDao;
        private GetLinesAsyncTask(EdgeDao edgeDao) {
            this.edgeDao = edgeDao;
        }

        @Override
        protected LiveData<List<String>> doInBackground(Void... voids) {
            return edgeDao.getLines();
        }
    }

    private static class GetDaysAsyncTask extends AsyncTask<String, Void, LiveData<List<String>>> {

        private EdgeDao edgeDao;
        private GetDaysAsyncTask(EdgeDao edgeDao) {
            this.edgeDao = edgeDao;
        }

        @Override
        protected LiveData<List<String>> doInBackground(String... strings) {
            return edgeDao.getDays(strings[0]);
        }
    }

    private static class GetServicesAsyncTask extends AsyncTask<String, Void, LiveData<List<String>>> {

        private EdgeDao edgeDao;
        private GetServicesAsyncTask(EdgeDao edgeDao) {
            this.edgeDao = edgeDao;
        }

        @Override
        protected LiveData<List<String>> doInBackground(String... strings) {
            return edgeDao.getServiceByLineDay(strings[0], strings[1]);
        }
    }

    private static class GetSelectedEdgesAsyncTask extends AsyncTask<String, Void, LiveData<List<Edge>>> {

        private EdgeDao edgeDao;
        private GetSelectedEdgesAsyncTask(EdgeDao edgeDao) {
            this.edgeDao = edgeDao;
        }

        @Override
        protected LiveData<List<Edge>> doInBackground(String... strings) {
            return edgeDao.getEdgesByService(strings[0]);
        }
    }
//
//    public void setLines(LiveData<List<String>> mLines) {
//        lines = mLines;
//    }

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

//    private static class getLinesAsyncTask extends AsyncTask<Edge, Void, Void> {
//
//        private EdgeDao edgeDao;
//        private getLinesAsyncTask(EdgeDao edgeDao) {
//            this.edgeDao = edgeDao;
//        }
//
//        @Override
//        protected Void doInBackground(Edge... edges) {
//            lines = edgeDao.getLines();
//            return null;
//        }
//    }
//
//    private static class getDaysAsyncTask extends AsyncTask<Edge, Void, Void> {
//
//        private EdgeDao edgeDao;
//        private getDaysAsyncTask(EdgeDao edgeDao) {
//            this.edgeDao = edgeDao;
//        }
//
//        @Override
//        protected Void doInBackground(Edge... edges) {
//            edgeDao.getDays();
//            return null;
//        }
//    }
//
//    private static class getServiceByLineDayEdgeAsyncTask extends AsyncTask<Edge, Void, Void> {
//
//        private EdgeDao edgeDao;
//        private getServiceByLineDayEdgeAsyncTask(EdgeDao edgeDao) {
//            this.edgeDao = edgeDao;
//        }
//
//        @Override
//        protected Void doInBackground(Edge... edges) {
//            edgeDao.deleteAll();
//            return null;
//        }
//    }
}