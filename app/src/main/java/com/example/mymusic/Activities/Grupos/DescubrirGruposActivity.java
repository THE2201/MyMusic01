package com.example.mymusic.Activities.Grupos;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mymusic.R;
import com.example.mymusic.databinding.ActivityDescubrirGruposBinding;
import com.example.mymusic.Fragments.DescubrirAudioFragment;
import com.example.mymusic.Fragments.DescubrirVideoFragment;

public class DescubrirGruposActivity extends AppCompatActivity {
    ActivityDescubrirGruposBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDescubrirGruposBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new DescubrirAudioFragment());

        binding.bottomNavDescubrir.setOnItemSelectedListener(item -> {
            if(item.getItemId()==R.id.audiobottom){
                replaceFragment(new DescubrirAudioFragment());
            } else if (item.getItemId()==R.id.videobottom) {
                replaceFragment(new DescubrirVideoFragment());
            } else{
                replaceFragment(new DescubrirAudioFragment());
            }
            return true;
        });
    }


    private void replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_descubrir, fragment);
        fragmentTransaction.commit();
    }
}



