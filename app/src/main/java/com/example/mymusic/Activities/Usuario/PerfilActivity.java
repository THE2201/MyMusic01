package com.example.mymusic.Activities.Usuario;

import android.content.Intent;
import android.graphics.Bitmap;
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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mymusic.Activities.Grabadora.GrabadoraActivity;
import com.example.mymusic.Activities.Inicio.LoginActivity;
import com.example.mymusic.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PerfilActivity extends AppCompatActivity {
    ImageView imageView;
    EditText usuario,nombre, apellido;
    Button guardarCambios, btcambiarfoto, cancelar, passwd;

    //seleccion de imagen galeria
    private static final int REQUEST_IMAGE_PICK = 2;
    private String currentPhotoPath;

    String vNombre =" ",vApellido=" ",vUsuario=" ";
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

        passwd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), EditarPerfilActivity.class));
            }
        });

        btcambiarfoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchPickPictureIntent();
            }
        });

        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuario.setText(vUsuario);
                apellido.setText(vApellido);
                nombre.setText(vNombre);
                imageView.setImageDrawable(ContextCompat.getDrawable(PerfilActivity.this, R.drawable.user_24));

                usuario.setEnabled(false);
                nombre.setEnabled(false);
                apellido.setEnabled(false);
                passwd.setVisibility(View.INVISIBLE);
                passwd.setEnabled(false);
                guardarCambios.setVisibility(View.INVISIBLE);
                btcambiarfoto.setVisibility(View.INVISIBLE);
                cancelar.setVisibility(View.INVISIBLE);
            }
        });


        guardarCambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nUsuario = usuario.getText().toString().trim();
                String nNombre = nombre.getText().toString().trim();
                String nApellido = apellido.getText().toString().trim();
                String nPassword = passwd.getText().toString().trim();
                String foto;


                if(TextUtils.isEmpty(nUsuario)){
                    usuario.setError("Usuario requerido");
                    Toast.makeText(PerfilActivity.this, "Rellenar campo indicado", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(nNombre)){
                    nombre.setError("Usuario requerido");
                    Toast.makeText(PerfilActivity.this, "Rellenar campo indicado", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(nApellido)){
                    apellido.setError("apellido requerido");
                    Toast.makeText(PerfilActivity.this, "Rellenar campo indicado", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(nPassword)){
                    passwd.setError("Contrasena vacia");
                    Toast.makeText(PerfilActivity.this, "Rellenar campo indicado", Toast.LENGTH_SHORT).show();
                    return;
                }


            }
        });

    }
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opciones_perfil,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.editar_perfil){

            usuario.setEnabled(true);
            nombre.setEnabled(true);
            apellido.setEnabled(true);
            passwd.setVisibility(View.VISIBLE);
            passwd.setEnabled(true);
            guardarCambios.setVisibility(View.VISIBLE);
            btcambiarfoto.setVisibility(View.VISIBLE);
            cancelar.setVisibility(View.VISIBLE);

            //tomar los datos actuales / existentes
            vUsuario = usuario.getText().toString().trim();
            vNombre = nombre.getText().toString().trim();
            vApellido = apellido.getText().toString().trim();

        } else{
            return super.onOptionsItemSelected(item);
        }
        return true;
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
                String base64String = convertToBase64(bitmap);
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