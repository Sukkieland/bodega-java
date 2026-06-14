/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bodega.modelo.entidades;

public class Usuario {
    private int idUsuario;
    private String username;
    private String nombreUsuario;
    private String contrasena;

    public Usuario() {}

    public Usuario(int idUsuario, String username, String nombreUsuario, String contrasena) {
        this.idUsuario = idUsuario;
        this.username = username;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
    }

    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getNombreUsuario() { return nombreUsuario; }
    public void setNombreUsuario(String nombreUsuario) { this.nombreUsuario = nombreUsuario; }
    public String getContrasena() { return contrasena; }
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }
}