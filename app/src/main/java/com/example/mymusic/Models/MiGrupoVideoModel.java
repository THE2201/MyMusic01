package com.example.mymusic.Models;

public class MiGrupoVideoModel {
    private String IdGrupo;
    private String NombreGrupo;
    private String CaratulaGrupo;
    private String CantidadVideos;

    public MiGrupoVideoModel(String idGrupo, String nombreGrupo, String caratulaGrupo, String cantidadVideos) {
        this.IdGrupo = idGrupo;
        this.NombreGrupo = nombreGrupo;
        this.CaratulaGrupo = caratulaGrupo;
        this.CantidadVideos = cantidadVideos;
    }

    public String getIdGrupo() { return IdGrupo; }
    public void setIdGrupo(String idGrupo) { this.IdGrupo = idGrupo; }

    public String getNombreGrupo() { return NombreGrupo; }
    public void setNombreGrupo(String nombreGrupo) { this.NombreGrupo = nombreGrupo; }

    public String getCaratulaGrupo() { return CaratulaGrupo; }
    public void setCaratulaGrupo(String caratulaGrupo) { this.CaratulaGrupo = caratulaGrupo; }

    public String getCantidadVideos() { return CantidadVideos; }
    public void setCantidadVideos(String cantidadVideos) { this.CantidadVideos = cantidadVideos; }
}
