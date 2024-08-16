/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modelos;

import java.time.LocalDate;

/**
 *
 * @author DELL
 */
public class tarjeta {
   int noDeTarjeta;
   String nombre;
   int tipoDeTarjeta;
    LocalDate fecha;
    String estadoTarjeta;
    

    public int getNoDeTarjeta() {
        return noDeTarjeta;
    }

    public void setNoDeTarjeta(int noDeTarjeta) {
        this.noDeTarjeta = noDeTarjeta;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getTipoDeTarjeta() {
        return tipoDeTarjeta;
    }

    public void setTipoDeTarjeta(int tipoDeTarjeta) {
        this.tipoDeTarjeta = tipoDeTarjeta;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getEstadoTarjeta() {
        return estadoTarjeta;
    }

    public void setEstadoTarjeta(String estadoTarjeta) {
        this.estadoTarjeta = estadoTarjeta;
    }

    public tarjeta(int noDeTarjeta, String nombre, int tipoDeTarjeta, LocalDate fecha, String estadoTarjeta) {
        this.noDeTarjeta = noDeTarjeta;
        this.nombre = nombre;
        this.tipoDeTarjeta = tipoDeTarjeta;
        this.fecha = fecha;
        this.estadoTarjeta = estadoTarjeta;
    }
    
    
}
