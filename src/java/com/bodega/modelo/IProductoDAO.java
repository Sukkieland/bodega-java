/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.bodega.modelo.dao;

import com.bodega.modelo.entidades.Producto;
import java.util.List;

public interface IProductoDAO {
    Producto obtenerPorId(int id);
    List<Producto> listarTodos();
    List<Producto> listarPorProveedor(int idProveedor);
    boolean crear(Producto producto);
    boolean actualizar(Producto producto);
    boolean actualizarStock(int idProducto, int nuevoStock);
    boolean eliminar(int id);
}