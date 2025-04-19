package objects;

import java.io.Serializable;
import java.util.ArrayList;

public class Recipe implements Serializable {

    ArrayList<Ingredient> ingredients;
    String name;
    String description;
    ArrayList<String> instructions;
    int prepTime;
    int cookTime;
    int totalTime;

    public Recipe(String name, String description, ArrayList<Ingredient> ingredients, ArrayList<String> instructions, int prepTime, int cookTime) {
        this.name = name;
        this.description = description;
        this.ingredients = ingredients;
        this.instructions = instructions;
        this.prepTime = prepTime;
        this.cookTime = cookTime;
        this.totalTime = prepTime + cookTime;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getInstructions() {
        return instructions;
    }

    public void setInstructions(ArrayList<String> instructions) {
        this.instructions = instructions;
    }

    public int getPrepTime() {
        return prepTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public int getTotalTime() {
        return totalTime;
    }

	
    @Override
	public String toString(){
		StringBuilder sb = new StringBuilder();
		sb.append("Recipe Name: ").append(name).append("\n");
		sb.append("Description: ").append(description).append("\n");
		sb.append("Ingredients: \n");
		for (Ingredient ingredient : ingredients) {
			sb.append(ingredient.toString()).append("\n");
		}
		sb.append("Instructions: \n");
		for (String instruction : instructions) {
			sb.append(instruction).append("\n");
		}
		sb.append("Prep Time: ").append(prepTime).append(" minutes\n");
		sb.append("Cook Time: ").append(cookTime).append(" minutes\n");
		sb.append("Total Time: ").append(totalTime).append(" minutes\n");
		return sb.toString();
	}
}
