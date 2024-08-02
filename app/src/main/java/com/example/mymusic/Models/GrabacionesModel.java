package com.example.mymusic.Models;

public class GrabacionesModel {
    private String idGrabacion;
    private String tituloGrabacion;
    private String fechaGrabacion;
    private String duracionGrabacion;

    public GrabacionesModel(String idGrabacion, String tituloGrabacion, String fechaGrabacion, String duracionGrabacion) {
        this.idGrabacion = idGrabacion;
        this.tituloGrabacion = tituloGrabacion;
        this.fechaGrabacion = fechaGrabacion;
        this.duracionGrabacion = duracionGrabacion;
    }
    public GrabacionesModel() {
    }

    public String getIdGrabacion() {
        return idGrabacion;
    }

    public void setIdGrabacion(String idGrabacion) {
        this.idGrabacion = idGrabacion;
    }

    public String getTituloGrabacion() {
        return tituloGrabacion;
    }

    public void setTituloGrabacion(String tituloGrabacion) {
        this.tituloGrabacion = tituloGrabacion;
    }

    public String getFechaGrabacion() {
        return fechaGrabacion;
    }

    public void setFechaGrabacion(String fechaGrabacion) {
        this.fechaGrabacion = fechaGrabacion;
    }

    public String getDuracionGrabacion() {
        return duracionGrabacion;
    }

    public void setDuracionGrabacion(String duracionGrabacion) {
        this.duracionGrabacion = duracionGrabacion;
    }
}



//TextView id_recording_api, title_recording, recording_timestamp, duration_recording;
//        ImageView icon_recorder;
//        ImageButton btn_delete_recording, bt_share_recording;
