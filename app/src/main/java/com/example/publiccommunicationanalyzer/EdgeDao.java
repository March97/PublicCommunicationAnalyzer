package com.example.publiccommunicationanalyzer;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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
}
