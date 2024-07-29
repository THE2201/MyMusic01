package com.example.mymusic.Activities.Modelos;

public class GrabacionModel {
    private int idGrabacion;
    private String titulo;
    private String audioData;
    private int AudioSize;
    private String typeExt;
    private int duracion;
    private int agregadoPorId;
    private String fechaSubido;
    private int estadoGrabacion;

    public GrabacionModel(int idGrabacion, String titulo, String audioData, int audioSize, String typeExt, int duracion, int agregadoPorId, String fechaSubido, int estadoGrabacion) {
        this.idGrabacion = idGrabacion;
        this.titulo = titulo;
        this.audioData = audioData;
        AudioSize = audioSize;
        this.typeExt = typeExt;
        this.duracion = duracion;
        this.agregadoPorId = agregadoPorId;
        this.fechaSubido = fechaSubido;
        this.estadoGrabacion = estadoGrabacion;
    }
    public GrabacionModel(){}

    public int getIdGrabacion() {
        return idGrabacion;
    }

    public void setIdGrabacion(int idGrabacion) {
        this.idGrabacion = idGrabacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAudioData() {
        return audioData;
    }

    public void setAudioData(String audioData) {
        this.audioData = audioData;
    }

    public int getAudioSize() {
        return AudioSize;
    }

    public void setAudioSize(int audioSize) {
        AudioSize = audioSize;
    }

    public String getTypeExt() {
        return typeExt;
    }

    public void setTypeExt(String typeExt) {
        this.typeExt = typeExt;
    }

    public int getDuracion() {
        return duracion;
    }

    public void setDuracion(int duracion) {
        this.duracion = duracion;
    }

    public int getAgregadoPorId() {
        return agregadoPorId;
    }

    public void setAgregadoPorId(int agregadoPorId) {
        this.agregadoPorId = agregadoPorId;
    }

    public String getFechaSubido() {
        return fechaSubido;
    }

    public void setFechaSubido(String fechaSubido) {
        this.fechaSubido = fechaSubido;
    }

    public int getEstadoGrabacion() {
        return estadoGrabacion;
    }

    public void setEstadoGrabacion(int estadoGrabacion) {
        this.estadoGrabacion = estadoGrabacion;
    }
}
