import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ReportManager {

    // Generate a sales report dynamically based on the sales data
    public static Report generateSalesReport(String type, String startDate, String endDate,
            List<SalesRecord> salesData) {
        double totalSales = 0;
        StringBuilder content = new StringBuilder("Sales Report:\n");

        content.append(String.format("Period: %s to %s\n", startDate, endDate));
        content.append("------------------------------------------------\n");
        for (SalesRecord sale : salesData) {
            if (sale.getDate().compareTo(startDate) >= 0 && sale.getDate().compareTo(endDate) <= 0) {
                content.append(String.format("Sale ID: %s | Product ID: %s | Quantity: %d | Amount: %.2f\n",
                        sale.getSaleID(), sale.getProductID(), sale.getQuantitySold(), sale.getTotalAmount()));
                totalSales += sale.getTotalAmount();
            }
        }
        content.append("------------------------------------------------\n");
        content.append(String.format("Total Sales: %.2f\n", totalSales));

        // Return a Report object containing both metadata and the report content
        return new Report(type, startDate, endDate, content.toString());
    }

    // List all reports
    public static void listAllReports(Report report) {
        if (report != null) {
            System.out.println(report.getReportDetails());
            System.out.println(report.getContent()); // Display the content as well
        } else {
            System.out.println("Report not found.");
        }
    }

    // Export a report to CSV
    public void exportReportToCSV(Report report, String filename) {
        try (FileWriter writer = new FileWriter(filename)) {
            writer.append("Type,StartDate,EndDate,Content\n");
            writer.append(report.getType())
                    .append(",")
                    .append(report.getStartDate())
                    .append(",")
                    .append(report.getEndDate())
                    .append(",")
                    .append(report.getContent().replaceAll("\n", " ")) // Flatten content to fit CSV format
                    .append("\n");
            System.out.println("Report exported to " + filename);
        } catch (IOException e) {
            System.out.println("Error exporting report: " + e.getMessage());
        }
    }
}
