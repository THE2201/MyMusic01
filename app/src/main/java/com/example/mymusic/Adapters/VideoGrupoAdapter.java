package com.example.mymusic.Adapters;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.R;

public class VideoGrupoAdapter extends RecyclerView.Adapter<VideoGrupoAdapter.ViewHolder>{
    @NonNull
    @Override
    public VideoGrupoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull VideoGrupoAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
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

}
