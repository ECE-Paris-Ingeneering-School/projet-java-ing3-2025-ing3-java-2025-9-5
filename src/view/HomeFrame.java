package view;

import utils.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class HomeFrame extends JFrame {
    private JLabel statusLabel;
    private JButton productsButton;
    private JButton cartButton;
    private JButton logoutButton;
    private JButton historyButton;
    private JLabel welcomeTitle;
    private JLabel announcementLabel;
    private String[] announcements = {
            "// Promo : -20% sur tout aujourd'hui !",
            "// Livraison offerte dès 50€ d'achat ",
            "// Nouveautés disponibles en exclusivité !"
    };
    private int currentAnnouncementIndex = 0;

    public HomeFrame(String userRole) {
        setTitle("Accueil - Shopping App");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        Style.stylePanel(mainPanel);

        // Bandeau supérieur
        JPanel topPanel = new JPanel(new BorderLayout());
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

        // Centre
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(50, 20, 20, 20));

        welcomeTitle = new JLabel("Bienvenue dans notre Concept Store");
        Style.styleTitle(welcomeTitle);
        welcomeTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        welcomeTitle.setForeground(Style.DEEP_RED);
        welcomeTitle.setFont(welcomeTitle.getFont().deriveFont(40f));

        productsButton = new JButton("Voir les produits");
        Style.styleButton(productsButton);
        productsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        productsButton.setMaximumSize(new Dimension(250, 50));

        // Label d'annonces
        announcementLabel = new JLabel(announcements[0]);
        announcementLabel.setFont(Style.DEFAULT_FONT.deriveFont(Font.BOLD, 18f));
        announcementLabel.setForeground(Style.SOFT_RED);
        announcementLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        announcementLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));

        centerPanel.add(welcomeTitle);
        centerPanel.add(announcementLabel);
        centerPanel.add(Box.createVerticalStrut(20));
        centerPanel.add(productsButton);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);

        startAnnouncementCarousel();
    }

    private void startAnnouncementCarousel() {
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                SwingUtilities.invokeLater(() -> {
                    currentAnnouncementIndex = (currentAnnouncementIndex + 1) % announcements.length;
                    announcementLabel.setText(announcements[currentAnnouncementIndex]);
                });
            }
        }, 0, 3000); // Changer toutes les 3 secondes
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
