import java.io.*;
import java.util.ArrayList;

class AlertManager {
    private ArrayList<StockAlert> alertList;  // List of stock alerts
    private static final String FILE_NAME = "low_inventory_alerts.txt"; // File to store low inventory alerts

    // Constructor
    public AlertManager() {
        this.alertList = new ArrayList<>();
    }

    // Monitor inventory and update alerts
    public void monitorInventory(InventoryManager inventoryManager) {
        for (InventoryItem item : inventoryManager.getAllItems()) {
            StockAlert existingAlert = findAlertByItemID(item.getItemID());
            if (item.isLowStock()) {
                if (existingAlert == null) {
                    // Create a new alert and add it to the list
                    StockAlert newAlert = new StockAlert("ALERT-" + item.getItemID(), item);
                    alertList.add(newAlert);
                    // Create a file entry for this low inventory item
                    createLowInventoryFile(item);
                } else {
                    // Update existing alert
                    existingAlert.updateStatus();
                }
            } else if (existingAlert != null && existingAlert.isActive()) {
                // Deactivate alert if stock is sufficient
                existingAlert.updateStatus();
            }
        }
    }

    // Find an alert by item ID
    private StockAlert findAlertByItemID(String itemID) {
        for (StockAlert alert : alertList) {
            if (alert.getItem().getItemID().equals(itemID)) {
                return alert;
            }
        }
        return null;
    }

    // Display all active alerts
    public void displayActiveAlerts() {
        for (StockAlert alert : alertList) {
            if (alert.isActive()) {
                System.out.println(alert.getAlertDetails());
                System.out.println("--------------------");
            }
        }
    }

    // Method to create a file entry when an item is low on inventory
    private void createLowInventoryFile(InventoryItem item) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            writer.write("Low Inventory Alert for Item: " + item.getName());
            writer.newLine();
            writer.write("Item ID: " + item.getItemID());
            writer.newLine();
            writer.write("Quantity: " + item.getQuantity());
            writer.newLine();
            writer.write("Threshold: " + item.getThreshold());
            writer.newLine();
            writer.write("--------------------");
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
