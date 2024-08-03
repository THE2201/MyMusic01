package com.example.mymusic.Activities.Grupos;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymusic.R;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class SubirVideoActivity extends AppCompatActivity {

    private static final String BASE_URL = "http://34.125.8.146/";
    private static final int REQUEST_CODE_PICK_VIDEO = 1;
    private static final int REQUEST_CODE_READ_EXTERNAL_STORAGE = 2;
    private Uri videoUri;
    private VideoView videoView;
    private SeekBar seekBar;
    private EditText tvDuration;
    private Button btn_subir_video,btnUpload;
    private Handler handler = new Handler();

    //private Button btnPlayVideo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subir_video);

        btnUpload = findViewById(R.id.btnUpload);
        //btnPlayVideo = findViewById(R.id.btnPlayVideo);
        videoView = findViewById(R.id.videoView);
        seekBar = findViewById(R.id.seekBar);
        tvDuration = findViewById(R.id.tvDuration);
        btn_subir_video = findViewById(R.id.btn_subir_video);

        btn_subir_video.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadVideoToAPI(videoUri);
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ContextCompat.checkSelfPermission(SubirVideoActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(SubirVideoActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_CODE_READ_EXTERNAL_STORAGE);
                } else {
                    pickVideoFromGallery();
                }
            }
        });

        videoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (videoUri != null) {
                    playVideo();
                }
            }
        });

        videoView.setOnPreparedListener(mp -> {
            seekBar.setMax(videoView.getDuration());
            tvDuration.setText(formatDuration(videoView.getDuration()));
            updateSeekBar();
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (fromUser) {
                    videoView.seekTo(progress);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {}

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {}
        });

    }


    private void pickVideoFromGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_CODE_PICK_VIDEO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_PICK_VIDEO && resultCode == RESULT_OK && data != null) {
            videoUri = data.getData();
        }
    }

    private void uploadVideoToAPI(Uri videoUri) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(videoUri);
            byte[] videoBytes = getBytes(inputStream);
            String url = BASE_URL + "guardarVideo.php";

            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            // Handle response
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle error
                        }
                    }) {
                @Override
                public byte[] getBody() throws AuthFailureError {
                    return videoBytes;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String> headers = new HashMap<>();
                    headers.put("Content-Type", "video/mp4");
                    return headers;
                }
            };

            Volley.newRequestQueue(this).add(stringRequest);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public byte[] getBytes(InputStream inputStream) throws IOException {
        ByteArrayOutputStream byteBuffer = new ByteArrayOutputStream();
        int bufferSize = 1024;
        byte[] buffer = new byte[bufferSize];
        int len = 0;
        while ((len = inputStream.read(buffer)) != -1) {
            byteBuffer.write(buffer, 0, len);
        }
        return byteBuffer.toByteArray();
    }

    private void playVideo() {
        videoView.setVideoURI(videoUri);
        videoView.start();
        updateSeekBar();
    }

    private void updateSeekBar() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (videoView != null && videoView.isPlaying()) {
                    seekBar.setProgress(videoView.getCurrentPosition());
                    handler.postDelayed(this, 100);
                }
            }
        }, 100);
    }

    private String formatDuration(int duration) {
        int minutes = (duration / 1000) / 60;
        int seconds = (duration / 1000) % 60;
        return String.format("%d:%02d", minutes, seconds);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_CODE_READ_EXTERNAL_STORAGE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            pickVideoFromGallery();
        }
    }
}