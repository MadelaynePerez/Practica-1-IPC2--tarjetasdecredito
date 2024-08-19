/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.modelos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import queries.QueryCliente;
import queries.QuerySolicitud;
import vistas.cargarArchivoVista;

/**
 *
 * @author DELL
 */
public class Modelos {

    public static void main(String[] args) {

        // Instanciar la clase CargaArchivosVista
        cargarArchivoVista cargarArchivosVista = new cargarArchivoVista();

        // Hacer visible la ventana 
        cargarArchivosVista.setVisible(true);
    }

    // coneccion.getConnection();
    //QuerySolicitud querysolicitud = new QuerySolicitud();
    // LocalDate date = LocalDate.of(2024, 8, 14);
    //LocalDate date2 = LocalDate.now();
    // LocalDate date3 = LocalDate.now();
    // solicitud soliciii = new solicitud(-1, 880000, 7, date, date2, "no hubo rechazo");
    // querysolicitud.crear(soliciii);
    //cliente cl2 = new cliente(-1, "Cliente 2", "Test");
    //queryCliente.crear(cl2);
    //querysolicitud.eliminar(1);
    // queryCliente.eliminar(2);
    // QuerySolicitud querysolicitud = new QuerySolicitud();
    // ArrayList<solicitud> lista = querysolicitud.listar();
    //   for (solicitud c : lista) {
    //   System.out.println(c.idSolicitud);
    // System.out.println(c.cliente.nombre);
}

//cliente clienteEncontrado = queryCliente.encontrarPorId(1);
//System.out.println(clienteEncontrado.nombre);

