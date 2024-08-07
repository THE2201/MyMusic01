package com.example.mymusic.Activities.Grabadora;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Environment;
import android.util.Base64;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymusic.R;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class PlayGrabacionesActivity extends AppCompatActivity {

    private Button btnPlay, btnPause, btnStop;
    private TextView tvCurrentTime, tvTotalTime, tituloCancion;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();
    private File tempAudioFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_grabaciones);

        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        btnStop = findViewById(R.id.btnStop);
        tvCurrentTime = findViewById(R.id.tvCurrentTime);
        tvTotalTime = findViewById(R.id.tvTotalTime);
        seekBar = findViewById(R.id.seekBar);
        tituloCancion = findViewById(R.id.tituloCancion);

        Intent intent = getIntent();
        if (intent != null) {
            String idGrabacion = intent.getStringExtra("IdGrabacion");
            String tituloGrabacion = intent.getStringExtra("tituloGrabacion");
            tituloCancion.setText(tituloGrabacion);

            fetchRecording(idGrabacion);
        }

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
                mediaPlayer.release();
                mediaPlayer = null;
                tvCurrentTime.setText("00:00");
                seekBar.setProgress(0);
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
            public void onStartTrackingTouch(SeekBar seekBar) { }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) { }
        });
    }

    private void fetchRecording(String idGrabacion) {
        String url = "http://34.125.8.146/getGrabacion.php";
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("idGrabacion", idGrabacion);
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                response -> {
                    try {
                        boolean success = response.getBoolean("success");
                        if (success) {
                            String grabacionData = response.getString("grabacionData");
                            String titulo = response.getString("titulo");
                            byte[] audioData = Base64.decode(grabacionData, Base64.DEFAULT);

                            saveToTempFile(audioData);
                            mediaPlayer = new MediaPlayer();
                            mediaPlayer.setDataSource(tempAudioFile.getAbsolutePath());
                            mediaPlayer.prepare();

                            String totalTime = createTimeLabel(mediaPlayer.getDuration());
                            tvTotalTime.setText(totalTime);
                        } else {
                            Toast.makeText(this, "Error al cargar la grabación.", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        Toast.makeText(this, "Error al procesar la grabación.", Toast.LENGTH_SHORT).show();
                    }
                },
                error -> Toast.makeText(this, "Error en la solicitud.", Toast.LENGTH_SHORT).show()
        );

        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(request);
    }

    private void saveToTempFile(byte[] audioData) throws IOException {
        File tempDir = getCacheDir();
        tempAudioFile = File.createTempFile("temp_audio", ".mp3", tempDir);

        try (OutputStream os = new FileOutputStream(tempAudioFile)) {
            os.write(audioData);
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
        if (tempAudioFile != null && tempAudioFile.exists()) {
            tempAudioFile.delete();
        }
        handler.removeCallbacksAndMessages(null);
    }
}
