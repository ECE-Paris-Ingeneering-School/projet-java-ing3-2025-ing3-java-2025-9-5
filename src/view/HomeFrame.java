package view;

import utils.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HomeFrame extends JFrame {
    private JLabel statusLabel;
    private JButton productsButton;
    private JButton cartButton;
    private JButton logoutButton;
    private JButton historyButton;

    public HomeFrame(String userRole) {
        setTitle("Accueil - Shopping App");
        // Configuration de la fenêtre
        setSize(800, 600); // Si tu veux une taille de base, mais elle sera maximisée
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Pour ouvrir la fenêtre en plein écran
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Maximiser la fenêtre

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        Style.stylePanel(mainPanel);

        // Bandeau supérieur
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        statusLabel = new JLabel("Connecté en tant que : " + userRole);
        Style.styleLabel(statusLabel);
        topPanel.add(statusLabel, BorderLayout.WEST);

        JPanel rightHeaderPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightHeaderPanel.setOpaque(false);
        cartButton = new JButton("Panier");
        historyButton = new JButton("Historique");
        logoutButton = new JButton("Déconnexion");

        Style.styleButton(cartButton);
        Style.styleButton(historyButton);
        Style.styleButton(logoutButton);

        rightHeaderPanel.add(cartButton);
        rightHeaderPanel.add(historyButton);
        rightHeaderPanel.add(logoutButton);

        topPanel.add(rightHeaderPanel, BorderLayout.EAST);

        // Centre : titre + bouton principal
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 20, 20));

        JLabel welcomeTitle = new JLabel("Bienvenue dans notre Concept Store");
        Style.styleTitle(welcomeTitle);
        welcomeTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        productsButton = new JButton("Voir les produits");
        Style.styleButton(productsButton);
        productsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        productsButton.setMaximumSize(new Dimension(200, 40));

        centerPanel.add(welcomeTitle);
        centerPanel.add(Box.createVerticalStrut(30));
        centerPanel.add(productsButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    public void addProductsButtonListener(ActionListener listener) {
        productsButton.addActionListener(listener);
    }

    public void addCartButtonListener(ActionListener listener) {
        cartButton.addActionListener(listener);
    }

    public void addLogoutButtonListener(ActionListener listener) {
        logoutButton.addActionListener(listener);
    }

    public void addHistoryButtonListener(ActionListener l) {
        historyButton.addActionListener(l);
    }
}
