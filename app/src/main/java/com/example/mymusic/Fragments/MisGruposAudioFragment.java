package com.example.mymusic.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.Adapters.MiGrupoAudioAdapter;
import com.example.mymusic.Models.MiGrupoAudioModel;
import com.example.mymusic.R;

import java.util.ArrayList;
import java.util.List;


public class MisGruposAudioFragment extends Fragment {

    private RecyclerView recyclerViewMiGrupoAudio;
    private MiGrupoAudioAdapter adpMiGrupoAudio;
    private List<MiGrupoAudioModel> ListaMiGrupoAudio;

    public MisGruposAudioFragment() {
        // Required empty public constructor
    }

    public static MisGruposAudioFragment newInstance() {
        return new MisGruposAudioFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mis_grupos_audio, container, false);

        //RecyclerView
        recyclerViewMiGrupoAudio = view.findViewById(R.id.recyclerViewMiGrupoAudio);
        recyclerViewMiGrupoAudio.setLayoutManager(new LinearLayoutManager(getContext()));

        ListaMiGrupoAudio = new ArrayList<>();
        ListaMiGrupoAudio.add(new MiGrupoAudioModel("1","Grupo Pridsamero", "10"));
        ListaMiGrupoAudio.add(new MiGrupoAudioModel("11","Grupo EE", "10"));
        ListaMiGrupoAudio.add(new MiGrupoAudioModel("21","Grupo Primero", "10"));

        adpMiGrupoAudio = new MiGrupoAudioAdapter(getContext(), ListaMiGrupoAudio);
        recyclerViewMiGrupoAudio.setAdapter(adpMiGrupoAudio);
        return view;
    }
}