package view;
import javax.swing.*;
import java.awt.*;
import controller.LoginController;

public class LoginView extends JPanel {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, createAccountButton;
    private LoginController controller;

    public LoginView(LoginController controller) {
        this.controller = controller;
        setLayout(new GridLayout(4, 2, 10, 10));

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

        loginButton.addActionListener(e -> controller.login(emailField.getText(), new String(passwordField.getPassword())));
        createAccountButton.addActionListener(e -> controller.createAccount(emailField.getText(), new String(passwordField.getPassword())));
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(this, message);
    }
}
