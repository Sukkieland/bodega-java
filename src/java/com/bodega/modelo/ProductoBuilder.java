/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bodega.modelo;

/**
 *
 * @author mishellcastillo
 */
import com.bodega.modelo.entidades.Producto;

public class ProductoBuilder {
    
    private Producto producto;
    
    public ProductoBuilder() {
        this.producto = new Producto();
    }
    
    public ProductoBuilder id(int id) {
        producto.setIdProducto(id);
        return this;
    }
    
    public ProductoBuilder nombre(String nombre) {
        producto.setNombreProducto(nombre);
        return this;
    }
    
    public ProductoBuilder descripcion(String descripcion) {
        producto.setDescripcion(descripcion);
        return this;
    }
    
    public ProductoBuilder precio(double precio) {
        producto.setPrecio(precio);
        return this;
    }
    
    public ProductoBuilder stock(int stock) {
        producto.setStockProducto(stock);
        return this;
    }
    
    public ProductoBuilder proveedor(int idProveedor) {
        producto.setIdProveedor(idProveedor);
        return this;
    }
    
    public Producto build() {
        return producto;
    }
}