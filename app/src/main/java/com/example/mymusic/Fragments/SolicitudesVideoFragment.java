package com.example.mymusic.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.example.mymusic.Adapters.SolicitudesAdapter;
import com.example.mymusic.Adapters.SolicitudesVideoAdapter;
import com.example.mymusic.Models.SolicitudModel;
import com.example.mymusic.R;

import java.util.ArrayList;
import java.util.List;

public class SolicitudesVideoFragment extends Fragment {

    private RecyclerView recyclerViewSolicitudesVideo;
    private SolicitudesVideoAdapter adpSolicitudVideo;
    private List<SolicitudModel> ListaSolicitudVideo;
    private RequestQueue requestQueue;


    public SolicitudesVideoFragment() {
        //contructor vacio

    }
    public static SolicitudesVideoFragment newInstance() {
        return new SolicitudesVideoFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_solicitudes_video, container, false);

        recyclerViewSolicitudesVideo = view.findViewById(R.id.recyclerViewSolicitudesVideo);
        recyclerViewSolicitudesVideo.setLayoutManager(new LinearLayoutManager(getContext()));
        ListaSolicitudVideo = new ArrayList<>();
        ListaSolicitudVideo.add(new SolicitudModel("Como la Video", "22/12/2024", "ChayanneUser", "Esta es la cancion mas divertida me gusta muxo"));
        ListaSolicitudVideo.add(new SolicitudModel("Te amo Video", "22/12/2024", "usuarioBonito", "Mi abueloo silia cantar en el pueblo cuando se ponia bolo"));

        adpSolicitudVideo = new SolicitudesVideoAdapter(getContext(), ListaSolicitudVideo);
        recyclerViewSolicitudesVideo.setAdapter(adpSolicitudVideo);
        return view;
    }
}
