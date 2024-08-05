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
import com.example.mymusic.Adapters.MiGrupoAudioAdapter;
import com.example.mymusic.Models.MiGrupoAudioModel;
import com.example.mymusic.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MisGruposAudioFragment extends Fragment {

    private RecyclerView recyclerViewMiGrupoAudio;
    private MiGrupoAudioAdapter adpMiGrupoAudio;
    private List<MiGrupoAudioModel> ListaMiGrupoAudio;
    private RequestQueue requestQueue;

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
        adpMiGrupoAudio = new MiGrupoAudioAdapter(getContext(), ListaMiGrupoAudio);
        recyclerViewMiGrupoAudio.setAdapter(adpMiGrupoAudio);

        // Initialize RequestQueue
        requestQueue = Volley.newRequestQueue(getContext());

        // Fetch user groups
        fetchUserGroups();
        //ListaMiGrupoAudio.add(new MiGrupoAudioModel("1","Grupo Pridsamero", "10"));
        //ListaMiGrupoAudio.add(new MiGrupoAudioModel("11","Grupo EE", "10"));
        //ListaMiGrupoAudio.add(new MiGrupoAudioModel("21","Grupo Primero", "10"));

        //adpMiGrupoAudio = new MiGrupoAudioAdapter(getContext(), ListaMiGrupoAudio);
        //recyclerViewMiGrupoAudio.setAdapter(adpMiGrupoAudio);
        return view;
    }

    private void fetchUserGroups() {
        //FirebaseUser user = fAuth.getCurrentUser();
        //String uid = user.getUid();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String firebaseUid = user.getUid();
            String url = "http://34.125.8.146/get_user_groups.php?FirebaseUid=" + firebaseUid;

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            ListaMiGrupoAudio.clear();
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    String id = jsonObject.getString("IdGrupo");
                                    String nombreGrupo = jsonObject.getString("NombreGrupo");
                                    String totalAudios = jsonObject.getString("TotalAudios");

                                    MiGrupoAudioModel grupo = new MiGrupoAudioModel(id, nombreGrupo, totalAudios);
                                    ListaMiGrupoAudio.add(grupo);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adpMiGrupoAudio.notifyDataSetChanged();
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
        } else {
            Toast.makeText(getContext(), "Usuario no autenticado.", Toast.LENGTH_SHORT).show();
        }
    }
}