package com.example.mymusic.Models;

public class GrupoModel {
    private String idGrupo;
    private String nombreGrupo;
    private String cantidadAudios;

    public GrupoModel() {
    }

    public GrupoModel(String idGrupo, String nombreGrupo, String cantidadAudios) {
        this.idGrupo = idGrupo;
        this.nombreGrupo = nombreGrupo;
        this.cantidadAudios = cantidadAudios;
    }

    public String getIdGrupo() {
        return idGrupo;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public String getCantidadAudios() {
        return cantidadAudios + " Audios";
    }
}

