package com.example.mymusic.Activities.Inicio;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mymusic.Activities.Registro.RegistroActivity;
import com.example.mymusic.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    EditText logCorreo, logPasswd;
    Button btLogin, btRegistro, btOlvide;
    ProgressBar progressBar2;

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        iniciarBg();

        logCorreo = findViewById(R.id.logCorreo);
        logPasswd = findViewById(R.id.logPasswd);
        btLogin = findViewById(R.id.btLogin);
        btRegistro = findViewById(R.id.btRegistro);
        btOlvide = findViewById(R.id.btOlvide);
        progressBar2 = findViewById(R.id.progressBar2);
        fAuth = FirebaseAuth.getInstance();

        btRegistro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), RegistroActivity.class));
            }
        });
        btOlvide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ExtravioCuentaActivity.class));
            }
        });

        btLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vCorreo = logCorreo.getText().toString().trim();
                String vPasswd = logPasswd.getText().toString().trim();

                if(TextUtils.isEmpty(vCorreo)){
                    logCorreo.setError("Correo requerido");
                    Toast.makeText(LoginActivity.this, "Rellenar campo indicado", Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(vPasswd)){
                    logPasswd.setError("Contrasena requerida");
                    Toast.makeText(LoginActivity.this, "Rellenar campo indicado", Toast.LENGTH_SHORT).show();
                    return;
                }
                progressBar2.setVisibility(View.VISIBLE);
                //Auth el usuario

                fAuth.signInWithEmailAndPassword(vCorreo, vPasswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
                            Toast.makeText(LoginActivity.this, "Usuario actual: "+ fAuth.getCurrentUser(), Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                        }else{
                            Toast.makeText(LoginActivity.this, "Error "+ task.getException().getMessage(), Toast.LENGTH_LONG).show();
                            progressBar2.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });
    }

    private void iniciarBg() {
        ConstraintLayout constraintLayout = findViewById(R.id.main);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();
    }
}