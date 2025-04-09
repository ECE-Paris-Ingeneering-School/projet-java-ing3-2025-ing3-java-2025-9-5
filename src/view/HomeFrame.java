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

        // Panneau supérieur pour le statut
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        statusLabel = new JLabel("Connecté en tant que : " + userRole);
        topPanel.add(statusLabel);

        // Panneau central avec bouton pour accéder aux produits
        JPanel contentPanel = new JPanel(new BorderLayout());
        productsButton = new JButton("Consulter les produits");
        contentPanel.add(productsButton, BorderLayout.CENTER);

        add(topPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    // Méthode pour ajouter un écouteur sur le bouton
    public void addProductsButtonListener(ActionListener listener) {
        productsButton.addActionListener(listener);
    }
}
