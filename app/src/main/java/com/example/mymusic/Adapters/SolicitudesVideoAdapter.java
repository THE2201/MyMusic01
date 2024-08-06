package com.example.mymusic.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.mymusic.Models.SolicitudModel;
import com.example.mymusic.R;

import java.util.List;

public class SolicitudesVideoAdapter extends RecyclerView.Adapter<SolicitudesVideoAdapter.ViewHolder>{
    private List<SolicitudModel> listaSolicitudVideo;
    private Context context;
    private RequestQueue requestQueue;


    public SolicitudesVideoAdapter(Context context, List<SolicitudModel> listaSolicitudVideo) {
        this.context = context;
        this.listaSolicitudVideo = listaSolicitudVideo;
        requestQueue = Volley.newRequestQueue(context);
    }

    @NonNull
    @Override
    public SolicitudesVideoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solicitud, parent, false);
        return new SolicitudesVideoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SolicitudesVideoAdapter.ViewHolder holder, int position) {
        SolicitudModel solicitud = listaSolicitudVideo.get(position);

        holder.tituloSolicitud.setText(solicitud.getTituloSolicitud());
        holder.fechaSolicitud.setText(solicitud.getFechaSolicitud());
        holder.usuarioSolicitud.setText(solicitud.getUsuarioSolicitud());
        holder.comentarioSolicitud.setText(solicitud.getComentarioSolicitud());


    }

    @Override
    public int getItemCount() {
        return listaSolicitudVideo.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tituloSolicitud,
                fechaSolicitud,
                usuarioSolicitud,
                comentarioSolicitud;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tituloSolicitud = itemView.findViewById(R.id.tituloSolicitud);
            fechaSolicitud = itemView.findViewById(R.id.fechaSolicitud);
            usuarioSolicitud = itemView.findViewById(R.id.usuarioSolicitud);
            comentarioSolicitud = itemView.findViewById(R.id.comentarioSolicitud);

        }
    }
}


