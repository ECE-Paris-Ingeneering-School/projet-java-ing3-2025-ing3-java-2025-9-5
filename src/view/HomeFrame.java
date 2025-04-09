package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HomeFrame extends JFrame {
    private JLabel statusLabel;
    private JButton productsButton;

    public HomeFrame(String userRole) {
        setTitle("Accueil - Application Shopping");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panneau supérieur pour afficher le statut
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        statusLabel = new JLabel("Connecté en tant que : " + userRole);
        topPanel.add(statusLabel);

        // Panneau central avec bouton d'accès aux produits
        JPanel centerPanel = new JPanel(new FlowLayout());
        productsButton = new JButton("Consulter les produits");
        centerPanel.add(productsButton);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    public void addProductsButtonListener(ActionListener listener) {
        productsButton.addActionListener(listener);
    }
}
