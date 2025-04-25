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
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        Style.stylePanel(mainPanel);

        // Bandeau supérieur avec titre
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
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

        // Panneau de critères de recherche
        JPanel criteriaPanel = new JPanel(new GridLayout(2, 4, 10, 10));
        criteriaPanel.setBorder(BorderFactory.createTitledBorder("Critères de recherche"));

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

        // Bouton de recherche
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

        // Panneau de produits
        productsPanel = new JPanel(new GridLayout(0, 2, 20, 20));
        productsPanel.setBackground(Style.CREAM);
        JScrollPane scrollPane = new JScrollPane(productsPanel);

        loadProducts(ProductDAO.getAllProducts());

        // Conteneur principal
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setOpaque(false);
        contentPanel.add(criteriaPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        mainPanel.add(topPanel, BorderLayout.NORTH);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        setContentPane(mainPanel);
    }

    private void loadProducts(List<Product> products) {
        productsPanel.removeAll();
        for (Product product : products) {
            ProductPanel pp = new ProductPanel(product);
            int threshold = DiscountDAO.getBulkQuantity(product.getProductId());
            double discountedUnit = DiscountDAO.getDiscountedPrice(product.getProductId(), threshold);

            if (threshold > 0 && discountedUnit > 0) {
                double original = product.getPrice();
                double percent = (1 - discountedUnit / original) * 100;
                JLabel promoLabel = new JLabel(
                        String.format("Promo: -%.0f%% à partir de %d unités", percent, threshold), SwingConstants.CENTER);
                promoLabel.setForeground(Style.SOFT_RED);

                JPanel wrapper = new JPanel(new BorderLayout());
                wrapper.setBackground(Style.CREAM);
                wrapper.add(pp, BorderLayout.CENTER);
                wrapper.add(promoLabel, BorderLayout.SOUTH);
                pp.getAddToCartButton().addActionListener(e -> {
                    int qty = pp.getSelectedQuantity();
                    Cart.getInstance().addProduct(product, qty);
                    JOptionPane.showMessageDialog(ProductView.this,
                            product.getName() + " (x" + qty + ") ajouté au panier !");
                });
                productsPanel.add(wrapper);
            } else {
                pp.getAddToCartButton().addActionListener(e -> {
                    int qty = pp.getSelectedQuantity();
                    Cart.getInstance().addProduct(product, qty);
                    JOptionPane.showMessageDialog(ProductView.this,
                            product.getName() + " (x" + qty + ") ajouté au panier !");
                });
                productsPanel.add(pp);
            }
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
}
