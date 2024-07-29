package com.example.mymusic.Activities.Modelos;

public class UsuarioModel {
    private int idUsuario;
    private String usuario;
    private String nombre;
    private String correo;
    private String passwd;
    private String fotoUsuario;
    private int estadoUsuario;

    public UsuarioModel(int idUsuario, String usuario, String nombre, String correo, String passwd, String fotoUsuario, int estadoUsuario) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.nombre = nombre;
        this.correo = correo;
        this.passwd = passwd;
        this.fotoUsuario = fotoUsuario;
        this.estadoUsuario = estadoUsuario;
    }
    public UsuarioModel(){}

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getFotoUsuario() {
        return fotoUsuario;
    }

    public void setFotoUsuario(String fotoUsuario) {
        this.fotoUsuario = fotoUsuario;
    }

    public int getEstadoUsuario() {
        return estadoUsuario;
    }

    public void setEstadoUsuario(int estadoUsuario) {
        this.estadoUsuario = estadoUsuario;
    }
}
