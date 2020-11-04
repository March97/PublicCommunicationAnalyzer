package com.example.publiccommunicationanalyzer;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        AddLineDialog.AddLineDialogListener, AddDayDialog.AddDayDialogListener,
        AddServiceDialog.AddServiceDialogListener {

    private GoogleMap mMap;
    private MapsViewModel mapsViewModel;

    private ArrayList<Edge> edgesList;
    private ArrayList<String> linesList;
    private ArrayList<String> daysList;
    private ArrayList<String> servicesList;
    private ArrayList<Edge> selectedEdgesList;
    private boolean listsReady;
    private String selectedLine;
    private String selectedDay;
    private String selectedService;

    private ArrayList<ArrayList<Edge>> graphEdges;
    private ArrayList<ArrayList<Vertex>> verticesList;
    private ArrayList<Vertex> graphVertices;

    private Observer<List<String>> observerLinesList;
    private Observer<List<String>> observerDaysList;
    private Observer<List<String>> observerServicesList;
    private Observer<List<Edge>> observerSelectedEdgesList;
    private Observer<Vertex> observerGetVertex;

    private ArrayList<Marker> markers;
    private ArrayList<Polyline> polylines;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //czekaj na zaladowanie listy dostepnych linii
        listsReady = false;

        mapsViewModel = new ViewModelProvider(this).get(MapsViewModel.class);
        verticesList = new ArrayList<ArrayList<Vertex>>();
        edgesList = new ArrayList<Edge>();

        graphEdges = new ArrayList<ArrayList<Edge>>();
        graphVertices = new ArrayList<Vertex>();

        markers = new ArrayList<Marker>();
        polylines = new ArrayList<Polyline>();

        final Button btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(listsReady) {

                    setVertices(graphEdges);
                    //Toast.makeText(MapsActivity.this, "btnDelete", Toast.LENGTH_SHORT).show();
                    drawVertices(verticesList, markers);
                    drawEdges(graphEdges, verticesList, polylines);
                }
                else {
                    //Toast.makeText(MapsActivity.this, "Poczekaj na załadowanie bazy danych", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listsReady) {
                    openAddLineDialog();
                    //Toast.makeText(MapsActivity.this, "btnAdd", Toast.LENGTH_SHORT).show();
                }
                else {
                    //Toast.makeText(MapsActivity.this, "Poczekaj na załadowanie bazy danych", Toast.LENGTH_SHORT).show();
                }
            }
        });

        setObserverLinesList();
        //setObserverDaysList();
        //setObserverServicesList();
        //setObserverSelectedEdgesList();
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng warsaw = new LatLng(52.25, 21);
//        mMap.addMarker(new MarkerOptions().position(warsaw).title("Marker in Warsaw"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(warsaw));
        mMap.setMinZoomPreference(10.0f);
        mMap.setMaxZoomPreference(16.0f);
    }

    private void setObserverLinesList() {
        mapsViewModel.getLines().observe(this, observerLinesList = new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                linesList = (ArrayList) strings;
                listsReady = true;
                //Toast.makeText(MapsActivity.this, "onChanged Linie " + strings.size() + " " + linesList.size(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setObserverDaysList() {
        mapsViewModel.getDays(selectedLine).observe(this, observerDaysList = new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                daysList = (ArrayList) strings;
                //Toast.makeText(MapsActivity.this, "onChanged dni " + strings.size() + " " + daysList.size(), Toast.LENGTH_SHORT).show();
                openAddDayDialog();
            }
        });
    }

    private void setObserverServicesList() {
        mapsViewModel.getServices(selectedLine, selectedDay).observe(this, observerServicesList = new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                servicesList = (ArrayList) strings;
                //Toast.makeText(MapsActivity.this, "onChanged kursy " + strings.size() + " " + servicesList.size(), Toast.LENGTH_SHORT).show();
                openAddServiceDialog();
            }
        });
    }

    private void setObserverSelectedEdgesList() {
        mapsViewModel.getSelectedEdges(selectedService).observe(this, observerSelectedEdgesList = new Observer<List<Edge>>() {
            @Override
            public void onChanged(List<Edge> edges) {
                selectedEdgesList = (ArrayList) edges;
                graphEdges.add(selectedEdgesList);
                //Toast.makeText(MapsActivity.this, "onChanged  wybrane łuki" + edges.size() + " " + selectedEdgesList.size() + " " + graphEdges.size(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setObserverGetVertex(int id, int position) {

        if(verticesList.size() <= position) {
            verticesList.add(new ArrayList<Vertex>());
        }
        mapsViewModel.getVertex(id).observe(this, observerGetVertex = new Observer<Vertex>() {
            @Override
            public void onChanged(Vertex vertex) {
                verticesList.get(position).add(vertex);
                mapsViewModel.getVertex(id).removeObserver(observerGetVertex);
            }
        });
    }

    public void openAddLineDialog() {
        AddLineDialog addLineDialog = new AddLineDialog(linesList);
        addLineDialog.show(getSupportFragmentManager(), "add line dialog");
    }

    @Override
    public void applyTextsLine(String line) {
        selectedLine = line;
        //Toast.makeText(MapsActivity.this, "selectedLine: " + selectedLine, Toast.LENGTH_SHORT).show();

        mapsViewModel.getDays(selectedLine).removeObserver(observerDaysList);
        setObserverDaysList();
    }

    public void openAddDayDialog() {
        AddDayDialog addDayDialog = new AddDayDialog(daysList);
        addDayDialog.show(getSupportFragmentManager(), "add line dialog");
    }

    @Override
    public void applyTextsDay(String day) {
        selectedDay = day;
        //Toast.makeText(MapsActivity.this, "selectedDay: " + selectedDay, Toast.LENGTH_SHORT).show();

        mapsViewModel.getServices(selectedLine, selectedDay).removeObserver(observerServicesList);
        setObserverServicesList();
    }

    public void openAddServiceDialog() {
        AddServiceDialog addServiceDialog = new AddServiceDialog(servicesList);
        addServiceDialog.show(getSupportFragmentManager(), "add line dialog");
    }

    @Override
    public void applyTextsService(String service) {
        selectedService = service;
        //Toast.makeText(MapsActivity.this, "selectedService: " + selectedService, Toast.LENGTH_SHORT).show();

        mapsViewModel.getSelectedEdges(selectedService).removeObserver(observerSelectedEdgesList);
        setObserverSelectedEdgesList();
    }

    private void setVertices(ArrayList<ArrayList<Edge>> edgeList) {

        ArrayList<Integer> verticesId = new ArrayList<Integer>();

        for(int i = 0; i < edgeList.size(); i++) {
            //for(ArrayList<Edge> list : edgeList) {
            //PolylineOptions polylineOptions = new PolylineOptions();
            for(Edge edge : edgeList.get(i)) {
                //for(Edge edge : list) {
                verticesId.add(edge.getV2());
                setObserverGetVertex(edge.getV2(), i);
//                polylineOptions.add(new LatLng(edge.ge))
//                //Toast.makeText(MapsActivity.this, " " + edge.getV2(),
//                        Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void removeMarkers(ArrayList<Marker> markers) {
        for(Marker marker : markers) {
            marker.remove();
        }
        markers.clear();
    }

    public void drawVertices(ArrayList<ArrayList<Vertex>> vertices, ArrayList<Marker> markers) {

        removeMarkers(markers);

        for(ArrayList<Vertex> vertexList : vertices) {
            for (Vertex vertex : vertexList) {
                LatLng latLng = new LatLng(vertex.getY(), vertex.getX());
                //markers.add(new MarkerOptions().position(latLng).title(String.valueOf(vertex.getId())));
                MarkerOptions markerOptions = new MarkerOptions().position(latLng).title(String.valueOf(vertex.getId())).icon(BitmapDescriptorFactory.fromAsset("Yellow_dot_24.png"));
                Marker marker = mMap.addMarker(markerOptions);
                markers.add(marker);
            }
        }
    }

    public void removePolylines(ArrayList<Polyline> polylines) {
        for(Polyline polyline : polylines) {
            polyline.remove();
        }
        polylines.clear();
    }

    private void drawEdges(ArrayList<ArrayList<Edge>> edgeList, ArrayList<ArrayList<Vertex>> vertices, ArrayList<Polyline> polylines) {

        removePolylines(polylines);
        ArrayList<Integer[]> vLists = new ArrayList<Integer[]>();

        for(int i = 0; i < edgeList.size(); i++) {
            for(int j = 0; j < edgeList.get(i).size() - 1; j++) {
                vLists.add(new Integer[] { edgeList.get(i).get(j).getV2(), edgeList.get(i).get(j + 1).getV2()});
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
}