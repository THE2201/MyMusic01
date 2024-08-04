package com.example.mymusic.Models;

public class GrupoModelVideo {
    private String idGrupo;
    private String nombreGrupo;
    private String cantidadVideos;

    public GrupoModelVideo() {
    }

    public GrupoModelVideo(String idGrupo, String nombreGrupo, String cantidadVideos) {
        this.idGrupo = idGrupo;
        this.nombreGrupo = nombreGrupo;
        this.cantidadVideos = cantidadVideos;
    }

    public String getIdGrupo() {
        return idGrupo;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public String getCantidadVideos() {
        return cantidadVideos + " Videos";
    }
}

