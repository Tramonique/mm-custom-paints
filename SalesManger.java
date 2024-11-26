import java.util.*;
import java.util.stream.Collectors;

class SalesManager {
    private List<SalesRecord> salesList;  // List of sales records

    // Constructor
    public SalesManager() {
        this.salesList = new ArrayList<>();
    }

    // Add a sales record
    public void logSale(SalesRecord sale) {
        salesList.add(sale);
    }

    // Remove a sales record by ID
    public boolean removeSale(String saleID) {
        return salesList.removeIf(sale -> sale.getSaleID().equals(saleID));
    }

    // Find a sales record by ID
    public Optional<SalesRecord> findSale(String saleID) {
        return salesList.stream()
                        .filter(sale -> sale.getSaleID().equals(saleID))
                        .findFirst();  // Return Optional to avoid null checks
    }

    // Generate daily sales report
    public List<SalesRecord> getSalesByDate(String date) {
        return salesList.stream()
                        .filter(sale -> sale.getDate().equals(date))
                        .collect(Collectors.toList());
    }

    // Calculate total revenue for a specific date
    public double calculateDailyRevenue(String date) {
        return salesList.stream()
                        .filter(sale -> sale.getDate().equals(date))
                        .mapToDouble(SalesRecord::getTotalAmount)
                        .sum();
    }

    // Export sales data to console (simple CSV format for now)
    public void exportSalesDataToCSV() {
        System.out.println("SaleID,Date,ProductID,QuantitySold,TotalAmount");
        for (SalesRecord sale : salesList) {
            System.out.println(sale.getSaleID() + "," + sale.getDate() + "," + sale.getProductID() + "," +
                               sale.getQuantitySold() + "," + sale.getTotalAmount());
        }
    }

    // Display all sales records
    public void viewAllSales() {
        for (SalesRecord sale : salesList) {
            System.out.println(sale);  // Uses the toString() method for clean output
            System.out.println("--------------------");
        }
    }
}
