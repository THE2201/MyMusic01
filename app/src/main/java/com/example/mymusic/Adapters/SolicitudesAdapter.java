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

public class SolicitudesAdapter extends RecyclerView.Adapter<SolicitudesAdapter.ViewHolder> {

    private List<SolicitudModel> listaSolicitudAudio;
    private Context context;
    private RequestQueue requestQueue;

    public SolicitudesAdapter(Context context, List<SolicitudModel> listaSolicitudAudio) {
        this.context = context;
        this.listaSolicitudAudio = listaSolicitudAudio;
        requestQueue = Volley.newRequestQueue(context);
    }

    @NonNull
    @Override
    public SolicitudesAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_solicitud, parent, false);
        return new SolicitudesAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SolicitudesAdapter.ViewHolder holder, int position) {
        SolicitudModel solicitud = listaSolicitudAudio.get(position);

        holder.tituloSolicitud.setText(solicitud.getTituloSolicitud());
        holder.fechaSolicitud.setText(solicitud.getFechaSolicitud());
        holder.usuarioSolicitud.setText(solicitud.getUsuarioSolicitud());
        holder.comentarioSolicitud.setText(solicitud.getComentarioSolicitud());

    }

    @Override
    public int getItemCount() {
        return listaSolicitudAudio.size();
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
