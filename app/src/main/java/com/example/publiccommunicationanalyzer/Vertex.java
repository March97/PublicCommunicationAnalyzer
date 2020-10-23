package com.example.publiccommunicationanalyzer;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//przystanek
@Entity(tableName = "vertex_table")
public class Vertex {

    @PrimaryKey(autoGenerate = true)
    private int rowId;
    private String address = "";
    private int id = 0;
    private Double y = 0.0;
    private Double x =0.0;

    public Vertex(String address, int id,  Double y, Double x) {
        this.id = id;
        this.address = address;
        this.y = y;
        this.x = x;
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }
}
