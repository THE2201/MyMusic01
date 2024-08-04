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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mymusic.R;

public class GrupoAudioActivity extends AppCompatActivity {
    Intent intent;
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
                Toast.makeText(this, idGrupo+" "+nombreGrupo+" "+cantidadAudios, Toast.LENGTH_SHORT).show();
            }
        }
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opciones_grupo,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
       if(item.getItemId()==R.id.agregar_audio){
           intent = new Intent(GrupoAudioActivity.this, SubirAudioActivity.class);
           startActivity(intent);
       } else{
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}