package com.example.mymusic.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.Activities.Playback.PlayAudioActivity;
import com.example.mymusic.Models.AudioModel;
import com.example.mymusic.R;

import java.util.List;

public class AudiosGrupoAdapter extends RecyclerView.Adapter<AudiosGrupoAdapter.ViewHolder> {
    private List<AudioModel> listaDeAudios;
    private Context context;

    public AudiosGrupoAdapter(Context context, List<AudioModel> listaDeAudios) {
        this.context = context;
        this.listaDeAudios = listaDeAudios;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_audio_grupo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AudiosGrupoAdapter.ViewHolder holder, int position) {
        AudioModel audioModel = listaDeAudios.get(position);
        holder.id_audio_api.setText(audioModel.getIdAudio());
        holder.titulo_audio.setText(audioModel.getTituloAudio());

        holder.bt_play_audio.setOnClickListener(v -> reproducirAudio(audioModel.getIdAudio(), audioModel.getTituloAudio()));
    }

    @Override
    public int getItemCount() {
        return listaDeAudios.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_audio_api, titulo_audio;
        ImageView caratula_audio;
        ImageButton bt_play_audio;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id_audio_api = itemView.findViewById(R.id.id_audio_api);
            titulo_audio = itemView.findViewById(R.id.titulo_audio);
            caratula_audio = itemView.findViewById(R.id.caratula_audio);
            bt_play_audio = itemView.findViewById(R.id.bt_play_audio);
        }
    }

    private void reproducirAudio(String id, String titulo) {
        Intent intent = new Intent(context, PlayAudioActivity.class);
        intent.putExtra("idAudio", id);
        intent.putExtra("tituloAudio", titulo);
        if (context != null) {
            context.startActivity(intent);
        }
    }
}
