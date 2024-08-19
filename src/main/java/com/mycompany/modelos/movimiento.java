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
public class movimiento {
     long tarjetaOrigen;
    int idMovimiento;
    String descripcion;
    double monto;
    String establecimiento;
    LocalDate fecha;

    public long getTarjetaOrigen() {
        return tarjetaOrigen;
    }

    public void setTarjetaOrigen(long tarjetaOrigen) {
        this.tarjetaOrigen = tarjetaOrigen;
    }

    public int getIdMovimiento() {
        return idMovimiento;
    }

    public String getEstablecimiento() {
        return establecimiento;
    }

    public void setEstablecimiento(String establecimiento) {
        this.establecimiento = establecimiento;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public void setIdMovimiento(int idMovimiento) {
        this.idMovimiento = idMovimiento;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getMonto() {
        return monto;
    }

    public void setMonto(double monto) {
        this.monto = monto;
    }

    public movimiento(int idMovimiento, String descripcion, double monto, String establecimiento, LocalDate fecha, Long tarjetaOrigen) {
        this.idMovimiento = idMovimiento;
        this.descripcion = descripcion;
        this.monto = monto;
        this.establecimiento = establecimiento;
        this.fecha = fecha;
        this.tarjetaOrigen=tarjetaOrigen;
    }
    
    
}
