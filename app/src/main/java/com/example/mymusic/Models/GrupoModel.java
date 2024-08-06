package com.example.mymusic.Models;

public class GrupoModel {
    private String idGrupo;
    private String nombreGrupo;
    private String cantidadAudios;
    private String caratulaGrupo; // Nueva propiedad

    public GrupoModel() {
    }

    public GrupoModel(String idGrupo, String nombreGrupo, String cantidadAudios, String caratulaGrupo) {
        this.idGrupo = idGrupo;
        this.nombreGrupo = nombreGrupo;
        this.cantidadAudios = cantidadAudios;
        this.caratulaGrupo = caratulaGrupo;
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

    public String getCaratulaGrupo() {
        return caratulaGrupo;
    }
}
