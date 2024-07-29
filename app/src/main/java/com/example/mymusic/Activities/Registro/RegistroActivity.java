package com.example.mymusic.Activities.Registro;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.mymusic.Activities.Inicio.DashboardActivity;
import com.example.mymusic.Activities.Inicio.LoginActivity;
import com.example.mymusic.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class RegistroActivity extends AppCompatActivity {
    EditText nombre, apellido, usuario, correo, contrasena;
    Button btRegistrar, btTengoCuenta;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        iniciarBg();

        nombre = findViewById(R.id.nombre);
        apellido = findViewById(R.id.apellido);
        usuario = findViewById(R.id.usuario);
        correo = findViewById(R.id.correo);
        contrasena = findViewById(R.id.contrasena);
        btRegistrar = findViewById(R.id.btRegistrar);
        btTengoCuenta = findViewById(R.id.btTengoCuenta);
        fAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressBar);

        if(fAuth.getCurrentUser() != null){
            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
            finish();
        }

        btTengoCuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
            }
        });

        btRegistrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vNombre = nombre.getText().toString().trim();
                String vApellido = apellido.getText().toString().trim();
                String vUsuario = usuario.getText().toString().trim();

                String vCorreo = correo.getText().toString().trim();
                String vPasswd = contrasena.getText().toString().trim();

                if(TextUtils.isEmpty(vNombre)){
                    nombre.setError("Nombre es requerido");
                    return;
                }
                if(TextUtils.isEmpty(vApellido)){
                    apellido.setError("Apellido es requerido");
                    return;
                }
                if(TextUtils.isEmpty(vUsuario)){
                    usuario.setError("Usuario es requerido");
                    return;
                }


                if(TextUtils.isEmpty(vCorreo)){
                    correo.setError("Correo es requerido");
                    return;
                }

                if(TextUtils.isEmpty(vPasswd)){
                    contrasena.setError("Contrasena requerida");
                    return;
                }

                if(vPasswd.length() < 8){
                    contrasena.setError("Debes tener 8 caracteres como minimo en contrasena!");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //Registrar usuario
                fAuth.createUserWithEmailAndPassword(vCorreo, vPasswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           Toast.makeText(RegistroActivity.this, "Cuenta creada con exito!", Toast.LENGTH_SHORT).show();
                           startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                       }else {
                           Toast.makeText(RegistroActivity.this, "Error "+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                           progressBar.setVisibility(View.GONE);
                       }
                    }
                });
            }
        });


    }

    private void iniciarBg() {
        ConstraintLayout constraintLayout = findViewById(R.id.reg);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();
    }

}