package com.example.mymusic.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.Adapters.MiGrupoAudioAdapter;
import com.example.mymusic.Adapters.MiGrupoVideoAdapter;
import com.example.mymusic.Models.MiGrupoAudioModel;
import com.example.mymusic.Models.MiGrupoVideoModel;
import com.example.mymusic.R;

import java.util.ArrayList;
import java.util.List;

public class MisGruposVideoFragment extends Fragment {
    private RecyclerView recyclerViewMiGrupoVideo;
    private MiGrupoVideoAdapter adpMiGrupoVideo;
    private List<MiGrupoVideoModel> ListaMiGrupoVideo;

    public MisGruposVideoFragment() {
        // Required empty public constructor
    }

    public static MisGruposVideoFragment newInstance() {
        return new MisGruposVideoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mis_grupos_video, container, false);

        recyclerViewMiGrupoVideo = view.findViewById(R.id.recyclerViewMiGrupoVideo);
        recyclerViewMiGrupoVideo.setLayoutManager(new LinearLayoutManager(getContext()));

        ListaMiGrupoVideo = new ArrayList<>();
        ListaMiGrupoVideo.add(new MiGrupoVideoModel("1","Grupo Video1", "10"));
        ListaMiGrupoVideo.add(new MiGrupoVideoModel("11","Grupo EE2", "10"));
        ListaMiGrupoVideo.add(new MiGrupoVideoModel("21","Grupo eera", "10"));

        adpMiGrupoVideo = new MiGrupoVideoAdapter(getContext(), ListaMiGrupoVideo);
        recyclerViewMiGrupoVideo.setAdapter(adpMiGrupoVideo);

        return view;
    }
}