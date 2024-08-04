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

import com.example.mymusic.Activities.Grupos.GrupoAudioActivity;
import com.example.mymusic.Activities.Grupos.GrupoVideoActivity;
import com.example.mymusic.Models.GrupoModel;
import com.example.mymusic.Models.GrupoModelVideo;
import com.example.mymusic.R;

import java.util.List;

public class GrupoDisponibleVideoAdapter extends RecyclerView.Adapter<GrupoDisponibleVideoAdapter.ViewHolder>{
    private List<GrupoModelVideo> ListaGrupovDisponible;
    private Context context;

    @NonNull
    @Override
    public GrupoDisponibleVideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grupo_disponible_video, parent, false);
        return new GrupoDisponibleVideoAdapter.ViewHolder(view);
    }

    public GrupoDisponibleVideoAdapter(Context context, List<GrupoModelVideo> ListaGrupovDisponible){
        this.context = context;
        this.ListaGrupovDisponible = ListaGrupovDisponible;
    }

    @Override
    public void onBindViewHolder(@NonNull GrupoDisponibleVideoAdapter.ViewHolder holder, int position) {
        GrupoModelVideo grupoModelVideo = ListaGrupovDisponible.get(position);
        holder.id_grupov_api.setText(grupoModelVideo.getIdGrupo());
        holder.titulo_vdisponible.setText(grupoModelVideo.getNombreGrupo());
        holder.cantidad_videos_disp.setText(grupoModelVideo.getCantidadVideos());

        holder.bt_entrar_grupov.setOnClickListener(v -> {
            irAGrupo(grupoModelVideo.getIdGrupo(), grupoModelVideo.getNombreGrupo(), grupoModelVideo.getCantidadVideos());
        });

    }

    public void irAGrupo(String idGrupo, String nombreGrupo, String cantidadVideos) {
        ContentValues cv = new ContentValues();
        cv.put("idGrupo", idGrupo);
        cv.put("nombreGrupo", nombreGrupo);
        cv.put("cantidadVideos",cantidadVideos);

        if (context != null) {
            Intent intent = new Intent(context, GrupoVideoActivity.class);
            for (String key : cv.keySet()) {
                intent.putExtra(key, cv.getAsString(key));
            }
            context.startActivity(intent);
        } else {
            Log.e("GrupoVideoAdapter", "Contexto nulo");
        }
    }

    @Override
    public int getItemCount() {
        return ListaGrupovDisponible.size();
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
