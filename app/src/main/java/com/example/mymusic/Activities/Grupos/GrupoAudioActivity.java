package com.example.mymusic.Activities.Grupos;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymusic.Adapters.AudiosGrupoAdapter;
import com.example.mymusic.Models.AudioModel;
import com.example.mymusic.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrupoAudioActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://34.125.8.146/";
    private AudiosGrupoAdapter adpAudios;
    private List<AudioModel> listaDeAudios;
    private RecyclerView recyclerViewAudios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo_audio);

        recyclerViewAudios = findViewById(R.id.recyclerViewAudios);
        recyclerViewAudios.setLayoutManager(new LinearLayoutManager(this));

        listaDeAudios = new ArrayList<>();
        adpAudios = new AudiosGrupoAdapter(this, listaDeAudios);
        recyclerViewAudios.setAdapter(adpAudios);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                String idGrupo = bundle.getString("idGrupo");
                String nombreGrupo = bundle.getString("nombreGrupo");
                String cantidadAudios = bundle.getString("cantidadAudios");

                consultarAudios(idGrupo);
            }
        }
    }

    private void consultarAudios(String idGrupo) {
        String url = BASE_URL + "consultarAudios.php";
        Log.d("GrupoAudioActivity", "ID del Grupo: " + idGrupo);

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        listaDeAudios.clear();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject audioJson = jsonArray.getJSONObject(i);
                            String idAudio = audioJson.getString("IdAudio");
                            String tituloAudio = audioJson.getString("Titulo");
                            String autorAudio = audioJson.optString("Autor", "Desconocido");
                            String duracionAudio = audioJson.optString("Duracion", "Desconocida");

                            // Crear AudioModel sin audioData
                            AudioModel audio = new AudioModel(idAudio, tituloAudio, autorAudio, duracionAudio);
                            listaDeAudios.add(audio);
                        }
                        adpAudios.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            Toast.makeText(GrupoAudioActivity.this, "Error al consultar audios", Toast.LENGTH_SHORT).show();
            Log.e("GrupoAudioActivity", "Error: " + error.getMessage());
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idGrupo", idGrupo);
                return params;
            }
        };

        queue.add(stringRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opciones_grupo, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.agregar_audio) {
            Intent intent = new Intent(GrupoAudioActivity.this, SubirAudioActivity.class);
            String idGrupo = getIntent().getStringExtra("idGrupo");
            intent.putExtra("idGrupo", idGrupo);
            startActivity(intent);
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
