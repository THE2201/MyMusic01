package com.example.mymusic.Activities.Modelos;

public class AudioModel {
    private int idAudio;
    private String titulo;
    private String audioData;
    private int audioSize;
    private String typeExt;
    private int duracion;
    private int agregadoPorId;
    private String fechaSubido;
    private int estadoAudio;

    public AudioModel(int idAudio, String titulo, String audioData, int audioSize, String typeExt, int duracion, int agregadoPorId, String fechaSubido, int estadoAudio) {
        this.idAudio = idAudio;
        this.titulo = titulo;
        this.audioData = audioData;
        this.audioSize = audioSize;
        this.typeExt = typeExt;
        this.duracion = duracion;
        this.agregadoPorId = agregadoPorId;
        this.fechaSubido = fechaSubido;
        this.estadoAudio = estadoAudio;
    }

    public AudioModel() {
    }

    public int getIdAudio() {
        return idAudio;
    }

    public void setIdAudio(int idAudio) {
        this.idAudio = idAudio;
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

    public String getTypeExt() {
        return typeExt;
    }

    public void setTypeExt(String typeExt) {
        this.typeExt = typeExt;
    }

    public int getAudioSize() {
        return audioSize;
    }

    public void setAudioSize(int audioSize) {
        this.audioSize = audioSize;
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

    public int getEstadoAudio() {
        return estadoAudio;
    }

    public void setEstadoAudio(int estadoAudio) {
        this.estadoAudio = estadoAudio;
    }
}
