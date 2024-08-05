package com.example.mymusic.Activities.Inicio;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.example.mymusic.Activities.Grabadora.GrabadoraActivity;
import com.example.mymusic.Activities.Grupos.CrearGrupoActivity;
import com.example.mymusic.Activities.Grupos.DescubrirGruposActivity;
import com.example.mymusic.Activities.Grupos.MisGruposActivity;
import com.example.mymusic.Activities.Solicitudes.SolicitudesActivity;
import com.example.mymusic.Fragments.DescubrirVideoFragment;
import com.example.mymusic.Fragments.DescubrirAudioFragment;
import com.example.mymusic.Network.UsuariosRest;
import com.example.mymusic.R;
import com.example.mymusic.Activities.Usuario.PerfilActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

public class DashboardActivity extends AppCompatActivity {

    Intent intent;
    Button btn_descubrir_gaudios, btn_descubrir_gvideos, btn_descubrir_vermas;
    ImageButton btn_mis_grupos, btn_crear_grupo, btn_grabadora, btn_solicitudes;
    TextView txtbienvenida;
    FirebaseAuth fAuth;
    private SharedPreferences sharedPreferences;

    private UsuariosRest usuariosRest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_dashboard);

        btn_descubrir_gaudios = findViewById(R.id.btn_descubrir_gaudios);
        btn_descubrir_gvideos = findViewById(R.id.btn_descubrir_gvideos);
        btn_descubrir_vermas = findViewById(R.id.btn_descubrir_vermas);
        btn_mis_grupos = findViewById(R.id.btn_mis_grupos);
        btn_crear_grupo = findViewById(R.id.btn_crear_grupo);
        btn_grabadora = findViewById(R.id.btn_grabadora);
        btn_solicitudes = findViewById(R.id.btn_solicitudes);

        txtbienvenida = findViewById(R.id.txtbienvenida);

        fAuth = FirebaseAuth.getInstance();
        sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE);

        usuariosRest = new UsuariosRest(this);
        FirebaseUser user = fAuth.getCurrentUser();
        String uid = user.getUid();

        btn_descubrir_gaudios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, DescubrirGruposActivity.class));

            }
        });
        btn_descubrir_gvideos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, DescubrirGruposActivity.class));
            }
        });
        btn_descubrir_vermas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, DescubrirGruposActivity.class));
            }
        });
        btn_mis_grupos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, MisGruposActivity.class));
            }
        });
        btn_crear_grupo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, CrearGrupoActivity.class));
            }
        });
        btn_grabadora.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, GrabadoraActivity.class));
            }
        });
        btn_solicitudes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashboardActivity.this, SolicitudesActivity.class));
            }
        });


        //Toast.makeText(this, "Logged: "+FirebaseAuth.getInstance().getCurrentUser().toString(), Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, "uid: "+uid, Toast.LENGTH_SHORT).show();

        //Llamar aqui a la funcion para que en txtbienvenida se ponga el texto "BIENVENIDO + Usuario"
        if (user != null) {
            usuariosRest.getUser(uid, new UsuariosRest.VolleyCallback() {
                @Override
                public void onSuccess(String result) {
                    try {
                        JSONObject jsonObject = new JSONObject(result);
                        String username = jsonObject.getString("Usuario");
                        txtbienvenida.setText("BIENVENIDO " + username.toUpperCase());
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(String error) {
                    Toast.makeText(DashboardActivity.this, "Error al obtener el usuario: " + error, Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.perfil_usuario) {
            intent = new Intent(DashboardActivity.this, PerfilActivity.class);
            startActivity(intent);
        } else if (item.getItemId() == R.id.cerrar_sesion) {
            logout();
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    //View view
    public void logout() {
        FirebaseAuth.getInstance().signOut();
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        Toast.makeText(this, "Sesion cerrada", Toast.LENGTH_SHORT).show();
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);

        finish();
    }
    @Override
    public void onBackPressed() {

        super.onBackPressed();
        Toast.makeText(this, "No puede retroceder en este punto.", Toast.LENGTH_SHORT).show();
    }
}