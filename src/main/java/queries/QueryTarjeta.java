/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package queries;

import com.mycompany.modelos.coneccion;
import com.mycompany.modelos.tarjeta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class QueryTarjeta implements IBaseCrud<tarjeta>{

    @Override
    public boolean crear(tarjeta entidad) {
   PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            // 1. Establecer la conexión con la base de datos
            connection = coneccion.getConnection();
            // 2. Crear el comando SQL para insertar datos
            String sql = "INSERT INTO tarjeta ( nombre, fecha, estado_de_tarjeta) VALUES (  ?, ?, ?)";

            // 3. Crear el PreparedStatement
            preparedStatement = connection.prepareStatement(sql);

            // 4. Configurar los valores para el INSERT
            preparedStatement.setString(1, entidad.getNombre());
             preparedStatement.setDate(2, java.sql.Date.valueOf(entidad.getFecha()));
            preparedStatement.setString(3, entidad.getEstadoTarjeta());

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
    public tarjeta eliminar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean actualizar(tarjeta entidadActualizar) {
    Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            // Establecer conexión
            connection = coneccion.getConnection();

            // Crear la sentencia SQL
            String sql = "UPDATE tarjeta SET   estado_de_tarjeta=? WHERE no_de_tarjeta = ?";

            // Crear un PreparedStatement
            pstmt = connection.prepareStatement(sql);

            // Establecer los valores de los parámetros
         
          
           pstmt.setString(1, entidadActualizar.getEstadoTarjeta());
            pstmt.setInt(2, entidadActualizar.getNoDeTarjeta()); // 

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
    public ArrayList<tarjeta> listar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public tarjeta encontrarPorId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
