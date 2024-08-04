package com.example.mymusic.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.Adapters.GrupoDisponibleAudioAdapter;
import com.example.mymusic.Models.GrupoModel;
import com.example.mymusic.R;

import java.util.ArrayList;
import java.util.List;

public class DescubrirAudioFragment extends Fragment {

    private RecyclerView recyclerVgAudiosD;
    private GrupoDisponibleAudioAdapter adpgAudioD;
    private List<GrupoModel> ListaGrupoaDisponible;

    public DescubrirAudioFragment() {
        // Required empty public constructor
    }

    public static DescubrirAudioFragment newInstance() {
        return new DescubrirAudioFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_descubrir_audio, container, false);

        // Find the RecyclerView and set it up
        recyclerVgAudiosD = view.findViewById(R.id.recyclerVgAudiosD);
        recyclerVgAudiosD.setLayoutManager(new LinearLayoutManager(getContext()));

        // Initialize the list and adapter
        ListaGrupoaDisponible = new ArrayList<>();
        ListaGrupoaDisponible.add(new GrupoModel("1","Grupo Primero", "10"));
        ListaGrupoaDisponible.add(new GrupoModel("2","Grupo Segundo", "5"));


        adpgAudioD = new GrupoDisponibleAudioAdapter(getContext(), ListaGrupoaDisponible);
        recyclerVgAudiosD.setAdapter(adpgAudioD);

        return view;
    }
}
