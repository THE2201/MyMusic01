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

import com.example.mymusic.Adapters.AudiosGrupoAdapter;
import com.example.mymusic.Models.AudioModel;
import com.example.mymusic.R;

import java.util.ArrayList;
import java.util.List;

public class GrupoAudioActivity extends AppCompatActivity {

    private AudiosGrupoAdapter adpAudios;
    private List<AudioModel> listaDeAudios;
    private RecyclerView recyclerViewAudios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo_audio);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                ContentValues cv = new ContentValues();
                for (String key : bundle.keySet()) {
                    cv.put(key, bundle.getString(key));
                }
                String idGrupo = cv.getAsString("idGrupo");
                String nombreGrupo = cv.getAsString("nombreGrupo");
                String cantidadAudios = cv.getAsString("cantidadAudios");
                Toast.makeText(this, idGrupo + " " + nombreGrupo + " " + cantidadAudios, Toast.LENGTH_SHORT).show();
            }
        }

        recyclerViewAudios = findViewById(R.id.recyclerViewAudios);
        recyclerViewAudios.setLayoutManager(new LinearLayoutManager(this));

        listaDeAudios = new ArrayList<>();
        listaDeAudios.add(new AudioModel("1", "Audio Prueba1", "Chayanne", "2:08"));
        listaDeAudios.add(new AudioModel("2", "Audio Prueba22", "cantante", "2:10"));

        adpAudios = new AudiosGrupoAdapter(this, listaDeAudios);
        recyclerViewAudios.setAdapter(adpAudios);
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
            startActivity(intent);
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}
