class InventoryItem {
    private String itemID;       // Unique identifier for the item
    private String name;         // Name of the item
    private int quantity;        // Current quantity in stock
    private double unitCost;     // Cost per unit
    private int threshold;       // Minimum stock level before triggering alerts

    public InventoryItem(String itemID, String name, int quantity, double unitCost, int threshold) {
        this.itemID = itemID;
        this.name = name;
        this.quantity = quantity;
        this.unitCost = unitCost;
        this.threshold = threshold;
    }

    // Getters
    public String getItemID() {
        return itemID;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getUnitCost() {
        return unitCost;
    }

    public int getThreshold() {
        return threshold;
    }

    // Setters
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setThreshold(int threshold) {
        this.threshold = threshold;
    }

    // Check if stock is below threshold
    public boolean isLowStock() {
        return this.quantity < this.threshold;
    }

    // Display item details
    public String getItemDetails() {
        return String.format("Item ID: %s | Name: %s | Quantity: %d",
                             itemID, name, quantity, threshold);
    }
}
