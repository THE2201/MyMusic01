package com.example.mymusic.Activities.Grupos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymusic.Activities.Inicio.DashboardActivity;
import com.example.mymusic.Activities.Solicitudes.CrearSolicitudActivity;
import com.example.mymusic.Activities.Usuario.PerfilActivity;
import com.example.mymusic.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CrearGrupoActivity extends AppCompatActivity {

    ImageView CaratulaGrupo;
    EditText NombreGrupo;
    Spinner spinner_crear_grupo;
    Button btn_seleccionar_caratula, btn_crear_grupo, btn_cancelar_crear_grupo;
    String base64String;

    private static final int REQUEST_IMAGE_PICK = 2;
    private String currentPhotoPath;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_grupo);

        CaratulaGrupo = findViewById(R.id.CaratulaGrupo);
        NombreGrupo = findViewById(R.id.NombreGrupo);
        spinner_crear_grupo = findViewById(R.id.spinner_crear_grupo);
        btn_seleccionar_caratula = findViewById(R.id.btn_seleccionar_caratula);
        btn_crear_grupo = findViewById(R.id.btn_crear_grupo);
        btn_cancelar_crear_grupo = findViewById(R.id.btn_cancelar_crear_grupo);

        requestQueue = Volley.newRequestQueue(this);

        btn_crear_grupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                crearGrupo();
            }
        });

        btn_seleccionar_caratula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchPickPictureIntent();
            }
        });

        btn_cancelar_crear_grupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CrearGrupoActivity.this, DashboardActivity.class));
            }
        });

        // Selecciones de spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dropdown_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_crear_grupo.setAdapter(adapter);
        spinner_crear_grupo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                // No hacer nada por ahora
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // No hacer nada por ahora
            }
        });
    }

    private void crearGrupo() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user == null) {
            Toast.makeText(this, "Usuario no autenticado", Toast.LENGTH_SHORT).show();
            return;
        }

        String vNombreGrupo = NombreGrupo.getText().toString().trim();
        String vTipo = spinner_crear_grupo.getSelectedItem().toString().trim();

        if (base64String == null) {
            Toast.makeText(CrearGrupoActivity.this, "No ha seleccionado foto", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(vNombreGrupo)) {
            NombreGrupo.setError("Nombre requerido");
            Toast.makeText(CrearGrupoActivity.this, "Rellenar campo indicado", Toast.LENGTH_SHORT).show();
            return;
        }

        if (vTipo.equals("Seleccionar")) {
            Toast.makeText(CrearGrupoActivity.this, "Seleccione tipo de grupo", Toast.LENGTH_SHORT).show();
            return;
        }

        String firebaseUid = user.getUid();

        JSONObject postData = new JSONObject();
        try {
            postData.put("NombreGrupo", vNombreGrupo);
            postData.put("FirebaseUid", firebaseUid);
            postData.put("TipoGrupo", vTipo);
            postData.put("CaratulaGrupo", base64String);
            postData.put("EstadoGrupo", 1); // Estado activo al crear
        } catch (JSONException e) {
            e.printStackTrace();
        }

        String url = "http://34.125.8.146/create_group.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            Toast.makeText(CrearGrupoActivity.this, message, Toast.LENGTH_SHORT).show();
                            if (message.equals("Grupo creado exitosamente.")) {
                                startActivity(new Intent(CrearGrupoActivity.this, DashboardActivity.class));
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(CrearGrupoActivity.this, "Error al procesar la respuesta", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("Volley", error.toString());
                        Toast.makeText(CrearGrupoActivity.this, "Error al crear el grupo", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        requestQueue.add(jsonObjectRequest);
    }

    private void dispatchPickPictureIntent() {
        Intent pickPictureIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPictureIntent, REQUEST_IMAGE_PICK);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICK && resultCode == RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                CaratulaGrupo.setImageBitmap(bitmap);
                base64String = convertToBase64(bitmap);
                Log.d("BASE64", base64String);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String convertToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
    }

    private File createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(null);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }
}