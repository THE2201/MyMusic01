package com.example.mymusic.Activities.Registro;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.example.mymusic.Activities.Inicio.DashboardActivity;
import com.example.mymusic.Activities.Inicio.LoginActivity;
import com.example.mymusic.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import com.example.mymusic.Network.UsuariosRest;


public class RegistroActivity extends AppCompatActivity {
    EditText nombre, apellido, usuario, correo, contrasena;
    Button btRegistrar, btTengoCuenta;
    FirebaseAuth fAuth;
    ProgressBar progressBar;

    private UsuariosRest usuariosRest;

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

        usuariosRest = new UsuariosRest(this);

        if (fAuth.getCurrentUser() != null) {
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

                if (TextUtils.isEmpty(vNombre)) {
                    nombre.setError("Nombre es requerido");
                    return;
                }
                if (TextUtils.isEmpty(vApellido)) {
                    apellido.setError("Apellido es requerido");
                    return;
                }
                if (TextUtils.isEmpty(vUsuario)) {
                    usuario.setError("Usuario es requerido");
                    return;
                }

                if (TextUtils.isEmpty(vCorreo)) {
                    correo.setError("Correo es requerido");
                    return;
                }

                if (TextUtils.isEmpty(vPasswd)) {
                    contrasena.setError("Contraseña requerida");
                    return;
                }

                if (vPasswd.length() < 8) {
                    contrasena.setError("Debes tener 8 caracteres como minimo en contraseña!");
                    return;
                }

                // Validar nombre de usuario
                usuariosRest.validarUsuario(vUsuario, new UsuariosRest.UsuarioRestListener() {
                    @Override
                    public void onResponse(boolean exists) {
                        if (exists) {
                            usuario.setError("El nombre de usuario ya está registrado");
                            return;
                        }

                        // Validar correo
                        usuariosRest.validarCorreo(vCorreo, new UsuariosRest.UsuarioRestListener() {
                            @Override
                            public void onResponse(boolean exists) {
                                if (exists) {
                                    correo.setError("El correo ya está enlazado a una cuenta.");
                                } else {
                                    //registro
                                    registrarUsuario(vNombre, vApellido, vUsuario, vCorreo, vPasswd);
                                }
                            }

                            @Override
                            public void onError(String error) {
                                Toast.makeText(RegistroActivity.this, "Error en la validación del correo: " + error, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    @Override
                    public void onError(String error) {
                        Toast.makeText(RegistroActivity.this, "Error en la validación del usuario: " + error, Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private void registrarUsuario(String vNombre, String vApellido, String vUsuario, String vCorreo, String vPasswd) {
        fAuth.createUserWithEmailAndPassword(vCorreo, vPasswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    FirebaseUser user = fAuth.getCurrentUser();
                    sendVerificationToEmail(user);
                    String uid = user.getUid();

                    //Si usuarioCreado -> DashboardActivity
                    progressBar.setVisibility(View.VISIBLE);
                    // Crear un usuario
                    usuariosRest.createUser(uid, vNombre, vApellido, vUsuario, vCorreo, "", new UsuariosRest.VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            //Toast.makeText(RegistroActivity.this, "creado", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void onError(String result) {
                            //Toast.makeText(RegistroActivity.this, "cuenta no creada", Toast.LENGTH_SHORT).show();
                        }
                    });

                    //Toast
                    Toast.makeText(RegistroActivity.this, "Cuenta creada con exito!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));

                } else {
                    Toast.makeText(RegistroActivity.this, "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
    }

    private void sendVerificationToEmail(FirebaseUser user) {
        user.sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful()){
                    Toast.makeText(RegistroActivity.this, "Verifica tu cuenta en el correo", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(RegistroActivity.this, "Error al enviar correo de verificacion", Toast.LENGTH_SHORT).show();
                }
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