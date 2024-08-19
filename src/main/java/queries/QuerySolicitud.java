/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package queries;

import com.mycompany.modelos.cliente;
import com.mycompany.modelos.coneccion;
import com.mycompany.modelos.solicitud;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class QuerySolicitud implements IBaseCrud<solicitud> {

    @Override
    public boolean crear(solicitud entidad) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;

        try {
            // 1. Establecer la conexión con la base de datos
            connection = coneccion.getConnection();
            // 2. Crear el comando SQL para insertar datos
            String sql;
            if (entidad.getIdSolicitud() <= 0) {
                sql = "INSERT INTO solicitud (salario, tipo_de_tarjeta, fecha_solicitud, fecha_autorizado, motivo_rechazo,no_de_tarjeta, id_cliente) VALUES (?, ?,?, ?, ?, ?, ? )";
            } else {
                sql = "INSERT INTO solicitud (salario, tipo_de_tarjeta, fecha_solicitud, fecha_autorizado, motivo_rechazo,no_de_tarjeta, id_cliente ,id_solicitud) VALUES (?,?,?, ?, ?, ?, ?, ? )";
            }

            // 3. Crear el PreparedStatement
            preparedStatement = connection.prepareStatement(sql);

            // 4. Configurar los valores para el INSERT
            preparedStatement.setDouble(1, entidad.getSalario());
            preparedStatement.setInt(2, entidad.getTipoDeTarjeta());
            preparedStatement.setDate(3, java.sql.Date.valueOf(entidad.getFechaSolicitud()));
            if (entidad.getFechaAutorizado() != null) {
                preparedStatement.setDate(4, java.sql.Date.valueOf(entidad.getFechaAutorizado()));
            } else {
                preparedStatement.setDate(4, null);
            }
            preparedStatement.setString(5, entidad.getMotivoRechazo());
            preparedStatement.setLong(6, entidad.getNoTarjeta());
            preparedStatement.setInt(7, entidad.getCliente().getId());
            if (entidad.getIdSolicitud() > 0) {
                preparedStatement.setInt(8, entidad.getIdSolicitud());
            }

            // 5. Ejecutar el comando
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 6. Cerrar la conexión y el PreparedStatement
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public solicitud eliminar(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // 1. Establecer la conexión con la base de datos
            connection = coneccion.getConnection();

            // 2. Crear el comando SQL para eliminar datos
            String sql = "DELETE FROM solicitud WHERE id_solicitud = ?";

            // 3. Crear el PreparedStatement
            preparedStatement = connection.prepareStatement(sql);

            // 4. Configurar el valor para el DELETE
            preparedStatement.setInt(1, id); // Suponiendo que deseas eliminar el cliente con ID 1

            // 5. Ejecutar el comando
            solicitud solicitudAntesDeEliminar = encontrarPorId(id);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                return solicitudAntesDeEliminar;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // 6. Cerrar la conexión y el PreparedStatement
            try {
                if (preparedStatement != null) {
                    preparedStatement.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    public boolean actualizar(solicitud entidadActualizar) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            // Establecer conexión
            connection = coneccion.getConnection();

            // Crear la sentencia SQL
            String sql = "UPDATE solicitud SET  salario = ?, tipo_de_tarjeta=?  WHERE id_solicitud = ?";

            // Crear un PreparedStatement
            pstmt = connection.prepareStatement(sql);

            // Establecer los valores de los parámetros
            pstmt.setDouble(1, entidadActualizar.getSalario());
            pstmt.setInt(2, entidadActualizar.getTipoDeTarjeta());
            pstmt.setInt(3, entidadActualizar.getIdSolicitud()); // Suponiendo que actualizas el registro con id=1

            // Ejecutar la sentencia
            int filasActualizadas = pstmt.executeUpdate();
            return filasActualizadas == 1;

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    @Override
    public ArrayList<solicitud> listar() {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ArrayList<solicitud> solicitudes = new ArrayList();
        try {
            connection = coneccion.getConnection();

            String sql = "select s.id_solicitud, s.salario, c.nombre, c.direccion, "
                    + "s.tipo_de_tarjeta, s.fecha_solicitud, s.fecha_autorizado, s.motivo_rechazo, s.no_de_tarjeta "
                    + "from solicitud as s\n"
                    + "INNER JOIN cliente as c ON\n"
                    + "s.id_cliente = c.id\n"
                    + ";";
            pstmt = connection.prepareStatement(sql);
            ResultSet resultado = pstmt.executeQuery();
            while (resultado.next()) {
                int idSolicitud = resultado.getInt("id_solicitud");
                double salario = resultado.getDouble("salario");
                int tipoDeTarjeta = resultado.getInt("tipo_de_tarjeta");
                LocalDate fechaSolicitud = resultado.getDate("fecha_solicitud").toLocalDate();
                LocalDate fechaAutorizado = resultado.getDate("fecha_autorizado").toLocalDate();
                String motivoRechazo = resultado.getString("motivo_rechazo");
                String nombreCliente = resultado.getString("nombre");
                Long noTarjeta = resultado.getLong("no_de_tajeta");

                solicitud temporal = new solicitud(idSolicitud, salario, tipoDeTarjeta, fechaSolicitud, fechaAutorizado, motivoRechazo, noTarjeta);
                cliente tmp = new cliente(-1, nombreCliente, "");
                temporal.setCliente(tmp);

                solicitudes.add(temporal);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return solicitudes;
    }
    
    public ArrayList<solicitud> filtrarSolicitudes(String tipo, double monto,String f1, String f2, String estado) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ArrayList<solicitud> solicitudes = new ArrayList<>();
        
        try {
            connection = coneccion.getConnection();

            String sql = """
                         SELECT s.no_de_tarjeta, s.id_solicitud, s.salario , s.fecha_solicitud, tt.id_tipo,
                         s.id_cliente
                         FROM solicitud s
                         INNER JOIN tipo_tarjeta tt
                         ON s.tipo_de_tarjeta = tt.id_tipo
                         INNER JOIN tarjetas t
                         ON t.tipo_tarjeta = tt.id_tipo
                         WHERE tt.nombre_tipo_tarjeta = ?
                         AND s.fecha_solicitud BETWEEN ? AND ?
                         AND t.estado_de_tarjeta = ?
                         AND t.dinero >= ?
                         """;
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, tipo);
            pstmt.setString(2, f1);
            pstmt.setString(3, f2);
            pstmt.setString(4, estado);
            pstmt.setDouble(5, monto);
            
            ResultSet resultado = pstmt.executeQuery();
            while (resultado.next()) {
                long noTarjeta = resultado.getLong("no_de_tarjeta");
                int tipoTarjeta = resultado.getInt("id_tipo");
                int id_cliente = resultado.getInt("id_cliente");
                LocalDate fecha = resultado.getDate("fecha_solicitud").toLocalDate();
                double salario = resultado.getDouble("salario");
                int id_solicitud = resultado.getInt("id_solicitud");
                
                solicitud tmp = new solicitud(id_solicitud, salario, tipoTarjeta, fecha, null, "", noTarjeta);
                tmp.setIdCliente(id_cliente);
                solicitudes.add(tmp);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return solicitudes;
    }

    @Override
    public solicitud encontrarPorId(int id) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = coneccion.getConnection();

            String sql = "SELECT id_solicitud,salario,tipo_de_tarjeta,no_de_tarjeta,fecha_solicitud,fecha_autorizado,motivo_rechazo FROM solicitud WHERE id_solicitud = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet resultado = pstmt.executeQuery();
            while (resultado.next()) {
                int idSolicitud = resultado.getInt("id_solicitud");
                double salario = resultado.getDouble("salario");
                int tipoDeTarjeta = resultado.getInt("tipo_de_tarjeta");
                LocalDate fechaSolicitud = resultado.getDate("fecha_solicitud").toLocalDate();

                LocalDate fechaAutorizado = null;
                if (resultado.getDate("fecha_autorizado") != null) {
                    fechaAutorizado = resultado.getDate("fecha_autorizado").toLocalDate();
                }

                String motivoRechazo = resultado.getString("motivo_rechazo");
                Long noTarjeta = resultado.getLong("no_de_tarjeta");
                solicitud temporal = new solicitud(idSolicitud, salario, tipoDeTarjeta, fechaSolicitud, fechaAutorizado, motivoRechazo, noTarjeta);
                return temporal;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public boolean AutorizarTarjeta(double salario, double credito) {

        double creditoTarjeta = 0.6;
        if (salario * creditoTarjeta > credito) {

            return true;
        } else {
            return false;
        }

    }
}
