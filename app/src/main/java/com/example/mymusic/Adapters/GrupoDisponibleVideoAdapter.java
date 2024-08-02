package com.example.mymusic.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.R;

public class GrupoDisponibleVideoAdapter extends RecyclerView.Adapter<GrupoDisponibleVideoAdapter.ViewHolder>{
    @NonNull
    @Override
    public GrupoDisponibleVideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull GrupoDisponibleVideoAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_grupov_api, titulo_vdisponible, autor_gvideo,cantidad_videos_disp;
        ImageView caratula_grupov_disp;
        ImageButton bt_entrar_grupov;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id_grupov_api = itemView.findViewById(R.id.id_grupov_api);
            titulo_vdisponible = itemView.findViewById(R.id.titulo_vdisponible);
            autor_gvideo = itemView.findViewById(R.id.autor_gvideo);
            cantidad_videos_disp = itemView.findViewById(R.id.cantidad_videos_disp);
            caratula_grupov_disp = itemView.findViewById(R.id.caratula_grupov_disp);
            bt_entrar_grupov = itemView.findViewById(R.id.bt_entrar_grupov);
        }
    }
}
