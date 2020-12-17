package com.example.publiccommunicationanalyzer;

import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
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
import java.util.Map;
import java.util.concurrent.ExecutionException;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback,
        AddLineDialog.AddLineDialogListener, AddDayDialog.AddDayDialogListener,
        AddServiceDialog.AddServiceDialogListener, AddSelectorDialog.AddSelectorDialogListener, AddAddressDialog.AddAddressDialogListener, AddStopDialog.AddStopDialogListener {

    private GoogleMap mMap;
    private MapsViewModel mapsViewModel;

    //private ArrayList<Edge> edgesList;
    private ArrayList<String> linesList;
    private ArrayList<String> daysList;
    private ArrayList<String> servicesList;
    private ArrayList<String> selectedServices;
    private ArrayList<Edge> selectedEdgesList;
    private boolean listsReady;
    private String selectedLine;
    private String selectedDay;
    private String selectedService;
    private String selectedAddress;

    private ArrayList<ArrayList<Edge>> graphEdges;
    private ArrayList<ArrayList<Vertex>> graphVertices;
//    private ArrayList<Vertex> graphVertices;

    private Observer<List<Vertex>> observerVertexIdsList;
    private Observer<List<String>> observerLinesList;
    private Observer<List<String>> observerDaysList;
    private Observer<List<String>> observerServicesList;
    private Observer<List<Edge>> observerSelectedEdgesList;
    private Observer<List<Edge>> observerEdgesList;
    private Observer<Vertex> observerGetVertex;
    private Observer<List<String>> observerServicesByVList;

    private ArrayList<Marker> markers;
    private ArrayList<Polyline> polylines;

    private ArrayList<String> addresses;
    private ArrayList<Vertex> selectedVertexIds;
    private ArrayList<String> servicesToAdd;

    private GraphDrawer graphDrawer;
    private JGraph jGraph;

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

        selectedServices = new ArrayList<String>();

        //edgesList = new ArrayList<Edge>();

        graphEdges = new ArrayList<ArrayList<Edge>>();
        graphVertices = new ArrayList<ArrayList<Vertex>>();
        //mMap.setInfoWindowAdapter(new InfoVertexAdapter(this, graphVertices, graphEdges));

        markers = new ArrayList<Marker>();
        polylines = new ArrayList<Polyline>();

        graphDrawer = new GraphDrawer();
        jGraph = new JGraph();

        try {
            mapsViewModel.getAddresses().observe(MapsActivity.this, new Observer<List<String>>() {
                @Override
                public void onChanged(List<String> strings) {
                    addresses = (ArrayList) strings;
                }
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


        final ImageButton btnInfo = findViewById(R.id.btnInfo);
        btnInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openInfoDialog();
            }
        });

        final Button btnDraw = findViewById(R.id.btnDraw);
        btnDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listsReady) {
                    jGraph.setGraph(graphVertices, graphEdges);
                    graphDrawer.drawGraph(mMap, graphEdges, graphVertices, markers, polylines);
                    mMap.setInfoWindowAdapter(new InfoVertexAdapter(MapsActivity.this, jGraph));
                    if(!markers.isEmpty())
                        btnInfo.setVisibility(View.VISIBLE);
                    Toast.makeText(MapsActivity.this, "Rysuję graf", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MapsActivity.this, "Poczekaj na załadowanie bazy danych", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Button btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (listsReady) {
//                    openInfoDialog();
                    graphDrawer.deleteGraph(mMap, graphVertices, graphEdges, markers, polylines);
                    jGraph.clearGraph();
                    selectedServices.clear();
                    if(markers.isEmpty())
                        btnInfo.setVisibility(View.INVISIBLE);
                    Toast.makeText(MapsActivity.this, "Usunięto graf", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MapsActivity.this, "Poczekaj na załadowanie bazy danych", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listsReady) {
//                    openAddLineDialog();
                    openAddSelectorDialog();

                    //Toast.makeText(MapsActivity.this, "btnAdd", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(MapsActivity.this, "Poczekaj na załadowanie bazy danych", Toast.LENGTH_SHORT).show();
                }
            }
        });

        try {
            setObserverLinesList();
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
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

    private void setObserverLinesList() throws ExecutionException, InterruptedException {
        mapsViewModel.getLines().observe(this, observerLinesList = new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                linesList = (ArrayList) strings;
                listsReady = true;
                //Toast.makeText(MapsActivity.this, "onChanged Linie " + strings.size() + " " + linesList.size(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setObserverDaysList() throws ExecutionException, InterruptedException {
        mapsViewModel.getDays(selectedLine).observe(this, observerDaysList = new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                daysList = (ArrayList) strings;
                //Toast.makeText(MapsActivity.this, "onChanged dni " + strings.size() + " " + daysList.size(), Toast.LENGTH_SHORT).show();
                openAddDayDialog();
            }
        });
    }

    private void setObserverServicesList() throws ExecutionException, InterruptedException {
        mapsViewModel.getServices(selectedLine, selectedDay).observe(this, observerServicesList = new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                servicesList = (ArrayList) strings;
                //Toast.makeText(MapsActivity.this, "onChanged kursy " + strings.size() + " " + servicesList.size(), Toast.LENGTH_SHORT).show();
                openAddServiceDialog();
            }
        });
    }

    private void setObserverSelectedEdgesList(String selectedService) throws ExecutionException, InterruptedException {
        mapsViewModel.getSelectedEdges(selectedService).observe(this, observerSelectedEdgesList = new Observer<List<Edge>>() {
            @Override
            public void onChanged(List<Edge> edges) {
                selectedEdgesList = (ArrayList) edges;
                graphEdges.add(selectedEdgesList);
                //Toast.makeText(MapsActivity.this, "onChanged  wybrane łuki" + edges.size() + " " + selectedEdgesList.size() + " " + graphEdges.size(), Toast.LENGTH_SHORT).show();
                try {
                    setVertices(graphEdges);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setObserverGetVertex(int id, int position) throws ExecutionException, InterruptedException {

        if (graphVertices.size() <= position) {
            graphVertices.add(new ArrayList<Vertex>());
        }
        mapsViewModel.getVertex(id).observe(this, observerGetVertex = new Observer<Vertex>() {
            @Override
            public void onChanged(Vertex vertex) {
                graphVertices.get(position).add(vertex);
                try {
                    mapsViewModel.getVertex(id).removeObserver(observerGetVertex);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void setObserverGetVertexIds(String selectedAddress) throws ExecutionException, InterruptedException {
        mapsViewModel.getByAddresses(selectedAddress).observe(this, observerVertexIdsList = new Observer<List<Vertex>>() {
            @Override
            public void onChanged(List<Vertex> vertexIds) {
                selectedVertexIds = (ArrayList) vertexIds;
//                Toast.makeText(MapsActivity.this, selectedVertexIds.toString(), Toast.LENGTH_SHORT).show();
                openAddStopDialog(selectedVertexIds);
            }
        });
    }

    private void setObserverGetServicesByV(int id) throws ExecutionException, InterruptedException {

        mapsViewModel.getServicesByV(id).observe(this, observerServicesByVList = new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                servicesToAdd = (ArrayList) strings;
                selectedServices.addAll(servicesToAdd);
                Toast.makeText(MapsActivity.this, servicesToAdd.toString(), Toast.LENGTH_SHORT).show();
                try {
                    setObserverGetEdgesByList(servicesToAdd);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
//                System.out.println(servicesToAdd.toString());
//                System.out.println(servicesToAdd.size());
            }
        });
    }

    private void setObserverGetEdgesByList(List<String> services) throws ExecutionException, InterruptedException {

        mapsViewModel.getEdgesByServicesList(services).observe(this, observerEdgesList = new Observer<List<Edge>>() {
            @Override
            public void onChanged(List<Edge> edges) {
                selectedEdgesList = (ArrayList) edges;
                graphEdges.add(selectedEdgesList);
                //Toast.makeText(MapsActivity.this, "onChanged  wybrane łuki" + edges.size() + " " + selectedEdgesList.size() + " " + graphEdges.size(), Toast.LENGTH_SHORT).show();
                try {
                    setVertices(graphEdges);
                } catch (ExecutionException | InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
    }



    public void openAddLineDialog() {
        AddLineDialog addLineDialog = new AddLineDialog(linesList);
        addLineDialog.show(getSupportFragmentManager(), "add line dialog");
    }

    @Override
    public void applyTextsLine(String line) throws ExecutionException, InterruptedException {
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
    public void applyTextsDay(String day) throws ExecutionException, InterruptedException {
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
    public void applyTextsService(String service) throws ExecutionException, InterruptedException {
        selectedService = service;
        selectedServices.add(service);
        //Toast.makeText(MapsActivity.this, "selectedService: " + selectedService, Toast.LENGTH_SHORT).show();

        mapsViewModel.getSelectedEdges(selectedService);
        mapsViewModel.getSelectedEdges(selectedService).removeObserver(observerSelectedEdgesList);
        setObserverSelectedEdgesList(selectedService);
        Toast.makeText(MapsActivity.this, "Dodano kurs: " + selectedService, Toast.LENGTH_SHORT).show();
    }

    public void openInfoDialog() {
        InfoDialog infoDialog = new InfoDialog(jGraph, selectedServices);
        infoDialog.show(getSupportFragmentManager(), "add line dialog");
    }

    private void setVertices(ArrayList<ArrayList<Edge>> edgeList) throws ExecutionException, InterruptedException {

        ArrayList<Integer> verticesId = new ArrayList<Integer>();
        for (int i = 0; i < edgeList.size(); i++) {
            for (Edge edge : edgeList.get(i)) {
                verticesId.add(edge.getV2());
                setObserverGetVertex(edge.getV2(), i);
            }
        }
    }

    public void openAddSelectorDialog() {
        AddSelectorDialog addSelectorDialog = new AddSelectorDialog();
        addSelectorDialog.show(getSupportFragmentManager(), "selector dialog");
    }

    @Override
    public void applyIntSelector(int choice) throws ExecutionException, InterruptedException {
        if (choice == 1)
            openAddLineDialog();
        else
            openAddAddressDialog();
    }

    public void openAddAddressDialog() {
        AddAddressDialog addAddressDialog = new AddAddressDialog(addresses);
        addAddressDialog.show(getSupportFragmentManager(), "add address dialog");
    }

    @Override
    public void applyTextsAddress(String address) throws ExecutionException, InterruptedException {
        selectedAddress = address;
        setObserverGetVertexIds(selectedAddress);
        Toast.makeText(MapsActivity.this, address, Toast.LENGTH_SHORT).show();
    }

    public void openAddStopDialog(ArrayList<Vertex> vertices) {
        AddStopDialog addStopDialog = new AddStopDialog(vertices);
        addStopDialog.show(getSupportFragmentManager(), "add stop dialog");
    }

    @Override
    public void applyTextsStop(int stop) throws ExecutionException, InterruptedException {
        setObserverGetServicesByV(stop);
    }
}