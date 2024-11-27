import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GUI {
    private JFrame loginFrame, mainFrame;

    // Method to launch the login screen
    public void launchLoginScreen() {
        // Create the login frame
        loginFrame = new JFrame("Login");
        loginFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        loginFrame.setSize(600, 400); // Increased size for easier view
        loginFrame.setLayout(new GridBagLayout());
        loginFrame.getContentPane().setBackground(Color.decode("#EEACAC")); // Set background color to #EEACAC
        loginFrame.setResizable(false); // Prevent resizing the window

        // GridBagConstraints for positioning components
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Set insets for padding
        gbc.fill = GridBagConstraints.HORIZONTAL; // Allows components to expand horizontally

        // Title Label
        JLabel titleLabel = new JLabel("MM Custom Paints!", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(new Color(0xD5006D)); // Dark pink color

        // Prompt Label
        JLabel promptLabel = new JLabel("Please Enter Username and Password:");
        promptLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        promptLabel.setForeground(new Color(0xD5006D)); // Dark pink color

        // Username Label and Field
        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(20);
        usernameField.setPreferredSize(new Dimension(400, 40));
        usernameField.setBackground(Color.WHITE);
        usernameField.setBorder(BorderFactory.createLineBorder(new Color(0xD5006D), 2)); // Pink border

        // Password Label and Field
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        passwordField.setPreferredSize(new Dimension(400, 40));
        passwordField.setBackground(Color.WHITE);
        passwordField.setBorder(BorderFactory.createLineBorder(new Color(0xD5006D), 2)); // Pink border

        // Login Button
        JButton loginButton = new JButton("Login");
        loginButton.setBackground(new Color(0xD5006D));
        loginButton.setForeground(Color.WHITE);

        // Exit Button
        JButton exitButton = new JButton("Exit");
        exitButton.setBackground(new Color(0xD5006D));
        exitButton.setForeground(Color.WHITE);

        // Positioning components using GridBagLayout
        gbc.gridx = 0;
        gbc.gridy = 0;
        loginFrame.add(titleLabel, gbc); // Title label positioned at the top

        gbc.gridy = 1;
        loginFrame.add(promptLabel, gbc); // Prompt label

        gbc.gridx = 0;
        gbc.gridy = 2;
        loginFrame.add(usernameLabel, gbc); // Username label

        gbc.gridx = 1; // Place the text field next to the label
        loginFrame.add(usernameField, gbc); // Username text field

        gbc.gridx = 0;
        gbc.gridy = 3;
        loginFrame.add(passwordLabel, gbc); // Password label

        gbc.gridx = 1; // Place the text field next to the label
        loginFrame.add(passwordField, gbc); // Password text field

        gbc.gridx = 0; // Move to button positioning
        gbc.gridy = 4;
        gbc.gridwidth = 2; // Make buttons span the width of the frame
        loginFrame.add(loginButton, gbc); // Login button

        gbc.gridy = 5;
        loginFrame.add(exitButton, gbc); // Exit button

        // Action Listener for Login Button
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // Simple authentication for demonstration (you can replace this with your
                // logic)
                if (username.equals("admin") && password.equals("password")) {
                    JOptionPane.showMessageDialog(loginFrame, "Login successful!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                    loginFrame.dispose(); // Close the login window
                    launchMainScreen(); // Launch the main application window
                } else {
                    JOptionPane.showMessageDialog(loginFrame, "Invalid username or password", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    // Clear the text fields
                    usernameField.setText("");
                    passwordField.setText("");
                }
            }
        });

        // Action Listener for Exit Button
        exitButton.addActionListener(e -> System.exit(0));

        // Display the frame
        loginFrame.setVisible(true);
    }

    // Method to launch the main screen after login
    public void launchMainScreen() {
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
        item1.addActionListener(e -> JOptionPane.showMessageDialog(null, "Opening Inventory Management..."));
        item2.addActionListener(e -> JOptionPane.showMessageDialog(null, "Displaying Alerts..."));
        item3.addActionListener(e -> JOptionPane.showMessageDialog(null, "Opening Supplier Management..."));
        item4.addActionListener(e -> JOptionPane.showMessageDialog(null, "Opening Sales Logging..."));
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
            launchLoginScreen(); // Relaunch the login screen
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

    public static void main(String[] args) {
        GUI gui = new GUI();
        gui.launchLoginScreen(); // Start with the login screen
    }
}
