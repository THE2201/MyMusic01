package com.example.mymusic.Activities.Playback;

import android.content.ContentValues;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.mymusic.R;

public class PlayAudioActivity extends AppCompatActivity {

    private Button btnPlay, btnPause, btnStop;
    private TextView tvCurrentTime, tvTotalTime,tituloCancion;
    private SeekBar seekBar;
    private MediaPlayer mediaPlayer;
    private Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_audio);
        iniciarBg();

        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        btnStop = findViewById(R.id.btnStop);
        tvCurrentTime = findViewById(R.id.tvCurrentTime);
        tvTotalTime = findViewById(R.id.tvTotalTime);
        seekBar = findViewById(R.id.seekBar);
        tituloCancion = findViewById(R.id.tituloCancion);

        Intent intent = getIntent();
        if (intent != null) {
            Bundle bundle = intent.getExtras();
            if (bundle != null) {
                ContentValues cv = new ContentValues();
                for (String key : bundle.keySet()) {
                    cv.put(key, bundle.getString(key));
                }
                String idAudio = cv.getAsString("idAudio");
                String tituloAudio  = cv.getAsString("tituloAudio");
                String autorAudio = cv.getAsString("autorAudio");
                String duracionAudio = cv.getAsString("duracionAudio");
                tituloCancion.setText(tituloAudio+"\n"+autorAudio);
                tvTotalTime.setText(duracionAudio);
            }
        }


        mediaPlayer = MediaPlayer.create(this, R.raw.sample_audio);
        seekBar.setMax(mediaPlayer.getDuration());

        String totalTime = createTimeLabel(mediaPlayer.getDuration());
        tvTotalTime.setText(totalTime);

        btnPlay.setOnClickListener(v -> {
            mediaPlayer.start();
            updateSeekBar();
        });

        btnPause.setOnClickListener(v -> mediaPlayer.pause());

        btnStop.setOnClickListener(v -> {
            mediaPlayer.stop();
            mediaPlayer = MediaPlayer.create(this, R.raw.sample_audio);
            seekBar.setProgress(0);
            tvCurrentTime.setText("00:00");
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    mediaPlayer.seekTo(progress);
                    tvCurrentTime.setText(createTimeLabel(progress));
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });

        mediaPlayer.setOnCompletionListener(mp -> {
            mediaPlayer.seekTo(0);
            seekBar.setProgress(0);
            tvCurrentTime.setText("00:00");
        });

    }

    private void updateSeekBar() {
        seekBar.setProgress(mediaPlayer.getCurrentPosition());

        String currentTime = createTimeLabel(mediaPlayer.getCurrentPosition());
        tvCurrentTime.setText(currentTime);

        if (mediaPlayer.isPlaying()) {
            Runnable updater = this::updateSeekBar;
            handler.postDelayed(updater, 1000);
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
        handler.removeCallbacksAndMessages(null);
    }

    private void iniciarBg() {
        RelativeLayout relativeLayout = findViewById(R.id.main);
        AnimationDrawable animationDrawable = (AnimationDrawable) relativeLayout.getBackground();
        animationDrawable.setEnterFadeDuration(2500);
        animationDrawable.setExitFadeDuration(2500);
        animationDrawable.start();
    }
}