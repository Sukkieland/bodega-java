/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.bodega.controlador;

import com.bodega.modelo.dao.PedidoDAO;
import com.bodega.modelo.dao.ProductoDAO;
import com.bodega.modelo.entidades.Pedido;
import com.bodega.modelo.entidades.Producto;
import com.bodega.modelo.entidades.Usuario;
import com.bodega.servicios.PedidoService;  // <-- IMPORT CORREGIDO: servicios (plural)
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "PedidoServlet", urlPatterns = {"/pedido"})
public class PedidoServlet extends HttpServlet {

    private PedidoDAO pedidoDAO;
    private ProductoDAO productoDAO;
    private PedidoService pedidoService;  // <-- DECLARAR
    
    @Override
    public void init() throws ServletException {
        pedidoDAO = new PedidoDAO();
        productoDAO = new ProductoDAO();
        pedidoService = new PedidoService();  // <-- INICIALIZAR (usa constructor sin args)
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
            List<Pedido> pedidos = pedidoDAO.listarTodos();
            request.setAttribute("pedidos", pedidos);
            request.getRequestDispatcher("pedidos.jsp").forward(request, response);
            
        } else if (action.equals("nuevo")) {
            List<Producto> productos = productoDAO.listarTodos();
            request.setAttribute("productos", productos);
            request.getRequestDispatcher("nuevoPedido.jsp").forward(request, response);
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
        
        if (action.equals("crear")) {
            String tipoBusqueda = request.getParameter("tipoBusqueda");
            Producto producto = null;
            
            if ("lista".equals(tipoBusqueda)) {
                int idProducto = Integer.parseInt(request.getParameter("idProductoLista"));
                producto = productoDAO.obtenerPorId(idProducto);
            } else {
                int idProductoEscrito = Integer.parseInt(request.getParameter("idProductoEscribir"));
                producto = productoDAO.obtenerPorId(idProductoEscrito);
            }
            
            int cantidad = Integer.parseInt(request.getParameter("cantidad"));
            String metodoPago = request.getParameter("metodoPago");
            Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
            
            // ============================================
            // USAR EL SERVICIO - lógica de negocio delegada
            // ============================================
            boolean exito = pedidoService.registrarPedido(producto, cantidad, metodoPago, usuario);

            if (exito) {
                request.setAttribute("mensaje", "Pedido registrado correctamente");
            } else {
                request.setAttribute("error", "Stock insuficiente o producto no encontrado");
            }
            
            List<Pedido> pedidos = pedidoDAO.listarTodos();
            request.setAttribute("pedidos", pedidos);
            request.getRequestDispatcher("pedidos.jsp").forward(request, response);
        }
    }
}