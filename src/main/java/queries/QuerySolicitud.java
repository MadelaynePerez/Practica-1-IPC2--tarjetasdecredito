/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package queries;

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
            String sql = "INSERT INTO solicitud (salario, tipo_de_tarjeta, fecha_solicitud, fecha_autorizado, motivo_rechazo) VALUES ( ?, ?, ?, ?, ? )";

            // 3. Crear el PreparedStatement
            preparedStatement = connection.prepareStatement(sql);

            // 4. Configurar los valores para el INSERT
            preparedStatement.setInt(1, entidad.getSalario());
            preparedStatement.setInt(2, entidad.getTipoDeTarjeta());
            preparedStatement.setDate(3, java.sql.Date.valueOf(entidad.getFechaSolicitud()));
            preparedStatement.setDate(4, java.sql.Date.valueOf(entidad.getFechaAutorizado()));
            preparedStatement.setString(5, entidad.getMotivoRechazo());

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
            String sql = "DELETE FROM solicitud WHERE id = ?";

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
            pstmt.setInt(1, entidadActualizar.getSalario());
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

            String sql = "SELECT id,salario,direccion FROM solicitud";
            pstmt = connection.prepareStatement(sql);
            ResultSet resultado = pstmt.executeQuery();
            while (resultado.next()) {
                int idSolicitud = resultado.getInt("idSolicitud");
                int salario = resultado.getInt("salario");
                int tipoDeTarjeta = resultado.getInt("");
                LocalDate fechaSolicitud = resultado.getDate("fecha_solicitud").toLocalDate();
                LocalDate fechaAutorizado = resultado.getDate("fecha_autorizado").toLocalDate();
                String motivoRechazo = resultado.getString("motivo_rechazo");

                solicitud temporal = new solicitud(idSolicitud, salario, tipoDeTarjeta, fechaSolicitud, fechaAutorizado, motivoRechazo);
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

    @Override
    public solicitud encontrarPorId(int id) {
    Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = coneccion.getConnection();

            String sql = "SELECT id,salario,tipo_de_tarjeta FROM solicitud WHERE id_solicitud = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet resultado = pstmt.executeQuery();
            while (resultado.next()) {
                int idSolicitud = resultado.getInt("idSolicitud");
                int salario = resultado.getInt("salario");
                int tipoDeTarjeta = resultado.getInt("");
                LocalDate fechaSolicitud = resultado.getDate("fecha_solicitud").toLocalDate();
                LocalDate fechaAutorizado = resultado.getDate("fecha_autorizado").toLocalDate();
                String motivoRechazo = resultado.getString("motivo_rechazo");

                solicitud temporal = new solicitud(idSolicitud, salario, tipoDeTarjeta, fechaSolicitud, fechaAutorizado, motivoRechazo);
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

}
