/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bodega.modelo.dao;

import com.bodega.modelo.conexion.ConexionPostgreSQL;
import com.bodega.modelo.entidades.Producto;
import com.bodega.observer.Observer;
import com.bodega.observer.Subject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductoDAO extends Subject implements IProductoDAO {
    
    private Connection connection;
    private static final int STOCK_MINIMO = 10;
    
    public ProductoDAO() {
        this.connection = ConexionPostgreSQL.getInstancia().getConnection();
    }
    
    @Override
    public Producto obtenerPorId(int id) {
        String sql = "SELECT * FROM producto WHERE id_producto = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearProducto(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error obtener producto: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public List<Producto> listarTodos() {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                lista.add(mapearProducto(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error listar productos: " + e.getMessage());
        }
        return lista;
    }
    
    @Override
    public List<Producto> listarPorProveedor(int idProveedor) {
        List<Producto> lista = new ArrayList<>();
        String sql = "SELECT * FROM producto WHERE id_proveedor = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idProveedor);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                lista.add(mapearProducto(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error listar por proveedor: " + e.getMessage());
        }
        return lista;
    }
    
    @Override
    public boolean crear(Producto producto) {
        String sql = "INSERT INTO producto (nombre_producto, descripcion, precio, stock_producto, id_proveedor) VALUES (?, ?, ?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombreProducto());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStockProducto());
            stmt.setInt(5, producto.getIdProveedor());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error crear producto: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public boolean actualizar(Producto producto) {
        String sql = "UPDATE producto SET nombre_producto = ?, descripcion = ?, precio = ?, stock_producto = ?, id_proveedor = ? WHERE id_producto = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, producto.getNombreProducto());
            stmt.setString(2, producto.getDescripcion());
            stmt.setDouble(3, producto.getPrecio());
            stmt.setInt(4, producto.getStockProducto());
            stmt.setInt(5, producto.getIdProveedor());
            stmt.setInt(6, producto.getIdProducto());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error actualizar producto: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public boolean actualizarStock(int idProducto, int nuevoStock) {
        String sql = "UPDATE producto SET stock_producto = ? WHERE id_producto = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, nuevoStock);
            stmt.setInt(2, idProducto);
            boolean exito = stmt.executeUpdate() > 0;
            
            // NOTIFICAR OBSERVADORES si el stock bajó del mínimo
            if (exito && nuevoStock < STOCK_MINIMO) {
                Producto p = obtenerPorId(idProducto);
                if (p != null) {
                    notificar("Stock bajo: " + p.getNombreProducto() + " solo tiene " + nuevoStock + " unidades");
                }
            }
            
            return exito;
        } catch (SQLException e) {
            System.out.println("Error actualizar stock: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM producto WHERE id_producto = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error eliminar producto: " + e.getMessage());
        }
        return false;
    }
    
    public int contarProductosConStock() {
        String sql = "SELECT COUNT(*) FROM producto WHERE stock_producto > 0";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error contar productos stock: " + e.getMessage());
        }
        return 0;
    }
    
    public int contarProductosBajoStock(int limite) {
        String sql = "SELECT COUNT(*) FROM producto WHERE stock_producto < ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, limite);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error contar bajo stock: " + e.getMessage());
        }
        return 0;
    }
    
    private Producto mapearProducto(ResultSet rs) throws SQLException {
        Producto producto = new Producto();
        producto.setIdProducto(rs.getInt("id_producto"));
        producto.setNombreProducto(rs.getString("nombre_producto"));
        producto.setDescripcion(rs.getString("descripcion"));
        producto.setPrecio(rs.getDouble("precio"));
        producto.setStockProducto(rs.getInt("stock_producto"));
        producto.setIdProveedor(rs.getInt("id_proveedor"));
        return producto;
    }
}