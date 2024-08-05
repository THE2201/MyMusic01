package com.example.mymusic.Models;

public class MiGrupoAudioModel {
    private String idGrupo;
    private String nombreGrupo;
    private String cantidadAudios;

    public MiGrupoAudioModel(String idGrupo, String nombreGrupo, String cantidadAudios) {
        this.idGrupo = idGrupo;
        this.nombreGrupo = nombreGrupo;
        this.cantidadAudios = cantidadAudios;
    }
    public MiGrupoAudioModel() {
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
