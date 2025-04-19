package objects;

public class Ingredient {

    private String name;
    private String type;
    private int quantity;
    private String unit;

    public Ingredient(String name, String type, int quantity, String unit) {
        this.name = name;
        this.type = type;
        this.quantity = quantity;
        this.unit = unit;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public int getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

}
