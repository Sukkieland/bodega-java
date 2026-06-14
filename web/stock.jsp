<%-- 
    Document   : stock
    Created on : 14 jun. 2026, 10:12:43
    Author     : mishellcastillo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.bodega.modelo.entidades.Producto"%>
<%@page import="com.bodega.modelo.entidades.Usuario"%>
<%@page import="java.util.List"%>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }
    List<Producto> productos = (List<Producto>) request.getAttribute("productos");
    String mensaje = (String) request.getAttribute("mensaje");
    String error = (String) request.getAttribute("error");
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bodega Vargas | Stock</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/fontawesome-free/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/dist/css/adminlte.min.css">
</head>
<body class="hold-transition sidebar-mini">
<div class="wrapper">

  <!-- Navbar -->
  <nav class="main-header navbar navbar-expand navbar-white navbar-light">
    <ul class="navbar-nav">
      <li class="nav-item">
        <a class="nav-link" data-widget="pushmenu" href="#" role="button"><i class="fas fa-bars"></i></a>
      </li>
      <li class="nav-item d-none d-sm-inline-block">
        <a href="dashboard" class="nav-link">Home</a>
      </li>
    </ul>
    <ul class="navbar-nav ml-auto">
      <li class="nav-item">
        <a class="nav-link" href="logout"><i class="fas fa-sign-out-alt"></i> Cerrar Sesión</a>
      </li>
    </ul>
  </nav>

  <!-- Sidebar -->
  <aside class="main-sidebar sidebar-dark-primary elevation-4">
    <a href="dashboard" class="brand-link">
      <span class="brand-text font-weight-light">Bodega Vargas</span>
    </a>
    <div class="sidebar">
      <div class="user-panel mt-3 pb-3 mb-3 d-flex">
        <div class="info">
          <a href="#" class="d-block"><%= usuario.getNombreUsuario() %></a>
        </div>
      </div>
      <nav class="mt-2">
        <ul class="nav nav-pills nav-sidebar flex-column" data-widget="treeview" role="menu" data-accordion="false">
          
          <li class="nav-item">
            <a href="dashboard" class="nav-link">
              <i class="nav-icon fas fa-tachometer-alt"></i>
              <p>Dashboard</p>
            </a>
          </li>

          <li class="nav-item menu-open">
            <a href="#" class="nav-link active">
              <i class="nav-icon fas fa-box"></i>
              <p>Productos<i class="right fas fa-angle-left"></i></p>
            </a>
            <ul class="nav nav-treeview">
              <li class="nav-item">
                <a href="stock?action=listar" class="nav-link active">
                  <i class="far fa-circle nav-icon"></i>
                  <p>Ver Stock</p>
                </a>
              </li>
              <li class="nav-item">
                <a href="stock?action=agregar" class="nav-link">
                  <i class="far fa-circle nav-icon"></i>
                  <p>Agregar Stock</p>
                </a>
              </li>
            </ul>
          </li>

          <li class="nav-item">
            <a href="#" class="nav-link">
              <i class="nav-icon fas fa-shopping-cart"></i>
              <p>Pedidos<i class="fas fa-angle-left right"></i></p>
            </a>
            <ul class="nav nav-treeview">
              <li class="nav-item">
                <a href="pedido?action=nuevo" class="nav-link">
                  <i class="far fa-circle nav-icon"></i>
                  <p>Nuevo Pedido</p>
                </a>
              </li>
              <li class="nav-item">
                <a href="pedido?action=listar" class="nav-link">
                  <i class="far fa-circle nav-icon"></i>
                  <p>Ver Pedidos</p>
                </a>
              </li>
            </ul>
          </li>

          <li class="nav-item">
            <a href="#" class="nav-link">
              <i class="nav-icon fas fa-chart-pie"></i>
              <p>Reportes<i class="fas fa-angle-left right"></i></p>
            </a>
            <ul class="nav nav-treeview">
              <li class="nav-item">
                <a href="reporte?action=semanal" class="nav-link">
                  <i class="far fa-circle nav-icon"></i>
                  <p>Semanal</p>
                </a>
              </li>
              <li class="nav-item">
                <a href="reporte?action=mensual" class="nav-link">
                  <i class="far fa-circle nav-icon"></i>
                  <p>Mensual</p>
                </a>
              </li>
            </ul>
          </li>

        </ul>
      </nav>
    </div>
  </aside>

  <!-- Content -->
  <div class="content-wrapper">
    <div class="content-header">
      <div class="container-fluid">
        <h1 class="m-0">Stock de Productos</h1>
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
            <h3 class="card-title">Productos</h3>
            <a href="stock?action=agregar" class="btn btn-primary float-right">
              <i class="fas fa-plus"></i> Agregar Stock
            </a>
          </div>
          
          <div class="card-body">
            <table class="table table-bordered table-striped">
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Nombre</th>
                  <th>Descripción</th>
                  <th>Precio</th>
                  <th>Stock</th>
                  <th>Estado</th>
                </tr>
              </thead>
              <tbody>
                <% if (productos != null) {
                    for (Producto p : productos) {
                        String estado = p.getStockProducto() < 10 ? "Bajo Stock" : "OK";
                        String color = p.getStockProducto() < 10 ? "text-danger" : "text-success";
                %>
                <tr>
                  <td><%= p.getIdProducto() %></td>
                  <td><%= p.getNombreProducto() %></td>
                  <td><%= p.getDescripcion() %></td>
                  <td>S/ <%= String.format("%,.2f", p.getPrecio()) %></td>
                  <td><%= p.getStockProducto() %></td>
                  <td class="<%= color %>"><strong><%= estado %></strong></td>
                </tr>
                <% } } %>
              </tbody>
            </table>
          </div>
        </div>

      </div>
    </section>
  </div>

  <footer class="main-footer">
    <strong>Bodega Vargas</strong> - Sistema de Gestión
  </footer>

</div>

<script src="${pageContext.request.contextPath}/plugins/jquery/jquery.min.js"></script>
<script src="${pageContext.request.contextPath}/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/dist/js/adminlte.min.js"></script>
</body>
</html>