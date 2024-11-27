class Report {
    private String type;          // Type of report (e.g., "Sales", "Inventory")
    private String startDate;     // Start date for the report (YYYY-MM-DD)
    private String endDate;       // End date for the report (YYYY-MM-DD)
    private String content;       // The report's content or data summary

    // Constructor
    public Report(String type, String startDate, String endDate, String content) {
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.content = content;
    }

    // Getters

    public String getType() {
        return type;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getContent() {
        return content;
    }


    // Display report details
    public String getReportDetails() {
        StringBuilder content = new StringBuilder();
        content.append(String.format("Report Type: %s\nStart Date: %s\nEnd Date: %s\n", 
                                    type, startDate, endDate));
        
        content.append("Sales Data:\n");
        for (SalesRecord sale : salesData) {
            content.append(sale.toString()).append("\n");
        }

        return content.toString();
    }
}
