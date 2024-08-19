/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package queries;

import com.mycompany.modelos.cliente;
import com.mycompany.modelos.coneccion;
import com.mycompany.modelos.tarjeta;
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
public class QueryTarjeta implements IBaseCrud<tarjeta> {

    @Override
    public boolean crear(tarjeta entidad) {
        PreparedStatement preparedStatement = null;
        Connection connection = null;
        try {
            // 1. Establecer la conexión con la base de datos
            connection = coneccion.getConnection();
            // 2. Crear el comando SQL para insertar datos
            String sql = "INSERT INTO tarjetas ( nombre, fecha, estado_de_tarjeta, no_de_tarjeta, dinero, id_cliente, tipo_tarjeta) VALUES (  ?, ?, ?, ?, ?,?,?)";

            // 3. Crear el PreparedStatement
            preparedStatement = connection.prepareStatement(sql);

            // 4. Configurar los valores para el INSERT
            preparedStatement.setString(1, entidad.getNombre());
            preparedStatement.setDate(2, java.sql.Date.valueOf(entidad.getFecha()));
            preparedStatement.setString(3, entidad.getEstadoTarjeta());
            preparedStatement.setLong(4, entidad.getNoDeTarjeta());
            preparedStatement.setDouble(5, entidad.getDinero());
            preparedStatement.setInt(6, entidad.getIdCliente());
            preparedStatement.setInt(7, entidad.getTipoDeTarjeta());

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
            String sql = "UPDATE tarjetas SET estado_de_tarjeta=? WHERE no_de_tarjeta = ?";

            // Crear un PreparedStatement
            pstmt = connection.prepareStatement(sql);

            // Establecer los valores de los parámetros
            pstmt.setString(1, entidadActualizar.getEstadoTarjeta());
            pstmt.setLong(2, entidadActualizar.getNoDeTarjeta()); // 

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

    public boolean actualizarDinero(tarjeta entidadActualizar) {
        Connection connection = null;
        PreparedStatement pstmt = null;

        try {
            // Establecer conexión
            connection = coneccion.getConnection();

            // Crear la sentencia SQL
            String sql = "UPDATE tarjetas SET dinero=? WHERE no_de_tarjeta = ?";

            // Crear un PreparedStatement
            pstmt = connection.prepareStatement(sql);

            // Establecer los valores de los parámetros
            pstmt.setDouble(1, entidadActualizar.getDinero());
            pstmt.setLong(2, entidadActualizar.getNoDeTarjeta()); // 

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

    public ArrayList<tarjeta> filtrarTarjeta(String tipo, double monto,String f1, String f2, String estado) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ArrayList<tarjeta> tarjetas = new ArrayList<>();
        
        tarjeta tarjeta = null;
        try {
            connection = coneccion.getConnection();

            String sql = """
                         SELECT t.no_de_tarjeta, t.dinero, tt.id_tipo, t.fecha, t.id_cliente FROM tarjetas t
                         INNER JOIN tipo_tarjeta tt
                         ON t.tipo_tarjeta = tt.id_tipo
                         WHERE tt.nombre_tipo_tarjeta = ?
                         AND t.fecha BETWEEN ? AND ?
                         AND t.estado_de_tarjeta = ?
                         AND dinero >= ?
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
                double dinero = resultado.getDouble("dinero");
                int id_cliente = resultado.getInt("id_cliente");
                LocalDate fecha = resultado.getDate("fecha").toLocalDate();
                
                tarjeta tmp = new tarjeta(noTarjeta);
                tmp.setDinero(dinero);
                tmp.setEstadoTarjeta(estado);
                tmp.setFecha(fecha);
                tmp.setIdCliente(id_cliente);
                tmp.setTipoDeTarjeta(tipoTarjeta);
                tarjetas.add(tmp);
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
        return tarjetas;
    }
    
    @Override
    public tarjeta encontrarPorId(int id) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        tarjeta tarjeta = null;
        try {
            connection = coneccion.getConnection();

            String sql = "SELECT no_de_tarjeta FROM tarjetas where no_de_tarjeta = ? and estado_de_tarjeta = 'Activo'";
            pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, id);
            ResultSet resultado = pstmt.executeQuery();
            while (resultado.next()) {
                long noTarjeta = resultado.getLong("no_de_tarjeta");
                tarjeta tmp = new tarjeta(noTarjeta);
                return tmp;
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
        return tarjeta;
    }

    public tarjeta encontrarPorId(Long id) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        tarjeta tarjeta = null;
        try {
            connection = coneccion.getConnection();

            String sql = "SELECT no_de_tarjeta,dinero,tipo_tarjeta,id_cliente, estado_de_tarjeta FROM tarjetas where no_de_tarjeta = ? ";
            pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, id);
            ResultSet resultado = pstmt.executeQuery();
            while (resultado.next()) {
                long noTarjeta = resultado.getLong("no_de_tarjeta");
                double dinero = resultado.getDouble("dinero");
                int tipoTarjeta = resultado.getInt("tipo_tarjeta");
                int id_cliente = resultado.getInt("id_cliente");
                String estado = resultado.getString("estado_de_tarjeta");
                
                tarjeta tmp = new tarjeta(noTarjeta);
                tmp.setDinero(dinero);
                tmp.setTipoDeTarjeta(tipoTarjeta);
                tmp.setIdCliente(id_cliente);
                tmp.setEstadoTarjeta(estado);
                tmp.setNoDeTarjeta(noTarjeta);
                return tmp;
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
        return tarjeta;
    }

    public tarjeta encontrarPorIdYTipoTarjeta(Long id, String tipo) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        tarjeta tarjeta = null;
        try {
            connection = coneccion.getConnection();

            String sql = """
                         SELECT no_de_tarjeta,dinero,tipo_tarjeta,id_cliente, estado_de_tarjeta FROM tarjetas t  
                         INNER JOIN tipo_tarjeta tt
                         ON t.tipo_tarjeta = tt.id_tipo
                         where no_de_tarjeta = ? AND tt.nombre_tipo_tarjeta = ?
                         """;
            pstmt = connection.prepareStatement(sql);
            pstmt.setLong(1, id);
            pstmt.setString(2, tipo);
            ResultSet resultado = pstmt.executeQuery();
            while (resultado.next()) {
                long noTarjeta = resultado.getLong("no_de_tarjeta");
                double dinero = resultado.getDouble("dinero");
                int tipoTarjeta = resultado.getInt("tipo_tarjeta");
                int id_cliente = resultado.getInt("id_cliente");
                String estado = resultado.getString("estado_de_tarjeta");
                
                tarjeta tmp = new tarjeta(noTarjeta);
                tmp.setDinero(dinero);
                tmp.setTipoDeTarjeta(tipoTarjeta);
                tmp.setIdCliente(id_cliente);
                tmp.setEstadoTarjeta(estado);
                return tmp;
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
        return tarjeta;
    }
    
    public ArrayList<tarjeta> listarTarjetaPorCliente(int idCliente) {
        Connection connection = null;
        PreparedStatement pstmt = null;
        ArrayList<tarjeta> tarjetas = new ArrayList();
        try {
            connection = coneccion.getConnection();

            String sql = "SELECT no_de_tarjeta FROM tarjetas where id_cliente = ? and estado_de_tarjeta = 'Activo'";
            pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, idCliente);
            ResultSet resultado = pstmt.executeQuery();
            while (resultado.next()) {
                long id = resultado.getLong("no_de_tarjeta");
                tarjeta tmp = new tarjeta(id);
                tarjetas.add(tmp);
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
        return tarjetas;
    }

}
