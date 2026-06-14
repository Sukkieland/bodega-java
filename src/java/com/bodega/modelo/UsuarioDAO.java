/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bodega.modelo.dao;

import com.bodega.modelo.conexion.ConexionPostgreSQL;
import com.bodega.modelo.entidades.Usuario;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO implements IUsuarioDAO {
    
    private Connection connection;
    
    public UsuarioDAO() {
        this.connection = ConexionPostgreSQL.getInstancia().getConnection();
    }
    
    @Override
    public Usuario validarLogin(String username, String password) {
        String sql = "SELECT id_usuario, username, nombre_usuario, contrasena FROM usuario WHERE username = ? AND contrasena = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                Usuario usuario = new Usuario();
                usuario.setIdUsuario(rs.getInt("id_usuario"));
                usuario.setUsername(rs.getString("username"));
                usuario.setNombreUsuario(rs.getString("nombre_usuario"));
                usuario.setContrasena(rs.getString("contrasena"));
                return usuario;
            }
        } catch (SQLException e) {
            System.out.println("Error login: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public Usuario obtenerPorId(int id) {
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();
            
            if (rs.next()) {
                return mapearUsuario(rs);
            }
        } catch (SQLException e) {
            System.out.println("Error obtener usuario: " + e.getMessage());
        }
        return null;
    }
    
    @Override
    public List<Usuario> listarTodos() {
        List<Usuario> lista = new ArrayList<>();
        String sql = "SELECT * FROM usuario";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            while (rs.next()) {
                lista.add(mapearUsuario(rs));
            }
        } catch (SQLException e) {
            System.out.println("Error listar usuarios: " + e.getMessage());
        }
        return lista;
    }
    
    @Override
    public boolean crear(Usuario usuario) {
        String sql = "INSERT INTO usuario (username, nombre_usuario, contrasena) VALUES (?, ?, ?)";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getNombreUsuario());
            stmt.setString(3, usuario.getContrasena());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error crear usuario: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public boolean actualizar(Usuario usuario) {
        String sql = "UPDATE usuario SET username = ?, nombre_usuario = ?, contrasena = ? WHERE id_usuario = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, usuario.getUsername());
            stmt.setString(2, usuario.getNombreUsuario());
            stmt.setString(3, usuario.getContrasena());
            stmt.setInt(4, usuario.getIdUsuario());
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error actualizar usuario: " + e.getMessage());
        }
        return false;
    }
    
    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Error eliminar usuario: " + e.getMessage());
        }
        return false;
    }
    
    private Usuario mapearUsuario(ResultSet rs) throws SQLException {
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(rs.getInt("id_usuario"));
        usuario.setUsername(rs.getString("username"));
        usuario.setNombreUsuario(rs.getString("nombre_usuario"));
        usuario.setContrasena(rs.getString("contrasena"));
        return usuario;
    }
}