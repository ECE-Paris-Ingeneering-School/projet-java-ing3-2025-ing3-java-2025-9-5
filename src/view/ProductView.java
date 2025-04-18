package view;


import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.Product;
import model.ProductDAO;
import model.ProductSearchDAO;
import model.Cart;
import model.User;

public class ProductView extends JFrame {
    private User currentUser;
    private JPanel productsPanel;

    // Champs de recherche
    private JTextField nameField;
    private JTextField brandField;
    private JTextField minPriceField;
    private JTextField maxPriceField;
    private JButton searchButton;

    public ProductView(User currentUser) {
        this.currentUser = currentUser;
        setTitle("Liste des Produits");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Bandeau supérieur avec titre et bouton "Voir le panier"
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Produits disponibles", SwingConstants.LEFT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(titleLabel, BorderLayout.WEST);

        JButton cartButton = new JButton("Voir le panier 🛒");
        cartButton.setFocusPainted(false);
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
        JPanel searchButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        searchButtonPanel.add(searchButton);

        // Conteneur de recherche
        JPanel searchContainer = new JPanel();
        searchContainer.setLayout(new BorderLayout());
        searchContainer.add(criteriaPanel, BorderLayout.CENTER);
        searchContainer.add(searchButtonPanel, BorderLayout.SOUTH);

        // Panneau des produits
        productsPanel = new JPanel();
        productsPanel.setLayout(new GridLayout(0, 2, 10, 10));
        JScrollPane scrollPane = new JScrollPane(productsPanel);

        // Chargement initial de tous les produits
        loadProducts(ProductDAO.getAllProducts());

        // Action du bouton de recherche
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String nameKeyword = nameField.getText().trim();
                String brand = brandField.getText().trim();
                double minPrice = parseDouble(minPriceField.getText().trim());
                double maxPrice = parseDouble(maxPriceField.getText().trim());
                // Utilisation du DAO de recherche pour filtrer les produits
                List<Product> filteredProducts = ProductSearchDAO.searchProducts(nameKeyword, brand, minPrice, maxPrice);
                loadProducts(filteredProducts);
            }
        });

        // Conteneur principal : on place le panneau de recherche en haut, puis la liste des produits
        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.add(searchContainer, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    // Méthode utilitaire pour charger une liste de produits dans le panneau
    private void loadProducts(List<Product> products) {
        productsPanel.removeAll();
        for (Product product : products) {
            ProductPanel pp = new ProductPanel(product);
            pp.getAddToCartButton().addActionListener(e -> {
                int qty = pp.getSelectedQuantity();  // Récupérer la quantité choisie via le JSpinner
                Cart.getInstance().addProduct(product, qty);
                JOptionPane.showMessageDialog(ProductView.this, product.getName() + " (x" + qty + ") ajouté au panier !");
            });
            productsPanel.add(pp);
        }
        productsPanel.revalidate();
        productsPanel.repaint();
    }

    // Méthode utilitaire pour convertir une chaîne en double
    private double parseDouble(String s) {
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException ex) {
            return 0;
        }
    }
}
