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

import com.example.mymusic.R;

public class GrupoVideoActivity extends AppCompatActivity {
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
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
        }
       else{
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}