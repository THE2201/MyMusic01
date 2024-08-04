package com.example.mymusic.Activities.Usuario;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.mymusic.Activities.Inicio.LoginActivity;
import com.example.mymusic.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class EliminarCuentaActivity extends AppCompatActivity {

    private static final String TAG = "EliminarCuentaActivity";
    private FirebaseAuth mAuth;

    private Button btn_confirmar_eliminar, cancelarEliminacion;
    private EditText editEliminar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eliminar_cuenta);

        editEliminar = findViewById(R.id.editEliminar);
        btn_confirmar_eliminar = findViewById(R.id.btn_confirmar_eliminar);
        cancelarEliminacion = findViewById(R.id.cancelarEliminacion);

        cancelarEliminacion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EliminarCuentaActivity.this, PerfilActivity.class));
            }
        });

        mAuth = FirebaseAuth.getInstance();

        btn_confirmar_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String vConfirmText = editEliminar.getText().toString().trim();

                if(TextUtils.isEmpty(vConfirmText)){
                    editEliminar.setError("Texto confirmacion vacio");
                    Toast.makeText(EliminarCuentaActivity.this, "Rellenar campo indicado", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (vConfirmText.equals("Eliminar")){
                    eliminarCuenta();
                }else{
                    Toast.makeText(EliminarCuentaActivity.this, "Si no escribe: Eliminar, entonces no", Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });



    }

    private void eliminarCuenta() {
        FirebaseUser user = mAuth.getCurrentUser();

        if (user != null) {
            user.delete()
                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(EliminarCuentaActivity.this, "Se elimino la cuenta", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EliminarCuentaActivity.this, LoginActivity.class));
                                finish();
                            } else {
                                Log.w(TAG, "Falla al eliminar cuenta", task.getException());
                                Toast.makeText(EliminarCuentaActivity.this, "Falla al eliminar cuenta", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } else {
            Log.d(TAG, "No hay una sesion activa");
        }
    }

}