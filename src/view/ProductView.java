package view;

import javax.swing.*;
import java.awt.*;

public class ProductView extends JFrame {
    public ProductView() {
        setTitle("Liste des Produits");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panneau de contenu
        JPanel contentPanel = new JPanel(new BorderLayout());

        // Titre de la page
        JLabel headerLabel = new JLabel("Liste des produits", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Arial", Font.BOLD, 18));
        contentPanel.add(headerLabel, BorderLayout.NORTH);

        // Zone de texte pour simuler la liste des produits
        JTextArea productListArea = new JTextArea("Ici seront listés les produits...\n(Fonctionnalité à développer)");
        productListArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(productListArea);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        // Bouton pour ajouter au panier interactif
        JButton addToCartButton = new JButton("Ajouter au panier interactif");
        contentPanel.add(addToCartButton, BorderLayout.SOUTH);

        add(contentPanel);
    }
}
