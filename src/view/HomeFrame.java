package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class HomeFrame extends JFrame {
    private JLabel statusLabel;
    private JButton productsButton;
    private JButton cartButton;

    public HomeFrame(String userRole) {
        setTitle("Accueil - Shopping App");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal avec fond dégradé
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(135, 206, 250);  // Bleu ciel
                Color color2 = new Color(255, 182, 193);  // Rose clair
                GradientPaint gp = new GradientPaint(0, 0, color1, getWidth(), getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        // Top panel
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);
        statusLabel = new JLabel(" Connecté en tant que : " + userRole);
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        topPanel.add(statusLabel);

        cartButton = createStyledButton("Voir le panier ", new Color(255, 105, 97));
        topPanel.add(cartButton);

        // Center panel
        JPanel centerPanel = new JPanel();
        centerPanel.setOpaque(false);
        productsButton = createStyledButton("Consulter les produits", new Color(100, 149, 237));
        centerPanel.add(productsButton);

        // Ajout aux sections
        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 16));
        button.setForeground(Color.WHITE);
        button.setBackground(bgColor);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        button.setOpaque(true);
        return button;
    }

    public void addProductsButtonListener(ActionListener listener) {
        productsButton.addActionListener(listener);
    }

    public void addCartButtonListener(ActionListener listener) {
        cartButton.addActionListener(listener);
    }
}
