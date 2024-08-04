package com.example.mymusic.Models;

import android.graphics.drawable.Drawable;

public class GrupoModel {
    private int idGrupo;
    private String nombreGrupo;
    private String cantidadAudios;

    public GrupoModel() {
    }

    public GrupoModel(int idGrupo, String nombreGrupo, String cantidadAudios) {
        this.idGrupo = idGrupo;
        this.nombreGrupo = nombreGrupo;
        this.cantidadAudios = cantidadAudios;
    }

    public int getIdGrupo() {
        return idGrupo;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public String getCantidadAudios() {
        return cantidadAudios;
    }
}

