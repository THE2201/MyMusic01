package com.example.mymusic.Activities.Solicitudes;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.mymusic.Fragments.MisGruposAudioFragment;
import com.example.mymusic.Fragments.MisGruposVideoFragment;
import com.example.mymusic.databinding.ActivityMisGruposBinding;
import com.example.mymusic.databinding.ActivitySolicitudesBinding;
import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mymusic.Activities.Inicio.DashboardActivity;
import com.example.mymusic.Activities.Usuario.PerfilActivity;
import com.example.mymusic.Fragments.Audio_fragme;
import com.example.mymusic.Fragments.Video_fragme;
import com.example.mymusic.R;

public class SolicitudesActivity extends AppCompatActivity {
    ActivitySolicitudesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = ActivitySolicitudesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new Audio_fragme());

        binding.bottomNavSolicitudes.setOnItemSelectedListener(item -> {
            if(item.getItemId()==R.id.audiobottom){
                replaceFragment(new Audio_fragme());
            } else if (item.getItemId()==R.id.videobottom) {
                replaceFragment(new Video_fragme());
            } else{
                replaceFragment(new Audio_fragme());
            }
            return true;
        });

    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opciones_solicitudes, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.crear_solicitud_menu) {
            startActivity(new Intent(SolicitudesActivity.this, CrearSolicitudActivity.class));
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_solicitudes, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}