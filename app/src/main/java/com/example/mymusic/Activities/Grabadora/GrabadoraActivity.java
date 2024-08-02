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
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

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

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

public class GrabadoraActivity extends AppCompatActivity {
    private Button btnStart, btnStop;
    private MediaRecorder mediaRecorder;
    private String audioFilePath;
    private ImageView imageView;
    private TextView elapsed;
    private boolean isRecording=false;
    private String saveAs;

    int seconds=0;
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

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startRecording();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecording();
            }
        });

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.opciones_grabadora,menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==R.id.mis_grabaciones){
            startActivity(new Intent(GrabadoraActivity.this, GrabacionesActivity.class));
        } else{
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
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
            mediaRecorder.setOutputFile(audioFilePath);

            try {
                mediaRecorder.prepare();
                mediaRecorder.start();
                Toast.makeText(this, "Grabando...", Toast.LENGTH_SHORT).show();
                imageView.setImageDrawable(ContextCompat.getDrawable(GrabadoraActivity.this, R.drawable.mic_on));
                isRecording=true;
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
        byte[] audioBytes = new byte[(int) audioFile.length()];
        try (InputStream is = new FileInputStream(audioFile)) {
            int bytesRead = is.read(audioBytes);
            if (bytesRead != audioFile.length()) {
                throw new IOException("Error al leer el archivo.");
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        String encodedString = Base64.encodeToString(audioBytes, Base64.DEFAULT);
        Log.d("GrabacionB64Check:", encodedString);
        return encodedString;
    }

    public static String createName(){
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        //String id = UUID.randomUUID().toString();
        return "Recording_"+timeStamp+".3gp";
    }

    private void elapsedCounter(){
        handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                int minutes = (seconds%3600)/60;
                int secs = seconds%60;
                String time = String.format(Locale.getDefault(),"%02d:%02d",minutes,secs);
                elapsed.setText(time);
                if(isRecording){
                    seconds++;
                    handler.removeCallbacksAndMessages(null);
                }
                handler.postDelayed(this,1000);
            }
        });
    }

    private void stopRecording() {
        mediaRecorder.stop();
        mediaRecorder.release();
        mediaRecorder = null;
        Toast.makeText(this, "Guardado: "+ saveAs, Toast.LENGTH_SHORT).show();
        imageView.setImageDrawable(ContextCompat.getDrawable(GrabadoraActivity.this, R.drawable.mic_off));
        isRecording=false;
        seconds=0;
        elapsed.setTextColor(Color.WHITE);
        elapsed.setText("00:00");
        btnStart.setEnabled(true);
        btnStop.setEnabled(false);

        encodeAudioFileToBase64();


        //Con esta funcion se guarda
        //guardarGrabacion();
    }

    private void guardarGrabacion() {
        String url = "http://34.125.8.146/guardarGrabacion.php";
        JSONObject jsonBody = new JSONObject();
        try {
            jsonBody.put("Titulo", saveAs);
            jsonBody.put("GrabacionData", encodeAudioFileToBase64());
            jsonBody.put("SubidoPorId", "");
            jsonBody.put("EstadoGrabacion", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonBody,
                response -> {
                    Log.d("Response", response.toString());
                    Toast.makeText(getApplicationContext(), "Guardado con exito!", Toast.LENGTH_SHORT).show();
                },error -> Log.e("Error", error.toString())) {
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