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
import androidx.recyclerview.widget.RecyclerView;

import com.example.mymusic.Activities.Grupos.GrupoAudioActivity;
import com.example.mymusic.Models.GrupoModel;
import com.example.mymusic.R;

import java.util.List;

public class GrupoDisponibleAudioAdapter extends RecyclerView.Adapter<GrupoDisponibleAudioAdapter.ViewHolder> {

    private List<GrupoModel> ListaGrupoaDisponible;
    private Context context;

    public GrupoDisponibleAudioAdapter(Context context, List<GrupoModel> ListaGrupoaDisponible) {
        this.context = context;
        this.ListaGrupoaDisponible = ListaGrupoaDisponible;
    }

    @NonNull
    @Override
    public GrupoDisponibleAudioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_grupo_disponible_audio, parent, false);
        return new GrupoDisponibleAudioAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GrupoDisponibleAudioAdapter.ViewHolder holder, int position) {
        GrupoModel grupoModel = ListaGrupoaDisponible.get(position);
        holder.id_grupoa_api.setText(grupoModel.getIdGrupo());
        holder.titulo_adisponible.setText(grupoModel.getNombreGrupo());
        holder.cantidad_audios_disp.setText(grupoModel.getCantidadAudios());

        // Decode and set the caratula image
        String base64Image = grupoModel.getCaratulaGrupo();
        if (base64Image != null && !base64Image.isEmpty()) {
            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.caratula_grupoa_disp.setImageBitmap(decodedByte);
        } else {
            holder.caratula_grupoa_disp.setImageResource(R.drawable.audio_default_24); // Default image resource
        }

        holder.bt_entrar_grupoa.setOnClickListener(v -> irAGrupo(grupoModel.getIdGrupo(), grupoModel.getNombreGrupo(), grupoModel.getCantidadAudios()));
    }

    public void irAGrupo(String idGrupo, String nombreGrupo, String cantidadAudios) {
        ContentValues cv = new ContentValues();
        cv.put("idGrupo", idGrupo);
        cv.put("nombreGrupo", nombreGrupo);
        cv.put("cantidadAudios", cantidadAudios);

        if (context != null) {
            Intent intent = new Intent(context, GrupoAudioActivity.class);
            for (String key : cv.keySet()) {
                intent.putExtra(key, cv.getAsString(key));
            }
            context.startActivity(intent);
        } else {
            Log.e("GrupoDisponibleAudioAdapter", "Contexto nulo");
        }
    }

    @Override
    public int getItemCount() {
        return ListaGrupoaDisponible.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_grupoa_api, titulo_adisponible, autor_gaudio, cantidad_audios_disp;
        ImageView caratula_grupoa_disp;
        ImageButton bt_entrar_grupoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            id_grupoa_api = itemView.findViewById(R.id.id_grupoa_api);
            titulo_adisponible = itemView.findViewById(R.id.titulo_adisponible);
            autor_gaudio = itemView.findViewById(R.id.autor_gaudio);
            cantidad_audios_disp = itemView.findViewById(R.id.cantidad_audios_disp);
            caratula_grupoa_disp = itemView.findViewById(R.id.caratula_grupoa_disp);
            bt_entrar_grupoa = itemView.findViewById(R.id.bt_entrar_grupoa);
        }
    }
}
