package com.example.mymusic.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.Models.GrabacionesModel;
import com.example.mymusic.R;

import java.util.List;

public class GrabacionesAdapter extends RecyclerView.Adapter<GrabacionesAdapter.ViewHolder>{

    private List<GrabacionesModel> listaGrabs;

    public GrabacionesAdapter(List<GrabacionesModel> listaGrabs){
        this.listaGrabs = listaGrabs;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_audio_recording, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        GrabacionesModel grabacion = listaGrabs.get(position);
        holder.id_recording_api.setText(grabacion.getIdGrabacion());
        holder.title_recording.setText(grabacion.getTituloGrabacion());
        holder.recording_timestamp.setText(grabacion.getFechaGrabacion());
        holder.duration_recording.setText(grabacion.getDuracionGrabacion());
    }

    @Override
    public int getItemCount() {
        return listaGrabs.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_recording_api, title_recording, recording_timestamp, duration_recording;
        ImageView icon_recorder;
        ImageButton btn_delete_recording, bt_share_recording;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id_recording_api = itemView.findViewById(R.id.id_recording_api);
            title_recording = itemView.findViewById(R.id.title_recording);
            recording_timestamp = itemView.findViewById(R.id.recording_timestamp);
            duration_recording = itemView.findViewById(R.id.duration_recording);
            icon_recorder = itemView.findViewById(R.id.icon_recorder);
            btn_delete_recording = itemView.findViewById(R.id.btn_delete_recording);
            bt_share_recording = itemView.findViewById(R.id.bt_share_recording);
        }
    }

}
