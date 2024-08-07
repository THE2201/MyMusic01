package com.example.mymusic.Activities.Playback;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymusic.R;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;

import android.media.MediaPlayer;

import org.json.JSONObject;

public class PlayAudioActivity extends AppCompatActivity {

    private Button btnPlay, btnPause, btnStop;
    private TextView tvCurrentTime, tvTotalTime, tituloCancion;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private String audioFilePath;
    private static final String BASE_URL = "http://34.125.8.146/";
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_audio);

        // Inicializar ProgressDialog
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Cargando audio...");
        progressDialog.setCancelable(false); // No permitir que se cancele

        // Inicializar otros elementos
        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        btnStop = findViewById(R.id.btnStop);
        tvCurrentTime = findViewById(R.id.tvCurrentTime);
        tvTotalTime = findViewById(R.id.tvTotalTime);
        seekBar = findViewById(R.id.seekBar);
        tituloCancion = findViewById(R.id.tituloCancion);

        Intent intent = getIntent();
        if (intent != null) {
            String idAudio = intent.getStringExtra("idAudio");
            if (idAudio != null) {
                // Consultar el archivo de audio desde el servidor
                consultarAudioPorId(idAudio);
            }
        }

        mediaPlayer = new MediaPlayer();

        btnPlay.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                mediaPlayer.start();
                updateSeekBar();
            }
        });

        btnPause.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                mediaPlayer.pause();
            }
        });

        btnStop.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.reset();
                if (audioFilePath != null) {
                    cargarAudio();
                }
                seekBar.setProgress(0);
                tvCurrentTime.setText("00:00");
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser && mediaPlayer != null) {
                    mediaPlayer.seekTo(progress);
                    tvCurrentTime.setText(createTimeLabel(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

        mediaPlayer.setOnCompletionListener(mp -> {
            if (mediaPlayer != null) {
                mediaPlayer.seekTo(0);
                seekBar.setProgress(0);
                tvCurrentTime.setText("00:00");
            }
        });
    }

    private void consultarAudioPorId(String idAudio) {
        String url = BASE_URL + "getAudioById.php";

        // Mostrar ProgressDialog mientras se carga el audio
        progressDialog.show();

        RequestQueue queue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss(); // Ocultar ProgressDialog

                        try {
                            JSONObject jsonObject = new JSONObject(response);
                            if (jsonObject.has("error")) {
                                Toast.makeText(PlayAudioActivity.this, jsonObject.getString("error"), Toast.LENGTH_SHORT).show();
                            } else {
                                String titulo = jsonObject.getString("Titulo");
                                String audioDataBase64 = jsonObject.getString("AudioData");
                                byte[] audioData = Base64.decode(audioDataBase64, Base64.DEFAULT);

                                // Guardar el archivo de audio en almacenamiento interno
                                File audioFile = new File(getFilesDir(), "audio_" + idAudio + ".mp3");
                                try (OutputStream os = new FileOutputStream(audioFile)) {
                                    os.write(audioData);
                                    os.flush();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                    Toast.makeText(PlayAudioActivity.this, "Error al guardar el archivo de audio", Toast.LENGTH_SHORT).show();
                                }

                                audioFilePath = audioFile.getAbsolutePath();
                                tituloCancion.setText(titulo);
                                cargarAudio();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(PlayAudioActivity.this, "Error al procesar la respuesta", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss(); // Ocultar ProgressDialog
                Toast.makeText(PlayAudioActivity.this, "Error al consultar el audio", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("idAudio", idAudio);
                return params;
            }
        };

        queue.add(stringRequest);
    }

    private void cargarAudio() {
        if (audioFilePath != null && mediaPlayer != null) {
            try {
                mediaPlayer.reset();
                mediaPlayer.setDataSource(audioFilePath);
                mediaPlayer.prepare();
                seekBar.setMax(mediaPlayer.getDuration());
                tvTotalTime.setText(createTimeLabel(mediaPlayer.getDuration()));
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(this, "Error al cargar el archivo de audio", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void updateSeekBar() {
        if (mediaPlayer != null) {
            seekBar.setProgress(mediaPlayer.getCurrentPosition());

            String currentTime = createTimeLabel(mediaPlayer.getCurrentPosition());
            tvCurrentTime.setText(currentTime);

            if (mediaPlayer.isPlaying()) {
                Runnable updater = this::updateSeekBar;
                handler.postDelayed(updater, 1000);
            }
        }
    }

    private String createTimeLabel(int time) {
        int min = time / 1000 / 60;
        int sec = time / 1000 % 60;

        return String.format("%02d:%02d", min, sec);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
