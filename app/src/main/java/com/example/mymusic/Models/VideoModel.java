package com.example.mymusic.Models;

public class VideoModel {
    private String idVideo;
    private String tituloVideo;
    private String autorVideo;
    private String duracionVideo;

    public VideoModel(String idVideo, String tituloVideo, String autorVideo, String duracionVideo) {
        this.idVideo = idVideo;
        this.tituloVideo = tituloVideo;
        this.autorVideo = autorVideo;
        this.duracionVideo = duracionVideo;
    }

    public VideoModel() {
    }

    public String getIdVideo() {
        return idVideo;
    }

    public String getTituloVideo() {
        return tituloVideo;
    }

    public String getAutorVideo() {
        return autorVideo;
    }

    public String getDuracionVideo() {
        return duracionVideo;
    }
}

