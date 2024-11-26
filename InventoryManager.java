import java.io.*;
import java.util.ArrayList;

class InventoryManager {
    private ArrayList<InventoryItem> inventoryList;  // List of inventory items
    private static final String FILE_NAME = "inventory.txt"; // File to save inventory

    // Constructor
    public InventoryManager() {
        this.inventoryList = new ArrayList<>();
        loadInventoryFromFile(); // Load inventory from file at startup
    }

    // Generate a new unique ID
    public String generateNewID() {
        if (inventoryList.isEmpty()) {
            return "0001"; // Start with 0001 if the list is empty
        }

        // Get the last item's ID and increment it
        String lastID = inventoryList.get(inventoryList.size() - 1).getItemID();
        int newID = Integer.parseInt(lastID) + 1;

        // Ensure it's always 4 digits (pad with leading zeros if necessary)
        return String.format("%04d", newID);
    }

    // Add a new item to the inventory
    public void addItem(InventoryItem item) {
        inventoryList.add(item);
        saveInventoryToFile(); // Save inventory to file
    }

    // Remove an item by ID
    public void removeItem(String itemID) {
        inventoryList.removeIf(item -> item.getItemID().equals(itemID));
        saveInventoryToFile(); // Save updated inventory to file
    }

    // Find an item by ID
    public InventoryItem findItem(String itemID) {
        for (InventoryItem item : inventoryList) {
            if (item.getItemID().equals(itemID)) {
                return item;
            }
        }
        return null; // Item not found
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

    // Save inventory to a file
    public void saveInventoryToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (InventoryItem item : inventoryList) {
                writer.println(item.getItemID() + "," +
                               item.getName() + "," +
                               item.getQuantity() + "," +
                               item.getUnitCost() + "," +
                               item.getThreshold());
            }
            System.out.println("Inventory saved to file.");
        } catch (IOException e) {
            System.err.println("Error saving inventory: " + e.getMessage());
        }
    }

    // Load inventory from a file
    private void loadInventoryFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("No inventory file found. Starting with an empty inventory.");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 5) {
                    String itemID = parts[0];
                    String name = parts[1];
                    int quantity = Integer.parseInt(parts[2]);
                    double unitCost = Double.parseDouble(parts[3]);
                    int threshold = Integer.parseInt(parts[4]);

                    InventoryItem item = new InventoryItem(itemID, name, quantity, unitCost, threshold);
                    inventoryList.add(item);
                }
            }
            System.out.println("Inventory loaded from file.");
        } catch (IOException e) {
            System.err.println("Error loading inventory: " + e.getMessage());
        }
    }
}
