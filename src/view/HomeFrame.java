package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HomeFrame extends JFrame {
    private JLabel statusLabel;
    private JButton productsButton;
    private JButton cartButton;

    public HomeFrame(String userRole) {
        setTitle("Accueil - Application Shopping");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        statusLabel = new JLabel("Connecté en tant que : " + userRole);
        topPanel.add(statusLabel);

        cartButton = new JButton("Voir le panier 🛒");
        topPanel.add(cartButton);

        JPanel centerPanel = new JPanel(new FlowLayout());
        productsButton = new JButton("Consulter les produits");
        centerPanel.add(productsButton);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
    }

    public void addProductsButtonListener(ActionListener listener) {
        productsButton.addActionListener(listener);
    }

    public void addCartButtonListener(ActionListener listener) {
        cartButton.addActionListener(listener);
    }
}
