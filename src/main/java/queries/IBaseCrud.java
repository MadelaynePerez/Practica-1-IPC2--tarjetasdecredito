/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package queries;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public interface IBaseCrud <T>{
    public boolean crear(T entidad );
    public T eliminar (int id);
    public boolean actualizar (T entidadActualizar);
    public ArrayList<T> listar ();
    public T encontrarPorId(int id);
    
}
