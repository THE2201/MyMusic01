package com.example.mymusic.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.R;

public class GrupoDisponibleAudioAdapter extends RecyclerView.Adapter<GrupoDisponibleAudioAdapter.ViewHolder>{
    @NonNull
    @Override
    public GrupoDisponibleAudioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull GrupoDisponibleAudioAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_grupoa_api, titulo_adisponible, autor_gaudio, cantidad_audios_disp;
        ImageView caratula_grupoa_disp;
        ImageButton bt_entrar_grupoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id_grupoa_api = itemView.findViewById(R.id.id_grupoa_api);
            titulo_adisponible = itemView.findViewById(R.id.titulo_adisponible);
            autor_gaudio = itemView.findViewById(R.id.autor_gaudio);
            cantidad_audios_disp = itemView.findViewById(R.id.cantidad_audios_disp);
            caratula_grupoa_disp = itemView.findViewById(R.id.caratula_grupoa_disp);
            bt_entrar_grupoa = itemView.findViewById(R.id.bt_entrar_grupoa);
        }
    }
}
