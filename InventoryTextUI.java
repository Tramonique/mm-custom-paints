import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class InventoryTextUI {
    public static void main(String[] args) {
        // Initialize InventoryManager and SalesManager
        InventoryManager inventoryManager = new InventoryManager();
        SalesManager salesManager = new SalesManager(inventoryManager);  // Pass InventoryManager to SalesManager
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Inventory Management System!");

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add Item");
            System.out.println("2. Display Items");
            System.out.println("3. Display Item Details");
            System.out.println("4. Restock Item");
            System.out.println("5. Edit Item");
            System.out.println("6. Delete Item");
            System.out.println("7. Log a Sale");
            System.out.println("8. Display All Sales");
            System.out.println("9. Delete a Sale");
            System.out.println("10. Exit"); 

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1 -> {
                    // Add Item
                    System.out.print("Enter Item Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter Unit Cost: ");
                    double unitCost = scanner.nextDouble();

                    System.out.print("Enter Threshold: ");
                    double threshold = scanner.nextDouble();

                    System.out.print("Enter Initial Quantity: ");
                    double quantity = scanner.nextDouble();
                    scanner.nextLine(); // Consume newline

                    System.out.print("Does this item require splitting into sub-units? (yes/no): ");
                    String hasSplitRatio = scanner.nextLine();

                    int splitRatio = 1; // Default: no splitting
                    if (hasSplitRatio.equalsIgnoreCase("yes")) {
                        System.out.print("Enter the number of sub-units per bulk unit: ");
                        splitRatio = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                    }

                    System.out.print("Does this item have a formula? (yes/no): ");
                    String hasFormula = scanner.nextLine();

                    Map<String, Double> formula = null;
                    if (hasFormula.equalsIgnoreCase("yes")) {
                        formula = new HashMap<>();
                        while (true) {
                            System.out.print("Enter raw material ID (or 'done' to finish): ");
                            String rawMaterialID = scanner.nextLine();
                            if (rawMaterialID.equalsIgnoreCase("done")) {
                                break;
                            }

                            InventoryItem rawMaterial = inventoryManager.findItem(rawMaterialID);
                            if (rawMaterial == null) {
                                System.out.println("Raw material not found. Please try again.");
                                continue;
                            }

                            System.out.print("Enter quantity required in sub-units (available: " + rawMaterial.getAvailableSubUnits() + "): ");
                            double requiredSubUnits = scanner.nextDouble();
                            scanner.nextLine(); // Consume newline

                            formula.put(rawMaterialID, requiredSubUnits);
                        }
                    }

                    boolean added = inventoryManager.addItem(name, quantity, unitCost, threshold, formula, splitRatio);
                    if (added) {
                        System.out.println(name + " added successfully!");
                    } else {
                        System.out.println("Failed to add " + name + " due to insufficient raw materials.");
                    }
                    break;
                }

                case 2 -> {
                    // Display Items
                    if (inventoryManager.getAllItems().isEmpty()) {
                        System.out.println("No items in the inventory.");
                    } else {
                        inventoryManager.displayAllItems();
                    }
                    break;
                }

                case 3 -> {
                    // Display item details
                    System.out.print("Enter the Item ID to display details: ");
                    String detailItemID = scanner.nextLine();

                    InventoryItem itemToDisplay = inventoryManager.findItem(detailItemID);
                    if (itemToDisplay != null) {
                        System.out.println("\nItem Details:");
                        System.out.println("ID: " + itemToDisplay.getItemID());
                        System.out.println("Name: " + itemToDisplay.getName());
                        System.out.println("Quantity: " + itemToDisplay.getQuantity());
                        System.out.println("Unit Cost: " + itemToDisplay.getUnitCost());
                        System.out.println("Threshold: " + itemToDisplay.getThreshold());
                        if (itemToDisplay.getFormula() != null) {
                            System.out.println("Formula:");
                            itemToDisplay.getFormula().forEach((rawMaterialID, quantity) -> 
                                System.out.println("  - Raw Material ID: " + rawMaterialID + ", Quantity: " + quantity));
                        } else {
                            System.out.println("Formula: None");
                        }
                    } else {
                        System.out.println("Item not found.");
                    }
                    break;
                }

                case 4 -> {
                    // Restock Item
                    System.out.print("Enter Item ID to restock: ");
                    String itemID = scanner.nextLine();

                    System.out.print("Enter quantity to add: ");
                    double restockQuantity = scanner.nextDouble();

                    boolean restocked = inventoryManager.restockItem(itemID, restockQuantity);
                    if (restocked) {
                        System.out.println("Item restocked successfully!");
                    } else {
                        System.out.println("Failed to restock item due to insufficient raw materials.");
                    }
                    break;
                }

                case 5 -> {
                    // Edit Item
                    System.out.print("Enter the Item ID to edit: ");
                    String editItemID = scanner.nextLine();

                    InventoryItem itemToEdit = inventoryManager.findItem(editItemID);
                    if (itemToEdit != null) {
                        System.out.println("Editing item: " + itemToEdit.getName());
                        System.out.println("Press Enter to skip a field.");

                        System.out.print("New Name (current: " + itemToEdit.getName() + "): ");
                        String newName = scanner.nextLine();
                        if (!newName.isEmpty()) {
                            itemToEdit.setName(newName);
                        }

                        System.out.print("New Unit Cost (current: " + itemToEdit.getUnitCost() + "): ");
                        String newUnitCost = scanner.nextLine();
                        if (!newUnitCost.isEmpty()) {
                            itemToEdit.setUnitCost(Double.parseDouble(newUnitCost));
                        }

                        System.out.print("New Threshold (current: " + itemToEdit.getThreshold() + "): ");
                        String newThreshold = scanner.nextLine();
                        if (!newThreshold.isEmpty()) {
                            itemToEdit.setThreshold(Double.parseDouble(newThreshold));
                        }

                        System.out.print("New Split Ratio (current: " + itemToEdit.getSplitRatio() + ", or press Enter to skip): ");
                        String newSplitRatio = scanner.nextLine();
                        if (!newSplitRatio.isEmpty()) {
                            itemToEdit.setSplitRatio(Integer.parseInt(newSplitRatio));
                        }

                        System.out.print("Do you want to edit the formula? (yes/no): ");
                        String editFormula = scanner.nextLine();
                        if (editFormula.equalsIgnoreCase("yes")) {
                            Map<String, Double> newFormula = new HashMap<>();
                            while (true) {
                                System.out.print("Enter raw material ID (or 'done' to finish): ");
                                String rawMaterialID = scanner.nextLine();
                                if (rawMaterialID.equalsIgnoreCase("done")) {
                                    break;
                                }

                                InventoryItem rawMaterial = inventoryManager.findItem(rawMaterialID);
                                if (rawMaterial == null) {
                                    System.out.println("Raw material not found. Please try again.");
                                    continue;
                                }

                                System.out.print("Enter quantity required in sub-units (available: " + rawMaterial.getAvailableSubUnits() + "): ");
                                double requiredSubUnits = scanner.nextDouble();
                                scanner.nextLine(); // Consume newline

                                newFormula.put(rawMaterialID, requiredSubUnits);
                            }
                            itemToEdit.setFormula(newFormula);
                        }

                        inventoryManager.saveInventoryToFile(); // Save changes
                        System.out.println("Item updated successfully.");
                    } else {
                        System.out.println("Item not found.");
                    }
                    break;
                }

                case 6 -> {
                    // Delete Item
                    System.out.print("Enter the Item ID to delete: ");
                    String deleteItemID = scanner.nextLine();

                    boolean deleted = inventoryManager.removeItem(deleteItemID);
                    if (deleted) {
                        System.out.println("Item deleted successfully.");
                    } else {
                        System.out.println("Item not found or could not be deleted.");
                    }
                    break;
                }

                case 7 -> {
                    // Log a Sale
                    System.out.print("Enter Product Item ID: ");
                    String itemID = scanner.nextLine();

                    InventoryItem item = inventoryManager.findItem(itemID);

                    if (item != null) {
                        System.out.print("Enter Quantity Sold: ");
                        int quantitySold = scanner.nextInt();

                        System.out.print("Enter Total Sale Amount: ");
                        double totalAmount = scanner.nextDouble();

                        salesManager.logSale(item.getItemID(), quantitySold, totalAmount);
                    } else {
                        System.out.println("Item not found.");
                    }
                    break;
                }

                case 8 -> {
                    // Display All Sales
                    salesManager.viewAllSales();
                    break;
                }
                case 9 -> {
                    // Remove a Sale
                    System.out.print("Enter Sale ID to delete: ");
                    String saleID = scanner.nextLine();
                    salesManager.removeSale(saleID);
                    break;
                }

                case 10 -> {
                    System.out.println("Exiting. Goodbye!");
                    scanner.close();
                    return;
                }

                default -> System.out.println("Invalid choice. Try again.");
            }
        }
    }
}