/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package queries;

import com.mycompany.modelos.cliente;
import com.mycompany.modelos.coneccion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class QueryCliente implements IBaseCrud<cliente> {

    @Override
    public boolean crear(cliente entidad) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            // 1. Establecer la conexión con la base de datos
            connection = coneccion.getConnection();
            // 2. Crear el comando SQL para insertar datos
            String sql = "INSERT INTO cliente (nombre, direccion) VALUES ( ?, ?)";

            // 3. Crear el PreparedStatement
            preparedStatement = connection.prepareStatement(sql);

            // 4. Configurar los valores para el INSERT
            preparedStatement.setString(1, entidad.getNombre());
            preparedStatement.setString(2, entidad.getDireccion());

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
    public cliente eliminar(int id) {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            // 1. Establecer la conexión con la base de datos
            connection = coneccion.getConnection();

            // 2. Crear el comando SQL para eliminar datos
            String sql = "DELETE FROM cliente WHERE id = ?";

            // 3. Crear el PreparedStatement
            preparedStatement = connection.prepareStatement(sql);

            // 4. Configurar el valor para el DELETE
            preparedStatement.setInt(1, id); // Suponiendo que deseas eliminar el cliente con ID 1

            // 5. Ejecutar el comando
            cliente clienteAntesDeEliminar = encontrarPorId(id);
            int rowsDeleted = preparedStatement.executeUpdate();
            if (rowsDeleted > 0) {
                return clienteAntesDeEliminar;
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
    public boolean actualizar(cliente entidadActualizar) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            // Establecer conexión
            connection = coneccion.getConnection();

            // Crear la sentencia SQL
            String sql = "UPDATE cliente SET  nombre = ?, direccion=? WHERE id = ?";

            // Crear un PreparedStatement
            pstmt = connection.prepareStatement(sql);

            // Establecer los valores de los parámetros
            pstmt.setString(1, entidadActualizar.getNombre());
            pstmt.setString(2, entidadActualizar.getDireccion());
            pstmt.setInt(3, entidadActualizar.getId()); // Suponiendo que actualizas el registro con id=1

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
    public ArrayList<cliente> listar() {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ArrayList<cliente> clientes = new ArrayList();
        try {
            connection = coneccion.getConnection();

            String sql = "SELECT id,nombre,direccion FROM cliente";
            pstmt = connection.prepareStatement(sql);
            ResultSet resultado = pstmt.executeQuery();
            while (resultado.next()) {
                int id = resultado.getInt("id");
                String nombre = resultado.getString("nombre");
                String direccion = resultado.getString("direccion");

                cliente temporal = new cliente(id, nombre, direccion);
                clientes.add(temporal);
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
        return clientes;
    }

    @Override
    public cliente encontrarPorId(int id) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = coneccion.getConnection();

            String sql = "SELECT id,nombre,direccion FROM cliente WHERE id = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet resultado = pstmt.executeQuery();
            while (resultado.next()) {
                int idSeleccionado = resultado.getInt("id");
                String nombre = resultado.getString("nombre");
                String direccion = resultado.getString("direccion");

                cliente temporal = new cliente(idSeleccionado, nombre, direccion);
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
   
    public cliente encontrarPorNombre(String nombreCliente) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = coneccion.getConnection();

            String sql = "SELECT id,nombre,direccion FROM cliente WHERE nombre = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, nombreCliente);
            ResultSet resultado = pstmt.executeQuery();
            while (resultado.next()) {
                int idSeleccionado = resultado.getInt("id");
                String nombre = resultado.getString("nombre");
                String direccion = resultado.getString("direccion");

                cliente temporal = new cliente(idSeleccionado, nombre, direccion);
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
