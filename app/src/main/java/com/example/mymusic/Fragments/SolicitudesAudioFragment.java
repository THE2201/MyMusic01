package com.example.mymusic.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymusic.Adapters.SolicitudesAdapter;
import com.example.mymusic.Models.SolicitudModel;
import com.example.mymusic.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolicitudesAudioFragment extends Fragment {

    private RecyclerView recyclerViewSolicitudesAudio;
    private SolicitudesAdapter adpSolicitudAudio;
    private List<SolicitudModel> ListaSolicitudAudio;
    private RequestQueue requestQueue;

    public SolicitudesAudioFragment() {
        // Constructor vacío
    }

    public static SolicitudesAudioFragment newInstance() {
        return new SolicitudesAudioFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_solicitudes_audio, container, false);

        recyclerViewSolicitudesAudio = view.findViewById(R.id.recyclerViewSolicitudesAudio);
        recyclerViewSolicitudesAudio.setLayoutManager(new LinearLayoutManager(getContext()));
        ListaSolicitudAudio = new ArrayList<>();

        adpSolicitudAudio = new SolicitudesAdapter(getContext(), ListaSolicitudAudio);
        recyclerViewSolicitudesAudio.setAdapter(adpSolicitudAudio);

        requestQueue = Volley.newRequestQueue(getContext());
        obtenerSolicitudes("Audio");

        return view;
    }

    private void obtenerSolicitudes(String tipoSolicitud) {
        String url = "http://34.125.8.146/obtenerSolicitudes.php";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String titulo = jsonObject.getString("Titulo");
                                String fechaSolicitado = jsonObject.getString("FechaSolicitado");
                                String usuario = jsonObject.getString("Usuario");
                                String comentario = jsonObject.getString("Comentario");

                                SolicitudModel solicitud = new SolicitudModel(titulo, fechaSolicitado, usuario, comentario);
                                ListaSolicitudAudio.add(solicitud);
                            }
                            adpSolicitudAudio.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("tipoSolicitud", tipoSolicitud);
                return params;
            }
        };

        requestQueue.add(stringRequest);
    }
}
