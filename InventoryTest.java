import java.util.Scanner;

public class InventoryTest {
    public static void main(String[] args) {
        InventoryManager inventoryManager = new InventoryManager(); // Create an instance of InventoryManager
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the Inventory Management Test Program!");

        while (true) {
            System.out.println("\nChoose an option:");
            System.out.println("1. Add Inventory Item");
            System.out.println("2. Display All Inventory Items");
            System.out.println("3. Edit/Update Inventory Item");
            System.out.println("4. Restock an Item");
            System.out.println("5. Remove Inventory Item");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    // Add a new inventory item
                    String itemID = inventoryManager.generateNewID(); // Auto-generate the ID

                    System.out.print("Enter Item Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter Quantity: ");
                    int quantity = scanner.nextInt();

                    System.out.print("Enter Unit Cost: ");
                    double unitCost = scanner.nextDouble();

                    System.out.print("Enter Threshold Quantity: ");
                    int threshold = scanner.nextInt();
                    scanner.nextLine(); // Consume the newline character

                    InventoryItem newItem = new InventoryItem(itemID, name, quantity, unitCost, threshold);
                    inventoryManager.addItem(newItem);

                    System.out.println("Item added successfully with ID: " + itemID);
                    break;

                case 2:
                    // Display all inventory items
                    if (inventoryManager.getAllItems().isEmpty()) {
                        System.out.println("No items in the inventory.");
                    } else {
                        System.out.println("Inventory Items:");
                        inventoryManager.displayAllItems();
                    }
                    break;

                case 3:
                    // Edit/Update an inventory item
                    System.out.print("Enter the Item ID of the item you want to edit: ");
                    String editItemID = scanner.nextLine();

                    InventoryItem itemToEdit = inventoryManager.findItem(editItemID);
                    if (itemToEdit != null) {
                        System.out.println("Editing item: " + itemToEdit.getName());
                        System.out.println("Enter new details (press Enter to keep the current value):");

                        System.out.print("New Quantity (current: " + itemToEdit.getQuantity() + "): ");
                        String newQuantity = scanner.nextLine();
                        if (!newQuantity.isEmpty()) {
                            itemToEdit.setQuantity(Integer.parseInt(newQuantity));
                        }

                        System.out.print("New Unit Cost (current: " + itemToEdit.getUnitCost() + "): ");
                        String newUnitCost = scanner.nextLine();
                        if (!newUnitCost.isEmpty()) {
                            itemToEdit.setThreshold((int) Double.parseDouble(newUnitCost));
                        }

                        System.out.print("New Threshold Quantity (current: " + itemToEdit.getThreshold() + "): ");
                        String newThreshold = scanner.nextLine();
                        if (!newThreshold.isEmpty()) {
                            itemToEdit.setThreshold(Integer.parseInt(newThreshold));
                        }

                        inventoryManager.saveInventoryToFile(); // Save updated inventory
                        System.out.println("Item updated successfully!");
                    } else {
                        System.out.println("Item not found.");
                    }
                    break;

                case 4:
                    // Restock an existing item
                    System.out.print("Enter the Item ID of the item you want to restock: ");
                    String restockItemID = scanner.nextLine();

                    InventoryItem itemToRestock = inventoryManager.findItem(restockItemID);
                    if (itemToRestock != null) {
                        System.out.print("Enter the quantity to add (current: " + itemToRestock.getQuantity() + "): ");
                        int additionalQuantity = scanner.nextInt();
                        scanner.nextLine(); // Consume the newline character

                        if (additionalQuantity > 0) {
                            itemToRestock.setQuantity(itemToRestock.getQuantity() + additionalQuantity);
                            inventoryManager.saveInventoryToFile(); // Save updated inventory
                            System.out.println("Item restocked successfully! New quantity: " + itemToRestock.getQuantity());
                        } else {
                            System.out.println("Invalid quantity. Restocking cancelled.");
                        }
                    } else {
                        System.out.println("Item not found.");
                    }
                    break;

                case 5:
                    // Remove an inventory item
                    System.out.print("Enter the Item ID of the item you want to remove: ");
                    String removeItemID = scanner.nextLine();

                    InventoryItem itemToRemove = inventoryManager.findItem(removeItemID);
                    if (itemToRemove != null) {
                        inventoryManager.removeItem(removeItemID);
                        System.out.println("Item removed successfully!");
                    } else {
                        System.out.println("Item not found.");
                    }
                    break;

                case 6:
                    System.out.println("Exiting the program. Goodbye!");
                    scanner.close();
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
}