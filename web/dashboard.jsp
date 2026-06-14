<%-- 
    Document   : dashboard
    Created on : 13 jun. 2026, 21:17:12
    Author     : mishellcastillo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="com.bodega.modelo.entidades.Usuario"%>
<%
    Usuario usuario = (Usuario) session.getAttribute("usuarioLogueado");
    if (usuario == null) {
        response.sendRedirect("login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Bodega Vargas | Dashboard</title>
  <link rel="stylesheet" href="https://fonts.googleapis.com/css?family=Source+Sans+Pro:300,400,400i,700&display=fallback">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/fontawesome-free/css/all.min.css">
  <link rel="stylesheet" href="https://code.ionicframework.com/ionicons/2.0.1/css/ionicons.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/tempusdominus-bootstrap-4/css/tempusdominus-bootstrap-4.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/icheck-bootstrap/icheck-bootstrap.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/jqvmap/jqvmap.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/dist/css/adminlte.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/overlayScrollbars/css/OverlayScrollbars.min.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/daterangepicker/daterangepicker.css">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/summernote/summernote-bs4.min.css">
</head>
<body class="hold-transition sidebar-mini layout-fixed">
<div class="wrapper">

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
        <a class="nav-link" href="logout">
          <i class="fas fa-sign-out-alt"></i> Cerrar Sesión
        </a>
      </li>
    </ul>
  </nav>

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
          
          <li class="nav-item menu-open">
            <a href="#" class="nav-link active">
              <i class="nav-icon fas fa-tachometer-alt"></i>
              <p>Dashboard<i class="right fas fa-angle-left"></i></p>
            </a>
            <ul class="nav nav-treeview">
              <li class="nav-item">
                <a href="dashboard" class="nav-link active">
                  <i class="far fa-circle nav-icon"></i>
                  <p>Dashboard Principal</p>
                </a>
              </li>
            </ul>
          </li>

          <li class="nav-item">
            <a href="#" class="nav-link">
              <i class="nav-icon fas fa-box"></i>
              <p>Productos<i class="fas fa-angle-left right"></i></p>
            </a>
            <ul class="nav nav-treeview">
              <li class="nav-item">
                <a href="stock?action=listar" class="nav-link">
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

  <div class="content-wrapper">
    <div class="content-header">
      <div class="container-fluid">
        <div class="row mb-2">
          <div class="col-sm-6">
            <h1 class="m-0">Dashboard</h1>
            <% if (request.getAttribute("alertaStock") != null) { %>
    <div class="alert alert-warning alert-dismissible">
        <button type="button" class="close" data-dismiss="alert" aria-hidden="true">&times;</button>
        <h5><i class="icon fas fa-exclamation-triangle"></i> Alerta de Stock!</h5>
        <%= request.getAttribute("alertaStock") %>
    </div>
<% } %>
          </div>
          <div class="col-sm-6">
            <ol class="breadcrumb float-sm-right">
              <li class="breadcrumb-item"><a href="#">Home</a></li>
              <li class="breadcrumb-item active">Dashboard</li>
            </ol>
          </div>
        </div>
      </div>
    </div>

    <section class="content">
      <div class="container-fluid">
        
        <div class="row">
          <div class="col-lg-3 col-6">
            <div class="small-box bg-info">
              <div class="inner">
                <h3><%= request.getAttribute("totalPedidosMes") != null ? request.getAttribute("totalPedidosMes") : "0" %></h3>
                <p>Pedidos del Mes</p>
              </div>
              <div class="icon">
                <i class="ion ion-bag"></i>
              </div>
              <a href="pedido?action=listar" class="small-box-footer">Ver más <i class="fas fa-arrow-circle-right"></i></a>
            </div>
          </div>
          
          <div class="col-lg-3 col-6">
            <div class="small-box bg-success">
              <div class="inner">
                <h3><%= request.getAttribute("totalProductosStock") != null ? request.getAttribute("totalProductosStock") : "0" %></h3>
                <p>Productos en Stock</p>
              </div>
              <div class="icon">
                <i class="ion ion-stats-bars"></i>
              </div>
              <a href="stock?action=listar" class="small-box-footer">Ver más <i class="fas fa-arrow-circle-right"></i></a>
            </div>
          </div>
          
          <div class="col-lg-3 col-6">
            <div class="small-box bg-warning">
              <div class="inner">
                <h3>S/ <%= request.getAttribute("ventasMes") != null ? String.format("%,.2f", request.getAttribute("ventasMes")) : "0.00" %></h3>
                <p>Ventas del Mes</p>
              </div>
              <div class="icon">
                <i class="ion ion-cash"></i>
              </div>
              <a href="reporte?action=ventas" class="small-box-footer">Ver más <i class="fas fa-arrow-circle-right"></i></a>
            </div>
          </div>
          
          <div class="col-lg-3 col-6">
            <div class="small-box bg-danger">
              <div class="inner">
                <h3><%= request.getAttribute("productosBajoStock") != null ? request.getAttribute("productosBajoStock") : "0" %></h3>
                <p>Productos Bajo Stock</p>
              </div>
              <div class="icon">
                <i class="ion ion-alert-circled"></i>
              </div>
              <a href="#" class="small-box-footer">Ver más <i class="fas fa-arrow-circle-right"></i></a>
            </div>
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
<script src="${pageContext.request.contextPath}/plugins/jquery-ui/jquery-ui.min.js"></script>
<script src="${pageContext.request.contextPath}/plugins/bootstrap/js/bootstrap.bundle.min.js"></script>
<script src="${pageContext.request.contextPath}/plugins/chart.js/Chart.min.js"></script>
<script src="${pageContext.request.contextPath}/plugins/sparklines/sparkline.js"></script>
<script src="${pageContext.request.contextPath}/plugins/jqvmap/jquery.vmap.min.js"></script>
<script src="${pageContext.request.contextPath}/plugins/jqvmap/maps/jquery.vmap.usa.js"></script>
<script src="${pageContext.request.contextPath}/plugins/jquery-knob/jquery.knob.min.js"></script>
<script src="${pageContext.request.contextPath}/plugins/moment/moment.min.js"></script>
<script src="${pageContext.request.contextPath}/plugins/daterangepicker/daterangepicker.js"></script>
<script src="${pageContext.request.contextPath}/plugins/tempusdominus-bootstrap-4/js/tempusdominus-bootstrap-4.min.js"></script>
<script src="${pageContext.request.contextPath}/plugins/summernote/summernote-bs4.min.js"></script>
<script src="${pageContext.request.contextPath}/plugins/overlayScrollbars/js/jquery.overlayScrollbars.min.js"></script>
<script src="${pageContext.request.contextPath}/dist/js/adminlte.js"></script>

</body>
</html>