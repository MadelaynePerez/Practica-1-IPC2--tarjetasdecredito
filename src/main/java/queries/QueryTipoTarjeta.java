/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package queries;
import com.mycompany.modelos.coneccion;
import com.mycompany.modelos.tipoTarjeta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class QueryTipoTarjeta implements IBaseCrud<tipoTarjeta> {

    @Override
    public boolean crear(tipoTarjeta entidad) {
      PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            // 1. Establecer la conexión con la base de datos
            connection = coneccion.getConnection();
            // 2. Crear el comando SQL para insertar datos
            String sql = "INSERT INTO tipo_tarjeta ( interes, nombre_tipo_tarjeta) VALUES ( ?, ?, ?)";

            // 3. Crear el PreparedStatement
            preparedStatement = connection.prepareStatement(sql);

            // 4. Configurar los valores para el INSERT
            preparedStatement.setDouble(1, entidad.getInteres());
            preparedStatement.setString(2, entidad.getNombreTarjeta());

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
    public tipoTarjeta eliminar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean actualizar(tipoTarjeta entidadActualizar) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<tipoTarjeta> listar() {
    Connection connection = null;
        PreparedStatement pstmt = null;
        ArrayList<tipoTarjeta> tarjetastipos = new ArrayList();
        try {
            connection = coneccion.getConnection();

            String sql = "SELECT id_tipo, interes, nombre_tipo_tarjeta, credito FROM tipo_tarjeta";
            pstmt = connection.prepareStatement(sql);
            ResultSet resultado = pstmt.executeQuery();
            while (resultado.next()) {
                int id = resultado.getInt("id_tipo");
                Double interes = resultado.getDouble("interes");
                String nombretarjeta = resultado.getString("nombre_tipo_tarjeta");
                Double credito = resultado.getDouble("credito");

                tipoTarjeta temporal = new tipoTarjeta(id, interes, nombretarjeta);
                tarjetastipos.add(temporal);
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
        return tarjetastipos;
    }

    @Override
    public tipoTarjeta encontrarPorId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
