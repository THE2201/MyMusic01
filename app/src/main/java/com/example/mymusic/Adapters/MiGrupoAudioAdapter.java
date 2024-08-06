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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymusic.Activities.Grupos.GrupoAudioActivity;
import com.example.mymusic.Models.MiGrupoAudioModel;
import com.example.mymusic.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MiGrupoAudioAdapter extends RecyclerView.Adapter<MiGrupoAudioAdapter.ViewHolder> {

    private List<MiGrupoAudioModel> ListaMiGrupoAudio;
    private Context context;
    private RequestQueue requestQueue;

    public MiGrupoAudioAdapter(Context context, List<MiGrupoAudioModel> ListaMiGrupoAudio) {
        this.context = context;
        this.ListaMiGrupoAudio = ListaMiGrupoAudio;
        requestQueue = Volley.newRequestQueue(context);
    }

    @NonNull
    @Override
    public MiGrupoAudioAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_migrupo_audio, parent, false);
        return new MiGrupoAudioAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MiGrupoAudioAdapter.ViewHolder holder, int position) {
        MiGrupoAudioModel miGrupoAudioModel = ListaMiGrupoAudio.get(position);
        holder.id_migrupoa_api.setText(miGrupoAudioModel.getIdGrupo());
        holder.titulo_migrupoa.setText(miGrupoAudioModel.getNombreGrupo());
        holder.cantidad_audios.setText(miGrupoAudioModel.getCantidadAudios());

        // Decode and set the caratula image
        String base64Image = miGrupoAudioModel.getCaratulaGrupo();
        if (base64Image != null && !base64Image.isEmpty()) {
            byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.caratula_grupoa.setImageBitmap(decodedByte);
        } else {
            holder.caratula_grupoa.setImageResource(R.drawable.audio_default_24); // Default image resource
        }

        holder.bt_salir_gaudio.setOnClickListener(v -> EliminarGrupo(miGrupoAudioModel.getIdGrupo(), position));
        holder.itemView.setOnClickListener(v -> irAGrupo(miGrupoAudioModel.getIdGrupo(), miGrupoAudioModel.getNombreGrupo(), miGrupoAudioModel.getCantidadAudios()));
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
            Log.e("MiGrupoAudioAdapter", "Contexto nulo");
        }
    }

    public void EliminarGrupo(String idGrupo, int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage("¿Eliminar grupo?")
                .setPositiveButton("Confirmar", (dialog, id) -> {
                    // Send request to delete group
                    StringRequest stringRequest = new StringRequest(
                            Request.Method.POST,
                            "http://34.125.8.146/eliminar_grupo.php",
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    try {
                                        JSONObject jsonResponse = new JSONObject(response);
                                        String message = jsonResponse.getString("message");
                                        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();

                                        // Remove group from list and notify adapter
                                        ListaMiGrupoAudio.remove(position);
                                        notifyItemRemoved(position);
                                        notifyItemRangeChanged(position, ListaMiGrupoAudio.size());
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                        Toast.makeText(context, "Error al eliminar el grupo.", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(context, "Error en la solicitud: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<>();
                            params.put("IdGrupo", idGrupo);
                            return params;
                        }
                    };

                    requestQueue.add(stringRequest);
                })
                .setNegativeButton("Cancelar", (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    @Override
    public int getItemCount() {
        return ListaMiGrupoAudio.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView id_migrupoa_api, titulo_migrupoa, cantidad_audios;
        ImageButton bt_salir_gaudio;
        ImageView caratula_grupoa;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            id_migrupoa_api = itemView.findViewById(R.id.id_migrupoa_api);
            titulo_migrupoa = itemView.findViewById(R.id.titulo_migrupoa);
            cantidad_audios = itemView.findViewById(R.id.cantidad_audios);
            bt_salir_gaudio = itemView.findViewById(R.id.bt_salir_gaudio);
            caratula_grupoa = itemView.findViewById(R.id.caratula_grupoa);
        }
    }
}
