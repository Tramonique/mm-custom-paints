import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class InventoryGUI {

    private JFrame frame;
    private InventoryManager inventoryManager;
    private JTextArea outputArea;

    private JTextField nameField, unitCostField, thresholdField, quantityField, hasSplitRatioField;
    private JTextField restockItemIDField, restockQuantityField;
    private JTextField itemIDField, editNameField, editUnitCostField, editThresholdField, editQuantityField,
            editSubUnitsField;
    private JTextArea formulaTextArea;

    public InventoryGUI() {
        inventoryManager = new InventoryManager();
        frame = new JFrame("Inventory Management System");
        frame.setSize(600, 500);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create the output area (text display area)
        outputArea = new JTextArea();
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Create the left panel with buttons
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(6, 1)); // 6 buttons, one for each action

        // Add buttons with custom colors
        JButton addButton = new JButton("Add Item");
        JButton displayButton = new JButton("Display Items");
        JButton restockButton = new JButton("Restock Item");
        JButton editButton = new JButton("Edit Item");
        JButton deleteButton = new JButton("Delete Item");
        JButton exitButton = new JButton("Exit");

        // Set button colors
        addButton.setBackground(new Color(0xFF6F61)); // Soft Red
        addButton.setForeground(Color.WHITE);
        displayButton.setBackground(new Color(0xFFB400)); // Orange
        displayButton.setForeground(Color.WHITE);
        restockButton.setBackground(new Color(0x6A0DAD)); // Purple
        restockButton.setForeground(Color.WHITE);
        editButton.setBackground(new Color(0x008CBA)); // Blue
        editButton.setForeground(Color.WHITE);
        deleteButton.setBackground(new Color(0xD5006D)); // Pink
        deleteButton.setForeground(Color.WHITE);
        exitButton.setBackground(new Color(0x4CAF50)); // Green
        exitButton.setForeground(Color.WHITE);

        // Add buttons to the panel
        panel.add(addButton);
        panel.add(displayButton);
        panel.add(restockButton);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(exitButton);

        frame.add(panel, BorderLayout.WEST);

        // Add listeners for buttons
        addButton.addActionListener(e -> openAddItemDialog());
        displayButton.addActionListener(e -> displayAllItems());
        restockButton.addActionListener(e -> openRestockItemDialog());
        editButton.addActionListener(e -> openEditItemDialog());
        deleteButton.addActionListener(e -> openDeleteItemDialog());
        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Launch Main and close InventoryGUI
                Main mainApp = new Main();
                mainApp.launch();
                frame.dispose(); // Close InventoryGUI window
            }
        });

        frame.setVisible(true);
    }

    // Open the "Add Item" dialog
    private void openAddItemDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(8, 2));

        panel.add(new JLabel("Item Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Unit Cost:"));
        unitCostField = new JTextField();
        panel.add(unitCostField);

        panel.add(new JLabel("Threshold:"));
        thresholdField = new JTextField();
        panel.add(thresholdField);

        panel.add(new JLabel("Quantity:"));
        quantityField = new JTextField();
        panel.add(quantityField);

        // "Has Split Ratio?" Field
        panel.add(new JLabel("Has Split Ratio? (yes/no):"));
        hasSplitRatioField = new JTextField();
        panel.add(hasSplitRatioField);

        // Sub-units field (dynamically added)
        JLabel subUnitsLabel = new JLabel("No. of sub-units:");
        JTextField subUnitsField = new JTextField();

        // Initially hidden, added dynamically
        subUnitsLabel.setVisible(false);
        subUnitsField.setVisible(false);

        // Add them to the panel in advance (in hidden state)
        panel.add(subUnitsLabel);
        panel.add(subUnitsField);

        // Add a key listener to show/hide the sub-units field dynamically
        hasSplitRatioField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String input = hasSplitRatioField.getText().trim();
                boolean show = input.equalsIgnoreCase("yes");
                subUnitsLabel.setVisible(show);
                subUnitsField.setVisible(show);
                panel.revalidate();
                panel.repaint();
            }
        });

        // Confirm button for adding the item
        JButton confirmButton = new JButton("Add Item");
        confirmButton.addActionListener(e -> {
            String hasSplitRatioText = hasSplitRatioField.getText().trim();
            int splitRatio = 1;

            if (hasSplitRatioText.equalsIgnoreCase("yes")) {
                try {
                    splitRatio = Integer.parseInt(subUnitsField.getText().trim());
                    outputArea.append("Split ratio set to: " + splitRatio + "\n");
                } catch (NumberFormatException ex) {
                    outputArea.append("Invalid number for sub-units. Please enter a valid integer.\n");
                    return;
                }
            }

            try {
                String name = nameField.getText().trim();
                double unitCost = Double.parseDouble(unitCostField.getText().trim());
                double threshold = Double.parseDouble(thresholdField.getText().trim());
                double quantity = Double.parseDouble(quantityField.getText().trim());

                boolean added = inventoryManager.addItem(name, quantity, unitCost, threshold, null, splitRatio);
                if (added) {
                    outputArea.append(name + " added successfully with a split ratio of " + splitRatio + "!\n");
                } else {
                    outputArea.append("Failed to add " + name + ".\n");
                }
            } catch (NumberFormatException ex) {
                outputArea.append("Invalid input for unit cost, threshold, or quantity. Please enter valid numbers.\n");
            }
        });

        panel.add(confirmButton);

        // Show the dialog
        JOptionPane.showConfirmDialog(frame, panel, "Add New Item", JOptionPane.OK_CANCEL_OPTION);
    }

    // Display all items in the inventory
    private void displayAllItems() {
        outputArea.setText(""); // Clear output area
        if (inventoryManager.getAllItems().isEmpty()) {
            outputArea.append("No items in the inventory.\n");
        } else {
            for (InventoryItem item : inventoryManager.getAllItems()) {
                outputArea.append(item.getItemDetails() + "\n");
            }
        }
    }

    // Open the "Restock Item" dialog
    private void openRestockItemDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 2));

        panel.add(new JLabel("Item ID:"));
        restockItemIDField = new JTextField();
        panel.add(restockItemIDField);

        panel.add(new JLabel("Quantity to Add:"));
        restockQuantityField = new JTextField();
        panel.add(restockQuantityField);

        // Confirm button for restocking item
        JButton confirmButton = new JButton("Restock Item");
        confirmButton.addActionListener(e -> restockItem());
        panel.add(confirmButton);

        // Create dialog window
        JOptionPane.showConfirmDialog(frame, panel, "Restock Item", JOptionPane.OK_CANCEL_OPTION);
    }

    // Restock an item in the inventory
    private void restockItem() {
        String itemID = restockItemIDField.getText();
        double quantity = Double.parseDouble(restockQuantityField.getText());

        boolean restocked = inventoryManager.restockItem(itemID, quantity);
        if (restocked) {
            outputArea.append("Item restocked successfully!\n");
        } else {
            outputArea.append("Failed to restock item due to insufficient raw materials.\n");
        }
    }

    // Open the "Edit Item" dialog
    private void openEditItemDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(7, 2)); // Increased to accommodate sub-units field

        panel.add(new JLabel("Item ID:"));
        itemIDField = new JTextField();
        panel.add(itemIDField);

        panel.add(new JLabel("New Name:"));
        editNameField = new JTextField();
        panel.add(editNameField);

        panel.add(new JLabel("New Unit Cost:"));
        editUnitCostField = new JTextField();
        panel.add(editUnitCostField);

        panel.add(new JLabel("New Threshold:"));
        editThresholdField = new JTextField();
        panel.add(editThresholdField);

        panel.add(new JLabel("New Quantity:"));
        editQuantityField = new JTextField();
        panel.add(editQuantityField);

        // New Sub-units field
        panel.add(new JLabel("New Sub-units:"));
        editSubUnitsField = new JTextField();
        panel.add(editSubUnitsField);

        // Confirm button for editing item
        JButton confirmButton = new JButton("Edit Item");
        confirmButton.addActionListener(e -> editItem());
        panel.add(confirmButton);

        // Create dialog window
        JOptionPane.showConfirmDialog(frame, panel, "Edit Item", JOptionPane.OK_CANCEL_OPTION);
    }

    // Edit an item in the inventory
    private void editItem() {
        String itemID = itemIDField.getText();
        InventoryItem item = inventoryManager.findItem(itemID);
        if (item != null) {
            String newName = editNameField.getText();
            if (!newName.isEmpty()) {
                item.setName(newName);
            }

            String newUnitCost = editUnitCostField.getText();
            if (!newUnitCost.isEmpty()) {
                item.setUnitCost(Double.parseDouble(newUnitCost));
            }

            String newThreshold = editThresholdField.getText();
            if (!newThreshold.isEmpty()) {
                item.setThreshold(Double.parseDouble(newThreshold));
            }

            String newSubUnits = editSubUnitsField.getText();
            if (!newSubUnits.isEmpty()) {
                try {
                    item.setSubUnits(Double.parseDouble(newSubUnits));
                } catch (NumberFormatException ex) {
                    outputArea.append("Invalid number format for sub-units.\n");
                    return;
                }
            }

            outputArea.append(item.getName() + " updated successfully!\n");
        } else {
            outputArea.append("Item not found.\n");
        }
    }

    // Open the "Delete Item" dialog
    private void openDeleteItemDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1));

        panel.add(new JLabel("Item ID to delete:"));
        JTextField deleteItemIDField = new JTextField();
        panel.add(deleteItemIDField);

        JButton confirmButton = new JButton("Delete Item");
        confirmButton.addActionListener(e -> deleteItem(deleteItemIDField.getText()));
        panel.add(confirmButton);

        // Create dialog window
        JOptionPane.showConfirmDialog(frame, panel, "Delete Item", JOptionPane.OK_CANCEL_OPTION);
    }

    // Delete an item in the inventory
    private void deleteItem(String itemID) {
        boolean deleted = inventoryManager.deleteItem(itemID);
        if (deleted) {
            outputArea.append("Item deleted successfully.\n");
        } else {
            outputArea.append("Item not found.\n");
        }
    }

    public static void main(String[] args) {
        new InventoryGUI();
    }
}
