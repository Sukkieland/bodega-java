/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.bodega.controlador;

import com.bodega.modelo.ProductoBuilder;
import com.bodega.modelo.dao.ProductoDAO;
import com.bodega.modelo.entidades.Producto;
import com.bodega.modelo.entidades.Usuario;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "StockServlet", urlPatterns = {"/stock"})
public class StockServlet extends HttpServlet {

    private ProductoDAO productoDAO;
    
    @Override
    public void init() throws ServletException {
        productoDAO = new ProductoDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String action = request.getParameter("action");
        
        if (action == null || action.equals("listar")) {
            List<Producto> productos = productoDAO.listarTodos();
            request.setAttribute("productos", productos);
            request.getRequestDispatcher("stock.jsp").forward(request, response);
            
        } else if (action.equals("agregar")) {
            List<Producto> productos = productoDAO.listarTodos();
            request.setAttribute("productos", productos);
            request.getRequestDispatcher("agregarStock.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        String action = request.getParameter("action");
        
        if (action.equals("actualizar")) {
            String tipo = request.getParameter("tipoStock");
            
            if ("existente".equals(tipo)) {
                // Agregar stock a producto existente
                int idProducto = Integer.parseInt(request.getParameter("idProducto"));
                int cantidad = Integer.parseInt(request.getParameter("cantidad"));
                
                Producto producto = productoDAO.obtenerPorId(idProducto);
                
                if (producto != null) {
                    int nuevoStock = producto.getStockProducto() + cantidad;
                    boolean exito = productoDAO.actualizarStock(idProducto, nuevoStock);
                    
                    if (exito) {
                        request.setAttribute("mensaje", "Stock agregado: " + producto.getNombreProducto() + " ahora tiene " + nuevoStock + " unidades");
                    } else {
                        request.setAttribute("error", "Error al actualizar stock");
                    }
                } else {
                    request.setAttribute("error", "Producto no encontrado");
                }
                
            } else if ("nuevo".equals(tipo)) {
                // Crear producto nuevo con Builder
                String nombre = request.getParameter("nombreProducto");
                String descripcion = request.getParameter("descripcion");
                double precio = Double.parseDouble(request.getParameter("precio"));
                int stock = Integer.parseInt(request.getParameter("cantidad"));
                int idProveedor = Integer.parseInt(request.getParameter("idProveedor"));
                
                Producto nuevoProducto = new ProductoBuilder()
                    .nombre(nombre)
                    .descripcion(descripcion)
                    .precio(precio)
                    .stock(stock)
                    .proveedor(idProveedor)
                    .build();
                
                boolean exito = productoDAO.crear(nuevoProducto);
                
                if (exito) {
                    request.setAttribute("mensaje", "Producto nuevo creado: " + nombre + " con " + stock + " unidades");
                } else {
                    request.setAttribute("error", "Error al crear producto");
                }
            }
            
            List<Producto> productos = productoDAO.listarTodos();
            request.setAttribute("productos", productos);
            request.getRequestDispatcher("stock.jsp").forward(request, response);
        }
    }
}