/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bodega.modelo.entidades;

import java.sql.Timestamp;

public class Pedido {
    private int idPedido;
    private Timestamp fecha;
    private String metodoPago;
    private double total;
    private String estado;
    private int idUsuario;
    private int idCliente;

    public Pedido() {}

    public Pedido(int idPedido, Timestamp fecha, String metodoPago, double total, String estado, int idUsuario, int idCliente) {
        this.idPedido = idPedido;
        this.fecha = fecha;
        this.metodoPago = metodoPago;
        this.total = total;
        this.estado = estado;
        this.idUsuario = idUsuario;
        this.idCliente = idCliente;
    }

    public int getIdPedido() { return idPedido; }
    public void setIdPedido(int idPedido) { this.idPedido = idPedido; }
    public Timestamp getFecha() { return fecha; }
    public void setFecha(Timestamp fecha) { this.fecha = fecha; }
    public String getMetodoPago() { return metodoPago; }
    public void setMetodoPago(String metodoPago) { this.metodoPago = metodoPago; }
    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }
    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }
    public int getIdUsuario() { return idUsuario; }
    public void setIdUsuario(int idUsuario) { this.idUsuario = idUsuario; }
    public int getIdCliente() { return idCliente; }
    public void setIdCliente(int idCliente) { this.idCliente = idCliente; }
}