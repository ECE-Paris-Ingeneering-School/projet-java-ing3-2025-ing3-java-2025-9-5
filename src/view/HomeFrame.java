package view;

import javax.swing.*;
import java.awt.*;

public class HomeFrame extends JFrame {
    private JLabel statusLabel;

    public HomeFrame(String userRole) {
        setTitle("Accueil - Application Shopping");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Création d'un panneau supérieur pour afficher le statut
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        statusLabel = new JLabel("Connecté en tant que : " + userRole);
        topPanel.add(statusLabel);

        // Pour l'instant, un panneau central vierge
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);

        // Disposition de la fenêtre
        add(topPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }
}
