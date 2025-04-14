package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public MainFrame() {
        setTitle("Application Shopping");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Ajout des différents panneaux
        mainPanel.add(new LoginPanel(this), "Login");
        mainPanel.add(new ClientPanel(), "Client");
        mainPanel.add(new AdminPanel(), "Admin");

        add(mainPanel);
        cardLayout.show(mainPanel, "Login");

        setLocationRelativeTo(null); // Centrer la fenêtre
        setVisible(true);
    }

    // Méthode pour changer de panneau
    public void showPanel(String panelName) {
        cardLayout.show(mainPanel, panelName);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame());
    }
}

class LoginPanel extends JPanel {
    private MainFrame mainFrame;
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton;

    public LoginPanel(MainFrame frame) {
        this.mainFrame = frame;
        setLayout(new GridLayout(3, 2, 10, 10));
        setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        add(new JLabel("Email :"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Mot de passe :"));
        passwordField = new JPasswordField();
        add(passwordField);

        // Bouton de connexion
        loginButton = new JButton("Connexion");
        add(new JLabel()); // Espace vide pour l'alignement
        add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Authentification simulée :
                // Si l'email contient "admin", on considère l'utilisateur comme administrateur, sinon client.
                String email = emailField.getText().trim().toLowerCase();
                if(email.contains("admin")) {
                    mainFrame.showPanel("Admin");
                } else {
                    mainFrame.showPanel("Client");
                }
            }
        });
    }
}

class ClientPanel extends JPanel {
    public ClientPanel() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Bienvenue, client !", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        add(label, BorderLayout.CENTER);
    }
}

class AdminPanel extends JPanel {
    public AdminPanel() {
        setLayout(new BorderLayout());
        JLabel label = new JLabel("Bienvenue, administrateur !", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 18));
        add(label, BorderLayout.CENTER);
    }
}
