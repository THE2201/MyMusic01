package com.example.mymusic.Activities.Playback;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
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


public class PlayVideoActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://34.125.8.146/";
    private VideoView videoViewPlay;
    private SeekBar seekBarVideoPlay;
    private TextView tactualPlay, tduracionPlay, tituloVideoPlay;
    private Button btnPlayPausa, btnRotar;
    private RelativeLayout controlPanel;
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


        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                ContentValues cv = new ContentValues();
                for (String key : bundle.keySet()) {
                    cv.put(key, bundle.getString(key));
                }
                idVideo = cv.getAsString("idVideo");
                String tituloVideo  = cv.getAsString("tituloVideo");
                String autorVideo = cv.getAsString("autorVideo");
                String duracionVideo = cv.getAsString("duracionVideo");
                tituloVideoPlay.setText(tituloVideo+"\n"+autorVideo);
                tduracionPlay.setText(duracionVideo);
            }
        }


        // Initialize Volley request queue
        requestQueue = Volley.newRequestQueue(this);



        // Aca se manda con el id del video
        //declarado linea44 definido linea 70
        fetchVideoUrl(idVideo);

        videoViewPlay.setOnPreparedListener(mediaPlayer -> {
            seekBarVideoPlay.setMax(videoViewPlay.getDuration());
            tduracionPlay.setText(formatTime(videoViewPlay.getDuration()));
            btnPlayPausa.setEnabled(true);
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

    private void fetchVideoUrl(String videoId) {
        //esta id la saca de ContentValue paquete que se manda de la actividad anterior /// Asi como ya esta lleno el Titulo, duracion, y autor
        //Es un String, Solo llama el cuerpo del video
        String url = BASE_URL + "?id=" + idVideo;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String videoUrl = response.getString("url");
                            playVideo(videoUrl);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(PlayVideoActivity.this, "Falla al adquirir video", Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(PlayVideoActivity.this, "No se pudo obtener la URL del video", Toast.LENGTH_SHORT).show();
            }
        });

        requestQueue.add(jsonObjectRequest);
    }

    private void playVideo(String videoUrl) {
        Uri videoUri = Uri.parse(videoUrl);
        videoViewPlay.setVideoURI(videoUri);
        videoViewPlay.requestFocus();
        videoViewPlay.start();
        btnPlayPausa.setText("Pausar");
        isPlaying = true;
        updateSeekBar();
    }

    private void updateSeekBar() {
        if (isPlaying) {
            seekBarVideoPlay.setProgress(videoViewPlay.getCurrentPosition());
            tactualPlay.setText(formatTime(videoViewPlay.getCurrentPosition()));
            handler.postDelayed(updateSeekBarRunnable, 1000);
        }
    }

    private final Runnable updateSeekBarRunnable = new Runnable() {
        @Override
        public void run() {
            updateSeekBar();
        }
    };

    private void toggleControls() {
        if (controlPanel.getVisibility() == View.VISIBLE) {
            controlPanel.setVisibility(View.GONE);
        } else {
            controlPanel.setVisibility(View.VISIBLE);
        }
    }

    private String formatTime(int milliseconds) {
        int seconds = (milliseconds / 1000) % 60;
        int minutes = (milliseconds / (1000 * 60)) % 60;
        int hours = milliseconds / (1000 * 60 * 60);

        if (hours > 0) {
            return String.format("%02d:%02d:%02d", hours, minutes, seconds);
        } else {
            return String.format("%02d:%02d", minutes, seconds);
        }
    }
}
