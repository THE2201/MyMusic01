package com.example.mymusic.Adapters;

import static androidx.core.content.ContextCompat.startActivity;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.Activities.Grabadora.GrabacionesActivity;
import com.example.mymusic.Activities.Grabadora.PlayGrabacionesActivity;
import com.example.mymusic.Activities.Playback.PlayAudioActivity;
import com.example.mymusic.MainActivity;
import com.example.mymusic.Models.GrabacionesModel;
import com.example.mymusic.R;

import java.io.File;
import java.util.List;

public class GrabacionesAdapter extends RecyclerView.Adapter<GrabacionesAdapter.ViewHolder>{

    private List<GrabacionesModel> listaGrabs;
    private Context context;

    public GrabacionesAdapter(Context context, List<GrabacionesModel> listaGrabs){
        this.context = context;
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


        holder.itemView.setOnClickListener(v -> {reproducirGrabacion(grabacion.getIdGrabacion(), grabacion.getTituloGrabacion(), grabacion.getFechaGrabacion());});
        holder.btn_delete_recording.setOnClickListener(v -> {eliminarGrabacion(grabacion.getIdGrabacion(), position);});
        holder.btn_delete_recording.setOnClickListener(v -> {compartirGrabacion(grabacion.getIdGrabacion(), position);});
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

    private void eliminarGrabacion(String id, int position) {
        Toast.makeText(context, "Eliminar: "+id, Toast.LENGTH_SHORT).show();

    }

    private void compartirGrabacion(String id, int position) {
        Toast.makeText(context, "Compartir: "+id, Toast.LENGTH_SHORT).show();

    }

    private void reproducirGrabacion(String id, String titulo, String fechaGrabacion) {
//        Toast.makeText(context, "Play"+ id, Toast.LENGTH_SHORT).show();
        ContentValues cv = new ContentValues();
        cv.put("IdGrabacion", id);
        cv.put("tituloGrabacion", titulo);
        cv.put("fechaGrabacion", fechaGrabacion);

        if (context != null) {
            Intent intent = new Intent(context, PlayGrabacionesActivity.class);
            for (String key : cv.keySet()) {
                intent.putExtra(key, cv.getAsString(key));
            }
            context.startActivity(intent);
        } else {
            Log.e("GrabAdapterContext", "Contexto nulo");
        }
    }


}
