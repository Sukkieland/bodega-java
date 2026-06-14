/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bodega.modelo.entidades;

import java.sql.Timestamp;

public class RegistroStock {
    private int idRegistro;
    private Timestamp fechaEntrada;
    private int cantidadEntrada;
    private int cantidadTotal;
    private int idUsuario;
    private int idProducto;

    public RegistroStock() {}

    public RegistroStock(int idRegistro, Timestamp fechaEntrada, int cantidadEntrada, int cantidadTotal, int idUsuario, int idProducto) {
        this.idRegistro = idRegistro;
        this.fechaEntrada = fechaEntrada;
        this.cantidadEntrada = cantidadEntrada;
        this.cantidadTotal = cantidadTotal;
        this.idUsuario = idUsuario;
        this.idProducto = idProducto;
    }

    public int getIdRegistro() { return idRegistro; }
    public void setIdRegistro(int idRegistro) { this.idRegistro = idRegistro; }
    public Timestamp getFechaEntrada() { return fechaEntrada; }
    public void setFechaEntrada(Timestamp fechaEntrada) { this.fechaEntrada = fechaEntrada; }
    public int getCantidadEntrada() { return cantidadEntrada; }
    public void setCantidadEntrada(int cantidadEntrada) { this.cantidadEntrada = cantidadEntrada; }
    public int getCantidadTotal() { return cantidadTotal; }
    public void setCantidadTotal(int cantidadTotal) { this.cantidadTotal = cantidadTotal; }
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public int getIdProducto() { return idProducto; }
    public void setIdProducto(int idProducto) { this.idProducto = idProducto; }
}