import javax.swing.*;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;

class AlertManager {
    private ArrayList<StockAlert> alertList; // List of stock alerts

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

    // Method to display active alerts in a table
    public void displayActiveAlertsInTable(JTable alertTable) {
        DefaultTableModel tableModel = (DefaultTableModel) alertTable.getModel();
        tableModel.setRowCount(0); // Clear existing rows

        for (StockAlert alert : alertList) {
            if (alert.isActive()) {
                Object[] row = {
                        alert.getAlertID(),
                        alert.getItem().getName(),
                        alert.getItem().getQuantity(),
                        alert.getItem().getThreshold(),
                        alert.isActive() ? "Active" : "Inactive"
                };
                tableModel.addRow(row);
            }
        }
    }
}