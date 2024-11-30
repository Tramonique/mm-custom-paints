import javax.swing.*;
import java.awt.*;

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

    public String getAlertID() {
        return alertID;
    }

    public String getAlertDetails() {
        return String.format(
                "Alert ID: %s | Item: %s | Current Quantity: %d | Threshold: %d | Active: %b",
                alertID,
                item.getName(),
                item.getQuantity(),
                item.getThreshold(),
                isActive);
    }

    public StockAlert(String alertID, InventoryItem item) {
        this.alertID = alertID;
        this.item = item;
        this.isActive = item.isLowStock();
    }

    public void updateStatus() {
        this.isActive = item.isLowStock();
    }
}
