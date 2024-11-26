import java.util.ArrayList;

class SupplierManager {
    private ArrayList<Supplier> supplierList;  // List of suppliers

    // Constructor
    public SupplierManager() {
        this.supplierList = new ArrayList<>();
    }

    // Add a supplier
    public void addSupplier(Supplier supplier) {
        supplierList.add(supplier);
    }

    // Remove a supplier by ID
    public void removeSupplier(String supplierID) {
        supplierList.removeIf(supplier -> supplier.getSupplierID().equals(supplierID));
    }

    // Find a supplier by ID
    public Supplier findSupplier(String supplierID) {
        for (Supplier supplier : supplierList) {
            if (supplier.getSupplierID().equals(supplierID)) {
                return supplier;
            }
        }
        return null;  // Supplier not found
    }

    // Search suppliers by name (case insensitive)
    public ArrayList<Supplier> searchSuppliersByName(String name) {
        ArrayList<Supplier> result = new ArrayList<>();
        for (Supplier supplier : supplierList) {
            if (supplier.getName().toLowerCase().contains(name.toLowerCase())) {
                result.add(supplier);
            }
        }
        return result;
    }

    // Display all suppliers
    public void viewAllSuppliers() {
        for (Supplier supplier : supplierList) {
            System.out.println(supplier.getSupplierDetails());
            System.out.println("--------------------");
        }
    }
}