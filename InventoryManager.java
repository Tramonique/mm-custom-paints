import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

class InventoryManager {
    private ArrayList<InventoryItem> inventoryList;  // List of inventory items
    private static final String FILE_NAME = "inventory.txt"; // File to save inventory

    public InventoryManager () {
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

    // Find an item by ID
    public InventoryItem findItem(String itemID) {
        for (InventoryItem item : inventoryList) {
            if (item.getItemID().equals(itemID)) {
                return item;
            }
        }
        return null; // Item not found
    }

    // Add a new item
    public boolean addItem(String name, double quantity, double unitCost, double threshold, Map<String, Double> formula, int splitRatio) {
        String itemID = generateNewID(); // Generate new ID for the item
        InventoryItem newItem = new InventoryItem(itemID, name, quantity, unitCost, threshold);
        newItem.setSplitRatio(splitRatio); // Set the split ratio

        // If the item has a formula, verify raw materials and deduct them
        if (formula != null) {
            if (!deductRawMaterials(formula, quantity)) {
                return false; // Insufficient raw materials
            }
            newItem.setFormula(formula); // Set the formula for the product
        }

        inventoryList.add(newItem);
        saveInventoryToFile();
        return true;
    }

    // Restock an existing item
    public boolean restockItem(String itemID, double additionalQuantity) {
        InventoryItem item = findItem(itemID);
        if (item == null) {
            System.out.println("Item not found.");
            return false;
        }

        // Check if the item has a formula
        if (item.getFormula() != null) {
            // Deduct raw materials based on the formula
            if (!deductRawMaterials(item.getFormula(), additionalQuantity)) {
                System.out.println("Failed to restock " + item.getName() + " due to insufficient raw materials.");
                return false;
            }
        }

        // Add the quantity to the item
        item.setQuantity(item.getQuantity() + additionalQuantity);
        saveInventoryToFile();
        System.out.println("Item " + item.getName() + " restocked successfully. New quantity: " + item.getQuantity());
        return true;
    }

    // Deduct raw materials based on the formula
    private boolean deductRawMaterials(Map<String, Double> formula, double productQuantity) {
        for (Map.Entry<String, Double> entry : formula.entrySet()) {
            String rawMaterialID = entry.getKey();
            double requiredSubUnits = entry.getValue() * productQuantity;

            InventoryItem rawMaterial = findItem(rawMaterialID);
            if (rawMaterial == null || rawMaterial.getAvailableSubUnits() < requiredSubUnits) {
                System.out.println("Insufficient raw material: " + (rawMaterial != null ? rawMaterial.getName() : rawMaterialID));
                return false;
            }
        }

        for (Map.Entry<String, Double> entry : formula.entrySet()) {
            String rawMaterialID = entry.getKey();
            double requiredSubUnits = entry.getValue() * productQuantity;

            InventoryItem rawMaterial = findItem(rawMaterialID);
            rawMaterial.deductSubUnits(requiredSubUnits); // Automatically adjusts for split ratio
        }

        saveInventoryToFile();
        return true;
    }

    // Display all inventory items
    public void displayAllItems() {
        for (InventoryItem item : inventoryList) {
            System.out.println(item.getItemDetails());
        }
    }

    // Get all items
    public ArrayList<InventoryItem> getAllItems() {
        return inventoryList;
    }

    //Remove inventory item
    public boolean removeItem(String itemID) {
        InventoryItem itemToRemove = findItem(itemID);
        if (itemToRemove != null) {
            inventoryList.remove(itemToRemove);
            saveInventoryToFile(); // Save updated inventory
            return true;
        }
        return false; // Item not found
    }

    // Save inventory to a file
    public void saveInventoryToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (InventoryItem item : inventoryList) {
                writer.printf("%s,%s,%.2f,%.2f,%.2f,%s%n",
                        item.getItemID(),
                        item.getName(),
                        item.getQuantity(),
                        item.getUnitCost(),
                        item.getThreshold(),
                        item.getFormula() == null ? "null" : item.getFormula().toString());
            }
            System.out.println("Inventory saved to file.");
        } catch (IOException e) {
            System.err.println("Error saving inventory: " + e.getMessage());
        }
    }

    // Load inventory from a file0.
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
                if (parts.length >= 5) {
                    String itemID = parts[0];
                    String name = parts[1];
                    double quantity = Double.parseDouble(parts[2]);
                    double unitCost = Double.parseDouble(parts[3]);
                    double threshold = Double.parseDouble(parts[4]);

                    InventoryItem item = new InventoryItem(itemID, name, quantity, unitCost, threshold);

                    // Handle formula if present
                    if (!"null".equals(parts[5]) && parts.length > 5) {
                        Map<String, Double> formula = new HashMap<>();
                        String[] formulaParts = parts[5].replace("{", "").replace("}", "").split(";");
                        for (String entry : formulaParts) {
                            String[] keyValue = entry.split("=");
                            formula.put(keyValue[0].trim(), Double.parseDouble(keyValue[1].trim()));
                        }
                        item.setFormula(formula);
                    }

                    inventoryList.add(item);
                }
            }
            System.out.println("Inventory loaded from file.");
        } catch (IOException e) {
            System.err.println("Error loading inventory: " + e.getMessage());
        }
    }
      
}
