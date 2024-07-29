package com.example.mymusic.Activities.Grupos;

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
        else if(item.getItemId()==R.id.abandonar_grupo_video){
//            intent = new Intent(GrupoAudioActivity.this, getParent().getClass());
//            startActivity(intent);
            Toast.makeText(this, "Abandonar gVideo", Toast.LENGTH_SHORT).show();
        } else{
            return super.onOptionsItemSelected(item);
        }
        return true;
    }
}