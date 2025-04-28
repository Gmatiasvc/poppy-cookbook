package exceptions;

/**
 * Excepcion de tipo BadFileType
 * <p> Es lanzada cuando un archivo proporcionado a un metodo tiebe problemas de formato o de extension</p>
 *
 * @author Grupo C [Backend]
 */
public class BadFileType extends Exception {

    /**
     * Constructor de la exepcion BadFileType
     * <p>
     * Devuelve un mensaje de error indicando que se debe proporcinar un archivo
     * .dat al metodo que lo lanza</p>
     */
    public BadFileType() {
        super("Filename must end with .dat");
    }

}
