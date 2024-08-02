package com.example.mymusic.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.R;

public class MiGrupoAudioAdapter extends RecyclerView.Adapter<MiGrupoAudioAdapter.ViewHolder> {
    @NonNull
    @Override
    public MiGrupoAudioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MiGrupoAudioAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_migrupoa_api, titulo_migrupoa, miembros_gaudio, cantidad_audios;
        ImageButton bt_salir_gaudio;
        ImageView caratula_grupoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id_migrupoa_api = itemView.findViewById(R.id.id_migrupoa_api);
            titulo_migrupoa = itemView.findViewById(R.id.titulo_migrupoa);
            miembros_gaudio = itemView.findViewById(R.id.miembros_gaudio);
            cantidad_audios = itemView.findViewById(R.id.cantidad_audios);
            bt_salir_gaudio = itemView.findViewById(R.id.bt_salir_gaudio);
            caratula_grupoa = itemView.findViewById(R.id.caratula_grupoa);

        }
    }
}
