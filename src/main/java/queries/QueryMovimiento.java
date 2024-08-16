/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package queries;

import com.mycompany.modelos.coneccion;
import com.mycompany.modelos.movimiento;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class QueryMovimiento implements IBaseCrud<movimiento> {

    @Override
    public boolean crear(movimiento entidad) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            // 1. Establecer la conexión con la base de datos
            connection = coneccion.getConnection();
            // 2. Crear el comando SQL para insertar datos
            String sql = "INSERT INTO movimiento (nombre, direccion, descripcion, monto) VALUES ( ?, ?, ?, ?)";

            // 3. Crear el PreparedStatement
            preparedStatement = connection.prepareStatement(sql);

            // 4. Configurar los valores para el INSERT
            preparedStatement.setString(1, entidad.getNombre());
            preparedStatement.setString(2, entidad.getDireccion());
            preparedStatement.setString(3, entidad.getDescripcion());
            preparedStatement.setInt(4, entidad.getMonto());

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
    public movimiento eliminar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean actualizar(movimiento entidadActualizar) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<movimiento> listar() {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ArrayList<movimiento> movimientos = new ArrayList();
        try {
            connection = coneccion.getConnection();

            String sql = "SELECT id_movimiento, nombre,direccion, descripcion, monto FROM movimiento";
            pstmt = connection.prepareStatement(sql);
            ResultSet resultado = pstmt.executeQuery();
            while (resultado.next()) {
                int idMovimiento = resultado.getInt("id_movimiento");
                String nombre = resultado.getString("nombre");
                String direccion = resultado.getString("direccion");
                String descripcion = resultado.getString("descripcion");
                int monto = resultado.getInt("monto");

                movimiento temporal = new movimiento(idMovimiento, nombre, direccion, descripcion, monto);
                movimientos.add(temporal);
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
        return movimientos;
    }

    @Override
    public movimiento encontrarPorId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
