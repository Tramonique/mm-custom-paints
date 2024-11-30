import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

public class SalesManager {
    private List<SalesRecord> salesList; // List of sales records
    private InventoryManager inventoryManager;
    private static final String SALES_FILE = "sales_data.txt"; // File to store sales data

    // Constructor
    public SalesManager(InventoryManager inventoryManager) {
        this.salesList = new ArrayList<>();
        this.inventoryManager = inventoryManager;
        loadSalesData(); // Load existing sales data from file
    }

    // Add a sales record (log sale)
    public void logSale(String itemID, int quantitySold, double totalAmount) {
        // Search for the item by ID
        InventoryItem item = inventoryManager.findItem(itemID);

        // Check if the item was found
        if (item == null) {
            JOptionPane.showMessageDialog(null, "Error: Item with ID " + itemID + " not found.");
        }
        // Check if the quantity is sufficient
        else if (item.getQuantity() < quantitySold) {
            JOptionPane.showMessageDialog(null,
                    "Error: Insufficient quantity of item " + itemID + ". Available stock: " + item.getQuantity());
        }
        // If the item is found and quantity is sufficient, log the sale
        else {
            // Generate a sale ID (simple example using UUID, or use a different approach)
            String saleID = generateSaleID();
            String date = getCurrentDate(); // Get current date for the sale record

            // Create and log the sale
            SalesRecord sale = new SalesRecord(saleID, date, item.getItemID(), quantitySold, totalAmount);
            salesList.add(sale);

            // Update the inventory by deducting the sold quantity
            item.setQuantity(item.getQuantity() - quantitySold); // Deduct sold quantity

            // Save the updated inventory
            inventoryManager.saveInventoryToFile();

            // Append the sale to the file
            appendSaleToFile(sale);

            JOptionPane.showMessageDialog(null, "Sale logged successfully for item: " + itemID);
        }
    }

    // Remove a sales record by ID
    public boolean removeSale(String saleID) {
        // Find the sale by its ID
        SalesRecord saleToRemove = findSaleById(saleID);

        if (saleToRemove != null) {
            // Remove the sale from the list
            salesList.remove(saleToRemove);

            // Save updated data to file
            saveSalesData();
            JOptionPane.showMessageDialog(null, "Sale with ID " + saleID + " was successfully removed.");
            return true;
        } else {
            JOptionPane.showMessageDialog(null, "Error: Sale with ID " + saleID + " not found.");
            return false;
        }
    }

    // Find a sale by its ID
    private SalesRecord findSaleById(String saleID) {
        for (SalesRecord sale : salesList) {
            if (sale.getSaleID().equals(saleID)) {
                return sale; // Return the sale if found
            }
        }
        return null; // Return null if no sale is found
    }

    // Generate a new unique Sale ID
    public String generateSaleID() {
        if (salesList.isEmpty()) {
            return "000001"; // Start with 000001 if the sales list is empty
        }

        // Get the last sale's ID from the sales list and increment it
        String lastSaleID = salesList.get(salesList.size() - 1).getSaleID();
        int newSaleID = Integer.parseInt(lastSaleID) + 1;

        // Ensure it's always 6 digits (pad with leading zeros if necessary)
        return String.format("%06d", newSaleID);
    }

    // Get current date in YYYY-MM-DD format
    private String getCurrentDate() {
        return java.time.LocalDate.now().toString(); // Using LocalDate to get today's date
    }

    // Load sales data from file
    private void loadSalesData() {
        File salesFile = new File(SALES_FILE);
        if (!salesFile.exists()) {
            try {
                salesFile.createNewFile(); // Create the file if it doesn't exist
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Error creating sales file: " + e.getMessage());
            }
        }

        // Proceed with loading sales data
        try (BufferedReader br = new BufferedReader(new FileReader(SALES_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] saleData = line.split(",");
                if (saleData.length == 5) {
                    String saleID = saleData[0];
                    String date = saleData[1];
                    String productID = saleData[2];
                    int quantitySold = Integer.parseInt(saleData[3]);
                    double totalAmount = Double.parseDouble(saleData[4]);

                    SalesRecord record = new SalesRecord(saleID, date, productID, quantitySold, totalAmount);
                    salesList.add(record);
                }
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error loading sales data: " + e.getMessage());
        }
    }

    // Save sales data to file
    private void saveSalesData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(SALES_FILE))) {
            for (SalesRecord sale : salesList) {
                bw.write(sale.getSaleID() + "," + sale.getDate() + "," + sale.getProductID() + "," +
                        sale.getQuantitySold() + "," + sale.getTotalAmount());
                bw.newLine();
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error saving sales data: " + e.getMessage());
        }
    }

    // Append a new sale to the sales data file
    private void appendSaleToFile(SalesRecord sale) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(SALES_FILE, true))) {
            bw.write(sale.getSaleID() + "," + sale.getDate() + "," + sale.getProductID() + "," +
                    sale.getQuantitySold() + "," + sale.getTotalAmount());
            bw.newLine();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error appending sale data: " + e.getMessage());
        }
    }

    // Get sales data as a 2D Object array for JTable display
    public Object[][] getSalesAsTable() {
        Object[][] tableData = new Object[salesList.size()][5];
        for (int i = 0; i < salesList.size(); i++) {
            SalesRecord sale = salesList.get(i);
            tableData[i][0] = sale.getSaleID();
            tableData[i][1] = sale.getDate();
            tableData[i][2] = sale.getProductID();
            tableData[i][3] = sale.getQuantitySold();
            tableData[i][4] = sale.getTotalAmount();
        }
        return tableData;
    }

    // View all sales records
    public void viewAllSales() {
        if (salesList.isEmpty()) {
            JOptionPane.showMessageDialog(null, "No sales records found.");
        } else {
            for (SalesRecord sale : salesList) {
                System.out.println(sale);
                System.out.println("--------------------");
            }
        }
    }

    // Implement the getSalesData() method to return the sales list
    public List<SalesRecord> getSalesData() {
        return salesList; // Return the sales list
    }
}
