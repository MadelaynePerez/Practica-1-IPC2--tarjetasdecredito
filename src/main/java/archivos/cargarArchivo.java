/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package archivos;

import com.mycompany.modelos.SecuenciaTarjeta;
import com.mycompany.modelos.cliente;
import com.mycompany.modelos.movimiento;
import com.mycompany.modelos.solicitud;
import com.mycompany.modelos.tarjeta;
import com.mycompany.modelos.tipoTarjeta;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import queries.QueryCliente;
import queries.QueryMovimiento;
import queries.QuerySecuenciaTarjeta;
import queries.QuerySolicitud;
import queries.QueryTarjeta;
import queries.QueryTipoTarjeta;
import reportes.consultaTarjetaReporte;
import reportes.listarTarjetasReporte;
import reportes.reporteSolicitudes;

/**
 *
 * @author DELL
 */
public class cargarArchivo {

    public ArrayList<String> cargarArchivo(String rutaArchivo, String ruta) {
        BufferedReader reader = null;
        ArrayList<String> resultados = new ArrayList();

        QueryCliente queryCliente = new QueryCliente();
        QueryTarjeta queryTarjeta = new QueryTarjeta();
        QueryMovimiento queryMovimiento = new QueryMovimiento();
        QueryTipoTarjeta queryTipoTarjeta = new QueryTipoTarjeta();
        QuerySolicitud querySolicitud = new QuerySolicitud();
        QuerySecuenciaTarjeta querySecuenciaTarjeta = new QuerySecuenciaTarjeta();

        try {
            // Crear un FileReader y un BufferedReader para leer el archivo
            reader = new BufferedReader(new FileReader(rutaArchivo));
            String linea;

            // Leer el archivo línea por línea
            while ((linea = reader.readLine()) != null) {

                linea = linea.replace(");", "");
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                linea = linea.replace("\"", "");
                if (linea.startsWith("SOLICITUD")) {
                    linea = linea.replace("SOLICITUD(", "");
                    String[] datos = linea.split(",");

                    // Crear cliente
                    String nombre = datos[3];
                    String direccion = datos[5];

                    // Crear solicitud
                    int id = Integer.valueOf(datos[0]);
                    LocalDate fecha = LocalDate.parse(datos[1], formatter);
                    String tipoTarjeta = datos[2];
                    double salario = Double.parseDouble(datos[4]);
                    tipoTarjeta tipo = queryTipoTarjeta.encontrarPorNombre(tipoTarjeta.toLowerCase());

                    SecuenciaTarjeta secuenciaObtenida = querySecuenciaTarjeta.ObtenerSecuencia(tipo.getNombreTarjeta());

                    cliente cliente = null;
                    if (queryCliente.encontrarPorNombre(nombre) == null) {
                        cliente = new cliente(-1, nombre, direccion);
                        queryCliente.crear(cliente);
                    }
                    cliente = queryCliente.encontrarPorNombre(nombre);

                    tarjeta tarjetaAutorizado = new tarjeta(secuenciaObtenida.getUltimoValor(),
                            nombre, tipo.getIdTipo(), fecha, "Desactivo", cliente.getId(), tipo.getCredito());

                    queryTarjeta.crear(tarjetaAutorizado);

                    solicitud solicitud = new solicitud(id, salario, tipo.getIdTipo(), fecha, fecha, "", tarjetaAutorizado.getNoDeTarjeta());
                    solicitud.setCliente(cliente);

                    querySolicitud.crear(solicitud);
                    querySecuenciaTarjeta.actualizar(secuenciaObtenida);
                    resultados.add("SOLICITUD CON ID " + id + " CREADA PARA EL CLIENTE " + cliente.getNombre());

                } else if (linea.startsWith("MOVIMIENTO")) {
                    linea = linea.replace("MOVIMIENTO(", "");
                    String[] datos = linea.split(",");

                    long noTarjeta = Long.parseLong(datos[0].replace(" ", ""));
                    LocalDate fecha = LocalDate.parse(datos[1], formatter);
                    String tipoMovimiento = datos[2];
                    String descripcion = datos[3];
                    String institucion = datos[4];
                    double monto = Double.parseDouble(datos[5]);

                    tarjeta tarjeta = queryTarjeta.encontrarPorId(noTarjeta);
                    double dineroActual = tarjeta.getDinero() - monto;

                    if (!"Activo".equals(tarjeta.getEstadoTarjeta())) {
                        resultados.add("ERROR EN MOVIMIENTO: Tarjeta " + noTarjeta + " no valida o autorizada para realizar un movimiento");
                        continue;
                    }

                    if (dineroActual >= 0) {

                        movimiento movimiento = new movimiento(-1, descripcion, monto, institucion, fecha, noTarjeta, tipoMovimiento);
                        queryMovimiento.crear(movimiento);
                        tarjeta.setDinero(dineroActual);
                        queryTarjeta.actualizarDinero(tarjeta);
                        resultados.add("MOVIMIENTO ACEPTADO PARA LA TARJETA " + noTarjeta + " CON UN MONTO DE " + monto);
                    } else {
                        resultados.add("ERROR EN MOVIMIENTO: Tarjeta " + noTarjeta + " sin dinero suficiente");
                    }

                } else if (linea.startsWith("CONSULTAR_TARJETA")) {
                    //CONSULTAR_TARJETA(4256 3102 6585 2417);
                    linea = linea.replace("CONSULTAR_TARJETA(", "").replace(" ", "");
                    Long noTarjeta = Long.parseLong(linea);
                    tarjeta tarjeta = queryTarjeta.encontrarPorId(noTarjeta);
                    if (tarjeta == null) {
                        resultados.add("NO EXISTE LA TARJETA " + noTarjeta);
                        continue;
                    }

                    cliente cliente = queryCliente.encontrarPorId(tarjeta.getIdCliente());
                    tipoTarjeta tipoTarjeta = queryTipoTarjeta.encontrarPorId(tarjeta.getTipoDeTarjeta());

                    String consulta = "CONSULTA: " + tarjeta.getNoDeTarjeta() + "," + tipoTarjeta.getNombreTarjeta() + "," + tipoTarjeta.getCredito() + "," + cliente.getNombre() + "," + cliente.getDireccion() + "," + tarjeta.getEstadoTarjeta();
                    resultados.add(consulta);
                } else if (linea.startsWith("AUTORIZACION_TARJETA")) {
                    //AUTORIZACION_TARJETA(1);
                    linea = linea.replace("AUTORIZACION_TARJETA(", "").replace(" ", "");
                    int idSolicitud = Integer.parseInt(linea);

                    solicitud solicitud = querySolicitud.encontrarPorId(idSolicitud);

                    tipoTarjeta tipoTarjeta = queryTipoTarjeta.encontrarPorId(solicitud.getTipoDeTarjeta());
                    boolean autorizado = querySolicitud.AutorizarTarjeta(solicitud.getSalario(), tipoTarjeta.getCredito());
                    tarjeta tarjeta = queryTarjeta.encontrarPorId(solicitud.getNoTarjeta());

                    if (autorizado) {
                        tarjeta.setEstadoTarjeta("Activo");
                        queryTarjeta.actualizar(tarjeta);
                        resultados.add("Tarjeta " + tarjeta.getNoDeTarjeta() + " autorizada");
                    } else {
                        resultados.add("Tarjeta " + tarjeta.getNoDeTarjeta() + " no autorizada");
                    }

                } else if (linea.startsWith("CANCELACION_TARJETA")) {
                    //CANCELACION_TARJETA(4256 3102 6585 2417);
                    linea = linea.replace("CANCELACION_TARJETA(", "").replace(" ", "");
                    Long noTarjeta = Long.parseLong(linea.replace(" ", ""));
                    tarjeta tarjeta = queryTarjeta.encontrarPorId(noTarjeta);
                    tarjeta.setEstadoTarjeta("CANCELADA");
                    queryTarjeta.actualizar(tarjeta);
                    resultados.add("Tarjeta " + tarjeta.getNoDeTarjeta() + " cancelada");
                } else if (linea.startsWith("ESTADO_CUENTA")) {
                    linea = linea.replace("ESTADO_CUENTA(", "").replace(" ", "");
                    String[] datos = linea.split(",");
                    Long noTarjeta = Long.parseLong(datos[0]);
                    String tipo = datos[1];
                    double saldo = Double.parseDouble(datos[2]);
                    double interes = Double.parseDouble(datos[3]);

                    consultaTarjetaReporte reporteConsulta = new consultaTarjetaReporte();
                    reporteConsulta.GenerarReporte(noTarjeta, tipo.toLowerCase(), saldo, interes, ruta);
                    resultados.add("REPORTE DE CONSULTA GENERADO");
                } else if (linea.startsWith("LISTADO_TARJETAS")) {
                    linea = linea.replace("LISTADO_TARJETAS(", "").replace(" ", "");
                    String[] datos = linea.split(",");
                    String tipo = datos[0];
                    double monto = Double.parseDouble(datos[1]);
                    String fecha1 = datos[2];
                    String fecha2 = datos[3];
                    String estado = datos[4];
                    listarTarjetasReporte reporte = new listarTarjetasReporte();
                    reporte.GenerarReporte(tipo, monto, fecha1, fecha2, estado, ruta);
                    resultados.add("REPORTE DE TARJETAS GENERADO");
                } else if (linea.startsWith("LISTADO_SOLICITUDES")) {
                    linea = linea.replace("LISTADO_SOLICITUDES(", "").replace(" ", "");
                    String[] datos = linea.split(",");
                    String fecha1 = datos[0];
                    String fecha2 = datos[1];
                    String tipo = datos[2];
                    double monto = Double.parseDouble(datos[3]);
                    String estado = datos[4];
                    
                    reporteSolicitudes reporteSolicitudes = new reporteSolicitudes();
                    reporteSolicitudes.GenerarReporte(tipo, monto, fecha1, fecha2, estado, ruta);
                    resultados.add("REPORTE DE SOLICITUDES GENERADO");
                } else {
                    resultados.add("INSTRUCCION " + linea + " NO ACEPTADA");
                    
                    
                }
            }

        } catch (IOException e) {
            // Manejar excepciones de entrada/salida
            e.printStackTrace();
        } finally {
            try {
                // Cerrar el BufferedReader
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return resultados;
    }

}
