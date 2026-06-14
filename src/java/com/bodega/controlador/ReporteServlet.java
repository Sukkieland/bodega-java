/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.bodega.controlador;

import com.bodega.modelo.dao.PedidoDAO;
import com.bodega.modelo.entidades.Pedido;
import com.bodega.modelo.entidades.Usuario;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.WeekFields;
import java.util.List;
import java.util.Locale;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

@WebServlet(name = "ReporteServlet", urlPatterns = {"/reporte"})
public class ReporteServlet extends HttpServlet {

    private PedidoDAO pedidoDAO;
    
    @Override
    public void init() throws ServletException {
        pedidoDAO = new PedidoDAO();
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
        
        if (action == null || action.equals("semanal")) {
            LocalDateTime inicioSemana = LocalDate.now()
                .with(WeekFields.of(Locale.getDefault()).dayOfWeek(), 1)
                .atStartOfDay();
            LocalDateTime finSemana = inicioSemana.plusDays(6).plusHours(23).plusMinutes(59);
            
            List<Pedido> pedidos = pedidoDAO.listarPorFecha(
                Timestamp.valueOf(inicioSemana),
                Timestamp.valueOf(finSemana)
            );
            
            double totalSemana = 0;
            for (Pedido p : pedidos) {
                totalSemana += p.getTotal();
            }
            
            request.setAttribute("pedidos", pedidos);
            request.setAttribute("totalSemana", totalSemana);
            request.setAttribute("titulo", "Reporte Semanal");
            request.getRequestDispatcher("reporte.jsp").forward(request, response);
            
        } else if (action.equals("mensual") || action.equals("ventas")) {
            LocalDateTime inicioMes = LocalDate.now()
                .withDayOfMonth(1)
                .atStartOfDay();
            LocalDateTime finMes = LocalDate.now()
                .withDayOfMonth(LocalDate.now().lengthOfMonth())
                .atTime(23, 59, 59);
            
            List<Pedido> pedidos = pedidoDAO.listarPorFecha(
                Timestamp.valueOf(inicioMes),
                Timestamp.valueOf(finMes)
            );
            
            double totalMes = 0;
            for (Pedido p : pedidos) {
                totalMes += p.getTotal();
            }
            
            request.setAttribute("pedidos", pedidos);
            request.setAttribute("totalMes", totalMes);
            request.setAttribute("titulo", action.equals("ventas") ? "Ventas del Mes" : "Reporte Mensual");
            request.getRequestDispatcher("reporte.jsp").forward(request, response);
        }
    }
}