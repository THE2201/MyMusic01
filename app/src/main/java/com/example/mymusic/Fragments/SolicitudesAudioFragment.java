package com.example.mymusic.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.example.mymusic.Models.AudioModel;
import com.example.mymusic.Models.SolicitudModel;
import com.example.mymusic.Adapters.SolicitudesAdapter;
import com.example.mymusic.R;

import java.util.ArrayList;
import java.util.List;

public class SolicitudesAudioFragment extends Fragment {

    private RecyclerView recyclerViewSolicitudesAudio;
    private SolicitudesAdapter adpSolicitudAudio;
    private List<SolicitudModel> ListaSolicitudAudio;
    private RequestQueue requestQueue;


    public SolicitudesAudioFragment() {
        //contructor vacio
    }
    public static SolicitudesAudioFragment newInstance() {
        return new SolicitudesAudioFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_solicitudes_audio, container, false);

        recyclerViewSolicitudesAudio = view.findViewById(R.id.recyclerViewSolicitudesAudio);
        recyclerViewSolicitudesAudio.setLayoutManager(new LinearLayoutManager(getContext()));
        ListaSolicitudAudio = new ArrayList<>();
        ListaSolicitudAudio.add(new SolicitudModel("Como la flor", "22/12/2024", "ChayanneUser", "Esta es la cancion mas divertida me gusta muxo"));
        ListaSolicitudAudio.add(new SolicitudModel("Te amo", "22/12/2024", "usuarioBonito", "Mi abueloo silia cantar en el pueblo cuando se ponia bolo"));
        ListaSolicitudAudio.add(new SolicitudModel("Te amo", "22/12/2024", "usuarioBonito", "Mi abueloo silia cantar en el pueblo cuando se ponia bolo Mi abueloo silia cantar en el pueblo cuando se ponia bolo Mi abueloo silia cantar en el pueblo cuando se ponia bolo Mi abueloo silia cantar en el pueblo cuando se ponia bolo"));
        ListaSolicitudAudio.add(new SolicitudModel("Te amo", "22/12/2024", "usuarioBonito", "Mi abueloo silia cantar en el pueblo cuando se ponia bolo"));


        adpSolicitudAudio = new SolicitudesAdapter(getContext(), ListaSolicitudAudio);
        recyclerViewSolicitudesAudio.setAdapter(adpSolicitudAudio);
        return view;
    }
}
