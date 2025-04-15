package View;

import javax.swing.*;
import java.awt.*;
import Controller.LoginController;

public class LoginView extends JPanel {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, createAccountButton;
    private LoginController controller; // sera affecté par setController()

    // Constructeur sans argument
    public LoginView() {
        initUI();
    }

    private void initUI() {
        setLayout(new GridLayout(4, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        add(new JLabel("Email :"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Mot de passe :"));
        passwordField = new JPasswordField();
        add(passwordField);

        loginButton = new JButton("Se connecter");
        createAccountButton = new JButton("Créer un compte");

        add(createAccountButton);
        add(loginButton);

        // Ajout des écouteurs qui utiliseront le contrôleur (s'il a été configuré)
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

    // Setter pour associer le contrôleur
    public void setController(LoginController controller) {
        this.controller = controller;
    }

    // Méthode pour afficher un message à l'utilisateur
    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
