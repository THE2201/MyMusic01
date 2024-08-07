package com.example.mymusic.Adapters;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;
import static androidx.core.content.ContextCompat.getSystemService;
import static androidx.core.content.ContextCompat.startActivity;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Spinner;
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
import com.example.mymusic.Activities.Grabadora.GrabacionesActivity;
import com.example.mymusic.Activities.Grabadora.PlayGrabacionesActivity;
import com.example.mymusic.Activities.Grupos.SubirAudioActivity;
import com.example.mymusic.Activities.Playback.PlayAudioActivity;
import com.example.mymusic.MainActivity;
import com.example.mymusic.Models.GrabacionesModel;
import com.example.mymusic.R;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GrabacionesAdapter extends RecyclerView.Adapter<GrabacionesAdapter.ViewHolder>{

    private List<GrabacionesModel> listaGrabs;
    private Context context;

    //para spinner al compartir a grupo
    private Spinner spinner;
    private List<String> spinnerItems = new ArrayList<>();
    private ArrayAdapter<String> adapter;


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
        holder.bt_share_recording.setOnClickListener(v -> {
//            GrabacionesActivity grabAct = new GrabacionesActivity();
            compartirGrabacion(grabacion.getIdGrabacion(),grabacion.getTituloGrabacion());});
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

    private void eliminarGrabacion(String idGrabacion, int position) {

        String url = "http://34.125.8.146/eliminarGrabacion.php";

        // Crear la solicitud POST
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                // Eliminar la grabación de la lista y actualizar la vista
                                listaGrabs.remove(position);
                                notifyItemRemoved(position);
                                notifyItemRangeChanged(position, listaGrabs.size());
                                Toast.makeText(context, "Grabación eliminada exitosamente.", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Error al eliminar la grabación.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "Error en la respuesta del servidor.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(context, "Error en la solicitud de eliminación.", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idGrabacion", idGrabacion);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(stringRequest);
    }


//    private void compartirGrabacion(String id,String titulo, int position) {
//        Toast.makeText(context, "Compartir: "+id, Toast.LENGTH_SHORT).show();
//
//
//    }

    public void compartirGrabacion(String idGrabacion , String titulo){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View dialogView = inflater.inflate(R.layout.dialog_spinner, null);

        spinner = dialogView.findViewById(R.id.spinnerListaGrupos);
        adapter = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, spinnerItems);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        for (int i=1; i<10; i++){
            spinnerItems.add("Grupo"+ i);
        }

        spinner.setAdapter(adapter);

//        llenarListaGrupos();

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Compartir a grupo")
                .setView(dialogView)
                .setMessage("Compartiendo "+ titulo + " hacia")
                .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String selectedOption = spinner.getSelectedItem().toString();
                        Toast.makeText(context, "Selected: " + selectedOption, Toast.LENGTH_SHORT).show();

/////////////////////////
                        ContentValues cv = new ContentValues();
                        cv.put("IdGrabacion", idGrabacion);
                        cv.put("tituloGrabacion", titulo);

                        if (context != null) {
                            Intent intent = new Intent(context, SubirAudioActivity.class);
                            for (String key : cv.keySet()) {
                                intent.putExtra(key, cv.getAsString(key));
                            }
                            context.startActivity(intent);
                        } else {
                            Log.e("GrabAdapterContext", "Contexto nulo");
                        }
                    }
                })
                .setNegativeButton("Cancelar", null)
                .create()
                .show();
    }

    private void llenarListaGrupos() {

        //Las debe pedir en base al idSQL
        // SELECT IdGrupo, TituloGrupo FROM tb_grupos WHERE IdUsuarioCreadordeGrupo="elUsuario actual del dashboard"

        String url = "http://34.125.8.146/gruposDeUsuario.php";
        RequestQueue queue = Volley.newRequestQueue(context);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);
                            spinnerItems.clear();
                            for (int i = 0; i < jsonArray.length(); i++) {
                                spinnerItems.add(jsonArray.getString(i));
                            }
                            adapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(context, "No se ha podido agregar la lista de grupos", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(context, "Error buscando lista data", Toast.LENGTH_SHORT).show();
            }
        });

        queue.add(stringRequest);
    }


    private void reproducirGrabacion(String id, String titulo, String fechaGrabacion) {
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
