/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.bodega.controlador;

import com.bodega.modelo.dao.PedidoDAO;
import com.bodega.modelo.dao.ProductoDAO;
import com.bodega.modelo.entidades.Usuario;
import com.bodega.observer.StockNotifier;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "DashboardServlet", urlPatterns = {"/dashboard"})
public class DashboardServlet extends HttpServlet {

    private PedidoDAO pedidoDAO;
    private ProductoDAO productoDAO;
    private StockNotifier stockNotifier;
    
    @Override
    public void init() throws ServletException {
        pedidoDAO = new PedidoDAO();
        productoDAO = new ProductoDAO();
        
        // Crear y registrar el observer
        stockNotifier = new StockNotifier();
        productoDAO.agregarObserver(stockNotifier);
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        HttpSession session = request.getSession(false);
        
        if (session == null || session.getAttribute("usuarioLogueado") == null) {
            response.sendRedirect("login.jsp");
            return;
        }
        
        // Datos para el dashboard
        int totalPedidosMes = pedidoDAO.contarPedidosMes();
        int totalProductosStock = productoDAO.contarProductosConStock();
        double ventasMes = pedidoDAO.sumarVentasMes();
        int productosBajoStock = productoDAO.contarProductosBajoStock(10);
        
        // Verificar si hay alerta de stock
        String alertaStock = stockNotifier.getUltimaAlerta();
        if (alertaStock != null) {
            request.setAttribute("alertaStock", alertaStock);
        }
        
        // Enviar a la vista
        request.setAttribute("totalPedidosMes", totalPedidosMes);
        request.setAttribute("totalProductosStock", totalProductosStock);
        request.setAttribute("ventasMes", ventasMes);
        request.setAttribute("productosBajoStock", productosBajoStock);
        
        request.getRequestDispatcher("dashboard.jsp").forward(request, response);
    }
}