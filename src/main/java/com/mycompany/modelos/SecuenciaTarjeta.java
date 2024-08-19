/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.modelos;

/**
 *
 * @author DELL
 */
public class SecuenciaTarjeta {
    String TipoTarjetaNombre;
    long ultimoValor;

    public String getTipoTarjetaNombre() {
        return TipoTarjetaNombre;
    }

    public void setTipoTarjetaNombre(String TipoTarjetaNombre) {
        this.TipoTarjetaNombre = TipoTarjetaNombre;
    }

    public long getUltimoValor() {
        return ultimoValor;
    }

    public void setUltimoValor(long ultimoValor) {
        this.ultimoValor = ultimoValor;
    }

    public SecuenciaTarjeta(String TipoTarjetaNombre, long ultimoValor) {
        this.TipoTarjetaNombre = TipoTarjetaNombre;
        this.ultimoValor = ultimoValor;
    }
    
}
