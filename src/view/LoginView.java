package View;

import javax.swing.*;
import java.awt.*;
import controller.LoginController;

public class LoginView extends JPanel {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, createAccountButton;
    private LoginController controller; // sera affecté par setController()

    public LoginView() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Panel central avec formulaire
        JPanel formPanel = new JPanel(new GridBagLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(255, 255, 255);
                Color color2 = new Color(173, 216, 230); // Light Blue
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        Font labelFont = new Font("Segoe UI", Font.PLAIN, 14);
        Font fieldFont = new Font("Segoe UI", Font.PLAIN, 14);

        JLabel emailLabel = new JLabel("Email :");
        emailLabel.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(emailLabel, gbc);

        emailField = new JTextField();
        emailField.setFont(fieldFont);
        emailField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        gbc.gridx = 1; gbc.gridy = 0;
        formPanel.add(emailField, gbc);

        JLabel passwordLabel = new JLabel("Mot de passe :");
        passwordLabel.setFont(labelFont);
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setFont(fieldFont);
        passwordField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 1),
                BorderFactory.createEmptyBorder(8, 8, 8, 8)));
        gbc.gridx = 1; gbc.gridy = 1;
        formPanel.add(passwordField, gbc);

        loginButton = createStyledButton("Se connecter", new Color(100, 149, 237));
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(loginButton, gbc);

        createAccountButton = createStyledButton("Créer un compte", new Color(144, 238, 144));
        gbc.gridx = 1; gbc.gridy = 2;
        formPanel.add(createAccountButton, gbc);

        add(formPanel, BorderLayout.CENTER);

        // Actions
        loginButton.addActionListener(e -> {
            if (controller != null) {
                controller.login(emailField.getText(), new String(passwordField.getPassword()));
            }
        });
        createAccountButton.addActionListener(e -> {
            if (controller != null) {
                controller.createAccount(emailField.getText(), new String(passwordField.getPassword()));
            }
        });
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        return button;
    }

    public void setController(LoginController controller) {
        this.controller = controller;
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
