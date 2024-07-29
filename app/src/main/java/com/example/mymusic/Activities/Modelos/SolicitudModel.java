package com.example.mymusic.Activities.Modelos;

public class SolicitudModel {
    private int idSolicitud;
    private String titulo;
    private String comentario;
    private int solicitadoPorId;
    private String fechaSolicitado;
    private String tipoSolicitud;
    private int estadoSolicitud;

    public SolicitudModel(int idSolicitud, String titulo, String comentario, int solicitadoPorId, String tipoSolicitud, String fechaSolicitado, int estadoSolicitud) {
        this.idSolicitud = idSolicitud;
        this.titulo = titulo;
        this.comentario = comentario;
        this.solicitadoPorId = solicitadoPorId;
        this.tipoSolicitud = tipoSolicitud;
        this.fechaSolicitado = fechaSolicitado;
        this.estadoSolicitud = estadoSolicitud;
    }

    public SolicitudModel() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public int getSolicitadoPorId() {
        return solicitadoPorId;
    }

    public void setSolicitadoPorId(int solicitadoPorId) {
        this.solicitadoPorId = solicitadoPorId;
    }

    public String getFechaSolicitado() {
        return fechaSolicitado;
    }

    public void setFechaSolicitado(String fechaSolicitado) {
        this.fechaSolicitado = fechaSolicitado;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public int getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(int estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }
}
