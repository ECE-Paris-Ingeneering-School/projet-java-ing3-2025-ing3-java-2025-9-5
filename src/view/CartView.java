package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Model.Cart;
import Model.OrderDAO;
import Model.Product;
import Model.User;
import java.util.List;

public class CartView extends JFrame {
    private JPanel productsPanel;
    private JLabel totalLabel;
    private User user;

    public CartView(User user) {
        this.user = user;
        setTitle(" Mon Panier");
        setSize(500, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panneau principal avec fond dégradé
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(255, 175, 189); // Rose clair
                Color color2 = new Color(255, 195, 160); // Pêche
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };

        productsPanel = new JPanel();
        productsPanel.setLayout(new BoxLayout(productsPanel, BoxLayout.Y_AXIS));
        productsPanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(productsPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(scrollPane, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setOpaque(false);

        totalLabel = new JLabel();
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        totalLabel.setForeground(new Color(80, 0, 60));
        totalLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        bottomPanel.add(totalLabel, BorderLayout.WEST);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.setOpaque(false);

        JButton clearCartButton = createStyledButton("Vider le panier", new Color(255, 99, 71));
        clearCartButton.addActionListener(e -> {
            Cart.getInstance().clear();
            refreshCart();
        });

        JButton orderButton = createStyledButton("Passer commande", new Color(60, 179, 113));
        orderButton.addActionListener(e -> {
            boolean success = OrderDAO.placeOrder(user, Cart.getInstance());
            if (success) {
                JOptionPane.showMessageDialog(CartView.this, "🎉 Commande passée avec succès !");
                Cart.getInstance().clear();
                refreshCart();
            } else {
                JOptionPane.showMessageDialog(CartView.this, "❌ Erreur lors du passage de la commande.");
            }
        });

        buttonPanel.add(clearCartButton);
        buttonPanel.add(orderButton);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel);

        refreshCart();
    }

    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBackground(bgColor);
        button.setBorder(BorderFactory.createEmptyBorder(8, 16, 8, 16));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setOpaque(true);
        return button;
    }

    public void refreshCart() {
        productsPanel.removeAll();
        List<Product> cartProducts = Cart.getInstance().getProducts();

        if (cartProducts.isEmpty()) {
            JLabel emptyLabel = new JLabel(" Votre panier est vide.");
            emptyLabel.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            emptyLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            emptyLabel.setForeground(new Color(80, 0, 60));
            productsPanel.add(Box.createVerticalStrut(20));
            productsPanel.add(emptyLabel);
        } else {
            for (Product product : cartProducts) {
                JPanel productRow = new JPanel(new BorderLayout());
                productRow.setOpaque(false);
                productRow.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                JLabel productLabel = new JLabel(product.getName() + " - €" + product.getPrice());
                productLabel.setFont(new Font("Segoe UI", Font.PLAIN, 16));
                productLabel.setForeground(Color.DARK_GRAY);

                JButton removeButton = createStyledButton("Retirer", new Color(220, 20, 60));
                removeButton.addActionListener(e -> {
                    Cart.getInstance().removeProduct(product);
                    refreshCart();
                });

                productRow.add(productLabel, BorderLayout.CENTER);
                productRow.add(removeButton, BorderLayout.EAST);
                productsPanel.add(productRow);
            }
        }

        double total = Cart.getInstance().getTotalPrice();
        totalLabel.setText("Total : €" + String.format("%.2f", total));

        productsPanel.revalidate();
        productsPanel.repaint();
    }
}
