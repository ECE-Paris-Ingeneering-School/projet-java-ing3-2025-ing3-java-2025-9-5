package view;
/**
 * Vue représentant l'écran de connexion de l'application.
 * Cette vue permet à un utilisateur de se connecter avec son adresse email et son mot de passe,
 * ou de créer un nouveau compte. Elle est composée de champs pour l'email et le mot de passe,
 * ainsi que de boutons pour l'action de connexion et la création de compte.
 */
import controller.LoginController;
import utils.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class LoginView extends JPanel {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, createAccountButton;
    private LoginController controller;

    public LoginView() {
        super(new GridBagLayout());
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        setBackground(Style.CREAM);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;

        // Titre - Réduction de la taille du texte
        JLabel titleLabel = new JLabel("Connexion à votre compte");
        Style.styleTitle(titleLabel);  // Assurez-vous que le style du titre est adapté (taille de police réduite)
        titleLabel.setFont(Style.TITLE_FONT);  // Ajustez la taille ici
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(titleLabel, gbc);

        // Email
        JLabel emailLabel = new JLabel("Email :");
        Style.styleLabel(emailLabel);
        gbc.gridy = 1;
        add(emailLabel, gbc);

        emailField = new JTextField(20);
        emailField.setFont(Style.DEFAULT_FONT);
        gbc.gridy = 2;
        gbc.gridwidth = 1;  // Assurez-vous que la largeur du champ soit raisonnable
        gbc.fill = GridBagConstraints.HORIZONTAL; // Pour que le champ occupe la largeur disponible
        add(emailField, gbc);

        // Mot de passe
        JLabel passwordLabel = new JLabel("Mot de passe :");
        Style.styleLabel(passwordLabel);
        gbc.gridy = 3;
        add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setFont(Style.DEFAULT_FONT);
        gbc.gridy = 4;
        add(passwordField, gbc);

        // Panneau des boutons (ajusté pour avoir un espace suffisant)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        createAccountButton = new JButton("Créer un compte");
        Style.styleButton(createAccountButton);
        buttonPanel.add(createAccountButton);

        loginButton = new JButton("Se connecter");
        Style.styleButton(loginButton);
        buttonPanel.add(loginButton);

        gbc.gridy = 5;
        gbc.gridwidth = 2;  // Le panneau des boutons occupe deux colonnes pour centrer
        gbc.fill = GridBagConstraints.HORIZONTAL;  // Centrer les boutons
        add(buttonPanel, gbc);

        // Actions pour les boutons
        createAccountButton.addActionListener(e -> {
            String email = emailField.getText().trim();
            String pass = new String(passwordField.getPassword());
            controller.startCreateAccount(email, pass);
        });

        loginButton.addActionListener(e -> {
            controller.login(
                    emailField.getText().trim(),
                    new String(passwordField.getPassword())
            );
        });
    }

    public void setController(LoginController controller) {
        this.controller = controller;
    }

    public void showMessage(String msg) {
        JOptionPane.showMessageDialog(this, msg);
    }

    public void setEmail(String email) {
        emailField.setText(email);
    }

    public void setPassword(String password) {
        passwordField.setText(password);
    }
}
