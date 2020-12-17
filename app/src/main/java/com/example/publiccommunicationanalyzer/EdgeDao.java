package com.example.publiccommunicationanalyzer;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface EdgeDao {

    @Insert
    void insert(Edge edge);

    @Update
    void update(Edge edge);

    @Delete
    void delete(Edge edge);

    @Query("DELETE FROM edge_table")
    void deleteAll();

    @Query("SELECT * FROM edge_table")
    LiveData<List<Edge>> getAll();

    @Query("SELECT line FROM edge_table GROUP BY line")
    LiveData<List<String>> getLines();

    @Query("SELECT day FROM edge_table WHERE line LIKE :line GROUP BY day")
    LiveData<List<String>> getDays(String line);

    @Query("SELECT service FROM edge_table WHERE line LIKE :line AND day LIKE :day GROUP BY service")
    LiveData<List<String>> getServiceByLineDay(String line, String day);

    @Query("SELECT * FROM edge_table WHERE service LIKE :service")
    LiveData<List<Edge>> getEdgesByService(String service);

    @Query("SELECT service FROM edge_table WHERE v2 LIKE :id AND day LIKE 'DP'")
    LiveData<List<String>> getServiceByV(Integer id);

    @Query("SELECT * FROM edge_table WHERE service IN (:services) AND day LIKE 'DP'")
    LiveData<List<Edge>> getEdgesByServicesList(List<String> services);
}
