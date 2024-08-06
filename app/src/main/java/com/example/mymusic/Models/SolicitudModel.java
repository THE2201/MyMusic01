package com.example.mymusic.Models;

public class SolicitudModel {
    private String tituloSolicitud;
    private String fechaSolicitud;
    private String usuarioSolicitud;
    private String comentarioSolicitud;

    public SolicitudModel(String tituloSolicitud, String fechaSolicitud, String usuarioSolicitud, String comentarioSolicitud) {
        this.tituloSolicitud = tituloSolicitud;
        this.fechaSolicitud = fechaSolicitud;
        this.usuarioSolicitud = usuarioSolicitud;
        this.comentarioSolicitud = comentarioSolicitud;
    }

    public SolicitudModel() {
    }

    public String getTituloSolicitud() {
        return tituloSolicitud;
    }

    public String getFechaSolicitud() {
        return fechaSolicitud;
    }

    public String getUsuarioSolicitud() {
        return usuarioSolicitud;
    }

    public String getComentarioSolicitud() {
        return comentarioSolicitud;
    }
}
