/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modelos;

/**
 *
 * @author DELL
 */
public class tipoTarjeta {
    int idTipo;
   double interes;
   String nombreTarjeta;
   double credito;

    public double getCredito() {
        return credito;
    }

    public void setCredito(double credito) {
        this.credito = credito;
    }

    public int getIdTipo() {
        return idTipo;
    }

    public void setIdTipo(int idTipo) {
        this.idTipo = idTipo;
    }

    public double getInteres() {
        return interes;
    }

    public void setInteres(double interes) {
        this.interes = interes;
    }

    public String getNombreTarjeta() {
        return nombreTarjeta;
    }

    public void setNombreTarjeta(String nombreTarjeta) {
        this.nombreTarjeta = nombreTarjeta;
    }

    public tipoTarjeta(int idTipo, double interes, String nombreTarjeta) {
        this.idTipo = idTipo;
        this.interes = interes;
        this.nombreTarjeta = nombreTarjeta;
    }

    @Override
    public String toString() {
        return this.nombreTarjeta;
    }
   
}
