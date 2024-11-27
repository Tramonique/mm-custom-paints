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
