package com.example.mymusic.Models;

public class GrupoModelVideo {
    private String idGrupo;
    private String nombreGrupo;
    private String cantidadVideos;
    private String caratulaGrupo; // Nueva propiedad

    public GrupoModelVideo() {
    }

    public GrupoModelVideo(String idGrupo, String nombreGrupo, String cantidadVideos, String caratulaGrupo) {
        this.idGrupo = idGrupo;
        this.nombreGrupo = nombreGrupo;
        this.cantidadVideos = cantidadVideos;
        this.caratulaGrupo = caratulaGrupo;
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

    public String getCaratulaGrupo() {
        return caratulaGrupo;
    }
}
