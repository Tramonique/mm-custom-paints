import java.util.List;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private JFrame mainFrame;
    private SupplierManager supplierManager; // Instance of SupplierManager
    private SalesManager salesManager; // Instance of SalesManager
    private AlertManager alertManager; // Instance of AlertManager
    private ReportManager reportManager;

    public Main() {
        // Initialize SupplierManager
        supplierManager = new SupplierManager();

        // Initialize SalesManager
        InventoryManager inventoryManager = new InventoryManager(); // Assuming InventoryManager is already implemented
        salesManager = new SalesManager(inventoryManager); // Pass InventoryManager to SalesManager

        // Initialize AlertManager
        alertManager = new AlertManager();

        // Call AlertManager to monitor inventory
        alertManager.monitorInventory(inventoryManager); // This function call triggers inventory monitoring for alerts

        reportManager = new ReportManager();
    }

    public void launch() {
        // Label for the image and text
        JLabel label = new JLabel();
        label.setText("Welcome to MM Custom Paints!");
        ImageIcon image = new ImageIcon("mmlogo.jpeg");
        Image img = image.getImage();
        Image resizedImage = img.getScaledInstance(400, 300, Image.SCALE_SMOOTH);
        image = new ImageIcon(resizedImage);
        label.setIcon(image);
        label.setHorizontalTextPosition(JLabel.CENTER);
        label.setVerticalTextPosition(JLabel.TOP);
        label.setForeground(new Color(0x123456));
        label.setFont(new Font("MV Boli", Font.PLAIN, 20));

        // Center the label within its parent container
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setVerticalAlignment(SwingConstants.TOP);

        // Hamburger Menu (three lines button)
        JButton hamburgerButton = new JButton("≡");
        hamburgerButton.setFont(new Font("Arial", Font.PLAIN, 30));
        hamburgerButton.setBackground(Color.decode("#EEACAC")); // Set background to match the window
        hamburgerButton.setForeground(new Color(0xD5006D)); // Dark pink color
        hamburgerButton.setBorder(BorderFactory.createEmptyBorder());
        hamburgerButton.setFocusPainted(false);

        // Create a PopupMenu for the Hamburger menu
        JPopupMenu menu = new JPopupMenu();
        JMenuItem item1 = new JMenuItem("Manage Inventory");
        JMenuItem item2 = new JMenuItem("View Alerts");
        JMenuItem item3 = new JMenuItem("Manage Suppliers");
        JMenuItem item4 = new JMenuItem("Log Sales");
        JMenuItem item5 = new JMenuItem("Generate Reports");

        menu.add(item1);
        menu.add(item2);
        menu.add(item3);
        menu.add(item4);
        menu.add(item5);

        // Show the menu when hamburger button is clicked
        hamburgerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.show(hamburgerButton, 0, hamburgerButton.getHeight());
            }
        });

        // Action listener for menu items
        item1.addActionListener(e -> openInventoryManagementSystem()); // Open Inventory Management
        item2.addActionListener(e -> openViewAlertsWindow());
        item3.addActionListener(e -> openSupplierManagementSystem()); // Open Supplier Management
        item4.addActionListener(e -> openSalesLoggingWindow()); // Open Sales Logging
        item5.addActionListener(e -> openGenerateReportsDialog());

        // Panel to center hamburger button in the frame
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Use FlowLayout to align left
        topPanel.setBackground(Color.decode("#EEACAC"));
        topPanel.add(hamburgerButton);

        // Logout button in the bottom-right corner
        JButton logoutButton = new JButton("Logout");
        logoutButton.setBackground(new Color(0xD5006D)); // Dark pink
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 14));
        logoutButton.setOpaque(true);
        logoutButton.setBorderPainted(false);
        logoutButton.addActionListener(e -> {
            mainFrame.dispose(); // Close the main frame
            LoginScreen loginScreen = new LoginScreen();
            loginScreen.launch(); // Relaunch the login screen
        });

        // Panel for the bottom-right corner
        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.decode("#EEACAC")); // Set new background color
        bottomPanel.add(logoutButton, BorderLayout.EAST); // Add the logout button to the right

        // Main frame
        mainFrame = new JFrame();
        mainFrame.setTitle("MM Custom Paints");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setResizable(false);
        mainFrame.setSize(500, 500);

        // Set layout manager for the frame
        mainFrame.setLayout(new BorderLayout());
        mainFrame.getContentPane().setBackground(Color.decode("#EEACAC")); // Set new frame background color
        mainFrame.add(label, BorderLayout.CENTER); // Add label to the center (to show the image)
        mainFrame.add(topPanel, BorderLayout.NORTH); // Add the hamburger menu to the top-left
        mainFrame.add(bottomPanel, BorderLayout.SOUTH); // Add bottom panel for logout button

        mainFrame.setVisible(true);
    }

    private void openGenerateReportsDialog() {
        // Create input fields for date range and report type
        JTextField startDateField = new JTextField(10);
        JTextField endDateField = new JTextField(10);
        JComboBox<String> reportTypeComboBox = new JComboBox<>(
                new String[] { "Sales Report" });

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Start Date (YYYY-MM-DD):"));
        panel.add(startDateField);
        panel.add(new JLabel("End Date (YYYY-MM-DD):"));
        panel.add(endDateField);
        panel.add(new JLabel("Report Type:"));
        panel.add(reportTypeComboBox);

        int option = JOptionPane.showConfirmDialog(null, panel, "Generate Report", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String startDate = startDateField.getText();
            String endDate = endDateField.getText();
            String reportType = (String) reportTypeComboBox.getSelectedItem();

            // Get sales data from SalesManager
            List<SalesRecord> salesData = salesManager.getSalesData(); // Assuming you have a method to get sales data

            // Generate the report based on the selected type
            if (reportType.equals("Sales Report")) {
                Report report = reportManager.generateSalesReport(reportType, startDate, endDate, salesData);
                // Optionally, display or export the report
                openReportWindow(report); // Display report in console
            } else {
                JOptionPane.showMessageDialog(null, "Report type not supported yet.");
            }
        }
    }

    private void openReportWindow(Report report) {
        // Create a JTextArea to display the content of the report
        JTextArea reportArea = new JTextArea(report.getContent());
        reportArea.setEditable(false); // Make the text area non-editable

        // Set the background and text color
        reportArea.setBackground(Color.decode("#EEACAC"));
        reportArea.setForeground(Color.BLACK);

        // Wrap the JTextArea in a JScrollPane for scrolling functionality
        JScrollPane scrollPane = new JScrollPane(reportArea);

        // Create the JFrame for the report window
        JFrame reportFrame = new JFrame("Generated Report");
        reportFrame.setSize(600, 400);
        reportFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Add the scroll pane containing the report area to the center of the frame
        reportFrame.add(scrollPane, BorderLayout.CENTER);

        // Set the report frame to be visible
        reportFrame.setVisible(true);
    }

    private void openSalesLoggingWindow() {
        // Sales logging window
        JFrame salesFrame = new JFrame("Sales Logging");
        salesFrame.setSize(400, 150);
        salesFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Fields for sale input
        JTextField productIdField = new JTextField(10);
        JTextField quantityField = new JTextField(10);
        JTextField amountField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(4, 2));
        panel.add(new JLabel("Item ID:"));
        panel.add(productIdField);
        panel.add(new JLabel("Quantity:"));
        panel.add(quantityField);
        panel.add(new JLabel("Amount:"));
        panel.add(amountField);
        panel.setBackground(new Color(0xEEACAC));

        // Create a button for submitting the sale
        JButton logSaleButton = new JButton("Log Sale");
        logSaleButton.addActionListener(e -> {
            try {
                String productId = productIdField.getText();
                int quantity = Integer.parseInt(quantityField.getText());
                double amount = Double.parseDouble(amountField.getText());

                // Call SalesManager to log the sale
                salesManager.logSale(productId, quantity, amount);
                JOptionPane.showMessageDialog(salesFrame, "Sale logged successfully!", "Success",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(salesFrame, "Please enter valid numeric values for Quantity and Amount.",
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(logSaleButton);

        salesFrame.add(panel);
        salesFrame.setVisible(true); // Keep the sales frame visible while handling sale logging
    }

    // Method to open the inventory management system in a new window
    void openInventoryManagementSystem() {
        // Open the Inventory GUI
        new InventoryGUI(); // This will instantiate and display the Inventory GUI
    }

    // Method to open the supplier management system in a new window
    private void openSupplierManagementSystem() {
        // Create a new frame for the Supplier Management System
        JFrame supplierFrame = new JFrame("Supplier Management");
        supplierFrame.setSize(600, 400);
        supplierFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Example suppliers to add
        // supplierManager.addSupplier(new Supplier("S001", "Supplier A",
        // "123-456-7890", "supplierA@example.com"));
        // supplierManager.addSupplier(new Supplier("S002", "Supplier B",
        // "987-654-3210", "supplierB@example.com"));

        // Get the supplier data as a table
        Object[][] supplierData = supplierManager.getSuppliersAsTable();
        String[] columns = { "Supplier ID", "Name", "Phone", "Email" };

        // Create a JTable with a DefaultTableModel
        DefaultTableModel tableModel = new DefaultTableModel(supplierData, columns);
        JTable supplierTable = new JTable(tableModel);

        // Set the background color for the supplier table
        supplierTable.setBackground(Color.decode("#EEACAC"));
        supplierTable.setForeground(Color.BLACK);

        // Create a JScrollPane and set its background color
        JScrollPane scrollPane = new JScrollPane(supplierTable);
        scrollPane.setBackground(Color.decode("#EEACAC"));
        supplierTable.setGridColor(Color.BLACK); // Set grid color for visibility (optional)

        // Create buttons for adding, removing, editing, and searching suppliers
        JButton addSupplierButton = new JButton("Add Supplier");
        JButton removeSupplierButton = new JButton("Remove Supplier");
        JButton editSupplierButton = new JButton("Edit Supplier");
        JButton searchSupplierButton = new JButton("Search Supplier");

        addSupplierButton.addActionListener(e -> openAddSupplierDialog(tableModel));
        removeSupplierButton.addActionListener(e -> openRemoveSupplierDialog(tableModel));
        editSupplierButton.addActionListener(e -> openEditSupplierDialog(supplierTable, tableModel));
        searchSupplierButton.addActionListener(e -> openSearchSupplierDialog(tableModel));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addSupplierButton);
        buttonPanel.add(removeSupplierButton);
        buttonPanel.add(editSupplierButton);
        buttonPanel.add(searchSupplierButton);

        supplierFrame.add(buttonPanel, BorderLayout.NORTH);
        supplierFrame.add(scrollPane, BorderLayout.CENTER);

        // Set background color for the frame itself
        supplierFrame.getContentPane().setBackground(Color.decode("#EEACAC"));

        supplierFrame.setVisible(true);
    }

    // Method to open the Add Supplier Dialog
    private void openAddSupplierDialog(DefaultTableModel tableModel) {
        JTextField idField = new JTextField(10);
        JTextField nameField = new JTextField(10);
        JTextField phoneField = new JTextField(10);
        JTextField emailField = new JTextField(10);

        JPanel panel = new JPanel(new GridLayout(5, 2));
        panel.add(new JLabel("Supplier ID:"));
        panel.add(idField);
        panel.add(new JLabel("Name:"));
        panel.add(nameField);
        panel.add(new JLabel("Phone:"));
        panel.add(phoneField);
        panel.add(new JLabel("Email:"));
        panel.add(emailField);

        int option = JOptionPane.showConfirmDialog(null, panel, "Add Supplier", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String id = idField.getText();
            String name = nameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();

            Supplier newSupplier = new Supplier(id, name, phone, email);
            supplierManager.addSupplier(newSupplier);
            tableModel.addRow(new Object[] { id, name, phone, email });
        }
    }

    // Method to open the Remove Supplier Dialog
    private void openRemoveSupplierDialog(DefaultTableModel tableModel) {
        JTextField idField = new JTextField(10);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Supplier ID:"));
        panel.add(idField);

        int option = JOptionPane.showConfirmDialog(null, panel, "Remove Supplier", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String supplierId = idField.getText();
            supplierManager.removeSupplier(supplierId);
            updateSupplierTable(tableModel); // Refresh the table after removal
        }
    }

    // Method to update the supplier table after removal
    private void updateSupplierTable(DefaultTableModel tableModel) {
        // Clear the existing data
        tableModel.setRowCount(0);
        // Add updated supplier data
        Object[][] supplierData = supplierManager.getSuppliersAsTable();
        for (Object[] row : supplierData) {
            tableModel.addRow(row);
        }
    }

    // Method to open the Edit Supplier Dialog
    private void openEditSupplierDialog(JTable supplierTable, DefaultTableModel tableModel) {
        int selectedRow = supplierTable.getSelectedRow();
        if (selectedRow != -1) {
            String supplierId = (String) supplierTable.getValueAt(selectedRow, 0);
            String supplierName = (String) supplierTable.getValueAt(selectedRow, 1);
            String supplierPhone = (String) supplierTable.getValueAt(selectedRow, 2);
            String supplierEmail = (String) supplierTable.getValueAt(selectedRow, 3);

            JTextField idField = new JTextField(supplierId);
            JTextField nameField = new JTextField(supplierName);
            JTextField phoneField = new JTextField(supplierPhone);
            JTextField emailField = new JTextField(supplierEmail);

            JPanel panel = new JPanel(new GridLayout(5, 2));
            panel.add(new JLabel("Supplier ID:"));
            panel.add(idField);
            panel.add(new JLabel("Name:"));
            panel.add(nameField);
            panel.add(new JLabel("Phone:"));
            panel.add(phoneField);
            panel.add(new JLabel("Email:"));
            panel.add(emailField);

            int option = JOptionPane.showConfirmDialog(null, panel, "Edit Supplier", JOptionPane.OK_CANCEL_OPTION);
            if (option == JOptionPane.OK_OPTION) {
                String newId = idField.getText();
                String newName = nameField.getText();
                String newPhone = phoneField.getText();
                String newEmail = emailField.getText();

                Supplier editedSupplier = new Supplier(newId, newName, newPhone, newEmail);
                supplierManager.editSupplier(supplierId, editedSupplier); // Edit the supplier in the manager
                updateSupplierTable(tableModel); // Refresh the table after editing
            }
        } else {
            JOptionPane.showMessageDialog(null, "Please select a supplier to edit.");
        }
    }

    // Method to open the Search Supplier Dialog
    // Method to open the Search Supplier Dialog
    private void openSearchSupplierDialog(DefaultTableModel tableModel) {
        JTextField searchField = new JTextField(15);
        JPanel panel = new JPanel();
        panel.add(new JLabel("Enter Supplier ID or Name:"));
        panel.add(searchField);

        int option = JOptionPane.showConfirmDialog(null, panel, "Search Supplier", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String searchTerm = searchField.getText().toLowerCase();

            // Clear existing table data
            tableModel.setRowCount(0);

            // Get the matching suppliers based on search term
            Object[][] searchResults = supplierManager.searchSupplier(searchTerm); // This should return matching
                                                                                   // suppliers only

            // Add the search results to the table
            if (searchResults.length > 0) {
                for (Object[] row : searchResults) {
                    tableModel.addRow(row);
                }
            } else {
                // If no match, display a message in the table (optional)
                tableModel.addRow(new Object[] { "No matching supplier found", "", "", "" });
            }
        }
    }

    private void openViewAlertsWindow() {
        JFrame alertsFrame = new JFrame("View Alerts");
        alertsFrame.setSize(600, 400);
        alertsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Create JTable with DefaultTableModel
        String[] columnNames = { "Alert ID", "Item", "Quantity", "Threshold", "Status" };
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable alertTable = new JTable(tableModel);

        // Set background color for the alert table
        alertTable.setBackground(Color.decode("#EEACAC"));
        alertTable.setForeground(Color.BLACK); // Set the text color to black for readability

        // Populate the table with active alerts from AlertManager
        alertManager.displayActiveAlertsInTable(alertTable);

        JScrollPane scrollPane = new JScrollPane(alertTable);
        alertsFrame.add(scrollPane, BorderLayout.CENTER);

        // Set frame visible
        alertsFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Main mainApp = new Main();
            mainApp.launch();
        });
    }
}
