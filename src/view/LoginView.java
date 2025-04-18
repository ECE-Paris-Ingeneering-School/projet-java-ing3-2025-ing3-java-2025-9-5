package view;

import javax.swing.*;
import java.awt.*;
import controller.LoginController;

public class LoginView extends JPanel {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, createAccountButton;
    private LoginController controller;

    public LoginView() {
        super(new GridLayout(4, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(new JLabel("Email :"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Mot de passe :"));
        passwordField = new JPasswordField();
        add(passwordField);

        createAccountButton = new JButton("Créer un compte");
        add(createAccountButton);

        loginButton = new JButton("Se connecter");
        add(loginButton);

        // Les listeners appelleront le contrôleur une fois injecté
        loginButton.addActionListener(e -> {
            if (controller != null) {
                controller.login(
                        emailField.getText().trim(),
                        new String(passwordField.getPassword())
                );
            }
        });
        createAccountButton.addActionListener(e -> {
            if (controller != null) {
                controller.createAccount(
                        emailField.getText().trim(),
                        new String(passwordField.getPassword())
                );
            }
        });
    }

    /** Injecte le contrôleur et initialise les actions */
    public void setController(LoginController controller) {
        this.controller = controller;
    }

    /** Affiche une boîte de dialogue d’info/erreur */
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
