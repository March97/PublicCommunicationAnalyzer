package com.example.publiccommunicationanalyzer;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

//polaczenie
@Entity(tableName = "edge_table")
public class Edge {

    @PrimaryKey(autoGenerate = true)
    private int rowId;
    private String service = "";
    private int v1 = 0;
    private int v2 = 0;
    private String line = "";
    private String route = "";
    private String day = "";
    private String start = "";
    private String end = "";
    private int time_from_start = 0;
    private int time_from_last = 0;

    public Edge(String service, int v1, int v2, String line, String route, String day, String start,
                String end, int time_from_start, int time_from_last) {
        this.service = service;
        this.v1 = v1;
        this.v2 = v2;
        this.line = line;
        this.route = route;
        this.day = day;
        this.start = start;
        this.end = end;
        this.time_from_start = time_from_start;
        this.time_from_last = time_from_last;
    }

    public int getRowId() {
        return rowId;
    }

    public void setRowId(int rowId) {
        this.rowId = rowId;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public int getV1() {
        return v1;
    }

    public void setV1(int v1) {
        this.v1 = v1;
    }

    public int getV2() {
        return v2;
    }

    public void setV2(int v2) {
        this.v2 = v2;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public int getTime_from_start() {
        return time_from_start;
    }

    public void setTime_from_start(int time_from_start) {
        this.time_from_start = time_from_start;
    }

    public int getTime_from_last() {
        return time_from_last;
    }

    public void setTime_from_last(int time_from_last) {
        this.time_from_last = time_from_last;
    }
}
