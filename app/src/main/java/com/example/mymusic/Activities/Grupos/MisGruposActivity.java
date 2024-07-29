package com.example.mymusic.Activities.Grupos;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mymusic.R;
import com.example.mymusic.databinding.ActivityMisGruposBinding;
import com.example.mymusic.Fragments.MisGruposAudioFragment;
import com.example.mymusic.Fragments.MisGruposVideoFragment;

public class MisGruposActivity extends AppCompatActivity {
    ActivityMisGruposBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMisGruposBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new MisGruposAudioFragment());

        binding.bottomNavMisGrupos.setOnItemSelectedListener(item -> {
            if(item.getItemId()==R.id.audiobottom){
                replaceFragment(new MisGruposAudioFragment());
            } else if (item.getItemId()==R.id.videobottom) {
                replaceFragment(new MisGruposVideoFragment());
            } else{
                replaceFragment(new MisGruposAudioFragment());
            }
            return true;
        });

    }

    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_mis_grupos, fragment);
        fragmentTransaction.commit();
    }
}