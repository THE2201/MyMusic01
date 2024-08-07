package com.example.mymusic.Activities.Grupos;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymusic.Adapters.VideoGrupoAdapter;
import com.example.mymusic.Models.VideoModel;
import com.example.mymusic.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GrupoVideoActivity extends AppCompatActivity {
    private RecyclerView recyclerViewVideos;
    private VideoGrupoAdapter adpVideos;
    private List<VideoModel> listaDeVideos;
    private RequestQueue requestQueue;
    private static final String BASE_URL = "http://34.125.8.146/consultar_videos.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo_video);

        requestQueue = Volley.newRequestQueue(this);

        recyclerViewVideos = findViewById(R.id.recyclerViewVideos);
        recyclerViewVideos.setLayoutManager(new LinearLayoutManager(this));
        listaDeVideos = new ArrayList<>();
        adpVideos = new VideoGrupoAdapter(this, listaDeVideos);
        recyclerViewVideos.setAdapter(adpVideos);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                ContentValues cv = new ContentValues();
                for (String key : bundle.keySet()) {
                    cv.put(key, bundle.getString(key));
                }
                String idGrupo = cv.getAsString("idGrupo"); //id del grupo
                String nombreGrupo = cv.getAsString("nombreGrupo");
                String cantidadVideos = cv.getAsString("cantidadVideos");

                fetchVideos(idGrupo);
            }
        }
    }

    private void fetchVideos(String idGrupo) {
        String url = BASE_URL + "?idGrupo=" + idGrupo;

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        listaDeVideos.clear();
                        for (int i = 0; i < response.length(); i++) {
                            try {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String idVideo = jsonObject.getString("IdVideo");
                                String titulo = jsonObject.getString("Titulo");
                                listaDeVideos.add(new VideoModel(idVideo, titulo, "", "Grupo de video"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                        adpVideos.notifyDataSetChanged();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(GrupoVideoActivity.this, "Error al obtener videos", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opciones_grupo_video, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.agregar_video) {
            Intent intent = new Intent(GrupoVideoActivity.this, SubirVideoActivity.class);
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }
}
