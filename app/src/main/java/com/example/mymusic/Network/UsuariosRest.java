package com.example.mymusic.Network;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class UsuariosRest {
    private static final String BASE_URL = "http://34.125.8.146/";
    private RequestQueue requestQueue;
    private Context context;

    // Constructor que recibe el contexto
    public UsuariosRest(Context context) {
        this.context = context;
        this.requestQueue = Volley.newRequestQueue(context);
    }

    public interface UsuarioRestListener {
        void onResponse(boolean exists);
        void onError(String error);
    }

    public void createUser(String firebaseUid, String nombre, String apellido, String usuario, String correo, String fotoUsuario, final VolleyCallback callback) {
        String url = BASE_URL + "create_user.php";
        JSONObject postData = new JSONObject();
        try {
            postData.put("FirebaseUid", firebaseUid);
            postData.put("Nombre", nombre);
            postData.put("Apellido", apellido);
            postData.put("Usuario", usuario);
            postData.put("Correo", correo);
            postData.put("FotoUsuario", fotoUsuario);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error.toString());
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    public void getUser(int id, final VolleyCallback callback) {
        String url = BASE_URL + "get_user.php?id=" + id;

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error.toString());
                    }
                });

        requestQueue.add(stringRequest);
    }

    public void updateUser(int id, String firebaseUid, String nombre, String apellido, String usuario, String correo, String fotoUsuario, final VolleyCallback callback) {
        String url = BASE_URL + "update_user.php";
        JSONObject postData = new JSONObject();
        try {
            postData.put("IdUsuario", id);
            postData.put("FirebaseUid", firebaseUid);
            postData.put("Nombre", nombre);
            postData.put("Apellido", apellido);
            postData.put("Usuario", usuario);
            postData.put("Correo", correo);
            postData.put("FotoUsuario", fotoUsuario);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.PUT, url, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error.toString());
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    public void deleteUser(int id, final VolleyCallback callback) {
        String url = BASE_URL + "delete_user.php";
        JSONObject postData = new JSONObject();
        try {
            postData.put("IdUsuario", id);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.DELETE, url, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        callback.onSuccess(response.toString());
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error.toString());
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    //validar usuario
    public void validarUsuario(String usuario, final UsuarioRestListener listener) {
        String url = BASE_URL + "validate_user.php?Usuario=" + usuario;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("UsuarioResponse", response.toString());
                        try {
                            boolean exists = response.getBoolean("exists");
                            listener.onResponse(exists);
                        } catch (JSONException e) {
                            listener.onError(e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error.toString());
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }


    //validar correo
    public void validarCorreo(String correo, final UsuarioRestListener listener) {
        String url = BASE_URL + "validate_email.php?Correo=" + correo;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("CorreoResponse", response.toString());
                        try {
                            boolean exists = response.getBoolean("exists");
                            listener.onResponse(exists);
                        } catch (JSONException e) {
                            listener.onError(e.toString());
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.onError(error.toString());
                    }
                });

        requestQueue.add(jsonObjectRequest);
    }

    public interface VolleyCallback {
        void onSuccess(String result);
        void onError(String result);
    }
}
