package objects;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Almacena los datos correspondientes a una receta
 *
 * @author Grupo C [Backend]
 */
public class Recipe implements Serializable {

    ArrayList<Ingredient> ingredients;
    String name;
    String description;
    String instructions;
    int prepTime;
    int cookTime;
    int totalTime;

    /**
     * Constructor de la clase Recipe
     *
     * @param name Nombre de la receta
     * @param description Descripcion corta de la reeta
     * @param ingredients Lista de ingredientes de la receta, en formato ArrayList
     * @param instructions Instrucciones de la preparacion de la receta
     * @param prepTime Tiempo de preparacion
     * @param cookTime Tiempo de coccion
     */
    public Recipe(String name, String description, ArrayList<Ingredient> ingredients, String instructions, int prepTime, int cookTime) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.totalTime = prepTime + cookTime;
    }

    /**
     * Metodo getter de Ingredients
     *
     * @return Lista de ingredientes, en formato ArrayList
     */
    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    /**
     * Metodo setter de Ingredients
     *
     * @param ingredients Lista de ingredientes, en formato ArrayList
     */
    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    /**
     * Metodo getter de Name
     *
     * @return Nombre de la receta
     */
    public String getName() {
        return name;
    }

    /**
     * Metodo setter de Name
     *
     * @param name Nombre de la receta
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Metodo getter de Descripcion
     *
     * @return Descripcion corta de la receta
     */
    public String getDescription() {
        return description;
    }

    /**
     * Metodo setter de Description
     *
     * @param description Descripcion corta de la receta
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Metodo getter de Instrucciones
     *
     * @return Instrucciones de la preparacion de la receta
     */
    public String getInstructions() {
        return instructions;
    }

    /**
     * Metodo setter de Instructions
     *
     * @param instructions Instrucciones de preparacion de la receta
     */
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }

    /**
     * Metodo getter de PrepTime
     *
     * @return Tiempo de preparacion
     */
    public int getPrepTime() {
        return prepTime;
    }

    /**
     * Metodo setter de PrepTime
     *
     * @param prepTime Tiempo de preparacion
     */
    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    /**
     * Metodo getter de CookTime
     *
     * @return Tiempo de coccion
     */
    public int getCookTime() {
        return cookTime;
    }

    /**
     * Metodo setter de CookTime
     *
     * @param cookTime Tiempo de coccion
     */
    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    /**
     * Metodo getter de TotalTime
     *
     * @return tiempo de preparacion total de la receta
     */
    public int getTotalTime() {
        return totalTime;
    }
}
