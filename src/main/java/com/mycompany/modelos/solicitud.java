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
public class solicitud {

    int idSolicitud;
    double salario;
    int tipoDeTarjeta;
    LocalDate fechaSolicitud;
    LocalDate fechaAutorizado;
    String motivoRechazo;
    cliente cliente;
    long noTarjeta;
    int idCliente;

    public int getIdCliente() {
        return idCliente;
    }

    public void setIdCliente(int idCliente) {
        this.idCliente = idCliente;
    }
    

    public long getNoTarjeta() {
        return noTarjeta;
    }

    public void setNoTarjeta(long noTarjeta) {
        this.noTarjeta = noTarjeta;
    }
    

    public cliente getCliente() {
        return cliente;
    }

    public void setCliente(cliente cliente) {
        this.cliente = cliente;
    }

    public int getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(int idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    public double getSalario() {
        return salario;
    }

    public void setSalario(double salario) {
        this.salario = salario;
    }

    public int getTipoDeTarjeta() {
        return tipoDeTarjeta;
    }

    public void setTipoDeTarjeta(int tipoDeTarjeta) {
        this.tipoDeTarjeta = tipoDeTarjeta;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public LocalDate getFechaAutorizado() {
        return fechaAutorizado;
    }

    public void setFechaAutorizado(LocalDate fechaAutorizado) {
        this.fechaAutorizado = fechaAutorizado;
    }

    public String getMotivoRechazo() {
        return motivoRechazo;
    }

    public void setMotivoRechazo(String motivoRechazo) {
        this.motivoRechazo = motivoRechazo;
    }

    public solicitud(int idSolicitud, double salario, int tipoDeTarjeta, LocalDate fechaSolicitud, LocalDate fechaAutorizado, String motivoRechazo, long noTarjeta) {
        this.idSolicitud = idSolicitud;
        this.salario = salario;
        this.tipoDeTarjeta = tipoDeTarjeta;
        this.fechaSolicitud = fechaSolicitud;
        this.fechaAutorizado = fechaAutorizado;
        this.motivoRechazo = motivoRechazo;
        this.noTarjeta = noTarjeta;
    }

}
