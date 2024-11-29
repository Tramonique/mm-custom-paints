import java.util.Map;

public class InventoryItem {
    private String itemID;       // Unique identifier for the item
    private String name;         // Name of the item
    private double quantity;     // Current quantity in stock (supports fractional values)
    private double unitCost;     // Cost per unit
    private double threshold;    // Minimum stock level before triggering alerts
    private Map<String, Double> formula; // Formula for the item (raw materials and their quantities)
    private int splitRatio;        // Number of sub-units per bulk unit

    public InventoryItem(String itemID, String name, double quantity, double unitCost, double threshold) {
        this.itemID = itemID;
        this.name = name;
        this.quantity = quantity;
        this.unitCost = unitCost;
        this.threshold = threshold;
        this.splitRatio = 1; // Default: 1 unit = 1 sub-unit
    }

    // Getters and setters
    public String getItemID() {
        return itemID;
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

    public Map<String, Double> getFormula() {
        return formula;
    }

    public void setFormula(Map<String, Double> formula) {
        this.formula = formula;
    }

    public int getSplitRatio() {
        return splitRatio;
    }

    public void setSplitRatio(int splitRatio) {
        this.splitRatio = splitRatio;
    }

    // Calculate available sub-units based on split ratio
    public double getAvailableSubUnits() {
        return quantity * splitRatio;
    }

    // Deduct sub-units and convert to bulk units
    public void deductSubUnits(double subUnits) {
        this.quantity -= subUnits / splitRatio;
    }

    // Deduct a specified quantity from the item's quantity
    public void deductQuantity(double quantitySold) {
        if (quantitySold <= 0) {
            System.out.println("Quantity to deduct must be greater than 0.");
            return;
        }

        if (quantitySold > this.quantity) {
            System.out.println("Insufficient quantity to deduct.");
        } else {
            this.quantity -= quantitySold; // Deduct the quantity
            System.out.println("Deducted " + quantitySold + " from " + this.name + ". Remaining quantity: " + this.quantity);
        }
    }

    public String getItemDetails() {
        String splitRatioInfo = (splitRatio > 1) ? " (1 bulk unit = " + splitRatio + " sub-units)" : "";
        return String.format("Item ID: %s | Name: %s | Quantity: %.2f%s",
                            itemID, name, quantity, splitRatioInfo);
    }
}