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

import com.example.mymusic.Activities.Playback.PlayAudioActivity;
import com.example.mymusic.Activities.Playback.PlayVideoActivity;
import com.example.mymusic.Models.AudioModel;
import com.example.mymusic.Models.VideoModel;
import com.example.mymusic.R;

import java.util.List;

public class VideoGrupoAdapter extends RecyclerView.Adapter<VideoGrupoAdapter.ViewHolder>{
    private List<VideoModel> listaDeVideos;
    private Context context;

    public VideoGrupoAdapter(Context context, List<VideoModel> listaDeVideos){
        this.context = context;
        this.listaDeVideos = listaDeVideos;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_video_grupo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VideoGrupoAdapter.ViewHolder holder, int position) {
        VideoModel videoModel = listaDeVideos.get(position);
        holder.id_video_api.setText(videoModel.getIdVideo());
        holder.titulo_video.setText(videoModel.getTituloVideo());
        holder.autor_video.setText(videoModel.getAutorVideo());
        holder.duration_video.setText(videoModel.getDuracionVideo());

        holder.bt_play_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reproducirVideo(videoModel.getIdVideo(),videoModel.getTituloVideo(),videoModel.getAutorVideo(), videoModel.getDuracionVideo());
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaDeVideos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_video_api, titulo_video, autor_video, duration_video;
        ImageView caratula_video;
        ImageButton bt_play_video;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id_video_api = itemView.findViewById(R.id.id_video_api);
            titulo_video = itemView.findViewById(R.id.titulo_video);
            autor_video = itemView.findViewById(R.id.autor_video);
            duration_video = itemView.findViewById(R.id.duration_video);
            caratula_video = itemView.findViewById(R.id.caratula_video);
            bt_play_video = itemView.findViewById(R.id.bt_play_video);
        }
    }

    private void reproducirVideo(String id, String titulo, String autor, String duracion) {
        ContentValues cv = new ContentValues();
        cv.put("idVideo", id);
        cv.put("tituloVideo", titulo);
        cv.put("autorVideo", autor);
        cv.put("duracionVideo", duracion);

        if (context != null) {
            Intent intent = new Intent(context, PlayVideoActivity.class);
            for (String key : cv.keySet()) {
                intent.putExtra(key, cv.getAsString(key));
            }
            context.startActivity(intent);
        } else {
            Log.e("VideoAdapterContext", "Contexto nulo");
        }
    }

}
