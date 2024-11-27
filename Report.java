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
        return String.format("Type: %s\nStart Date: %s\nEnd Date: %s\nContent:\n%s",
                             type, startDate, endDate, content);
    }
}
