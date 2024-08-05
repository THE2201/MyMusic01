package com.example.mymusic.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymusic.Adapters.GrupoDisponibleAudioAdapter;
import com.example.mymusic.Models.GrupoModel;
import com.example.mymusic.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DescubrirAudioFragment extends Fragment {

    private RecyclerView recyclerVgAudiosD;
    private GrupoDisponibleAudioAdapter adpgAudioD;
    private List<GrupoModel> ListaGrupoaDisponible;
    private RequestQueue requestQueue;

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
        ListaGrupoaDisponible = new ArrayList<>();

        // Initialize the adapter
        adpgAudioD = new GrupoDisponibleAudioAdapter(getContext(), ListaGrupoaDisponible);
        recyclerVgAudiosD.setAdapter(adpgAudioD);

        // Initialize RequestQueue
        requestQueue = Volley.newRequestQueue(getContext());

        // Fetch groups
        fetchAudioGroups();
        //ListaGrupoaDisponible = new ArrayList<>();
        //ListaGrupoaDisponible.add(new GrupoModel("1","Grupo Primero", "10"));
        //ListaGrupoaDisponible.add(new GrupoModel("2","Grupo Segundo", "5"));


        //adpgAudioD = new GrupoDisponibleAudioAdapter(getContext(), ListaGrupoaDisponible);
        //recyclerVgAudiosD.setAdapter(adpgAudioD);

        return view;
    }

    private void fetchAudioGroups() {
        String url = "http://34.125.8.146/get_audio_groups.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ListaGrupoaDisponible.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String id = jsonObject.getString("IdGrupo");
                                String nombreGrupo = jsonObject.getString("NombreGrupo");
                                String totalAudios = jsonObject.getString("TotalAudios");

                                GrupoModel grupo = new GrupoModel(id, nombreGrupo, totalAudios);
                                ListaGrupoaDisponible.add(grupo);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adpgAudioD.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error al obtener los grupos: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }
}
