import java.util.*;

public class InventoryItem {
    private String itemID;
    private String name;
    private double quantity;
    private double unitCost;
    private double threshold;
    private int splitRatio;
    private Map<String, Double> formula; // Formula to handle raw material requirements
    private double subUnits;

    // Constructor for InventoryItem
    public InventoryItem(String itemID, String name, double quantity, double unitCost, double threshold) {
        this.itemID = itemID;
        this.name = name;
        this.quantity = quantity;
        this.unitCost = unitCost;
        this.threshold = threshold;
        this.splitRatio = 1; // Default split ratio if not set
        this.formula = new HashMap<>(); // Initialize the formula map
    }

    // Getters and Setters
    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getQuantity() {
        return quantity;
    }

    public void setQuantity(double quantity) {
        this.quantity = quantity;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public void setUnitCost(double unitCost) {
        this.unitCost = unitCost;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public double getSubUnits() {
        return subUnits;
    }

    public void setSubUnits(double subUnits) {
        this.subUnits = subUnits;
    }

    public int getSplitRatio() {
        return splitRatio;
    }

    public void setSplitRatio(int splitRatio) {
        this.splitRatio = splitRatio;
    }

    public Map<String, Double> getFormula() {
        return formula;
    }

    public void setFormula(Map<String, Double> formula) {
        this.formula = formula; // Set the formula if it exists
    }

    public void deductSubUnits(double quantity) {
        // Logic to deduct sub-units based on the split ratio
        this.quantity -= quantity / splitRatio; // Adjusted for split ratio
    }

    // Get the available sub-units
    public double getAvailableSubUnits() {
        return quantity * splitRatio; // Returns quantity adjusted for split ratio
    }

    // Get item details as a string
    public String getItemDetails() {
        return String.format("ID: %s, Name: %s, Quantity: %.2f, Cost: %.2f, Threshold: %.2f",
                itemID, name, quantity, unitCost, threshold);
    }

    @Override
    public String toString() {
        return getItemDetails();
    }

    public boolean isLowStock() {
        // TODO Auto-generated method stub
        return quantity <= threshold;
    }
}
