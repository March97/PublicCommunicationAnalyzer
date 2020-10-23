package com.example.publiccommunicationanalyzer;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

@Database(entities = {Edge.class, Vertex.class}, version = 1)
public abstract class GraphDatabase extends RoomDatabase {

    private static GraphDatabase instance;

    public abstract EdgeDao edgeDao();
    public abstract VertexDao vertexDao();

    public static synchronized GraphDatabase getInstance(Context context) {
        if (instance == null) {
//            instance = Room.databaseBuilder(context.getApplicationContext(),
//                    GraphDatabase.class, "graph_database")
//                    .fallbackToDestructiveMigration()
//                    .addCallback(roomCallback)
//                    .build();
            instance = Room.databaseBuilder(context.getApplicationContext(),
                    GraphDatabase.class, "graph_database")
                    .createFromAsset("graph_database.db")
                    .build();

        }
        return instance;
    }

    private static RoomDatabase.Callback roomCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new GraphDatabase.PopulateDbAsyncTask(instance).execute();
        }
    };

    private static class PopulateDbAsyncTask extends AsyncTask<Void, Void, Void> {
        private VertexDao vertexDao;
        private EdgeDao edgeDao;

        private PopulateDbAsyncTask(GraphDatabase db) {
            vertexDao = db.vertexDao();
            edgeDao = db.edgeDao();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            //dodawanie jesli pusta baza danych
            return null;
        }
    }

}
