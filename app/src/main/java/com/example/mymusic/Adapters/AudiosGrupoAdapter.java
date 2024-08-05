package com.example.mymusic.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.Activities.Grabadora.PlayGrabacionesActivity;
import com.example.mymusic.Activities.Playback.PlayAudioActivity;
import com.example.mymusic.Models.AudioModel;
import com.example.mymusic.Models.GrabacionesModel;
import com.example.mymusic.R;

import java.util.List;

public class AudiosGrupoAdapter extends RecyclerView.Adapter<AudiosGrupoAdapter.ViewHolder>{
    private List<AudioModel> listaDeAudios;
    private Context context;


    public AudiosGrupoAdapter(Context context, List<AudioModel> listaDeAudios){
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
        holder.autor_audio.setText(audioModel.getAutorAudio());
        holder.duration_audio.setText(audioModel.getDuracionAudio());

        holder.bt_play_audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reproducirAudio(audioModel.getIdAudio(),audioModel.getTituloAudio(),audioModel.getAutorAudio(), audioModel.getDuracionAudio());
            }
        });
    }

    @Override
    public int getItemCount() {
        return listaDeAudios.size();
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

    private void reproducirAudio(String id, String titulo, String autor, String duracion) {
        ContentValues cv = new ContentValues();
        cv.put("idAudio", id);
        cv.put("tituloAudio", titulo);
        cv.put("autorAudio", autor);
        cv.put("duracionAudio", duracion);

        if (context != null) {
            Intent intent = new Intent(context, PlayAudioActivity.class);
            for (String key : cv.keySet()) {
                intent.putExtra(key, cv.getAsString(key));
            }
            context.startActivity(intent);
        } else {
            Log.e("AudioAdapterContext", "Contexto nulo");
        }
    }
}
