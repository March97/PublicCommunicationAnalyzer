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
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private MapsViewModel mapsViewModel;
    private ArrayList<Vertex> verticesList;
    private ArrayList<Edge> edgesList;
    private ArrayList<String> linesList;
    private boolean listsReady;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        //czekaj na zaladowanie list wierzcholkow i krawedzi
        listsReady = false;

        mapsViewModel = new ViewModelProvider(this).get(MapsViewModel.class);
        verticesList = new ArrayList<Vertex>();
        edgesList = new ArrayList<Edge>();
        edgesList.add(new Edge("",0,0,"S1","","","","",0,0));
        edgesList.add(new Edge("",0,0,"720","","","","",0,0));
        edgesList.add(new Edge("",0,0,"M1","","","","",0,0));
        edgesList.add(new Edge("",0,0,"WKD","","","","",0,0));
        edgesList.add(new Edge("",0,0,"17","","","","",0,0));
        edgesList.add(new Edge("",0,0,"1","","","","",0,0));
        edgesList.add(new Edge("",0,0,"20","","","","",0,0));
        edgesList.add(new Edge("",0,0,"R3","","","","",0,0));
        edgesList.add(new Edge("",0,0,"L2","","","","",0,0));
        edgesList.add(new Edge("",0,0,"N7","","","","",0,0));

        final Button btnDelete = findViewById(R.id.btnDelete);
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(listsReady) {
                    Toast.makeText(MapsActivity.this, "btnDelete",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MapsActivity.this,
                            "Poczekaj na załadowanie bazy danych", Toast.LENGTH_SHORT).show();
                }
            }
        });

        final Button btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //List<String> lines = mapsViewModel.getLines();
                if(listsReady) {
                    AddLineDialog addLineDialog = new AddLineDialog(MapsActivity.this, linesList);
                    //AddLineDialog addLineDialog = new AddLineDialog(MapsActivity.this, edgesList);
                    addLineDialog.show();
                    Toast.makeText(MapsActivity.this, "btnAdd",
                            Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MapsActivity.this,
                            "Poczekaj na załadowanie bazy danych", Toast.LENGTH_SHORT).show();
                }

            }
        });

        mapsViewModel.getAllVertex().observe(this, new Observer<List<Vertex>>() {
            @Override
            public void onChanged(List<Vertex> vertices) {
                verticesList = (ArrayList) vertices;
                Toast.makeText(MapsActivity.this, "onChanged Vertex " +
                        vertices.size() + " " + verticesList.size(), Toast.LENGTH_SHORT).show();
            }
        });

        mapsViewModel.getLines().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> strings) {
                linesList = (ArrayList) strings;
                Toast.makeText(MapsActivity.this, "onChanged Linie " +
                        strings.size() + " " + linesList.size(), Toast.LENGTH_SHORT).show();
            }
        });

        mapsViewModel.getAllEdge().observe(this, new Observer<List<Edge>>() {
            @Override
            public void onChanged(List<Edge> edges) {
                edgesList = (ArrayList) edges;
                Toast.makeText(MapsActivity.this, "onChanged Edge" + edges.size()
                        + " " + edgesList.size(), Toast.LENGTH_SHORT).show();
                listsReady = true;
            }
        });
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
        mMap.addMarker(new MarkerOptions().position(warsaw).title("Marker in Warsaw"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(warsaw));
        mMap.setMinZoomPreference(10.0f);
        mMap.setMaxZoomPreference(16.0f);
    }
}