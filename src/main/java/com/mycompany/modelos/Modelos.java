/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.modelos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import queries.QueryCliente;
import queries.QuerySolicitud;


/**
 *
 * @author DELL
 */
public class Modelos {

    public static void main(String[] args) {
      // coneccion.getConnection();
       QuerySolicitud querysolicitud = new QuerySolicitud();
        LocalDate date = LocalDate.of(2024, 8, 14);
        LocalDate date2 = LocalDate.now();
        LocalDate date3 = LocalDate.now();
        solicitud soliciii = new solicitud(-1, 880000, 7, date, date2, "no hubo rechazo");
        querysolicitud.crear(soliciii);
        
        //cliente cl2 = new cliente(-1, "Cliente 2", "Test");
        //queryCliente.crear(cl2);
  
        
        //querysolicitud.eliminar(1);
       // queryCliente.eliminar(2);
       
      // cliente clienteana = new cliente(1, "ANA MARIA", "RETALHULEU");
      // ArrayList<cliente> lista = queryCliente.listar();
        //for (cliente c : lista) {
          //  System.out.println(clienteana);
    
        //}
        
        //cliente clienteEncontrado = queryCliente.encontrarPorId(1);
        //System.out.println(clienteEncontrado.nombre);
     }
}
