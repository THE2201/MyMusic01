package com.example.mymusic.Activities.Solicitudes;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.mymusic.databinding.ActivitySolicitudesBinding;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mymusic.Fragments.SolicitudesAudioFragment;
import com.example.mymusic.Fragments.SolicitudesVideoFragment;
import com.example.mymusic.R;

public class SolicitudesActivity extends AppCompatActivity {
    ActivitySolicitudesBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        binding = ActivitySolicitudesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new SolicitudesAudioFragment());

        binding.bottomNavSolicitudes.setOnItemSelectedListener(item -> {
            if(item.getItemId()==R.id.audiobottom){
                replaceFragment(new SolicitudesAudioFragment());
            } else if (item.getItemId()==R.id.videobottom) {
                replaceFragment(new SolicitudesVideoFragment());
            } else{
                replaceFragment(new SolicitudesAudioFragment());
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
        fragmentTransaction.commit();
    }
}