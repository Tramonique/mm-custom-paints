public class Report {
    private String startDate;
    private String endDate;
    private String content; 

    public Report(String type, String startDate, String endDate, String content) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.content = content;
    }

    public String getStartDate() { return startDate; }
    public String getEndDate() { return endDate; }
    public String getContent() { return content; }

    public String getReportDetails() {
        return """
               Sales Report
               Period: """ + startDate + " to " + endDate + "\n";
    }
}