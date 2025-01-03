public class SalesRecord {
    private final String saleID; // Unique identifier for the sale
    private final String date; // Date of the sale (format: YYYY-MM-DD)
    private final String productID; // ID of the product sold
    private int quantitySold; // Quantity of the product sold
    private double totalAmount; // Total amount of the sale

    // Constructor
    public SalesRecord(String saleID, String date, String productID, int quantitySold, double totalAmount) {
        this.saleID = saleID;
        this.date = date;
        this.productID = productID;
        setQuantitySold(quantitySold); // Using setter for validation
        setTotalAmount(totalAmount); // Using setter for validation
    }

    // Getters
    public String getSaleID() {
        return saleID;
    }

    public String getDate() {
        return date;
    }

    public String getProductID() {
        return productID;
    }

    public int getQuantitySold() {
        return quantitySold;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    // Setters
    public void setQuantitySold(int quantitySold) {
        if (quantitySold < 0) {
            throw new IllegalArgumentException("Quantity sold cannot be negative.");
        }
        this.quantitySold = quantitySold;
    }

    public void setTotalAmount(double totalAmount) {
        if (totalAmount < 0) {
            throw new IllegalArgumentException("Total amount cannot be negative.");
        }
        this.totalAmount = totalAmount;
    }

    @Override
    public String toString() {
        return String.format("Sale ID: %s\nDate: %s\nProduct ID: %s\nQuantity Sold: %d\nTotal Amount: %.2f",
                saleID, date, productID, quantitySold, totalAmount);
    }
}