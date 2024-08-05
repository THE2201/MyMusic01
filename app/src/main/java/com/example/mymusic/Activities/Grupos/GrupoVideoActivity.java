package com.example.mymusic.Activities.Grupos;

import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.Adapters.AudiosGrupoAdapter;
import com.example.mymusic.Adapters.VideoGrupoAdapter;
import com.example.mymusic.Models.AudioModel;
import com.example.mymusic.Models.GrabacionesModel;
import com.example.mymusic.Models.VideoModel;
import com.example.mymusic.R;

import java.util.ArrayList;
import java.util.List;

public class GrupoVideoActivity extends AppCompatActivity {
    Intent intent;
    private RecyclerView recyclerViewVideos;
    private VideoGrupoAdapter adpVideos;
    private List<VideoModel> listaDeVideos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grupo_video);

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
                String cantidadVideos = cv.getAsString("cantidadVideos");
                Toast.makeText(this, idGrupo+" "+nombreGrupo+" "+cantidadVideos, Toast.LENGTH_SHORT).show();
            }
        }

        recyclerViewVideos = findViewById(R.id.recyclerViewVideos);
        recyclerViewVideos.setLayoutManager(new LinearLayoutManager(this));
        listaDeVideos = new ArrayList<>();
        listaDeVideos.add(new VideoModel("1", "Video Prueba1", "Chayanne", "2:08"));
        listaDeVideos.add(new VideoModel("2", "Video Prueba22", "cantante", "2:10"));

        adpVideos = new VideoGrupoAdapter(this, listaDeVideos);
        recyclerViewVideos.setAdapter(adpVideos);


    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opciones_grupo_video,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.agregar_video){
            intent = new Intent(GrupoVideoActivity.this, SubirVideoActivity.class);
            startActivity(intent);
        } else{
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}