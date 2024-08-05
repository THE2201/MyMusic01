package com.example.mymusic.Models;

public class MiGrupoVideoModel {
    private String idGrupo;
    private String nombreGrupo;
    private String cantidadVideos;

    public MiGrupoVideoModel(String idGrupo, String nombreGrupo, String cantidadVideos) {
        this.idGrupo = idGrupo;
        this.nombreGrupo = nombreGrupo;
        this.cantidadVideos = cantidadVideos;
    }
    public MiGrupoVideoModel() {
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
