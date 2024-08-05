package com.example.mymusic.Activities.Inicio;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import android.content.SharedPreferences;

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
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        iniciarBg();

        sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);
        // Aca reviso si usuario esta iniciado y lo manda diuan al dashboard
        if (sharedPreferences.getBoolean("isLoggedIn", false)) {
            startActivity(new Intent(LoginActivity.this, DashboardActivity.class));
        }

        logCorreo = findViewById(R.id.logCorreo);
        logPasswd = findViewById(R.id.logPasswd);
        btLogin = findViewById(R.id.btLogin);
        btRegistro = findViewById(R.id.btRegistro);
        btOlvide = findViewById(R.id.btOlvide);
        progressBar2 = findViewById(R.id.progressBar2);
        fAuth = FirebaseAuth.getInstance();


        logPasswd.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if ((event.getAction() == KeyEvent.ACTION_DOWN) && (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Start login process
                    login();
                    return true;
                }
                return false;
            }
        });

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
                login();
            }
        });
    }

    private void login() {
        String vCorreo = logCorreo.getText().toString().trim();
        String vPasswd = logPasswd.getText().toString().trim();

        if (TextUtils.isEmpty(vCorreo)) {
            logCorreo.setError("Correo requerido");
            Toast.makeText(LoginActivity.this, "Rellenar campo indicado", Toast.LENGTH_SHORT).show();
            return;
        }

        if (TextUtils.isEmpty(vPasswd)) {
            logPasswd.setError("Contrasena requerida");
            Toast.makeText(LoginActivity.this, "Rellenar campo indicado", Toast.LENGTH_SHORT).show();
            return;
        }
        progressBar2.setVisibility(View.VISIBLE);
        //Auth el usuario

        fAuth.signInWithEmailAndPassword(vCorreo, vPasswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
//                            Toast.makeText(LoginActivity.this, "Bienvenido", Toast.LENGTH_SHORT).show();
//                            Toast.makeText(LoginActivity.this, "Usuario actual: "+ fAuth.getCurrentUser(), Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                    finish();
                } else {
                    Toast.makeText(LoginActivity.this, "Error " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                    progressBar2.setVisibility(View.GONE);
                }
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