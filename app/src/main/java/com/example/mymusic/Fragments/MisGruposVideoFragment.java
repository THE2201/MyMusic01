package com.example.mymusic.Fragments;

import android.os.Bundle;
import android.util.Log;
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
import com.example.mymusic.Adapters.MiGrupoVideoAdapter;
import com.example.mymusic.Models.MiGrupoVideoModel;
import com.example.mymusic.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MisGruposVideoFragment extends Fragment {
    private RecyclerView recyclerViewMiGrupoVideo;
    private MiGrupoVideoAdapter adpMiGrupoVideo;
    private List<MiGrupoVideoModel> ListaMiGrupoVideo;
    private RequestQueue requestQueue;

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
        adpMiGrupoVideo = new MiGrupoVideoAdapter(getContext(), ListaMiGrupoVideo);
        recyclerViewMiGrupoVideo.setAdapter(adpMiGrupoVideo);

        requestQueue = Volley.newRequestQueue(getContext());

        fetchUserVideoGroups();

        return view;
    }

    private void fetchUserVideoGroups() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String firebaseUid = user.getUid();
            String url = "http://34.125.8.146/get_userVideo_groups.php?FirebaseUid=" + firebaseUid;

            JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                    Request.Method.GET,
                    url,
                    null,
                    new Response.Listener<JSONArray>() {
                        @Override
                        public void onResponse(JSONArray response) {
                            ListaMiGrupoVideo.clear();
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    String id = jsonObject.getString("IdGrupo");
                                    String nombreGrupo = jsonObject.getString("NombreGrupo");
                                    String caratulaGrupo = jsonObject.getString("CaratulaGrupo");
                                    String totalVideos = jsonObject.getString("TotalVideos");

                                    MiGrupoVideoModel grupo = new MiGrupoVideoModel(id, nombreGrupo, caratulaGrupo, totalVideos);
                                    ListaMiGrupoVideo.add(grupo);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                            adpMiGrupoVideo.notifyDataSetChanged();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            Toast.makeText(getContext(), "Crea un grupo de video para visualizar", Toast.LENGTH_SHORT).show();
                            Log.d("MensajePeticion", "Mensaje"+ error.getMessage()); }
                    }
            );

            requestQueue.add(jsonArrayRequest);
        } else {
            Toast.makeText(getContext(), "Usuario no autenticado.", Toast.LENGTH_SHORT).show();
        }
    }
}
