import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private JFrame mainFrame;
    private SupplierManager supplierManager; // Instance of SupplierManager
    private SalesManager salesManager; // Instance of SalesManager
    private AlertManager alertManager;
    private InventoryManager inventoryManager;

    public Main() {
        // Initialize SupplierManager
        supplierManager = new SupplierManager();

        // Initialize SalesManager
        inventoryManager = new InventoryManager(); // Assuming InventoryManager is already implemented
        salesManager = new SalesManager(inventoryManager); // Pass InventoryManager to SalesManager
        alertManager = new AlertManager();
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
        item2.addActionListener(e -> {
            StringBuilder alerts = new StringBuilder("Active Alerts:\n\n");
            alertManager.monitorInventory(inventoryManager);
            alertManager.displayActiveAlerts();
        
            for (StockAlert alert : alertManager.getActiveAlertsList()) {
                alerts.append(alert.getAlertDetails()).append("\n--------------------\n");
            }
        
            if (alerts.toString().trim().isEmpty()) {
                alerts.append("No active alerts.");
            }
        
            JTextArea alertTextArea = new JTextArea(alerts.toString());
            alertTextArea.setEditable(false);
            JScrollPane scrollPane = new JScrollPane(alertTextArea);
        
            JOptionPane.showMessageDialog(null, scrollPane, "Active Alerts", JOptionPane.INFORMATION_MESSAGE);
        });
        item3.addActionListener(e -> openSupplierManagementSystem()); // Open Supplier Management
        item4.addActionListener(e -> openSalesLoggingWindow()); // Open Sales Logging
        item5.addActionListener(e -> JOptionPane.showMessageDialog(null, "Generating Reports..."));

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

    private Object openSalesLoggingWindow() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'openSalesLoggingWindow'");
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
        supplierManager.addSupplier(new Supplier("S001", "Supplier A", "123-456-7890", "supplierA@example.com"));
        supplierManager.addSupplier(new Supplier("S002", "Supplier B", "987-654-3210", "supplierB@example.com"));

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

        // Create buttons for adding and removing suppliers
        JButton addSupplierButton = new JButton("Add Supplier");
        JButton removeSupplierButton = new JButton("Remove Supplier");

        addSupplierButton.addActionListener(e -> openAddSupplierDialog(tableModel));
        removeSupplierButton.addActionListener(e -> openRemoveSupplierDialog(tableModel));

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addSupplierButton);
        buttonPanel.add(removeSupplierButton);

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
            String supplierID = idField.getText();
            String name = nameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();

            // Add new supplier to the SupplierManager
            Supplier supplier = new Supplier(supplierID, name, phone, email);
            supplierManager.addSupplier(supplier);

            // Refresh the table with the updated supplier data
            Object[][] updatedData = supplierManager.getSuppliersAsTable();
            // Clear existing rows
            tableModel.setRowCount(0);
            // Add the new rows from the updated data
            for (Object[] row : updatedData) {
                tableModel.addRow(row);
            }
        }
    }

    // Method to open the Remove Supplier Dialog
    private void openRemoveSupplierDialog(DefaultTableModel tableModel) {
        String supplierID = JOptionPane.showInputDialog("Enter Supplier ID to Remove:");
        if (supplierID != null) {
            supplierManager.removeSupplier(supplierID);

            // Refresh the table after removing the supplier
            Object[][] updatedData = supplierManager.getSuppliersAsTable();
            // Clear existing rows
            tableModel.setRowCount(0);
            // Add the new rows from the updated data
            for (Object[] row : updatedData) {
                tableModel.addRow(row);
            }
        }
    }
}

