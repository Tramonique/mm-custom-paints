import javax.swing.*;

public class StockAlert {
    private String alertID;       
    private InventoryItem item;   
    private boolean isActive;     

    public boolean isActive() {
        return isActive;
    }

    public InventoryItem getItem() {
        return item;
    }

    public String getAlertDetails() {
        return String.format(
            "Alert ID: %s | Item: %s | Current Quantity: %d | Threshold: %d | Active: %b",
            alertID, 
            item.getName(), 
            item.getQuantity(), 
            item.getThreshold(), 
            isActive
        );
    }

    public StockAlert(String alertID, InventoryItem item) {
        this.alertID = alertID;
        this.item = item;
        this.isActive = item.isLowStock();
        checkAndShowLowInventoryAlert();
    }
   
    public void updateStatus() {
        this.isActive = item.isLowStock();
        checkAndShowLowInventoryAlert();
    }

    private void checkAndShowLowInventoryAlert() {
        if (isActive) {
            JOptionPane.showMessageDialog(null,
                    String.format("Low Inventory Alert!\nItem: %s\nCurrent Quantity: %d\nThreshold: %d",
                            item.getName(), item.getQuantity(), item.getThreshold()),
                    "Low Inventory Detected",
                    JOptionPane.WARNING_MESSAGE);
        }
    }
}
