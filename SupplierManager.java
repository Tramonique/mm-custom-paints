import java.util.ArrayList;

public class SupplierManager {
    private ArrayList<Supplier> supplierList; // List of suppliers

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
        return null; // Supplier not found
    }

    // Search suppliers by name or ID (case insensitive)
    public ArrayList<Supplier> searchSuppliersByName(String name) {
        ArrayList<Supplier> result = new ArrayList<>();
        for (Supplier supplier : supplierList) {
            if (supplier.getSupplierID().toLowerCase().contains(name.toLowerCase()) ||
                    supplier.getName().toLowerCase().contains(name.toLowerCase())) {
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

    // This method returns a 2D array with supplier data to be displayed in the GUI
    public Object[][] getSuppliersAsTable() {
        Object[][] tableData = new Object[supplierList.size()][4];
        for (int i = 0; i < supplierList.size(); i++) {
            Supplier supplier = supplierList.get(i);
            tableData[i][0] = supplier.getSupplierID();
            tableData[i][1] = supplier.getName();
            tableData[i][2] = supplier.getPhoneNumber();
            tableData[i][3] = supplier.getEmail();
        }
        return tableData;
    }

    // Search for suppliers by ID or name and return as a 2D array for display
    public Object[][] searchSupplier(String searchTerm) {
        ArrayList<Supplier> matchedSuppliers = new ArrayList<>();

        for (Supplier supplier : supplierList) {
            // Search by Supplier ID or Name (case insensitive)
            if (supplier.getSupplierID().toLowerCase().contains(searchTerm.toLowerCase()) ||
                    supplier.getName().toLowerCase().contains(searchTerm.toLowerCase())) {
                matchedSuppliers.add(supplier);
            }
        }

        // Convert matched suppliers to 2D object array
        Object[][] searchResults = new Object[matchedSuppliers.size()][4];
        for (int i = 0; i < matchedSuppliers.size(); i++) {
            Supplier supplier = matchedSuppliers.get(i);
            searchResults[i][0] = supplier.getSupplierID();
            searchResults[i][1] = supplier.getName();
            searchResults[i][2] = supplier.getPhoneNumber();
            searchResults[i][3] = supplier.getEmail();
        }

        return searchResults; // Return only the matched suppliers
    }

    // Edit a supplier's details
    public boolean editSupplier(String supplierID, Supplier editedSupplier) {
        for (int i = 0; i < supplierList.size(); i++) {
            Supplier existingSupplier = supplierList.get(i);
            if (existingSupplier.getSupplierID().equals(supplierID)) {
                // Update supplier details
                supplierList.set(i, editedSupplier);
                return true; // Supplier updated successfully
            }
        }
        return false; // Supplier not found
    }

    // Get a supplier by ID
    public Supplier getSupplierByID(String supplierID) {
        for (Supplier supplier : supplierList) {
            if (supplier.getSupplierID().equals(supplierID)) {
                return supplier;
            }
        }
        return null; // Supplier not found
    }
}
