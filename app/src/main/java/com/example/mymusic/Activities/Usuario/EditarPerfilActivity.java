package com.example.mymusic.Activities.Usuario;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.mymusic.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class EditarPerfilActivity extends AppCompatActivity {

    private EditText etPassword,etPassword1;
    private Button btnTogglePassword, btConfirmar, btCancelar;
    private boolean isPasswordVisible = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_editar_perfil);

        etPassword1 = findViewById(R.id.etPassword1);
        etPassword = findViewById(R.id.etPassword);
        btnTogglePassword = findViewById(R.id.btnTogglePassword);
        btCancelar = findViewById(R.id.btCancelarPasswd);
        btConfirmar = findViewById(R.id.btCambiarPasswd);

        btConfirmar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
                
                if(isEqualPass()){
                    String nueva = etPassword.getText().toString().trim();

                    if(minimoCaracteres(nueva)){
                        updContrasena(nueva);
                    }else{
                        Toast.makeText(EditarPerfilActivity.this, "Debe ser mayor a 8 caracteres.", Toast.LENGTH_SHORT).show();
                    }
                    
                    
                }else{
                    Toast.makeText(EditarPerfilActivity.this, "No conciden", Toast.LENGTH_SHORT).show();
                }

            }
        });

        btCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditarPerfilActivity.this, PerfilActivity.class));
            }
        });

        btnTogglePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isPasswordVisible) {
                    // Hide password
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    etPassword1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    btnTogglePassword.setText("Mostrar");
                } else {
                    // Show password
                    etPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    etPassword1.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    btnTogglePassword.setText("Ocultar");
                }
                // Move the cursor to the end of the text
                etPassword.setSelection(etPassword.getText().length());
                isPasswordVisible = !isPasswordVisible;
            }
        });

    }

    private boolean minimoCaracteres(String input) {
        return input != null && input.length() > 8;
    }


    private boolean isEqualPass() {
        return etPassword.getText().toString().trim().equals(etPassword1.getText().toString().trim());
    }

    private void updContrasena(String newPassword) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user != null) {
            user.updatePassword(newPassword)
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                // Password updated
                                Toast.makeText(EditarPerfilActivity.this, "Contrasena actualizada exitosamente!", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EditarPerfilActivity.this, PerfilActivity.class));
                            } else {
                                // If update fails, display a message to the user.
                                Toast.makeText(EditarPerfilActivity.this, "Error al actualizar", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
    }

}