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
import com.example.mymusic.Adapters.GrupoDisponibleVideoAdapter;
import com.example.mymusic.Models.GrupoModelVideo;
import com.example.mymusic.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DescubrirVideoFragment extends Fragment {

    private RecyclerView recyclerVgVideosD;
    private GrupoDisponibleVideoAdapter adpgVideoD;
    private List<GrupoModelVideo> ListaGrupovDisponible;
    private RequestQueue requestQueue;

    public DescubrirVideoFragment() {
        // Required empty public constructor
    }

    public static DescubrirVideoFragment newInstance() {
        return new DescubrirVideoFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_descubrir_video, container, false);

        // Find the RecyclerView and set it up
        recyclerVgVideosD = view.findViewById(R.id.recyclerVgVideosD);
        recyclerVgVideosD.setLayoutManager(new LinearLayoutManager(getContext()));
        ListaGrupovDisponible = new ArrayList<>();

        // Initialize the adapter
        adpgVideoD = new GrupoDisponibleVideoAdapter(getContext(), ListaGrupovDisponible);
        recyclerVgVideosD.setAdapter(adpgVideoD);

        // Initialize RequestQueue
        requestQueue = Volley.newRequestQueue(getContext());

        // Fetch groups
        fetchVideoGroups();

        return view;
    }

    private void fetchVideoGroups() {
        String url = "http://34.125.8.146/get_video_groups.php";

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ListaGrupovDisponible.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String id = jsonObject.getString("IdGrupo");
                                String nombreGrupo = jsonObject.getString("NombreGrupo");
                                String totalVideos = jsonObject.getString("TotalVideos");
                                String caratulaGrupo = jsonObject.getString("CaratulaGrupo"); // Nueva propiedad

                                GrupoModelVideo grupo = new GrupoModelVideo(id, nombreGrupo, totalVideos, caratulaGrupo);
                                ListaGrupovDisponible.add(grupo);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adpgVideoD.notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), ": " + error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonArrayRequest);
    }
}
