import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import  java.time.format.DateTimeFormatter;
import java.util.List;

public class ReportManager {

    // Generate and export a sales report dynamically based on the sales data and date range
    public void generateAndExportSalesReportByDate(String startDate, String endDate, SalesManager salesManager) {
        List<SalesRecord> salesData = salesManager.getSalesList();
        double totalSales = 0;
        StringBuilder content = new StringBuilder("Sales Report:\n");

        content.append("--------------------------\n");

        // Filter and append sales records within the specified date range
        for (SalesRecord sale : salesData) {
            if (sale.getDate().compareTo(startDate) >= 0 && sale.getDate().compareTo(endDate) <= 0) {
                content.append(String.format("Sale Date: %s | Sale ID: %s | Product ID: %s | Quantity: %d | Amount: %.2f\n",
                                            sale.getDate(), sale.getSaleID(), sale.getProductID(), sale.getQuantitySold(), sale.getTotalAmount()));
                totalSales += sale.getTotalAmount();
            }
        }

        content.append("-------------------------\n");
        content.append(String.format("Total Sales for Period %s to %s: %.2f\n", startDate, endDate, totalSales));

        exportReportToCSV(startDate, endDate, content.toString());
    }

    // Generate and export a sales report dynamically based on product ID
    public void generateAndExportSalesReportByProduct(String startDate, String endDate, SalesManager salesManager, String itemID) {
        List<SalesRecord> salesData = salesManager.getSalesList();  // Access the sales data from SalesManager
        double totalSales = 0;
        StringBuilder content = new StringBuilder("Sales Report:\n");

        content.append("------------------------\n");

        // Filter and append sales records for the given product ID within the date range
        for (SalesRecord sale : salesData) {
            if (sale.getProductID().equals(itemID) &&
                sale.getDate().compareTo(startDate) >= 0 && sale.getDate().compareTo(endDate) <= 0) {
                content.append(String.format("Sale Date: %s | Sale ID: %s | Product ID: %s | Quantity: %d | Amount: %.2f\n",
                                            sale.getDate(), sale.getSaleID(), sale.getProductID(), sale.getQuantitySold(), sale.getTotalAmount()));
                totalSales += sale.getTotalAmount();
            }
        }

        content.append("--------------------------\n");
        content.append(String.format("Total Sales for Product %s in Period %s to %s: %.2f\n", itemID, startDate, endDate, totalSales));

        exportReportToCSV(startDate, endDate, content.toString());
    }

    // Export a report to a CSV file
    private void exportReportToCSV(String startDate, String endDate, String content) {
        // Determine the path to the Downloads folder based on the OS
        String userHome = System.getProperty("user.home");
        String os = System.getProperty("os.name").toLowerCase();
        String downloadsPath = "";

        if (os.contains("win")) {
            downloadsPath = userHome + "\\Downloads\\";
        } else if (os.contains("nix") || os.contains("nux") || os.contains("mac")) {
            downloadsPath = userHome + "/Downloads/";
        }

        File downloadDir = new File(downloadsPath);
        if (!downloadDir.exists()) {
            downloadDir.mkdirs();  // Create the directory if it doesn't exist
        }

        // Generate filename using the current date in "yyyy-MM-dd" format
        String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String filename = "sales_report_" + currentDate + ".csv";

        // Set the full path to the file, including the filename
        String filePath = downloadsPath + filename;

        // Write the content to the CSV file in the Downloads folder
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("Report for Sales from " + startDate + " to " + endDate + "\n");
            writer.append("Sale Date,Sale ID,Product ID,Quantity,Amount\n");

            String[] lines = content.split("\n");
            for (String line : lines) {
                if (line.contains("Sale Date:")) { 
                
                    String saleDate = line.split("Sale Date:")[1].split("\t")[0].trim();
                    String saleID = line.split("Sale ID:")[1].split("\t")[0].trim();
                    String productID = line.split("Product ID:")[1].split("\t")[0].trim();
                    String quantity = line.split("Quantity:")[1].split("\t")[0].trim();
                    String amount = line.split("Amount:")[1].trim();

                    writer.append(String.format("%s,%s,%s,%s,%s\n", saleDate, saleID, productID, quantity, amount));
                }
            }

            System.out.println("Report exported to " + filePath);
        } catch (IOException e) {
            System.out.println("Error exporting report: " + e.getMessage());
        }
    }
}
