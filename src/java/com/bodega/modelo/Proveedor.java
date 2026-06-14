/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bodega.modelo.entidades;

public class Proveedor {
    private int idProveedor;
    private String nombreProv;
    private String telefonoProv;
    private String correoProv;

    public Proveedor() {}

    public Proveedor(int idProveedor, String nombreProv, String telefonoProv, String correoProv) {
        this.idProveedor = idProveedor;
        this.nombreProv = nombreProv;
        this.telefonoProv = telefonoProv;
        this.correoProv = correoProv;
    }

    public int getIdProveedor() { return idProveedor; }
    public void setIdProveedor(int idProveedor) { this.idProveedor = idProveedor; }
    public String getNombreProv() { return nombreProv; }
    public void setNombreProv(String nombreProv) { this.nombreProv = nombreProv; }
    public String getTelefonoProv() { return telefonoProv; }
    public void setTelefonoProv(String telefonoProv) { this.telefonoProv = telefonoProv; }
    public String getCorreoProv() { return correoProv; }
    public void setCorreoProv(String correoProv) { this.correoProv = correoProv; }
}