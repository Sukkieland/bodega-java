/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bodega.modelo.conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * PATRÓN SINGLETON
 * Garantiza UNA SOLA instancia de conexión a PostgreSQL para todo el programa.
 * Nadie puede crear objetos de esta clase con "new" porque el constructor es privado.
 */
public class ConexionPostgreSQL {
    
    // ═══════════════════════════════════════════════════════════
    // 1. LA ÚNICA INSTANCIA (inicialmente null)
    // ═══════════════════════════════════════════════════════════
    private static ConexionPostgreSQL instancia;
    
    // Objeto Connection de JDBC (el puente entre Java y PostgreSQL)
    private Connection connection;
    
    // ═══════════════════════════════════════════════════════════
    // 2. CONFIGURACIÓN DE TU BASE DE DATOS EN LA NUBE (RENDER)
    // ═══════════════════════════════════════════════════════════
    private static final String URL = "jdbc:postgresql://dpg-d8nk2qcm0tmc73e7k3g0-a.ohio-postgres.render.com:5432/bodegavargas?sslmode=require";
    private static final String USUARIO = "admin_mishell";  
    private static final String PASSWORD = "JsVsrssbDSGulLnJ4MahmvUrUMWcyfzH"; 
    
    // ═══════════════════════════════════════════════════════════
    // 3. CONSTRUCTOR PRIVADO
    //    Solo esta clase misma puede crear la instancia
    // ═══════════════════════════════════════════════════════════
    private ConexionPostgreSQL() {
        try {
            // Cargar el driver de PostgreSQL (como "encender" el puente)
            Class.forName("org.postgresql.Driver");
            
            // Crear la conexión real hacia los servidores de Render
            this.connection = DriverManager.getConnection(URL, USUARIO, PASSWORD);
            
            System.out.println("✅ Conexión Singleton a PostgreSQL establecida exitosamente");
            System.out.println("   📍 Base de datos: bodegavargas (Render Cloud)");
            System.out.println("   🔌 URL: " + URL);
            
        } catch (ClassNotFoundException e) {
            System.err.println("❌ ERROR: No se encontró el driver de PostgreSQL");
            System.err.println("   Asegúrate de tener postgresql-42.7.11.jar en tu proyecto");
            throw new RuntimeException("Driver no encontrado", e);
            
        } catch (SQLException e) {
            System.err.println("❌ ERROR: No se pudo conectar a PostgreSQL en la nube");
            System.err.println("   Verifica las credenciales de Render y tu conexión a internet");
            throw new RuntimeException("Error de conexión", e);
        }
    }
    
    // ═══════════════════════════════════════════════════════════
    // 4. MÉTODO PÚBLICO PARA OBTENER LA ÚNICA INSTANCIA
    // ═══════════════════════════════════════════════════════════
    public static synchronized ConexionPostgreSQL getInstancia() {
        if (instancia == null) {
            System.out.println("🔨 Creando nueva instancia Singleton...");
            instancia = new ConexionPostgreSQL();
        } else {
            System.out.println("♻️ Reutilizando instancia Singleton existente");
        }
        return instancia;
    }
    
    // ═══════════════════════════════════════════════════════════
    // 5. OBTENER LA CONEXIÓN JDBC
    // ═══════════════════════════════════════════════════════════
    public Connection getConnection() {
        try {
            // Si la conexión se cerró o murió, recrearla automáticamente
            if (connection == null || connection.isClosed()) {
                System.out.println("🔄 Reconectando a PostgreSQL en Render...");
                instancia = null;  // Resetear para forzar nueva instancia
                return getInstancia().getConnection();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
    
    // ═══════════════════════════════════════════════════════════
    // 6. CERRAR CONEXIÓN (llamar al cerrar la aplicación)
    // ═══════════════════════════════════════════════════════════
    public void cerrarConexion() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("🔒 Conexión a PostgreSQL cerrada correctamente");
            } catch (SQLException e) {
                System.err.println("⚠️ Error al cerrar conexión: " + e.getMessage());
            }
        }
        instancia = null;  // Permitir crear nueva instancia si se necesita
    }
    
    // ═══════════════════════════════════════════════════════════
    // 7. MÉTODO DE PRUEBA (opcional, para verificar conexión)
    // ═══════════════════════════════════════════════════════════
    public boolean probarConexion() {
        try {
            return connection != null && !connection.isClosed();
        } catch (SQLException e) {
            return false;
        }
    }
}