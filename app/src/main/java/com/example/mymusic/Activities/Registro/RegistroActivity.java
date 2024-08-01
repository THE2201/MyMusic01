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
import com.android.volley.toolbox.StringRequest;
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
                    contrasena.setError("Contrasena requerida");
                    return;
                }

                if (vPasswd.length() < 8) {
                    contrasena.setError("Debes tener 8 caracteres como minimo en contrasena!");
                    return;
                }

                if (usuarioTieneCuenta(vUsuario)) {
                    usuario.setError("nombre de usuario ya esta tomado");
                    return;
                }

                if (correoTieneCuenta(vCorreo)) {
                    correo.setError("El correo ya esta enlazado a cuenta");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);

                //Registrar usuario
                fAuth.createUserWithEmailAndPassword(vCorreo, vPasswd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            String uid = user.getUid();


                            //Si usuarioCreado -> DashboardActivity
                            crearUsuarioSQL(uid, vNombre, vApellido, vUsuario, vCorreo, "noInsertada");
                            Toast.makeText(RegistroActivity.this, "Cuenta creada con exito!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(), DashboardActivity.class));

                        } else {
                            Toast.makeText(RegistroActivity.this, "Error " + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });
            }
        });


    }

    private boolean usuarioTieneCuenta(String usuario) {
        String url = "http://34.125.8.146/verificacionUsuario.php";

        JSONObject postData = new JSONObject();
        try {
            postData.put("usuario", usuario);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean exists = response.getBoolean("exists");
                            if (exists) {
                                // Email exists
                                Toast.makeText(RegistroActivity.this, "Usuario ya tomado", Toast.LENGTH_SHORT).show();
                                finish(); //No estoy muy seguro de este Finish();
                            } else {
                                // Email does not exist
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(RegistroActivity.this, "Error servidor", Toast.LENGTH_SHORT).show();
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

        return false;
    }

    private boolean correoTieneCuenta(String correo) {
        String url = "http://34.125.8.146/verificacionCorreo.php";

        JSONObject postData = new JSONObject();
        try {
            postData.put("correo", correo);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.POST, url, postData, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            boolean exists = response.getBoolean("exists");
                            if (exists) {
                                // Email exists
                                Toast.makeText(RegistroActivity.this, "Correo ya asociado a cuenta", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                // Email does not exist


                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Handle error
                        Toast.makeText(RegistroActivity.this, "Error servidor", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);

        return false;
    }

    private void crearUsuarioSQL(String uid, String vNombre, String vApellido, String vUsuario, String vCorreo, String noInsertada) {

        String url = "http://34.125.8.146/crearUsuario.php";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("FirebaseUid", uid);
            jsonBody.put("Nombre", vNombre);
            jsonBody.put("Apellido", vApellido);
            jsonBody.put("Usuario", vUsuario);
            jsonBody.put("Correo", vCorreo);
            jsonBody.put("FotoUsuario", "noInsertada");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                response -> {
                    Log.d("Response", response.toString());

                    Toast.makeText(RegistroActivity.this, "Cuenta creada con exito!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getApplicationContext(), DashboardActivity.class));
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Error: ", error.toString());
            }
        }) {
            @Override
            public Map<String, String> getHeaders() {
                Map<String, String> headers = new HashMap<>();
                headers.put("Content-Type", "application/json");
                return headers;
            }
        };
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jsonObjectRequest);
    }


    private void iniciarBg() {
        ConstraintLayout constraintLayout = findViewById(R.id.reg);
        AnimationDrawable animationDrawable = (AnimationDrawable) constraintLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();
    }

}