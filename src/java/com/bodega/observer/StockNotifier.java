/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bodega.observer;

public class StockNotifier implements Observer {
    
    private String ultimaAlerta;
    
    @Override
    public void update(String mensaje) {
        this.ultimaAlerta = mensaje;
        System.out.println("ALERTA STOCK: " + mensaje);
    }
    
    public String getUltimaAlerta() {
        return ultimaAlerta;
    }
}