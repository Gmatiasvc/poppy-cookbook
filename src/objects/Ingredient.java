package objects;

/**
 * Almacena los datos correspondientes a un ingrediente
 *
 * @author Grupo C [Backend]
 */
public class Ingredient {

    /**
     * Nombre del ingrediente
     */
    private String name;

    /**
     * Tipo de ingrediente (carbohidrato, grasa, etc)
     */
    private String type;

    /**
     * Cantidad del ingrediente
     */
    private int quantity;

    /**
     * Unidad de medida utilizada
     */
    private String unit;

    /**
     * Constructor de la clase Ingredient
     *
     * @param name Nombre del ingrediente
     * @param type Tipo de ingrediente
     * @param quantity Cantidad del ingrediente
     * @param unit Unidad de medida utilizada
     */
    public Ingredient(String name, String type, int quantity, String unit) {
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.unit = unit;
    }

    /**
     * Metodo getter de Name
     *
     * @return Nombre del ingrediente
     */
    public String getName() {
        return name;
    }

    /**
     * Metodo getter de Type
     *
     * @return Tipo de ingrediente
     */
    public String getType() {
        return type;
    }

    /**
     * Metodo getter de Quantity
     *
     * @return Cantidad del ingrediente utilizado
     */
    public int getQuantity() {
        return quantity;
    }

    /**
     * Metodo getter de Unit
     *
     * @return Unidad de medida utilizada
     */
    public String getUnit() {
        return unit;
    }

    /**
     * Metodo setter de Name
     *
     * @param name Nombre del ingrediente
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Metodo setter de Type
     *
     * @param type Tipo de ingrediente
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * Metodo setter de Quantity
     *
     * @param quantity Cantidad del ingrediente utilizado
     */
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * Metodo setter de Unit
     *
     * @param unit Unidad de medida utilizada
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

}
