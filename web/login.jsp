<%-- 
    Document   : login
    Created on : 13 jun. 2026, 17:12:20
    Author     : mishellcastillo
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
  <head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link href="https://fonts.googleapis.com/css?family=Roboto:300,400&display=swap" rel="stylesheet">

    <link rel="stylesheet" href="${pageContext.request.contextPath}/fonts/icomoon/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/owl.carousel.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/bootstrap.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">

    <title>Bodega Vargas | Iniciar Sesión</title>
  </head>
  <body>
  
  <div class="d-lg-flex half">
    <div class="bg order-1 order-md-2" 
         style="background-image: url('${pageContext.request.contextPath}/images/bg_1.jpg'); 
                background-size: cover; 
                background-position: center; 
                min-height: 100vh;">
    </div>
    
    <div class="contents order-2 order-md-1">
      <div class="container">
        <div class="row align-items-center justify-content-center">
          <div class="col-md-7">
            <h3>Bienvenido(a) a <strong>Bodega Vargas</strong></h3>
            <p class="mb-4">Ingresa tus credenciales para administrar las ventas y el stock.</p>
            
            <% if (request.getAttribute("error") != null) { %>
                <div class="alert alert-danger" role="alert">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>

            <form action="${pageContext.request.contextPath}/login" method="POST">
              
              <div class="form-group first">
                <label for="username">Usuario</label>
                <input type="text" name="username" class="form-control" placeholder="Tu usuario" id="username" required>
              </div>
              
              <div class="form-group last mb-3">
                <label for="password">Contraseña</label>
                <input type="password" name="password" class="form-control" placeholder="Tu Contraseña" id="password" required>
              </div>
              
              <div class="d-flex mb-5 align-items-center">
                <label class="control control--checkbox mb-0">
                  <span class="caption">Recordarme</span>
                  <input type="checkbox" checked="checked"/>
                  <div class="control__indicator"></div>
                </label>
                <span class="ml-auto">
                  <a href="#" class="forgot-pass">¿Olvidaste tu contraseña?</a>
                </span> 
              </div>

              <input type="submit" value="Ingresar" class="btn btn-block btn-primary">

            </form>
          </div>
        </div>
      </div>
    </div>
  </div>
    

    <script src="${pageContext.request.contextPath}/js/jquery-3.3.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/popper.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/js/main.js"></script>
  </body>
</html>
    