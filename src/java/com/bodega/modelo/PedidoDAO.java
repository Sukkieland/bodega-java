/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bodega.modelo.dao;

import com.bodega.modelo.conexion.ConexionPostgreSQL;
import com.bodega.modelo.entidades.Pedido;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PedidoDAO {
    
    private Connection connection;
    
    public PedidoDAO() {
        this.connection = ConexionPostgreSQL.getInstancia().getConnection();
    }
    
    public Pedido obtenerPorId(int id) {
        String sql = "SELECT * FROM pedido WHERE id_pedido = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearPedido(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error obtener pedido: " + e.getMessage());
        }
        return null;
    }
    
    public List<Pedido> listarTodos() {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedido ORDER BY fecha DESC";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                lista.add(mapearPedido(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error listar pedidos: " + e.getMessage());
        }
        return lista;
    }
    
    public List<Pedido> listarPorUsuario(int idUsuario) {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedido WHERE id_usuario = ? ORDER BY fecha DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, idUsuario);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                lista.add(mapearPedido(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error listar por usuario: " + e.getMessage());
        }
        return lista;
    }
    
    public List<Pedido> listarPorFecha(Timestamp inicio, Timestamp fin) {
        List<Pedido> lista = new ArrayList<>();
        String sql = "SELECT * FROM pedido WHERE fecha BETWEEN ? AND ? ORDER BY fecha DESC";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, inicio);
            stmt.setTimestamp(2, fin);
            ResultSet rs = stmt.executeQuery();
            
            while (rs.next()) {
                lista.add(mapearPedido(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error listar por fecha: " + e.getMessage());
        }
        return lista;
    }
    
    public int crear(Pedido pedido) {
        String sql = "INSERT INTO pedido (fecha, metodo_pago, total, estado, id_usuario, id_cliente) VALUES (?, ?, ?, ?, ?, ?) RETURNING id_pedido";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setTimestamp(1, pedido.getFecha());
            stmt.setString(2, pedido.getMetodoPago());
            stmt.setDouble(3, pedido.getTotal());
            stmt.setString(4, pedido.getEstado());
            stmt.setInt(5, pedido.getIdUsuario());
            stmt.setInt(6, pedido.getIdCliente());
            
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("id_pedido");
            }
        } catch (SQLException e) {
            System.out.println("Error crear pedido: " + e.getMessage());
        }
        return -1;
    }
    
    public boolean actualizarEstado(int idPedido, String estado) {
        String sql = "UPDATE pedido SET estado = ? WHERE id_pedido = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, estado);
            stmt.setInt(2, idPedido);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error actualizar estado: " + e.getMessage());
        }
        return false;
    }
    
    public boolean eliminar(int id) {
        String sql = "DELETE FROM pedido WHERE id_pedido = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error eliminar pedido: " + e.getMessage());
        }
        return false;
    }
    
    public int contarPedidosMes() {
        String sql = "SELECT COUNT(*) FROM pedido WHERE fecha >= date_trunc('month', CURRENT_DATE)";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (SQLException e) {
            System.out.println("Error contar pedidos mes: " + e.getMessage());
        }
        return 0;
    }
    
    public double sumarVentasMes() {
        String sql = "SELECT COALESCE(SUM(total), 0) FROM pedido WHERE fecha >= date_trunc('month', CURRENT_DATE)";
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            if (rs.next()) {
                return rs.getDouble(1);
            }
        } catch (SQLException e) {
            System.out.println("Error sumar ventas mes: " + e.getMessage());
        }
        return 0;
    }
    
    private Pedido mapearPedido(ResultSet rs) throws SQLException {
        Pedido pedido = new Pedido();
        pedido.setIdPedido(rs.getInt("id_pedido"));
        pedido.setFecha(rs.getTimestamp("fecha"));
        pedido.setMetodoPago(rs.getString("metodo_pago"));
        pedido.setTotal(rs.getDouble("total"));
        pedido.setEstado(rs.getString("estado"));
        pedido.setIdUsuario(rs.getInt("id_usuario"));
        pedido.setIdCliente(rs.getInt("id_cliente"));
        return pedido;
    }
}