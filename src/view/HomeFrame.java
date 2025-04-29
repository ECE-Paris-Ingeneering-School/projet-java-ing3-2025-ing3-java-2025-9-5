package view;
/**
 * Classe représentant la fenêtre d'accueil de l'application Shoppy.
 * Cette fenêtre affiche des informations générales, des boutons pour accéder aux différentes sections de l'application,
 * ainsi qu'un carrousel d'annonces promotionnelles.
 */
import utils.Style;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;
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
        setTitle("Accueil - Shoppy App");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        Style.stylePanel(mainPanel);

        // Bandeau supérieur avec logo
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        // Ajouter le logo de l'entreprise à gauche
        JLabel logoLabel = new JLabel();
        try {
            Image logoImage = ImageIO.read(new File("src/img/logo.png")).getScaledInstance(200, 100, Image.SCALE_SMOOTH); 
            logoLabel.setIcon(new ImageIcon(logoImage));
        } catch (IOException e) {
            e.printStackTrace();
        }
        topPanel.add(logoLabel, BorderLayout.WEST);

        // Ajouter le statut de l'utilisateur
        statusLabel = new JLabel("Connecté en tant que : " + userRole);
        Style.styleLabel(statusLabel);
        topPanel.add(statusLabel, BorderLayout.CENTER);

        // Panneau avec les boutons à droite
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

        // Centre du panneau
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
        centerPanel.add(Box.createVerticalStrut(30));  // Espacement entre le bouton et l'image

        // Bandeau en bas (soft red)
        JPanel bottomPanel = new JPanel();
        bottomPanel.setBackground(Style.SOFT_RED); // Appliquer le fond de couleur soft red
        bottomPanel.setPreferredSize(new Dimension(getWidth(), 50)); // Fixer la hauteur du bandeau

        JLabel bottomText = new JLabel("Shoppy -  Votre Application de Shopping en ligne préférée! ");  // Texte ou autre contenu pour le footer
        bottomText.setForeground(Color.WHITE);
        bottomPanel.add(bottomText);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);  // Ajouter le bandeau en bas

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
