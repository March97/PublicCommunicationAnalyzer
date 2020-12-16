package com.example.publiccommunicationanalyzer;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface VertexDao {
    
    @Insert
    void insert(Vertex vertex);

    @Update
    void update(Vertex vertex);

    @Delete
    void delete(Vertex vertex);

    @Query("DELETE FROM vertex_table")
    void deleteAll();

    @Query("SELECT * FROM vertex_table")
    LiveData<List<Vertex>> getAll();

    @Query("SELECT * FROM vertex_table WHERE id LIKE :id")
    LiveData<Vertex> getVertex(int id);

    @Query("SELECT address FROM vertex_table GROUP BY address")
    LiveData<List<String>> getAddresses();
}
