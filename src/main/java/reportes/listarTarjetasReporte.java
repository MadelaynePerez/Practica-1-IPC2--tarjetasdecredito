/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reportes;

import com.mycompany.modelos.cliente;
import com.mycompany.modelos.movimiento;
import com.mycompany.modelos.tarjeta;
import com.mycompany.modelos.tipoTarjeta;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import queries.QueryCliente;
import queries.QueryMovimiento;
import queries.QueryTarjeta;
import queries.QueryTipoTarjeta;

/**
 *
 * @author DELL
 */
public class listarTarjetasReporte {

    public void GenerarReporte(String tipo, double monto, String fecha1, String fecha2, String estado, String ruta) {
        QueryTarjeta queryTarjeta = new QueryTarjeta();

         ArrayList<tarjeta> tarjetas = queryTarjeta.filtrarTarjeta(tipo, monto, fecha1, fecha2, estado);
         ObtenerHtml(tarjetas, ruta);
    }

    public void generarHtml(String contenidoHtml, String rutaArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            writer.write(contenidoHtml);
            JOptionPane.showMessageDialog(null, "Archivo generado");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al generar html");
        }
    }
    
    public void ObtenerHtml(ArrayList<tarjeta> tarjetas, String ruta) {
        String movimientosHtml = "";
        QueryTipoTarjeta queryTipoTarjeta = new QueryTipoTarjeta();
        QueryCliente queryCliente = new QueryCliente();
        
        for (tarjeta tarjeta : tarjetas) {
            tipoTarjeta tipo = queryTipoTarjeta.encontrarPorId(tarjeta.getTipoDeTarjeta());
            cliente cliente = queryCliente.encontrarPorId(tarjeta.getIdCliente());
            
            movimientosHtml += String.format("""
                              <tr>
                                <td>%s</td>
                                <td>%s</td>
                                <td>%s</td>
                                <td>%s</td>
                                <td>%s</td>
                                <td>%s</td>
                                <td>%s</td>
                            </tr>
                              """,tarjeta.getNoDeTarjeta(),
                              tipo.getNombreTarjeta(),tipo.getCredito(),
                              cliente.getNombre(), cliente.getDireccion(),
                              tarjeta.getFecha(),tarjeta.getEstadoTarjeta());
        }
        
        String html = String.format("""
                           <!DOCTYPE html>
                           <html lang="es">
                           <head>
                               <meta charset="UTF-8">
                               <meta name="viewport" content="width=device-width, initial-scale=1.0">
                           </head>
                           <body>
                           
                               <h1>Reporte de tarjetas</h1>
                           
                           <hr>
                           
                                <table border="1" cellspacing="0" cellpadding="5">
                                       <tr>
                                           <th>NO. TARJETA</th>
                                           <th>TIPO</th>
                                           <th>LIMITE</th>
                                           <th>NOMBRE</th>
                                           <th>DIRECCION</th>
                                            <th>FECHA</th>
                                    <th>ESTADO</th>
                                       </tr>
                                       %s
                                </table>
                           <hr>
                           </body>
                           </html>
                           """, movimientosHtml);
        
        generarHtml(html, ruta+"/reportetarjetas.html");
    }
}
