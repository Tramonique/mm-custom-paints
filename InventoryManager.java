import java.util.ArrayList;

class InventoryManager {
    private ArrayList<InventoryItem> inventoryList;  // List of inventory items

    // Constructor
    public InventoryManager() {
        this.inventoryList = new ArrayList<>();
    }

    // Add a new item to the inventory
    public void addItem(InventoryItem item) {
        inventoryList.add(item);
    }

    // Remove an item by ID
    public void removeItem(String itemID) {
        inventoryList.removeIf(item -> item.getItemID().equals(itemID));
    }

    // Find an item by ID
    public InventoryItem findItem(String itemID) {
        for (InventoryItem item : inventoryList) {
            if (item.getItemID().equals(itemID)) {
                return item;
            }
        }
        return null;  // Item not found
    }

    // Get all items
    public ArrayList<InventoryItem> getAllItems() {
        return inventoryList;
    }

    // Display all inventory items
    public void displayAllItems() {
        for (InventoryItem item : inventoryList) {
            System.out.println(item.getItemDetails());
        }
    }
}
