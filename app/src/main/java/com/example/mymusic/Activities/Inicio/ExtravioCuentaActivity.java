package com.example.mymusic.Activities.Inicio;

import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.mymusic.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ExtravioCuentaActivity extends AppCompatActivity {

    private EditText email;
    private Button enviar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_extravio_cuenta);

        iniciarBg();
        mAuth = FirebaseAuth.getInstance();

        email = findViewById(R.id.emailRecup);
        enviar = findViewById(R.id.btRecuperar);

        enviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vEmail = email.getText().toString().trim();
                if (vEmail.isEmpty()) {
                    email.setError("Email requerido");
                    Toast.makeText(ExtravioCuentaActivity.this, "Ingrese un correo", Toast.LENGTH_SHORT).show();
                    return;
                }
                sendPasswordResetEmail(vEmail);
            }
        });

    }
    private void iniciarBg() {
        ConstraintLayout constraintLayout = findViewById(R.id.ext);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();
    }

    private void sendPasswordResetEmail(String email) {
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(ExtravioCuentaActivity.this, "Se ha enviado un correo de restablecimiento", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(ExtravioCuentaActivity.this, "Error enviando al correo ingresado", Toast.LENGTH_SHORT).show();
                    Log.e("Extravio", "Error: " + task.getException().getMessage());
                }
            }
        });
    }
}