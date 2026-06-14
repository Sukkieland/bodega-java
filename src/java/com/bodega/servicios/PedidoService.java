/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bodega.servicios;

import com.bodega.modelo.dao.PedidoDAO;
import com.bodega.modelo.dao.ProductoDAO;
import com.bodega.modelo.entidades.Pedido;
import com.bodega.modelo.entidades.Producto;
import com.bodega.modelo.entidades.Usuario;
import java.sql.Timestamp;

public class PedidoService {
    
    private PedidoDAO pedidoDAO;
    private ProductoDAO productoDAO;
    
    public PedidoService() {
        this.pedidoDAO = new PedidoDAO();
        this.productoDAO = new ProductoDAO();
    }
    
    public boolean registrarPedido(Producto producto, int cantidad, String metodoPago, Usuario usuario) {
        // Validar stock
        if (producto.getStockProducto() < cantidad) {
            return false;
        }
        
        // Calcular subtotal
        double subtotal = producto.getPrecio() * cantidad;
        
        // Aplicar descuento si lleva 3+ unidades
        double total = subtotal;
        if (cantidad >= 3) {
            total = subtotal * 0.97; // 3% descuento
        }
        
        // Crear pedido
        Pedido pedido = new Pedido();
        pedido.setFecha(new Timestamp(System.currentTimeMillis()));
        pedido.setMetodoPago(metodoPago);
        pedido.setTotal(total);
        pedido.setEstado("Completado");
        pedido.setIdUsuario(usuario.getIdUsuario());
        pedido.setIdCliente(1);
        
        int idPedido = pedidoDAO.crear(pedido);
        
        if (idPedido > 0) {
            // Reducir stock
            int nuevoStock = producto.getStockProducto() - cantidad;
            productoDAO.actualizarStock(producto.getIdProducto(), nuevoStock);
            return true;
        }
        
        return false;
    }
}