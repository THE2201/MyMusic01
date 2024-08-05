package com.example.mymusic.Models;

public class AudioModel {
    private String idAudio;
    private String tituloAudio;
    private String autorAudio;
    private String duracionAudio;

    public AudioModel(String idAudio, String tituloAudio, String autorAudio, String duracionAudio) {
        this.idAudio = idAudio;
        this.tituloAudio = tituloAudio;
        this.autorAudio = autorAudio;
        this.duracionAudio = duracionAudio;
    }

    public AudioModel() {
    }

    public String getIdAudio() {
        return idAudio;
    }

    public String getTituloAudio() {
        return tituloAudio;
    }

    public String getAutorAudio() {
        return autorAudio;
    }

    public String getDuracionAudio() {
        return duracionAudio;
    }
}
