package com.example.realtimework.Modelo;

public class Usuario {
    private String idUsuario;
    private String nombre;
    private String cedula;
    private String genero;
    private String pais;
    private String provincia;
    private String correo;

    public Usuario() {

    }

    public Usuario(String correo) {
        this.correo = correo;
    }
    public Usuario(String nombre, String cedula, String genero, String pais, String provincia, String correo) {

        this.nombre = nombre;
        this.cedula = cedula;
        this.genero = genero;
        this.pais = pais;
        this.provincia = provincia;
        this.correo = correo;
    }
    public Usuario(String idUsuario,String nombre, String cedula, String genero, String pais, String provincia, String correo) {
        this.idUsuario=idUsuario;
        this.nombre = nombre;
        this.cedula = cedula;
        this.genero = genero;
        this.pais = pais;
        this.provincia = provincia;
        this.correo = correo;
    }
    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }
}