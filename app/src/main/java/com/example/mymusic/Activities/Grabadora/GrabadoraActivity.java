package com.example.mymusic.Activities.Grabadora;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.mymusic.Activities.Inicio.DashboardActivity;
import com.example.mymusic.Activities.Usuario.PerfilActivity;
import com.example.mymusic.R;
import com.google.firebase.auth.FirebaseAuth;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class GrabadoraActivity extends AppCompatActivity {
    private Button btnStart, btnStop;
    private MediaRecorder mediaRecorder;
    private String audioFilePath;
    private ImageView imageView;
    private TextView elapsed;
    private boolean isRecording = false;
    private String saveAs;

    int seconds = 0;
    Handler handler;

    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;
    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_grabadora);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        imageView = findViewById(R.id.imageView);
        elapsed = findViewById(R.id.elapsed);

        btnStart.setOnClickListener(v -> startRecording());
        btnStop.setOnClickListener(v -> stopRecording());

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opciones_grabadora, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.mis_grabaciones) {
            startActivity(new Intent(GrabadoraActivity.this, GrabacionesActivity.class));
        } else {
            return super.onOptionsItemSelected(item);
        }
        return true;
    }

    private void startRecording() {
        if (checkPermissions()) {
            saveAs = createName();
            File audioFile = new File(getExternalFilesDir(null), saveAs);
            audioFilePath = audioFile.getAbsolutePath();
            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4); // Cambiado a MPEG_4
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC); // Cambiado a AAC
            mediaRecorder.setOutputFile(audioFilePath);

            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
                Toast.makeText(this, "Grabando...", Toast.LENGTH_SHORT).show();
                imageView.setImageDrawable(ContextCompat.getDrawable(GrabadoraActivity.this, R.drawable.mic_on));
                isRecording = true;
                elapsedCounter();
                elapsed.setTextColor(Color.RED);
                btnStart.setEnabled(false);
                btnStop.setEnabled(true);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private String encodeAudioFileToBase64() {
        File audioFile = new File(audioFilePath);
        try (InputStream is = new FileInputStream(audioFile);
             ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = is.read(buffer)) != -1) {
                baos.write(buffer, 0, bytesRead);
            }
            String base64String = Base64.encodeToString(baos.toByteArray(), Base64.NO_WRAP);
            Log.d("Base64Audio", base64String); // Imprime el Base64 en los logs
            return base64String;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }


    public static String createName() {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(new Date());
        return "Recording_" + timeStamp + ".mp4"; // Cambiado a mp4
    }

    private void elapsedCounter() {
        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int minutes = (seconds % 3600) / 60;
                int secs = seconds % 60;
                String time = String.format(Locale.getDefault(), "%02d:%02d", minutes, secs);
                elapsed.setText(time);
                if (isRecording) {
                    seconds++;
                    handler.removeCallbacksAndMessages(null);
                }
                handler.postDelayed(this, 1000);
            }
        });
    }

    private void stopRecording() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        Toast.makeText(this, "Guardado: " + saveAs, Toast.LENGTH_SHORT).show();
        imageView.setImageDrawable(ContextCompat.getDrawable(GrabadoraActivity.this, R.drawable.mic_off));
        isRecording = false;
        seconds = 0;
        elapsed.setTextColor(Color.WHITE);
        elapsed.setText("00:00");
        btnStart.setEnabled(true);
        btnStop.setEnabled(false);

        guardarGrabacion();
    }

    private void guardarGrabacion() {
        String url = "http://34.125.8.146/guardarGrabacion.php";
        RequestQueue queue = Volley.newRequestQueue(this);

        // Convertir el archivo a Base64
        String encodedAudio = encodeAudioFileToBase64();

        // Verificar que la conversión fue exitosa
        if (encodedAudio != null) {
            try {
                JSONObject jsonBody = new JSONObject();
                jsonBody.put("Titulo", saveAs);
                jsonBody.put("GrabacionData", encodedAudio);
                jsonBody.put("SubidoPorId", getFirebaseUserId());
                jsonBody.put("EstadoGrabacion", "1");

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                        response -> {
                            Log.d("Response", response.toString());
                            Toast.makeText(getApplicationContext(), "Guardado con éxito!", Toast.LENGTH_SHORT).show();
                        }, error -> {
                    Log.e("Error", error.toString());
                    Toast.makeText(getApplicationContext(), "Error al guardar la grabación.", Toast.LENGTH_SHORT).show();
                }) {
                    @Override
                    public Map<String, String> getHeaders() {
                        Map<String, String> headers = new HashMap<>();
                        headers.put("Content-Type", "application/json");
                        return headers;
                    }
                };

                queue.add(jsonObjectRequest);
            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(getApplicationContext(), "Error al crear JSON.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Error al codificar la grabación.", Toast.LENGTH_SHORT).show();
        }
    }

    private String getFirebaseUserId() {
        FirebaseAuth fAuth = FirebaseAuth.getInstance();
        return fAuth.getCurrentUser() != null ? fAuth.getCurrentUser().getUid() : "";
    }

    private boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        permissionToRecordAccepted = requestCode == REQUEST_RECORD_AUDIO_PERMISSION && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
        if (!permissionToRecordAccepted) finish();
    }
}
