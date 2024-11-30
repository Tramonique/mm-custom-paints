class Report {
    private String type;
    private String startDate;
    private String endDate;
    private String content;

    public Report(String type, String startDate, String endDate, String content) {
        this.type = type;
        this.startDate = startDate;
        this.endDate = endDate;
        this.content = content;
    }

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

    public String getReportDetails() {
        return "Report Type: " + type + "\n" +
                "Period: " + startDate + " to " + endDate + "\n";
    }
}