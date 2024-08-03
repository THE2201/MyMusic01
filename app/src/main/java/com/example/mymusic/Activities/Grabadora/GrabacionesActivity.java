package com.example.mymusic.Activities.Grabadora;

import static androidx.core.content.ContextCompat.getSystemService;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.Adapters.GrabacionesAdapter;
import com.example.mymusic.Models.GrabacionesModel;
import com.example.mymusic.R;

import java.util.ArrayList;
import java.util.List;

public class GrabacionesActivity extends AppCompatActivity {

    //para llenar lista de grabaciones
    private RecyclerView recyclerViewGrabaciones;
    private GrabacionesAdapter adpGrab;
    private List<GrabacionesModel> listaGrabs;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabaciones);

        recyclerViewGrabaciones = findViewById(R.id.recyclerViewGrabaciones);
        recyclerViewGrabaciones.setLayoutManager(new LinearLayoutManager(this));

        listaGrabs = new ArrayList<>();
        listaGrabs.add(new GrabacionesModel("1","Rec1", "22/01/2004","2:08min"));
        listaGrabs.add(new GrabacionesModel("2","Rec2", "22/01/2020","2:10min"));

        adpGrab = new GrabacionesAdapter(this, listaGrabs);
        recyclerViewGrabaciones.setAdapter(adpGrab);



    }


}