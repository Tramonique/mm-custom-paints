import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

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
        if (supplierList.isEmpty()) {
            System.out.println("No suppliers to display.");
            return;
        }
        for (Supplier supplier : supplierList) {
            System.out.println(supplier.getSupplierDetails());
            System.out.println("--------------------");
        }
    }

    // Write suppliers to a CSV file (overwrite mode)
    public void writeSuppliersToCSV(String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename, false))) {
            // Write CSV header
            writer.write("Supplier ID,Name,Phone,Email\n");

            // Write supplier data
            for (Supplier supplier : supplierList) {
                writer.write(String.format("%s,%s,%s,%s\n",
                        supplier.getSupplierID(),
                        supplier.getName(),
                        supplier.getPhoneNumber(),
                        supplier.getEmail()));
            }

            System.out.println("Suppliers have been successfully saved to " + filename);
        } catch (IOException e) {
            System.out.println("An error occurred while writing to the CSV file.");
            e.printStackTrace();
        }
    }

    // Load suppliers from a CSV file
    public void loadSuppliersFromCSV(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            reader.readLine(); // Skip the header line

            while ((line = reader.readLine()) != null) {
                String[] details = line.split(",");
                String id = details[0];
                String name = details[1];
                String phone = details[2];
                String email = details[3];

                Supplier supplier = new Supplier(id, name, phone, email);
                supplierList.add(supplier);
            }
            System.out.println("Suppliers loaded from " + filename);
        } catch (FileNotFoundException e) {
            System.out.println("No existing CSV file found. Starting fresh.");
        } catch (IOException e) {
            System.out.println("Error reading CSV file.");
            e.printStackTrace();
        }
    }

    // Main method
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        SupplierManager manager = new SupplierManager();
        String filename = "supplier.csv";

        // Load suppliers from CSV at the start of the program
        manager.loadSuppliersFromCSV(filename);

        while (true) {
            // Menu for interaction
            System.out.println("Supplier Management System");
            System.out.println("1. Add Supplier");
            System.out.println("2. Remove Supplier");
            System.out.println("3. View All Suppliers");
            System.out.println("4. Search Supplier by Name");
            System.out.println("5. Find Supplier by ID");
            System.out.println("6. Save Suppliers to CSV");
            System.out.println("7. Update Supplier Information");
            System.out.println("8. Exit");
            System.out.print("Choose an option: ");
            
            int option = scanner.nextInt();
            scanner.nextLine();  // Consume newline

            switch (option) {
                case 1:
                    // Add supplier
                    System.out.println("Enter Supplier details:");
                    System.out.print("Enter Supplier ID: ");
                    String id = scanner.nextLine();
                    System.out.print("Enter Supplier Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Supplier Phone (remaining 7 digits, e.g., 1234567): ");
                    String phone = "(876)" + scanner.nextLine();
                    System.out.print("Enter Supplier Email: ");
                    String email = scanner.nextLine();

                    Supplier supplier = new Supplier(id, name, phone, email);
                    manager.addSupplier(supplier);
                    System.out.println("Supplier added successfully.");
                    break;

                case 2:
                    // Remove supplier
                    System.out.print("Enter Supplier ID to remove: ");
                    String removeID = scanner.nextLine();
                    Supplier supplierToRemove = manager.findSupplier(removeID);
                    if (supplierToRemove == null) {
                        System.out.println("Error: No supplier found with ID: " + removeID);
                    } else {
                        manager.removeSupplier(removeID);
                        System.out.println("Supplier removed successfully.");
                    }
                    break;

                case 3:
                    // View all suppliers
                    manager.viewAllSuppliers();
                    break;

                case 4:
                    // Search suppliers by name
                    System.out.print("Enter supplier name to search: ");
                    String searchName = scanner.nextLine();
                    ArrayList<Supplier> results = manager.searchSuppliersByName(searchName);
                    if (results.isEmpty()) {
                        System.out.println("Error: No suppliers found with name containing: \"" + searchName + "\"");
                    } else {
                        for (Supplier result : results) {
                            System.out.println(result.getSupplierDetails());
                        }
                    }
                    break;

                case 5:
                    // Find supplier by ID
                    System.out.print("Enter Supplier ID to find: ");
                    String findID = scanner.nextLine();
                    Supplier foundSupplier = manager.findSupplier(findID);
                    if (foundSupplier == null) {
                        System.out.println("Error: No supplier found with ID: " + findID);
                    } else {
                        System.out.println(foundSupplier.getSupplierDetails());
                    }
                    break;

                case 6:
                    // Save suppliers to CSV
                    manager.writeSuppliersToCSV(filename);
                    break;

                    case 7:
                    // Update supplier information
                    System.out.print("Enter Supplier ID to update: ");
                    String updateID = scanner.nextLine();
                    Supplier supplierToUpdate = manager.findSupplier(updateID);
                
                    if (supplierToUpdate == null) {
                        System.out.println("Error: No supplier found with ID: " + updateID);
                    } else {
                        System.out.println("Current Details: ");
                        System.out.println(supplierToUpdate.getSupplierDetails());
                
                        System.out.println("Enter new details (leave blank to keep current values):");
                        System.out.print("Enter new Name: ");
                        String newName = scanner.nextLine();
                        if (!newName.isEmpty()) {
                            supplierToUpdate.setName(newName);
                        }
                
                        System.out.print("Enter new Phone: ");
                        String newPhone = scanner.nextLine();
                        if (!newPhone.isEmpty()) {
                            supplierToUpdate.setPhoneNumber("(876)" + newPhone);
                        }
                
                        System.out.print("Enter new Email: ");
                        String newEmail = scanner.nextLine();
                        if (!newEmail.isEmpty()) {
                            supplierToUpdate.setEmail(newEmail);
                        }
                
                        System.out.println("Supplier details updated successfully.");
                    }
                    break;

                case 8:
                    // Exit the application
                    System.out.println("Exiting...");
                    manager.writeSuppliersToCSV(filename); // Save before exiting
                    scanner.close();
                    System.exit(0);

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
