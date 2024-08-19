/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package queries;

import com.mycompany.modelos.SecuenciaTarjeta;
import com.mycompany.modelos.coneccion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author DELL
 */
public class QuerySecuenciaTarjeta implements IBaseCrud<SecuenciaTarjeta> {

    @Override
    public boolean crear(SecuenciaTarjeta entidad) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public SecuenciaTarjeta eliminar(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean actualizar(SecuenciaTarjeta entidadActualizar) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            // Establecer conexión
            connection = coneccion.getConnection();
            
            SecuenciaTarjeta seguimiento = ObtenerSecuencia(entidadActualizar.getTipoTarjetaNombre());

            // Crear la sentencia SQL
            String sql = "UPDATE secuencias_tarjetas SET  ultimo_valor=? WHERE tipo_tarjeta = ?";

            // Crear un PreparedStatement
            pstmt = connection.prepareStatement(sql);

            // Establecer los valores de los parámetros
            pstmt.setLong(1, seguimiento.getUltimoValor() + 1);
            pstmt.setString(2, entidadActualizar.getTipoTarjetaNombre());
          

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
    public ArrayList<SecuenciaTarjeta> listar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public SecuenciaTarjeta encontrarPorId(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
    public SecuenciaTarjeta ObtenerSecuencia (String nombreTipoTarjeta){
         Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            connection = coneccion.getConnection();

            String sql = "SELECT tipo_tarjeta, ulltimo_valor FROM secuencias_tarjetas WHERE tipo_tarjeta = ?";
            pstmt = connection.prepareStatement(sql);
            pstmt.setString(1, nombreTipoTarjeta);
            ResultSet resultado = pstmt.executeQuery();
            while (resultado.next()) {
                String tipoTarjeta = resultado.getString("tipo_tarjeta");
                long ultimoValor = resultado.getLong("ultimo_valor");

                SecuenciaTarjeta temporal = new SecuenciaTarjeta(  tipoTarjeta, ultimoValor);
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
