package objects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Guarda el estado de los datos en archivos de acceso aleatorio
 *
 * @author Grupo C [Backend]
 */
public class DataMap implements Serializable {

    private String id;
    private ArrayList<String> data;
    private ArrayList<Integer> loc;
    private ArrayList<Integer> size;
    private int byteTotalSize;

    /**
     * Constructor de la clase DataMap
     *
     * @param id Identificador del mapa de datos guardado
     */
    public DataMap(String id) {
        this.id = id;
        data = new ArrayList<>();
        loc = new ArrayList<>();
        size = new ArrayList<>();
        byteTotalSize = 0;
    }

    /**
     * Añade un nueva entrada al mapa de datos
     *
     * @param data Identificador de los datos
     * @param size Tamaño en bytes de los datos
     */
    public void addData(String data, int size) {
        this.data.add(data);
        byteTotalSize += size;
        this.loc.add(byteTotalSize);
        this.size.add(size);
    }

    /**
     * Elimina una entrada del mapa de datos
     *
     * @param name Identificador de los datos a eliminar
     * @return true si se ha eliminado correctamente, false si no existe
     */
    public boolean removeData(String name) {
        int index = data.indexOf(name);
        if (index != -1) {
            data.remove(index);
            loc.remove(index);
            size.remove(index);
            return true;
        }
        return false;
    }

    /**
     * Busca una entrada en el mapa de datos utilizando su identificador
     *
     * @param name Identificador de los datos a buscar
     * @return Un array de String con el nombre, la localización y el tamaño de los datos, o null si no se encuentra
     */
    public String[] searchData(String name) {
        int index = data.indexOf(name);
        if (index != -1) {
            String[] result = new String[3];
            result[0] = data.get(index);
            result[1] = String.valueOf(loc.get(index));
            result[2] = String.valueOf(size.get(index));
            return result;
        }
        return null;
    }

    /**
     * Devuelve una lista con todos los identificadores de los datos
     *
     * @return ArrayList<String> con los identificadores de los datos
     */
    public ArrayList<String> getData() {
        return data;
    }

    /**
     * Devuelve el identificador del mapa de datos
     *
     * @return String con el identificador del mapa de datos
     */
    public String getId() {
        return id;
    }

}
