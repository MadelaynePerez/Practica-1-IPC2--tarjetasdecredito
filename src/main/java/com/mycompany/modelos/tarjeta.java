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

    long noDeTarjeta;
    String nombre;
    int tipoDeTarjeta;
    LocalDate fecha;
    String estadoTarjeta;
    int idCliente;
    double dinero;

    public double getDinero() {
        return dinero;
    }

    public void setDinero(double dinero) {
        this.dinero = dinero;
    }
    
    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    

    public long getNoDeTarjeta() {
        return noDeTarjeta;
    }

    public void setNoDeTarjeta(long noDeTarjeta) {
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

    public tarjeta(long noDeTarjeta, String nombre, int tipoDeTarjeta, LocalDate fecha, String estadoTarjeta, int idCliente, double dinero) {
        this.noDeTarjeta = noDeTarjeta;
        this.nombre = nombre;
        this.tipoDeTarjeta = tipoDeTarjeta;
        this.fecha = fecha;
        this.estadoTarjeta = estadoTarjeta;
        this.idCliente = idCliente;
        this.dinero = dinero;
    }
    
    public tarjeta(long noTarjeta){
        this.noDeTarjeta = noTarjeta;
    }

    @Override
    public String toString() {
      return String.valueOf(noDeTarjeta);
    }
    
}
