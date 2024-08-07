package com.example.mymusic.Activities.Playback;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymusic.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PlayVideoActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://34.125.8.146/obtener_video.php";
    private VideoView videoViewPlay;
    private SeekBar seekBarVideoPlay;
    private TextView tactualPlay, tduracionPlay, tituloVideoPlay;
    private Button btnPlayPausa, btnRotar;
    private RelativeLayout controlPanel;
    private ProgressBar progressBar;
    private Handler handler = new Handler();
    private boolean isPlaying = false;
    private RequestQueue requestQueue;

    String idVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        videoViewPlay = findViewById(R.id.videoViewPlay);
        seekBarVideoPlay = findViewById(R.id.seekBarVideoPlay);
        tactualPlay = findViewById(R.id.tactualPlay);
        tduracionPlay = findViewById(R.id.tduracionPlay);
        tituloVideoPlay = findViewById(R.id.tituloVideoPlay);
        btnPlayPausa = findViewById(R.id.btnPlayPausa);
        btnRotar = findViewById(R.id.btnRotar);
        controlPanel = findViewById(R.id.controlPanel);

        progressBar = new ProgressBar(this);
        progressBar.setLayoutParams(new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT));
        progressBar.setIndeterminate(true);

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        progressBar.setLayoutParams(params);

        RelativeLayout rootLayout = findViewById(R.id.relatiVideo);
        rootLayout.addView(progressBar);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                ContentValues cv = new ContentValues();
                for (String key : bundle.keySet()) {
                    cv.put(key, bundle.getString(key));
                }
                idVideo = cv.getAsString("idVideo");
                String tituloVideo = cv.getAsString("tituloVideo");
                String autorVideo = cv.getAsString("autorVideo");
                String duracionVideo = cv.getAsString("duracionVideo");
                tituloVideoPlay.setText(tituloVideo + "\n" + autorVideo);
                tduracionPlay.setText(duracionVideo);
            }
        }

        requestQueue = Volley.newRequestQueue(this);

        fetchVideo(idVideo);

        videoViewPlay.setOnPreparedListener(mediaPlayer -> {
            seekBarVideoPlay.setMax(videoViewPlay.getDuration());
            tduracionPlay.setText(formatTime(videoViewPlay.getDuration()));
            btnPlayPausa.setEnabled(true);
            progressBar.setVisibility(View.GONE);
        });

        videoViewPlay.setOnCompletionListener(mediaPlayer -> {
            isPlaying = false;
            btnPlayPausa.setText("Play");
            videoViewPlay.seekTo(0);
            seekBarVideoPlay.setProgress(0);
        });

        videoViewPlay.setOnClickListener(v -> toggleControls());

        btnPlayPausa.setOnClickListener(v -> {
            if (isPlaying) {
                videoViewPlay.pause();
                btnPlayPausa.setText("Play");
            } else {
                videoViewPlay.start();
                btnPlayPausa.setText("Pausar");
                updateSeekBar();
            }
            isPlaying = !isPlaying;
        });

        btnRotar.setOnClickListener(v -> {
            if (getResources().getConfiguration().orientation == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        });

        seekBarVideoPlay.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    videoViewPlay.seekTo(progress);
                    tactualPlay.setText(formatTime(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                handler.removeCallbacks(updateSeekBarRunnable);
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                handler.post(updateSeekBarRunnable);
            }
        });
    }

    private void fetchVideo(String videoId) {
        String url = BASE_URL + "?id=" + videoId;
        progressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String videoBase64 = response.getString("VideoData");
                            String titulo = response.getString("Titulo");
                            String duracion = response.getString("Duracion");

                            // Convierte base64 a byte array
                            byte[] videoData = android.util.Base64.decode(videoBase64, android.util.Base64.DEFAULT);

                            // Guarda el video en un archivo temporal
                            File videoFile = saveVideoToFile(videoData);
                            if (videoFile != null) {
                                Uri videoUri = Uri.fromFile(videoFile);

                                videoViewPlay.setVideoURI(videoUri);
                                tituloVideoPlay.setText(titulo);
                                tduracionPlay.setText(duracion);
                            } else {
                                Toast.makeText(PlayVideoActivity.this, "Error al guardar video", Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(PlayVideoActivity.this, "Error al obtener video", Toast.LENGTH_SHORT).show();
                        } finally {
                            // Oculta el ProgressBar
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(PlayVideoActivity.this, "Error al obtener video", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private File saveVideoToFile(byte[] videoData) {
        File file = new File(getExternalFilesDir(Environment.DIRECTORY_MOVIES), "temp_video.mp4");
        try (FileOutputStream fos = new FileOutputStream(file)) {
            fos.write(videoData);
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void updateSeekBar() {
        handler.postDelayed(updateSeekBarRunnable, 1000);
    }

    private final Runnable updateSeekBarRunnable = new Runnable() {
        @Override
        public void run() {
            if (videoViewPlay.isPlaying()) {
                seekBarVideoPlay.setProgress(videoViewPlay.getCurrentPosition());
                tactualPlay.setText(formatTime(videoViewPlay.getCurrentPosition()));
                handler.postDelayed(this, 1000);
            }
        }
    };

    private String formatTime(int milliseconds) {
        int seconds = (milliseconds / 1000) % 60;
        int minutes = (milliseconds / (1000 * 60)) % 60;
        int hours = (milliseconds / (1000 * 60 * 60));
        return String.format("%02d:%02d:%02d", hours, minutes, seconds);
    }

    private void toggleControls() {
        if (controlPanel.getVisibility() == View.VISIBLE) {
            controlPanel.setVisibility(View.GONE);
        } else {
            controlPanel.setVisibility(View.VISIBLE);
        }
    }
}
