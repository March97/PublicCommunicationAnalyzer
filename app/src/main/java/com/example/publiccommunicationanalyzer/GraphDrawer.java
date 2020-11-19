package com.example.publiccommunicationanalyzer;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;

public class GraphDrawer {



    private void removeMarkers(ArrayList<Marker> markers) {
        for(Marker marker : markers) {
            marker.remove();
        }
        markers.clear();
    }

    private void drawVertices(GoogleMap mMap, ArrayList<ArrayList<Vertex>> vertices, ArrayList<Marker> markers) {

        removeMarkers(markers);

        for(ArrayList<Vertex> vertexList : vertices) {
            for (Vertex vertex : vertexList) {
                LatLng latLng = new LatLng(vertex.getY(), vertex.getX());
                //markers.add(new MarkerOptions().position(latLng).title(String.valueOf(vertex.getId())));

                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(String.valueOf(vertex.getId()))
                        .icon(BitmapDescriptorFactory.fromAsset("Yellow_dot.png"));

//                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(
//                        "ID: " + String.valueOf(vertex.getId()))
//                        .icon(BitmapDescriptorFactory.fromAsset("Yellow_dot.png"))
//                        .snippet("Czas:\n Pośrednictwo: " + jGraph.getbT().getVertexScore(String.valueOf(vertex.getId())));
                Marker marker = mMap.addMarker(markerOptions);
                markers.add(marker);
            }
        }
    }

    private void removePolylines(ArrayList<Polyline> polylines) {
        for(Polyline polyline : polylines) {
            polyline.remove();
        }
        polylines.clear();
    }

    private void drawEdges(GoogleMap mMap, ArrayList<ArrayList<Edge>> edgeList, ArrayList<ArrayList<Vertex>> vertices, ArrayList<Polyline> polylines) {

        removePolylines(polylines);
        ArrayList<Integer[]> vLists = new ArrayList<Integer[]>();

        for(int i = 0; i < edgeList.size(); i++) {
            for(int j = 0; j < edgeList.get(i).size(); j++) {
                if(edgeList.get(i).get(j).getV1() != 0 && edgeList.get(i).get(j).getV1() != edgeList.get(i).get(j).getV2() )
                    vLists.add(new Integer[] { edgeList.get(i).get(j).getV1(), edgeList.get(i).get(j).getV2()});
            }
        }

        for(int i = 0; i < vLists.size(); i++) {
            PolylineOptions polylineOptions = new PolylineOptions();
            for(int j = 0; j < vLists.get(i).length; j++) {
                for(ArrayList<Vertex> vertexList : vertices) {
                    for (Vertex vertex : vertexList) {
                        if(vertex.getId() == vLists.get(i)[j]) {
                            LatLng latLng = new LatLng(vertex.getY(), vertex.getX());
                            polylineOptions.add(latLng);
                        }
                    }
                }
            }
            Polyline polyline = mMap.addPolyline(polylineOptions);
            polylines.add(polyline);
        }

        System.out.println("VLists size:");
        System.out.println(vLists.size());

        System.out.println("polyline size:");
        System.out.println(polylines.size());
    }

    public void drawGraph(GoogleMap mMap, ArrayList<ArrayList<Edge>> edges, ArrayList<ArrayList<Vertex>> vertices, ArrayList<Marker> markers, ArrayList<Polyline> polylines) {

        drawEdges(mMap, edges, vertices, polylines);
        drawVertices(mMap, vertices, markers);
    }

    public void deleteGraph(GoogleMap mMap, ArrayList<ArrayList<Vertex>> vertice, ArrayList<ArrayList<Edge>> edgeList, ArrayList<Marker> markers, ArrayList<Polyline> polylines) {
        mMap.clear();
        removeMarkers(markers);
        removePolylines(polylines);
        vertice.clear();
        edgeList.clear();;
    }
}
