package com.example.mymusic.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.mymusic.R;

public class Video_fragme extends Fragment {

    public Video_fragme() {
        //contructor vacio

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.video_desing, container, false);
    }
}
