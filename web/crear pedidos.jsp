<%-- 
    Document   : crear pedidos
    Created on : 14 jun. 2026, 10:44:49
    Author     : mishellcastillo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.bodega.modelo.entidades.Pedido"%>
<%@page import="java.util.List"%>
<%
    List<Pedido> pedidos = (List<Pedido>) request.getAttribute("pedidos");
    String mensaje = (String) request.getAttribute("mensaje");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bodega Vargas | Pedidos</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/fontawesome-free/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/dist/css/adminlte.min.css">
</head>
<body class="hold-transition sidebar-mini">
<div class="wrapper">

  <nav class="main-header navbar navbar-expand navbar-white navbar-light">
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link" href="dashboard"><i class="fas fa-arrow-left"></i> Volver al Dashboard</a>
      </li>
    </ul>
  </nav>

  <div class="content-wrapper">
    <div class="content-header">
      <div class="container-fluid">
        <h1 class="m-0">Pedidos</h1>
      </div>
    </div>

    <section class="content">
      <div class="container-fluid">
        
        <% if (mensaje != null) { %>
            <div class="alert alert-success"><%= mensaje %></div>
        <% } %>
        <% if (error != null) { %>
            <div class="alert alert-danger"><%= error %></div>
        <% } %>

        <div class="card">
          <div class="card-header">
            <h3 class="card-title">Lista de Pedidos</h3>
            <a href="pedido?action=nuevo" class="btn btn-primary float-right">
              <i class="fas fa-plus"></i> Nuevo Pedido
            </a>
          </div>
          
          <div class="card-body">
            <table class="table table-bordered table-striped">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Fecha</th>
                  <th>Método de Pago</th>
                  <th>Total</th>
                  <th>Estado</th>
                </tr>
              </thead>
              <tbody>
                <% if (pedidos != null) {
                    for (Pedido p : pedidos) {
                %>
                <tr>
                  <td><%= p.getIdPedido() %></td>
                  <td><%= p.getFecha() %></td>
                  <td><%= p.getMetodoPago() %></td>
                  <td>S/ <%= String.format("%,.2f", p.getTotal()) %></td>
                  <td><span class="badge badge-success"><%= p.getEstado() %></span></td>
                </tr>
                <% } } %>
              </tbody>
            </table>
          </div>
        </div>

      </div>
    </section>
  </div>

</div>

<script src="${pageContext.request.contextPath}/plugins/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/dist/js/adminlte.min.js"></script>
</body>
</html>