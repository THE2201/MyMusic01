package com.example.mymusic.Activities.Grabadora;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymusic.Adapters.GrabacionesAdapter;
import com.example.mymusic.Models.GrabacionesModel;
import com.example.mymusic.R;
import com.google.firebase.auth.FirebaseAuth;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.List;

public class GrabacionesActivity extends AppCompatActivity {

    private RecyclerView recyclerViewGrabaciones;
    private GrabacionesAdapter adpGrab;
    private List<GrabacionesModel> listaGrabs;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grabaciones);

        recyclerViewGrabaciones = findViewById(R.id.recyclerViewGrabaciones);
        recyclerViewGrabaciones.setLayoutManager(new LinearLayoutManager(this));

        listaGrabs = new ArrayList<>();
        adpGrab = new GrabacionesAdapter(this, listaGrabs);
        recyclerViewGrabaciones.setAdapter(adpGrab);

        requestQueue = Volley.newRequestQueue(this);
        fetchGrabaciones();
    }

    private void fetchGrabaciones() {
        String firebaseUid = FirebaseAuth.getInstance().getCurrentUser().getUid();
        String url = "http://34.125.8.146/consultarGrabaciones.php?firebaseUid=" + firebaseUid;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listaGrabs.clear();
                        try {
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String idGrabacion = jsonObject.getString("IdGrabacion");
                                String titulo = jsonObject.getString("Titulo");
                                listaGrabs.add(new GrabacionesModel(idGrabacion, titulo, "", ""));
                            }
                            adpGrab.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(GrabacionesActivity.this, "Error al obtener grabaciones", Toast.LENGTH_SHORT).show();
                        Log.e("GrabacionesActivity", "Error: " + error.getMessage());
                    }
                });

        requestQueue.add(jsonArrayRequest);
    }
}
