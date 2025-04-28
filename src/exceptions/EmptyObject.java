package exceptions;

/**
 * Excepcion de tipo EmptyObject
 * <p>
 * Es lanzada cuando algo proporcionado esta vacio y/o no tiene datos
 * disponibles</p>
 *
 * @author Grupo C [Backend]
 */
public class EmptyObject extends Exception {

    /**
     * Constructor de la exepcion EmptyObject
     * <p>
     * Devuelve un mensaje de error indicando que se algo proporcionado no tiene
     * datos o estan no disponibles</p>
     */
    public EmptyObject() {
        super("Something is empty");
    }

}
