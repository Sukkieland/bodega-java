/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.bodega.observer;

import java.util.ArrayList;
import java.util.List;

public abstract class Subject {
    protected List<Observer> observadores = new ArrayList<>();
    
    public void agregarObserver(Observer o) {
        observadores.add(o);
    }
    
    public void eliminarObserver(Observer o) {
        observadores.remove(o);
    }
    
    public void notificar(String mensaje) {
        for (Observer o : observadores) {
            o.update(mensaje);
        }
    }
}