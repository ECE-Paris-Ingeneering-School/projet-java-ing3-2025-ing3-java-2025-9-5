package view;

import model.Product;
import model.ProductDAO;
import model.ProductSearchDAO;
import model.Cart;
import model.User;
import model.DiscountDAO;
import utils.Style;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class ProductView extends JFrame {
    private User currentUser;
    private JPanel productsPanel;
    private JTextField nameField;
    private JTextField brandField;
    private JTextField minPriceField;
    private JTextField maxPriceField;
    private JButton searchButton;

    public ProductView(User currentUser) {
        this.currentUser = currentUser;
        setTitle("Liste des Produits");
        setSize(800, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JPanel mainPanel = new JPanel(new BorderLayout());
        Style.stylePanel(mainPanel);

        // Top Panel
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Style.CREAM);
        topPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        JLabel titleLabel = new JLabel("Produits disponibles");
        titleLabel.setFont(Style.TITLE_FONT);
        titleLabel.setForeground(Style.DEEP_RED);
        topPanel.add(titleLabel, BorderLayout.WEST);

        JButton cartButton = new JButton("Voir le panier 🛒");
        Style.styleButton(cartButton);
        cartButton.addActionListener(e -> {
            CartView cartView = new CartView(currentUser);
            cartView.setVisible(true);
        });
        topPanel.add(cartButton, BorderLayout.EAST);

        // Search Criteria Panel
        JPanel criteriaPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        criteriaPanel.setBorder(BorderFactory.createTitledBorder("Critères de recherche"));
        criteriaPanel.setBackground(Style.CREAM);

        criteriaPanel.add(new JLabel("Nom:"));
        nameField = new JTextField();
        criteriaPanel.add(nameField);

        criteriaPanel.add(new JLabel("Marque:"));
        brandField = new JTextField();
        criteriaPanel.add(brandField);

        criteriaPanel.add(new JLabel("Prix min:"));
        minPriceField = new JTextField();
        criteriaPanel.add(minPriceField);

        criteriaPanel.add(new JLabel("Prix max:"));
        maxPriceField = new JTextField();
        criteriaPanel.add(maxPriceField);

        searchButton = new JButton("Rechercher");
        Style.styleButton(searchButton);
        searchButton.addActionListener(e -> {
            String nameKeyword = nameField.getText().trim();
            String brand = brandField.getText().trim();
            double minPrice = parseDouble(minPriceField.getText().trim());
            double maxPrice = parseDouble(maxPriceField.getText().trim());
            List<Product> filteredProducts = ProductSearchDAO.searchProducts(nameKeyword, brand, minPrice, maxPrice);
            loadProducts(filteredProducts);
        });

        criteriaPanel.add(new JLabel());
        criteriaPanel.add(searchButton);

        // Products Panel
        productsPanel = new JPanel(new GridLayout(0, 4, 20, 20)); // 4 colonnes
        productsPanel.setBackground(Style.CREAM);
        JScrollPane scrollPane = new JScrollPane(productsPanel);

        // Content Panel
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Style.CREAM);
        contentPanel.add(criteriaPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);

        loadProducts(ProductDAO.getAllProducts());
    }

    private void loadProducts(List<Product> products) {
        productsPanel.removeAll();
        for (Product product : products) {
            ProductCard card = new ProductCard(product);
            productsPanel.add(card);
        }
        productsPanel.revalidate();
        productsPanel.repaint();
    }

    private double parseDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }

    // Classe interne pour représenter une carte produit
    private class ProductCard extends JPanel {
        private Product product;
        private JButton addToCartButton;
        private JComboBox<Integer> quantityBox;

        public ProductCard(Product product) {
            this.product = product;
            setLayout(new BorderLayout());
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(Style.DEEP_RED, 2),
                    BorderFactory.createEmptyBorder(10, 10, 10, 10)
            ));
            setPreferredSize(new Dimension(250, 400)); // taille de carte

            // Nom du produit en haut
            JLabel nameLabel = new JLabel(product.getName(), SwingConstants.CENTER);
            nameLabel.setFont(Style.DEFAULT_FONT);
            nameLabel.setForeground(Style.DEEP_RED);
            add(nameLabel, BorderLayout.NORTH);

            // Image au centre
            JLabel imageLabel = new JLabel();
            imageLabel.setPreferredSize(new Dimension(200, 150));
            imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
            imageLabel.setVerticalAlignment(SwingConstants.CENTER);
            imageLabel.setIcon(new ImageIcon(product.getImagePath()));
            add(imageLabel, BorderLayout.CENTER);

            // Partie basse : infos + bouton
            JPanel southPanel = new JPanel(new BorderLayout());
            southPanel.setBackground(Color.WHITE);

            // Infos produit (prix + promo éventuelle)
            JPanel infoPanel = new JPanel(new GridLayout(0, 1));
            infoPanel.setBackground(Color.WHITE);

            JLabel priceLabel = new JLabel(String.format("Prix: €%.2f", product.getPrice()), SwingConstants.CENTER);
            priceLabel.setForeground(Style.DARK_BLUE);
            infoPanel.add(priceLabel);

            int threshold = DiscountDAO.getBulkQuantity(product.getProductId());
            double discountedUnit = DiscountDAO.getDiscountedPrice(product.getProductId(), threshold);
            if (threshold > 0 && discountedUnit > 0) {
                double original = product.getPrice();
                double percent = (1 - discountedUnit / original) * 100;
                JLabel promoLabel = new JLabel(
                        String.format("Promo: -%.0f%% dès %d unités", percent, threshold),
                        SwingConstants.CENTER
                );
                promoLabel.setForeground(Style.SOFT_RED);
                infoPanel.add(promoLabel);
            }

            southPanel.add(infoPanel, BorderLayout.NORTH);

            // Bas : sélection quantité + bouton
            JPanel bottomPanel = new JPanel(new FlowLayout());
            bottomPanel.setBackground(Color.WHITE);

            quantityBox = new JComboBox<>();
            for (int i = 1; i <= 10; i++) {
                quantityBox.addItem(i);
            }
            bottomPanel.add(quantityBox);

            addToCartButton = new JButton("Ajouter au panier");
            addToCartButton.setBackground(Style.DARK_BLUE);
            addToCartButton.setForeground(Color.WHITE);
            addToCartButton.setFocusPainted(false);
            addToCartButton.addActionListener(e -> {
                int qty = (int) quantityBox.getSelectedItem();
                Cart.getInstance().addProduct(product, qty);
                JOptionPane.showMessageDialog(ProductView.this,
                        product.getName() + " (x" + qty + ") ajouté au panier !");
            });

            bottomPanel.add(addToCartButton);

            southPanel.add(bottomPanel, BorderLayout.SOUTH);

            // Ajouter la partie basse complète
            add(southPanel, BorderLayout.SOUTH);
        }
    }
}
