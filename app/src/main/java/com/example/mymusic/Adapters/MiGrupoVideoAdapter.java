package com.example.mymusic.Adapters;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.Activities.Grupos.GrupoVideoActivity;
import com.example.mymusic.Models.MiGrupoVideoModel;
import com.example.mymusic.R;

import java.util.List;

public class MiGrupoVideoAdapter extends RecyclerView.Adapter<MiGrupoVideoAdapter.ViewHolder> {
    private List<MiGrupoVideoModel> ListaMiGrupoVideo;
    private Context context;

    public MiGrupoVideoAdapter(Context context, List<MiGrupoVideoModel> ListaMiGrupoVideo) {
        this.context = context;
        this.ListaMiGrupoVideo = ListaMiGrupoVideo;
    }

    @NonNull
    @Override
    public MiGrupoVideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_migrupo_video, parent, false);
        return new MiGrupoVideoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MiGrupoVideoAdapter.ViewHolder holder, int position) {
        MiGrupoVideoModel miGrupoVideoModel = ListaMiGrupoVideo.get(position);
        holder.id_migrupov_api.setText(miGrupoVideoModel.getIdGrupo());
        holder.titulo_migrupov.setText(miGrupoVideoModel.getNombreGrupo());
        holder.cantidad_videos.setText(miGrupoVideoModel.getCantidadVideos());

        // Decode base64 encoded image and set to ImageView
        String caratulaBase64 = miGrupoVideoModel.getCaratulaGrupo();
        if (caratulaBase64 != null && !caratulaBase64.isEmpty()) {
            byte[] decodedString = Base64.decode(caratulaBase64, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.caratula_grupov.setImageBitmap(decodedByte);
        } else {
            holder.caratula_grupov.setImageResource(R.drawable.groups_24);
        }

        holder.bt_salir_gvideo.setOnClickListener(v -> EliminarGrupo(miGrupoVideoModel.getIdGrupo(), position));
        holder.itemView.setOnClickListener(v -> irAGrupo(miGrupoVideoModel.getIdGrupo(), miGrupoVideoModel.getNombreGrupo(), miGrupoVideoModel.getCantidadVideos()));
    }

    public void irAGrupo(String idGrupo, String nombreGrupo, String cantidadVideos) {
        ContentValues cv = new ContentValues();
        cv.put("idGrupo", idGrupo);
        cv.put("nombreGrupo", nombreGrupo);
        cv.put("cantidadVideos", cantidadVideos);

        if (context != null) {
            Intent intent = new Intent(context, GrupoVideoActivity.class);
            for (String key : cv.keySet()) {
                intent.putExtra(key, cv.getAsString(key));
            }
            context.startActivity(intent);
        } else {
            Log.e("MiGrupoVideoAdapter", "Contexto nulo");
        }
    }

    public void EliminarGrupo(String idGrupo, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("¿Eliminar grupo?")
                .setPositiveButton("Confirmar", (dialog, id) -> {
                    ListaMiGrupoVideo.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, ListaMiGrupoVideo.size());
                })
                .setNegativeButton("Cancelar", (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return ListaMiGrupoVideo.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_migrupov_api, titulo_migrupov, cantidad_videos;
        ImageButton bt_salir_gvideo;
        ImageView caratula_grupov;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id_migrupov_api = itemView.findViewById(R.id.id_migrupov_api);
            titulo_migrupov = itemView.findViewById(R.id.titulo_migrupov);
            cantidad_videos = itemView.findViewById(R.id.cantidad_videos);
            bt_salir_gvideo = itemView.findViewById(R.id.bt_salir_gvideo);
            caratula_grupov = itemView.findViewById(R.id.caratula_grupov);
        }
    }
}
