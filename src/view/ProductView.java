package View;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import Model.Product;
import Model.ProductDAO;
import Model.Cart;
import Model.User;

public class ProductView extends JFrame {
    private User currentUser;

    public ProductView(User currentUser) {
        this.currentUser = currentUser;
        setTitle("Produits - Shopping App");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Bandeau supérieur avec fond en dégradé
        JPanel topPanel = new GradientPanel(new Color(85, 98, 112), new Color(255, 107, 107));
        topPanel.setLayout(new BorderLayout());
        topPanel.setBorder(BorderFactory.createEmptyBorder(15, 30, 15, 30));

        JLabel titleLabel = new JLabel("Produits disponibles");
        titleLabel.setFont(new Font("Verdana", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        topPanel.add(titleLabel, BorderLayout.WEST);

        JButton cartButton = new JButton("Mon Panier");
        styleFancyButton(cartButton, new Color(255, 255, 255), new Color(255, 107, 107));
        topPanel.add(cartButton, BorderLayout.EAST);

        cartButton.addActionListener(e -> {
            CartView cartView = new CartView(currentUser);
            cartView.setVisible(true);
        });

        // Zone centrale avec les produits
        List<Product> products = ProductDAO.getAllProducts();
        JPanel productsPanel = new JPanel(new GridLayout(0, 2, 20, 20));
        productsPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
        productsPanel.setBackground(new Color(250, 250, 250));

        for (Product product : products) {
            ProductPanel pp = new ProductPanel(product);
            pp.setBackground(new Color(255, 255, 255));
            pp.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(230, 230, 230), 1),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));

            pp.getAddToCartButton().addActionListener(e -> {
                Cart.getInstance().addProduct(product);
                JOptionPane.showMessageDialog(this, product.getName() + " ajouté au panier !");
            });

            productsPanel.add(pp);
        }

        JScrollPane scrollPane = new JScrollPane(productsPanel);
        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Layout principal
        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void styleFancyButton(JButton button, Color bg, Color borderColor) {
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.setBackground(bg);
        button.setForeground(borderColor);
        button.setBorder(BorderFactory.createLineBorder(borderColor, 2));
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(130, 40));
    }

    // Panneau avec fond en dégradé
    class GradientPanel extends JPanel {
        private final Color colorStart;
        private final Color colorEnd;

        public GradientPanel(Color start, Color end) {
            this.colorStart = start;
            this.colorEnd = end;
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2d = (Graphics2D) g;
            GradientPaint gp = new GradientPaint(0, 0, colorStart, getWidth(), getHeight(), colorEnd);
            g2d.setPaint(gp);
            g2d.fillRect(0, 0, getWidth(), getHeight());
        }
    }
}
