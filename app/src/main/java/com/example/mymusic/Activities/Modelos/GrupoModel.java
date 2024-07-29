package com.example.mymusic.Activities.Modelos;

public class GrupoModel {
    private int idGrupo;
    private String nombreGrupo;
    private String descripcion;
    private String tipoGrupo;
    private String fechaCreacion;
    private int cantidadMiembros;
    private String caratulaGrupo;
    private int estadoGrupo;

    public GrupoModel(int idGrupo, String nombreGrupo, String descripcion, String tipoGrupo, String fechaCreacion, int cantidadMiembros, String caratulaGrupo, int estadoGrupo) {
        this.idGrupo = idGrupo;
        this.nombreGrupo = nombreGrupo;
        this.descripcion = descripcion;
        this.tipoGrupo = tipoGrupo;
        this.fechaCreacion = fechaCreacion;
        this.cantidadMiembros = cantidadMiembros;
        this.caratulaGrupo = caratulaGrupo;
        this.estadoGrupo = estadoGrupo;
    }
    public GrupoModel(){}

    public int getIdGrupo() {
        return idGrupo;
    }

    public void setIdGrupo(int idGrupo) {
        this.idGrupo = idGrupo;
    }

    public String getNombreGrupo() {
        return nombreGrupo;
    }

    public void setNombreGrupo(String nombreGrupo) {
        this.nombreGrupo = nombreGrupo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTipoGrupo() {
        return tipoGrupo;
    }

    public void setTipoGrupo(String tipoGrupo) {
        this.tipoGrupo = tipoGrupo;
    }

    public String getFechaCreacion() {
        return fechaCreacion;
    }

    public void setFechaCreacion(String fechaCreacion) {
        this.fechaCreacion = fechaCreacion;
    }

    public int getCantidadMiembros() {
        return cantidadMiembros;
    }

    public void setCantidadMiembros(int cantidadMiembros) {
        this.cantidadMiembros = cantidadMiembros;
    }

    public String getCaratulaGrupo() {
        return caratulaGrupo;
    }

    public void setCaratulaGrupo(String caratulaGrupo) {
        this.caratulaGrupo = caratulaGrupo;
    }

    public int getEstadoGrupo() {
        return estadoGrupo;
    }

    public void setEstadoGrupo(int estadoGrupo) {
        this.estadoGrupo = estadoGrupo;
    }
}
