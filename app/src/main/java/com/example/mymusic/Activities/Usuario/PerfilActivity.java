package com.example.mymusic.Activities.Usuario;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymusic.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class PerfilActivity extends AppCompatActivity {
    ImageView imageView;
    EditText usuario, nombre, apellido;
    Button guardarCambios, btcambiarfoto, cancelar, passwd;

    private static final int REQUEST_IMAGE_PICK = 2;
    private RequestQueue requestQueue;
    private Bitmap currentImageBitmap;
    private FirebaseAuth fAuth;
    private String firebaseUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_perfil);

        usuario = findViewById(R.id.usuarioperfil);
        nombre = findViewById(R.id.nombreperfil);
        apellido = findViewById(R.id.apellidoperfil);
        passwd = findViewById(R.id.passwdperfil);
        imageView = findViewById(R.id.image_perfil);

        guardarCambios = findViewById(R.id.btguardar_perfil);
        btcambiarfoto = findViewById(R.id.btcambiarfoto);
        cancelar = findViewById(R.id.cancelarperfil);

        requestQueue = Volley.newRequestQueue(this);
        fAuth = FirebaseAuth.getInstance();

        // Obtener FirebaseUid del usuario actual
        FirebaseUser user = fAuth.getCurrentUser();
        if (user != null) {
            firebaseUid = user.getUid();
            fetchUserProfile(firebaseUid);
        }

        passwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EditarPerfilActivity.class));
            }
        });

        guardarCambios.setOnClickListener(v -> {
            String nUsuario = usuario.getText().toString().trim();
            String nNombre = nombre.getText().toString().trim();
            String nApellido = apellido.getText().toString().trim();
            String nPassword = passwd.getText().toString().trim();
            String foto = convertToBase64(currentImageBitmap);

            if (TextUtils.isEmpty(nUsuario)) {
                usuario.setError("Usuario requerido");
                Toast.makeText(PerfilActivity.this, "Rellenar campo indicado", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(nNombre)) {
                nombre.setError("Nombre requerido");
                Toast.makeText(PerfilActivity.this, "Rellenar campo indicado", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(nApellido)) {
                apellido.setError("Apellido requerido");
                Toast.makeText(PerfilActivity.this, "Rellenar campo indicado", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(nPassword)) {
                passwd.setError("Contraseña vacía");
                Toast.makeText(PerfilActivity.this, "Rellenar campo indicado", Toast.LENGTH_SHORT).show();
                return;
            }

            updateUserProfile(firebaseUid, nUsuario, nNombre, nApellido, foto);
        });

        btcambiarfoto.setOnClickListener(v -> dispatchPickPictureIntent());

        cancelar.setOnClickListener(v -> {
            usuario.setText("");
            apellido.setText("");
            nombre.setText("");
            imageView.setImageDrawable(ContextCompat.getDrawable(PerfilActivity.this, R.drawable.user_24));

            usuario.setEnabled(false);
            nombre.setEnabled(false);
            apellido.setEnabled(false);
            passwd.setVisibility(View.INVISIBLE);
            passwd.setEnabled(false);
            guardarCambios.setVisibility(View.INVISIBLE);
            btcambiarfoto.setVisibility(View.INVISIBLE);
            cancelar.setVisibility(View.INVISIBLE);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opciones_perfil, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.editar_perfil) {
            usuario.setEnabled(true);
            nombre.setEnabled(true);
            apellido.setEnabled(true);
            passwd.setVisibility(View.VISIBLE);
            passwd.setEnabled(true);
            guardarCambios.setVisibility(View.VISIBLE);
            btcambiarfoto.setVisibility(View.VISIBLE);
            cancelar.setVisibility(View.VISIBLE);

            // Tomar los datos actuales / existentes
            if (firebaseUid != null) {
                fetchUserProfile(firebaseUid);
            }
        } else if (item.getItemId() == R.id.eliminar_cuenta) {
            startActivity(new Intent(PerfilActivity.this, EliminarCuentaActivity.class));
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void fetchUserProfile(String firebaseUid) {
        String url = "http://34.125.8.146/get_user_profile.php?uid=" + firebaseUid;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                response -> {
                    try {
                        if (response.has("success") && response.getBoolean("success")) {
                            JSONObject data = response.getJSONObject("data");
                            usuario.setText(data.getString("Usuario"));
                            nombre.setText(data.getString("Nombre"));
                            apellido.setText(data.getString("Apellido"));
                            String base64Image = data.getString("FotoUsuario");
                            if (!base64Image.isEmpty()) {
                                byte[] decodedString = Base64.decode(base64Image, Base64.DEFAULT);
                                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                                imageView.setImageBitmap(decodedByte);
                                currentImageBitmap = decodedByte;
                            }
                        } else {
                            Toast.makeText(PerfilActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(PerfilActivity.this, "Error al procesar la respuesta", Toast.LENGTH_LONG).show();
                    }
                },
                error -> Toast.makeText(PerfilActivity.this, "Error de red", Toast.LENGTH_LONG).show()
        );

        requestQueue.add(jsonObjectRequest);
    }


    private void updateUserProfile(String firebaseUid, String usuario, String nombre, String apellido, String foto) {
        String url = "http://34.125.8.146/update_user_profile.php";

        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("firebaseUid", firebaseUid);
            jsonBody.put("usuario", usuario);
            jsonBody.put("nombre", nombre);
            jsonBody.put("apellido", apellido);
            jsonBody.put("foto", foto != null ? foto : ""); // Maneja el caso de null aquí
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonBody,
                response -> {
                    try {
                        if (response.has("message")) {
                            Toast.makeText(PerfilActivity.this, response.getString("message"), Toast.LENGTH_LONG).show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(PerfilActivity.this, "Error al procesar la respuesta", Toast.LENGTH_LONG).show();
                    }
                },
                error -> Toast.makeText(PerfilActivity.this, "Error de red", Toast.LENGTH_LONG).show()
        );

        requestQueue.add(jsonObjectRequest);
    }

    private String convertToBase64(Bitmap bitmap) {
        if (bitmap == null) {
            return "";
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(byteArray, Base64.DEFAULT);
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
                imageView.setImageBitmap(bitmap);
                currentImageBitmap = bitmap;
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al cargar la imagen", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
