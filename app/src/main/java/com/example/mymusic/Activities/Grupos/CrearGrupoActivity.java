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

import com.example.mymusic.Activities.Inicio.DashboardActivity;
import com.example.mymusic.Activities.Solicitudes.CrearSolicitudActivity;
import com.example.mymusic.Activities.Usuario.PerfilActivity;
import com.example.mymusic.R;

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


        btn_crear_grupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vNombreGrupo = NombreGrupo.getText().toString().trim();
                String vTipo = spinner_crear_grupo.getSelectedItem().toString().trim();

                if(base64String==null){
                    Toast.makeText(CrearGrupoActivity.this, "No ha sleccionado foto", Toast.LENGTH_SHORT).show();
                }


                if(TextUtils.isEmpty(vNombreGrupo)){
                    NombreGrupo.setError("nombre requerido");
                    Toast.makeText(CrearGrupoActivity.this, "Rellenar campo indicado", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(vTipo.equals("Seleccionar")){
                    Toast.makeText(CrearGrupoActivity.this, "Seleccione tipo de grupo", Toast.LENGTH_SHORT).show();
                    return;
                }



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

        //Selecciones de spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.dropdown_items, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_crear_grupo.setAdapter(adapter);
        spinner_crear_grupo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = parent.getItemAtPosition(position).toString();
                Toast.makeText(CrearGrupoActivity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });

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