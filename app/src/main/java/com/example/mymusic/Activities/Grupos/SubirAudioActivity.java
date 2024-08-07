package com.example.mymusic.Activities.Grupos;

import android.app.ProgressDialog;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymusic.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SubirAudioActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://34.125.8.146/";
    private static final int PICK_AUDIO_REQUEST = 1;
    private Uri audioUri;
    private MediaPlayer mediaPlayer;
    private SeekBar seekBar;
    private Handler handler = new Handler();
    private Runnable runnable;
    private int duration = 0;
    String formatDuration;
    private String idGrupo; // Variable para almacenar el idGrupo
    private FirebaseAuth fAuth; // Agregar instancia de FirebaseAuth

    TextView subir_audio_engrupo;
    EditText duracionAudioSeleccionadoSubir, titulo_audio_subir;
    Button bt_playaudio_sel_subir;

    private ProgressDialog progressDialog; // Agregar ProgressDialog

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_audio);

        duracionAudioSeleccionadoSubir = findViewById(R.id.duracionAudioSeleccionadoSubir);
        bt_playaudio_sel_subir = findViewById(R.id.bt_playaudio_sel_subir);
        subir_audio_engrupo = findViewById(R.id.subir_audio_engrupo);
        titulo_audio_subir = findViewById(R.id.titulo_audio_subir);

        Button buttonSelectAudio = findViewById(R.id.buttonSelectAudio);
        Button buttonUpload = findViewById(R.id.buttonUpload);
        seekBar = findViewById(R.id.seekBar);

        // Inicializar FirebaseAuth
        fAuth = FirebaseAuth.getInstance();

        // Obtener el idGrupo del Intent
        idGrupo = getIntent().getStringExtra("idGrupo");

        // Inicializar ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Subiendo audio...");
        progressDialog.setCancelable(false);

        bt_playaudio_sel_subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playAudio(audioUri);
            }
        });

        buttonSelectAudio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAudioSelector();
            }
        });

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (audioUri != null) {
                    try {
                        progressDialog.show(); // Mostrar ProgressDialog antes de subir el archivo
                        uploadAudio(audioUri);
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(SubirAudioActivity.this, "Error al enviar audio.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(SubirAudioActivity.this, "Se le olvido seleccionar un audio.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        bt_playaudio_sel_subir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (bt_playaudio_sel_subir.getText().equals("Detener")) {
                    stopAudio();
                } else if (bt_playaudio_sel_subir.getText().equals("Reproducir")) {
                    playAudio(audioUri);
                }
            }
        });
    }

    private void openAudioSelector() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Audio.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_AUDIO_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_AUDIO_REQUEST && resultCode == RESULT_OK && data != null) {
            audioUri = data.getData();
            playAudio(audioUri);
        }
    }

    private void playAudio(Uri audioUri) {
        stopAudio();
        mediaPlayer = MediaPlayer.create(this, audioUri);
        mediaPlayer.start();
        initializeSeekBar();

        duration = mediaPlayer.getDuration();
        formatDuration = createTimeLabel(duration);
        duracionAudioSeleccionadoSubir.setText(formatDuration);
        bt_playaudio_sel_subir.setEnabled(true);
        bt_playaudio_sel_subir.setText("Detener");
    }

    private void stopAudio() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.reset();
            mediaPlayer.release();
            mediaPlayer = null;
            handler.removeCallbacks(runnable);
            bt_playaudio_sel_subir.setText("Reproducir");
        }
    }

    private void initializeSeekBar() {
        seekBar.setMax(mediaPlayer.getDuration());

        runnable = new Runnable() {
            @Override
            public void run() {
                if (mediaPlayer != null) {
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                    handler.postDelayed(this, 1000);
                }
            }
        };
        handler.postDelayed(runnable, 1000);

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                handler.removeCallbacks(runnable);
            }
        });
    }

    private String createTimeLabel(int time) {
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;

        return String.format("%02d:%02d", min, sec);
    }

    private void uploadAudio(Uri audioUri) throws IOException {
        String vTituloAudio = titulo_audio_subir.getText().toString().trim();
        String vDuracionAudio = duracionAudioSeleccionadoSubir.getText().toString().trim();

        if (TextUtils.isEmpty(vTituloAudio)) {
            titulo_audio_subir.setError("Titulo requerido");
            Toast.makeText(SubirAudioActivity.this, "Rellenar campo indicado", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(vDuracionAudio)) {
            duracionAudioSeleccionadoSubir.setError("Seleccione un audio leer su duracion");
            Toast.makeText(SubirAudioActivity.this, "Elija un audio", Toast.LENGTH_SHORT).show();
            return;
        }

        String url = BASE_URL + "guardarAudio.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss(); // Ocultar ProgressDialog después de subir el archivo
                        Toast.makeText(SubirAudioActivity.this, "Subido Exitosamente", Toast.LENGTH_SHORT).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss(); // Ocultar ProgressDialog en caso de error
                Toast.makeText(SubirAudioActivity.this, "Error audio", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                try {
                    InputStream inputStream = getContentResolver().openInputStream(audioUri);
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        byteArrayOutputStream.write(buffer, 0, bytesRead);
                    }
                    String encodedAudio = Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);

                    // Obtener FirebaseUid
                    FirebaseUser user = fAuth.getCurrentUser();
                    String uid = user != null ? user.getUid() : "";

                    params.put("Titulo", vTituloAudio);
                    params.put("AudioData", encodedAudio);
                    params.put("IdGrupo", idGrupo);
                    params.put("SubidoPorId", uid);
                    params.put("FechaSubido", ""); // Puedes obtener la fecha en el servidor
                    params.put("EstadoSubido", "1");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return params;
            }
        };

        queue.add(stringRequest);
    }
}
