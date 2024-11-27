import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main {
    private JFrame mainFrame;

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

    public static void main(String[] args) {
        Main main = new Main();
        main.launch();
    }
}
