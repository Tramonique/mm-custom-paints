import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

class AlertManager {
    private ArrayList<StockAlert> alertList;  // List of stock alerts
    private static final String FILE_NAME = "low_inventory_alerts.txt"; // File to store low inventory alerts
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"); // Date-time format

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

    public ArrayList<StockAlert> getActiveAlertsList() {
        ArrayList<StockAlert> activeAlerts = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            String alertID = null;
            String itemName = null;
            String itemID = null;
            int quantity = 0;
            int threshold = 0;
            boolean readingAlert = false;

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("Low Inventory Alert for Item:")) {
                    itemName = line.substring(line.indexOf(":") + 2);
                    readingAlert = true;
                } else if (line.startsWith("Item ID:")) {
                    itemID = line.substring(line.indexOf(":") + 2);
                } else if (line.startsWith("Quantity:")) {
                    quantity = Integer.parseInt(line.substring(line.indexOf(":") + 2));
                } else if (line.startsWith("Threshold:")) {
                    threshold = Integer.parseInt(line.substring(line.indexOf(":") + 2));
                } else if (line.startsWith("Alert Created At:")) {
                    // Complete the reading of one alert
                    if (readingAlert && itemName != null && itemID != null) {
                        InventoryItem item = new InventoryItem(itemID, itemName, quantity, threshold);
                        StockAlert alert = new StockAlert("ALERT-" + itemID, item);
                        activeAlerts.add(alert);
                    }
                    readingAlert = false; // Reset for the next alert
                }
            }
        } catch (IOException e) {
            System.err.println("Error reading from file: " + e.getMessage());
        }
        return activeAlerts;
    }

    // Method to create a file entry when an item is low on inventory
    private void createLowInventoryFile(InventoryItem item) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME, true))) {
            // Get the current date and time
            String currentTime = LocalDateTime.now().format(DATE_FORMATTER);

            // Write details to the file
            writer.write("Low Inventory Alert for Item: " + item.getName());
            writer.newLine();
            writer.write("Item ID: " + item.getItemID());
            writer.newLine();
            writer.write("Quantity: " + item.getQuantity());
            writer.newLine();
            writer.write("Threshold: " + item.getThreshold());
            writer.newLine();
            writer.write("Alert Created At: " + currentTime); // Add the time
            writer.newLine();
            writer.write("--------------------");
            writer.newLine();
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
