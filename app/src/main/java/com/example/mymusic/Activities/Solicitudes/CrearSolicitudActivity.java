package com.example.mymusic.Activities.Solicitudes;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymusic.Activities.Usuario.EditarPerfilActivity;
import com.example.mymusic.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CrearSolicitudActivity extends AppCompatActivity {

    Button crear_solicitud, cancelar;
    EditText titulo, comentario;
    Spinner spinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_crear_solicitud);

        crear_solicitud = findViewById(R.id.crear_solicitud);
        cancelar = findViewById(R.id.cancelar);
        titulo = findViewById(R.id.titulo);
        comentario = findViewById(R.id.comentario);
        comentario.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_IME_MULTI_LINE);
        comentario.setSingleLine(false);
        comentario.setVerticalScrollBarEnabled(true);
        comentario.setMinLines(5);

        spinner = findViewById(R.id.spinner);

        crear_solicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vTitulo = titulo.getText().toString().trim();
                String vComentario = comentario.getText().toString().trim();
                String vTipo = spinner.getSelectedItem().toString().trim();

                if (TextUtils.isEmpty(vTitulo)) {
                    titulo.setError("Titulo requerido");
                    Toast.makeText(CrearSolicitudActivity.this, "Rellenar campo indicado", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(vComentario)) {
                    comentario.setError("Comentario requerido");
                    Toast.makeText(CrearSolicitudActivity.this, "Rellenar campo indicado", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(vTipo) || vTipo.equals("Seleccione")) {
                    Toast.makeText(CrearSolicitudActivity.this, "Seleccione tipo de solicitud", Toast.LENGTH_SHORT).show();
                    return;
                }

                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if (user != null) {
                    String firebaseUid = user.getUid();
                    guardarSolicitud(vTitulo, vComentario, vTipo, firebaseUid);
                } else {
                    Toast.makeText(CrearSolicitudActivity.this, "Usuario no autenticado.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CrearSolicitudActivity.this, SolicitudesActivity.class));
            }
        });


        // Configurar el adaptador para el Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,R.array.dropdown_items, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                //Toast.makeText(CrearSolicitudActivity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });


    }

    private void guardarSolicitud(String titulo, String comentario, String tipoSolicitud, String firebaseUid) {
        String url = "http://34.125.8.146/crearSolicitud.php";
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Guardando solicitud...");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            boolean success = jsonObject.getBoolean("success");
                            if (success) {
                                Toast.makeText(CrearSolicitudActivity.this, "Solicitud creada exitosamente.", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(CrearSolicitudActivity.this, SolicitudesActivity.class));
                            } else {
                                String message = jsonObject.getString("message");
                                Toast.makeText(CrearSolicitudActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(CrearSolicitudActivity.this, "Error al procesar la respuesta.", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        progressDialog.dismiss();
                        Toast.makeText(CrearSolicitudActivity.this, "Error al realizar la solicitud.", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("titulo", titulo);
                params.put("comentario", comentario);
                params.put("firebaseUid", firebaseUid);
                params.put("tipoSolicitud", tipoSolicitud);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    // ... (dentro del onClick de crear_solicitud)


}