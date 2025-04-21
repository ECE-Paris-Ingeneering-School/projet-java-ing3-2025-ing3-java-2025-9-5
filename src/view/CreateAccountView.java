package view;

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
        super("Création de compte");
        setSize(750, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(6, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Message d'information
        JLabel messageLabel = new JLabel(
                "<html>Veuillez saisir toutes les informations pour créer un compte.</html>"
        );
        messageLabel.setForeground(Color.BLUE);
        panel.add(messageLabel);
        panel.add(new JLabel()); // filler

        // Email
        panel.add(new JLabel("Email :"));
        emailField = new JTextField(email);
        panel.add(emailField);

        // Mot de passe
        panel.add(new JLabel("Mot de passe :"));
        passwordField = new JPasswordField(password);
        panel.add(passwordField);

        // Prénom
        panel.add(new JLabel("Prénom *:"));
        firstNameField = new JTextField();
        panel.add(firstNameField);

        // Nom
        panel.add(new JLabel("Nom *:"));
        lastNameField = new JTextField();
        panel.add(lastNameField);

        // Boutons
        confirmButton = new JButton("Valider");
        JButton cancelButton = new JButton("Annuler");
        panel.add(confirmButton);
        panel.add(cancelButton);

        setContentPane(panel);

        // Annuler : ferme la fenêtre, LoginView reste active
        cancelButton.addActionListener(e -> dispose());
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
