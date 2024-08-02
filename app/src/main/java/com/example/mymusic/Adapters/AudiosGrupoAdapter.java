package com.example.mymusic.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.R;

public class AudiosGrupoAdapter extends RecyclerView.Adapter<AudiosGrupoAdapter.ViewHolder>{
    @NonNull
    @Override
    public AudiosGrupoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull AudiosGrupoAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_audio_api, titulo_audio, autor_audio, duration_audio;
        ImageView caratula_audio;
        ImageButton bt_play_audio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id_audio_api = itemView.findViewById(R.id.id_audio_api);
            titulo_audio = itemView.findViewById(R.id.titulo_audio);
            autor_audio = itemView.findViewById(R.id.autor_audio);
            duration_audio = itemView.findViewById(R.id.duration_audio);
            caratula_audio = itemView.findViewById(R.id.caratula_audio);
            bt_play_audio = itemView.findViewById(R.id.bt_play_audio);
        }
    }
}
