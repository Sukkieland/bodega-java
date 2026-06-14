/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.bodega.modelo.dao;

import com.bodega.modelo.entidades.Usuario;
import java.util.List;

public interface IUsuarioDAO {
    Usuario validarLogin(String username, String password);
    Usuario obtenerPorId(int id);
    List<Usuario> listarTodos();         
    boolean crear(Usuario usuario);
    boolean actualizar(Usuario usuario);
    boolean eliminar(int id);
}