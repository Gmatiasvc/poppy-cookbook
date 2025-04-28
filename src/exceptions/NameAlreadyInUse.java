package exceptions;

/**
 * Excepcion de tipo NameAlreadyInUse
 * <p>
 * Es lanzada cuando el nombre proporcionado para algun archivo ya está en uso</p>
 *
 * @author Grupo C [Backend]
 */
public class NameAlreadyInUse extends Exception {

    /**
     * Constructor de la exepcion NameAlreadyInUse
     * <p>
     * Devuelve un mensaje de error indicando que el nombre proporcionado para un archivo ya esta en uso o no esta disponible por algun otro motivo</p>
     */
    public NameAlreadyInUse() {
        super("Name already in use");
    }

}
