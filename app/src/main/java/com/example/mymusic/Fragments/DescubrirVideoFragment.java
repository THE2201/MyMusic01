package com.example.mymusic.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.mymusic.Adapters.GrupoDisponibleVideoAdapter;
import com.example.mymusic.Models.GrupoModelVideo;
import com.example.mymusic.R;

import java.util.ArrayList;
import java.util.List;

public class DescubrirVideoFragment extends Fragment {

    private RecyclerView recyclerVgVideosD;
    private GrupoDisponibleVideoAdapter adpgVideoD;
    private List<GrupoModelVideo> ListaGrupovDisponible;

    public DescubrirVideoFragment() {
        // Required empty public constructor
    }

    public static DescubrirVideoFragment newInstance() {
        return new DescubrirVideoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_descubrir_video, container, false);

        // Find the RecyclerView and set it up
        recyclerVgVideosD = view.findViewById(R.id.recyclerVgVideosD);
        recyclerVgVideosD.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the list and adapter
        ListaGrupovDisponible = new ArrayList<>();
        ListaGrupovDisponible.add(new GrupoModelVideo("1","Grupo Primero Video", "10"));
        ListaGrupovDisponible.add(new GrupoModelVideo("2","Grupo Segundo Video", "5"));


        adpgVideoD = new GrupoDisponibleVideoAdapter(getContext(), ListaGrupovDisponible);
        recyclerVgVideosD.setAdapter(adpgVideoD);

        return view;
    }
}
