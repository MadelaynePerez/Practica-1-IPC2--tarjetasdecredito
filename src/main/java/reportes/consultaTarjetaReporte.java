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
public class consultaTarjetaReporte {

    public void GenerarReporte(Long noTarjeta, String tipo, double saldo, double interes, String ruta) {
        QueryTarjeta queryTarjeta = new QueryTarjeta();
        QueryMovimiento queryMovimiento = new QueryMovimiento();
        QueryTipoTarjeta queryTipoTarjeta = new QueryTipoTarjeta();
        QueryCliente queryCliente = new QueryCliente();

        tarjeta tarjeta = queryTarjeta.encontrarPorId(noTarjeta);
        if (tarjeta != null) {
            ArrayList<movimiento> movimientos = queryMovimiento.listarPorTarjeta(noTarjeta);
            tipoTarjeta tipoTarjeta = queryTipoTarjeta.encontrarPorId(tarjeta.getTipoDeTarjeta());
            cliente cliente = queryCliente.encontrarPorId(tarjeta.getIdCliente());
            ObtenerHtml(tarjeta, movimientos, tipoTarjeta, cliente,ruta,saldo,interes);
            
        }else{
            JOptionPane.showMessageDialog(null, "No tarjeta encontrada para generar reporte");
        }
    }

    public void generarHtml(String contenidoHtml, String rutaArchivo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(rutaArchivo))) {
            writer.write(contenidoHtml);
            JOptionPane.showMessageDialog(null, "Archivo generado");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al generar html");
        }
    }
    
    public void ObtenerHtml(tarjeta tarjeta, ArrayList<movimiento> movimientos, tipoTarjeta tipoTarjeta, cliente cliente, 
            String ruta, double saldoFiltro, double interesFiltro) {
        String movimientosHtml = "";
        double total = 0;
        double interes = 0;
        double saldoTotal = 0;

        for (movimiento movimiento : movimientos) {
            total += movimiento.getMonto();
            interes += movimiento.getMonto() * tipoTarjeta.getInteres();
            saldoTotal += total + interes;

            movimientosHtml += String.format("""
                              <tr>
                                <td>%s</td>
                                <td>%s</td>
                                <td>%s</td>
                                <td>%s</td>
                                <td>%s</td>
                            </tr>
                              """, movimiento.getFecha().toString(),
                    movimiento.getTipoMovimiento(),
                    movimiento.getDescripcion(),
                    movimiento.getEstablecimiento(),
                    movimiento.getMonto());
        }
        
        if(saldoTotal < saldoFiltro || interes<interesFiltro ){
            return;
        }
        String html = String.format("""
                           <!DOCTYPE html>
                           <html lang="es">
                           <head>
                               <meta charset="UTF-8">
                               <meta name="viewport" content="width=device-width, initial-scale=1.0">
                           </head>
                           <body>
                           
                               <h1>Reporte de consulta tarjetas</h1>
                           
                               <div>
                                   <h2>NÚMERO TARJETA: %s </h2>
                                   <p><strong>TIPO TARJETA:</strong> %s </p>
                                   <p><strong>NOMBRE CLIENTE:</strong> %s </p>
                                   <p><strong>DIRECCION CLIENTE:</strong> %s </p>
                               </div>
                           <hr>
                           
                                <table border="1" cellspacing="0" cellpadding="5">
                                       <tr>
                                           <th>FECHA</th>
                                           <th>TIPO DE MOVIMIENTO</th>
                                           <th>DESCRIPCIÓN</th>
                                           <th>ESTABLECIMIENTO</th>
                                           <th>MONTO</th>
                                       </tr>
                                       %s
                                </table>
                           <hr>
                           
                               <div>
                                   <p><strong>MONTO TOTAL:</strong> Q%s</p>
                                   <p><strong>INTERESES:</strong> Q%s</p>
                                   <p><strong>SALDO TOTAL:</strong> Q%s</p>
                               </div>
                           </body>
                           </html>
                           """, tarjeta.getNoDeTarjeta(), tipoTarjeta.getNombreTarjeta(),
                cliente.getNombre(), cliente.getDireccion(), movimientosHtml,
                total, interes, saldoTotal);
        
        generarHtml(html, ruta+"/reporteconsulta"+tarjeta.getNoDeTarjeta()+".html");
    }
}
