package com.example.mymusic.Activities.Solicitudes;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
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

import com.example.mymusic.Activities.Usuario.EditarPerfilActivity;
import com.example.mymusic.R;

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
        spinner = findViewById(R.id.spinner);

        crear_solicitud.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String vTitulo = titulo.getText().toString().trim();
                String vComentario = comentario.getText().toString().trim();
                String vTipo = spinner.getSelectedItem().toString().trim();

                if(TextUtils.isEmpty(vTitulo)){
                    titulo.setError("Titulo requerido");
                    Toast.makeText(CrearSolicitudActivity.this, "Rellenar campo indicado", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(vComentario)){
                    comentario.setError("Comentario requerido");
                    Toast.makeText(CrearSolicitudActivity.this, "Rellenar campo indicado", Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(vTipo)|| vTipo.equals("Seleccione")){


                    Toast.makeText(CrearSolicitudActivity.this, "Seleccione tipo de solicitud", Toast.LENGTH_SHORT).show();
                    return;
                }

                //guardarSolicitud(vTitulo, vComentario, vTipo, 125);

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
                Toast.makeText(CrearSolicitudActivity.this, "Selected: " + selectedItem, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                // Another interface callback
            }
        });


    }
}