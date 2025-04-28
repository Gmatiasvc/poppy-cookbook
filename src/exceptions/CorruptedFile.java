package exceptions;

/**
 * Excepcion de tipo CorruptedFile
 * <p>
 * Es lanzada cuando un archivo proporcionado a un metodo tiene corrupcion en
 * los archivos proporcionados</p>
 *
 * @author Grupo C [Backend]
 */
public class CorruptedFile extends Exception {

    /**
     * Constructor de la exepcion CorruptedFile
     * <p>
     * Devuelve un mensaje de error indicando que el archivo proporcionado esta
     * corrupto</p>
     */
    public CorruptedFile() {
        super("File is corrupted");
    }

}
