<%-- 
    Document   : agregarStock
    Created on : 14 jun. 2026, 10:13:05
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
%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Bodega Vargas | Agregar Stock</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/plugins/fontawesome-free/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/dist/css/adminlte.min.css">
    <style>
        .list-group-item:hover {
            background-color: #007bff;
            color: white;
            cursor: pointer;
        }
    </style>
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
                <a href="stock?action=listar" class="nav-link">
                  <i class="far fa-circle nav-icon"></i>
                  <p>Ver Stock</p>
                </a>
              </li>
              <li class="nav-item">
                <a href="stock?action=agregar" class="nav-link active">
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
        <h1 class="m-0">Agregar Stock</h1>
      </div>
    </div>

    <section class="content">
      <div class="container-fluid">
        
        <div class="card">
          <div class="card-body">
            
            <div class="form-group">
              <label>Tipo de Operación</label>
              <div class="form-check">
                <input class="form-check-input" type="radio" name="tipoOperacion" id="opExistente" value="existente" checked onclick="toggleTipo()">
                <label class="form-check-label" for="opExistente">Producto Existente</label>
              </div>
              <div class="form-check">
                <input class="form-check-input" type="radio" name="tipoOperacion" id="opNuevo" value="nuevo" onclick="toggleTipo()">
                <label class="form-check-label" for="opNuevo">Producto Nuevo</label>
              </div>
            </div>

            <form action="stock" method="POST" id="formExistente">
              <input type="hidden" name="action" value="actualizar">
              <input type="hidden" name="tipoStock" value="existente">
              
              <div class="form-group">
                <label>Buscar Producto</label>
                <input type="text" id="buscadorProducto" class="form-control" placeholder="Escriba el nombre del producto..." onkeyup="filtrarProductos()">
                <div id="listaResultados" class="list-group mt-2" style="max-height: 200px; overflow-y: auto;"></div>
                <input type="hidden" name="idProducto" id="idProductoSeleccionado">
                <div id="productoSeleccionado" class="alert alert-info mt-2" style="display:none;">
                  <strong>Producto seleccionado:</strong> <span id="nombreProductoSel"></span>
                </div>
              </div>
              
              <div class="form-group">
                <label>Cantidad a Agregar</label>
                <input type="number" name="cantidad" class="form-control" min="1" required>
              </div>
              
              <button type="submit" class="btn btn-success">
                <i class="fas fa-save"></i> Agregar Stock
              </button>
            </form>

            <form action="stock" method="POST" id="formNuevo" style="display:none;">
              <input type="hidden" name="action" value="actualizar">
              <input type="hidden" name="tipoStock" value="nuevo">
              
              <div class="form-group">
                <label>Nombre del Producto</label>
                <input type="text" name="nombreProducto" class="form-control" required>
              </div>
              
              <div class="form-group">
                <label>Descripción</label>
                <input type="text" name="descripcion" class="form-control" required>
              </div>
              
              <div class="form-group">
                <label>Precio</label>
                <input type="number" name="precio" class="form-control" step="0.01" min="0" required>
              </div>
              
              <div class="form-group">
                <label>Cantidad Inicial</label>
                <input type="number" name="cantidad" class="form-control" min="1" required>
              </div>
              
              <div class="form-group">
                <label>ID Proveedor</label>
                <input type="number" name="idProveedor" class="form-control" min="1" required>
              </div>
              
              <button type="submit" class="btn btn-primary">
                <i class="fas fa-plus"></i> Crear Producto
              </button>
            </form>

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

<script>
function toggleTipo() {
    var formExistente = document.getElementById('formExistente');
    var formNuevo = document.getElementById('formNuevo');
    var radioExistente = document.getElementById('opExistente');
    
    if (radioExistente.checked) {
        formExistente.style.display = 'block';
        formNuevo.style.display = 'none';
    } else {
        formExistente.style.display = 'none';
        formNuevo.style.display = 'block';
    }
}

var productos = [
    <% if (productos != null) {
        for (int i = 0; i < productos.size(); i++) {
            Producto p = productos.get(i);
    %>
    {id: <%= p.getIdProducto() %>, nombre: "<%= p.getNombreProducto() %>", precio: <%= p.getPrecio() %>, stock: <%= p.getStockProducto() %>}
    <% if (i < productos.size() - 1) { %>,<% } %>
    <% } } %>
];

function filtrarProductos() {
    var input = document.getElementById('buscadorProducto');
    var filtro = input.value.toLowerCase();
    var listaResultados = document.getElementById('listaResultados');
    
    listaResultados.innerHTML = '';
    
    if (filtro.length < 2) {
        return;
    }
    
    var coincidencias = productos.filter(function(p) {
        return p.nombre.toLowerCase().includes(filtro);
    });
    
    coincidencias.forEach(function(p) {
        var item = document.createElement('a');
        item.href = '#';
        item.className = 'list-group-item list-group-item-action';
        item.innerHTML = '<strong>' + p.nombre + '</strong> - S/ ' + p.precio.toFixed(2) + ' (Stock: ' + p.stock + ')';
        item.onclick = function() {
            seleccionarProducto(p);
            return false;
        };
        listaResultados.appendChild(item);
    });
}

function seleccionarProducto(producto) {
    document.getElementById('idProductoSeleccionado').value = producto.id;
    document.getElementById('nombreProductoSel').textContent = producto.nombre;
    document.getElementById('productoSeleccionado').style.display = 'block';
    document.getElementById('listaResultados').innerHTML = '';
    document.getElementById('buscadorProducto').value = '';
}
</script>

</body>
</html>