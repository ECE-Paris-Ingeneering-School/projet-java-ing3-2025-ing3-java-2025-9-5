package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import controller.LoginController;

public class LoginView extends JPanel {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, createAccountButton;
    private LoginController controller;

    public LoginView() {
        super(new GridLayout(3, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        add(new JLabel("Email :"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Mot de passe :"));
        passwordField = new JPasswordField();
        add(passwordField);

        createAccountButton = new JButton("Créer un compte");
        loginButton = new JButton("Se connecter");
        add(createAccountButton);
        add(loginButton);

        // Au clic, on appelle startCreateAccount, pas directement createAccount
        createAccountButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String pass  = new String(passwordField.getPassword());
            controller.startCreateAccount(email, pass);
        });

        loginButton.addActionListener(e -> {
            controller.login(
                    emailField.getText().trim(),
                    new String(passwordField.getPassword())
            );
        });
    }

    // Setters pour pré‑remplir/modifier les champs
    public void setEmail(String email) {
        emailField.setText(email);
    }
    public void setPassword(String password) {
        passwordField.setText(password);
    }

    public void setController(LoginController controller) {
        this.controller = controller;
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }
}
