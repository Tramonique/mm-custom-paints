import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginScreen {
    private JFrame loginFrame;

    public void launch() {
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
                    // Launch the main application window
                    Main mainApp = new Main();
                    mainApp.launch();
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

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LoginScreen screen = new LoginScreen();
            screen.launch();
        });
    }
}
