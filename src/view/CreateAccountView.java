package view;

import utils.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class CreateAccountView extends JFrame {
    private final JTextField emailField;
    private final JPasswordField passwordField;
    private final JTextField firstNameField;
    private final JTextField lastNameField;
    private final JButton confirmButton;

    public CreateAccountView(String email, String password) {
        super("Créer un compte");
        // Configuration de la fenêtre

        setSize(800, 600); // Si tu veux une taille de base, mais elle sera maximisée
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setBackground(Style.CREAM);

        // Pour ouvrir la fenêtre en plein écran
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiser la fenêtre

        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Message d'information
        JLabel messageLabel = new JLabel("<html>Veuillez saisir toutes les informations pour créer un compte.</html>");
        messageLabel.setForeground(Color.BLUE);
        messageLabel.setFont(Style.TITLE_FONT);  // Réduction de la taille du message
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;  // Centrage du message sur toute la largeur
        panel.add(messageLabel, gbc);

        // Email
        JLabel emailLabel = new JLabel("Email :");
        Style.styleLabel(emailLabel);
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.WEST;  // Aligner le label à gauche
        panel.add(emailLabel, gbc);

        emailField = new JTextField(20);
        emailField.setText(email);
        emailField.setFont(Style.DEFAULT_FONT);
        gbc.gridx = 1;  // Déplacer le champ à droite
        panel.add(emailField, gbc);

        // Mot de passe
        JLabel passwordLabel = new JLabel("Mot de passe :");
        Style.styleLabel(passwordLabel);
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField(20);
        passwordField.setText(password);
        passwordField.setFont(Style.DEFAULT_FONT);
        gbc.gridx = 1;
        panel.add(passwordField, gbc);

        // Prénom
        JLabel firstNameLabel = new JLabel("Prénom *:");
        Style.styleLabel(firstNameLabel);
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(firstNameLabel, gbc);

        firstNameField = new JTextField(20);
        firstNameField.setFont(Style.DEFAULT_FONT);
        gbc.gridx = 1;
        panel.add(firstNameField, gbc);

        // Nom
        JLabel lastNameLabel = new JLabel("Nom *:");
        Style.styleLabel(lastNameLabel);
        gbc.gridx = 0;
        gbc.gridy = 7;
        gbc.anchor = GridBagConstraints.WEST;
        panel.add(lastNameLabel, gbc);

        lastNameField = new JTextField(20);
        lastNameField.setFont(Style.DEFAULT_FONT);
        gbc.gridx = 1;
        panel.add(lastNameField, gbc);

        // Panneau des boutons (ajusté pour avoir un espace suffisant)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);

        confirmButton = new JButton("Valider");
        Style.styleButton(confirmButton);
        buttonPanel.add(confirmButton);

        JButton cancelButton = new JButton("Annuler");
        Style.styleButton(cancelButton);
        cancelButton.addActionListener(e -> dispose());
        buttonPanel.add(cancelButton);

        gbc.gridy = 9;
        gbc.gridwidth = 2;  // Les boutons centrés sur toute la largeur
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(buttonPanel, gbc);

        setContentPane(panel);
    }

    public void addConfirmListener(ActionListener l) {
        confirmButton.addActionListener(l);
    }

    public String getFirstName() {
        return firstNameField.getText().trim();
    }

    public String getLastName() {
        return lastNameField.getText().trim();
    }

    public String getEmail() {
        return emailField.getText().trim();
    }

    public String getPassword() {
        return new String(passwordField.getPassword());
    }
}
