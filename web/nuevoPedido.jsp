<%-- 
    Document   : nuevoPedido
    Created on : 14 jun. 2026, 10:42:40
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
    <title>Bodega Vargas | Nuevo Pedido</title>
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

          <li class="nav-item">
            <a href="stock?action=listar" class="nav-link">
              <i class="nav-icon fas fa-box"></i>
              <p>Productos</p>
            </a>
          </li>

          <li class="nav-item menu-open">
            <a href="#" class="nav-link active">
              <i class="nav-icon fas fa-shopping-cart"></i>
              <p>Pedidos<i class="right fas fa-angle-left"></i></p>
            </a>
            <ul class="nav nav-treeview">
              <li class="nav-item">
                <a href="pedido?action=nuevo" class="nav-link active">
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
                <a href="#" class="nav-link"><i class="far fa-circle nav-icon"></i><p>Semanal</p></a>
              </li>
              <li class="nav-item">
                <a href="#" class="nav-link"><i class="far fa-circle nav-icon"></i><p>Mensual</p></a>
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
        <h1 class="m-0">Nuevo Pedido</h1>
      </div>
    </div>

    <section class="content">
      <div class="container-fluid">
        
        <div class="card">
          <div class="card-body">
            <form action="pedido" method="POST">
              <input type="hidden" name="action" value="crear">
              
              <div class="form-group">
                <label>Tipo de Busqueda</label>
                <div class="form-check">
                  <input class="form-check-input" type="radio" name="tipoBusqueda" id="buscarLista" value="lista" checked onclick="toggleBusqueda()">
                  <label class="form-check-label" for="buscarLista">Seleccionar de lista</label>
                </div>
                <div class="form-check">
                  <input class="form-check-input" type="radio" name="tipoBusqueda" id="buscarEscribir" value="escribir" onclick="toggleBusqueda()">
                  <label class="form-check-label" for="buscarEscribir">Escribir nombre</label>
                </div>
              </div>

              <div id="divLista" class="form-group">
                <label>Producto</label>
                <select name="idProductoLista" class="form-control" required>
                  <option value="">Seleccione un producto</option>
                  <% if (productos != null) {
                      for (Producto p : productos) {
                  %>
                  <option value="<%= p.getIdProducto() %>">
                    <%= p.getNombreProducto() %> - S/ <%= String.format("%,.2f", p.getPrecio()) %> (Stock: <%= p.getStockProducto() %>)
                  </option>
                  <% } } %>
                </select>
              </div>

              <div id="divEscribir" class="form-group" style="display:none;">
                <label>Buscar Producto</label>
                <input type="text" id="buscadorProducto" class="form-control" placeholder="Escriba el nombre del producto..." onkeyup="filtrarProductos()">
                <div id="listaResultados" class="list-group mt-2" style="max-height: 200px; overflow-y: auto;"></div>
                <input type="hidden" name="idProductoEscribir" id="idProductoSeleccionado">
                <div id="productoSeleccionado" class="alert alert-info mt-2" style="display:none;">
                  <strong>Producto seleccionado:</strong> <span id="nombreProductoSel"></span>
                </div>
              </div>
              
              <div class="form-group">
                <label>Cantidad</label>
                <input type="number" name="cantidad" class="form-control" min="1" required>
              </div>
              
              <div class="form-group">
                <label>Metodo de Pago</label>
                <select name="metodoPago" class="form-control" required>
                  <option value="">Seleccione metodo de pago</option>
                  <option value="Efectivo">Efectivo</option>
                  <option value="Yape">Yape</option>
                  <option value="Plin">Plin</option>
                </select>
              </div>
              
              <button type="submit" class="btn btn-success">
                <i class="fas fa-save"></i> Registrar Pedido
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
function toggleBusqueda() {
    var lista = document.getElementById('divLista');
    var escribir = document.getElementById('divEscribir');
    var radioLista = document.getElementById('buscarLista');
    
    if (radioLista.checked) {
        lista.style.display = 'block';
        escribir.style.display = 'none';
        lista.querySelector('select').setAttribute('required', 'required');
        document.getElementById('buscadorProducto').removeAttribute('required');
        document.getElementById('idProductoSeleccionado').removeAttribute('required');
    } else {
        lista.style.display = 'none';
        escribir.style.display = 'block';
        lista.querySelector('select').removeAttribute('required');
        document.getElementById('buscadorProducto').setAttribute('required', 'required');
        document.getElementById('idProductoSeleccionado').setAttribute('required', 'required');
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