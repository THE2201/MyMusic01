package com.example.mymusic.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.R;

public class MiGrupoVideoAdapter extends RecyclerView.Adapter<MiGrupoVideoAdapter.ViewHolder>{
    @NonNull
    @Override
    public MiGrupoVideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MiGrupoVideoAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_migrupov_api,titulo_migrupov, miembros_gvideo, cantidad_videos;
        ImageButton bt_salir_gvideo;
        ImageView caratula_grupov;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id_migrupov_api = itemView.findViewById(R.id.id_migrupov_api);
            titulo_migrupov = itemView.findViewById(R.id.titulo_migrupov);
            miembros_gvideo = itemView.findViewById(R.id.miembros_gvideo);
            cantidad_videos = itemView.findViewById(R.id.cantidad_videos);
            bt_salir_gvideo = itemView.findViewById(R.id.bt_salir_gvideo);
            caratula_grupov = itemView.findViewById(R.id.caratula_grupov);

        }
    }
}
