public class Supplier {
    private String supplierID;
    private String name;
    private String phoneNumber;
    private String email;

    // Constructor
    public Supplier(String supplierID, String name, String phoneNumber, String email) {
        this.supplierID = supplierID;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }

    // Getter methods
    public String getSupplierID() {
        return supplierID;
    }

    public String getName() {
        return name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    // Setter methods
    public void setName(String name) {
        this.name = name;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    // Method to get supplier details in a formatted way
    public String getSupplierDetails() {
        return "Supplier ID: " + supplierID + "\nName: " + name + "\nPhone: " + phoneNumber + "\nEmail: " + email;
    }
}
