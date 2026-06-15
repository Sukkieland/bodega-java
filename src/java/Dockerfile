# Usamos una imagen oficial de Tomcat con Java 21
FROM tomcat:10.1-jdk21

# Borramos las aplicaciones por defecto de Tomcat para que cargue rápido
RUN rm -rf /usr/local/tomcat/webapps/*

# Copiamos tu archivo .war dentro del servidor Tomcat con el nombre ROOT.war
# Esto hace que tu sistema cargue directamente en el link principal sin poner textos raros al final
COPY dist/SistemaBodegaMVC.war /usr/local/tomcat/webapps/ROOT.war

# Exponemos el puerto estándar
EXPOSE 8080

# Comando para encender Tomcat
CMD ["catalina.sh", "run"]

