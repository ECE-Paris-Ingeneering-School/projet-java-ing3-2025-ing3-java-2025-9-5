package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Product;
import model.ProductDAO;
import model.Cart;

public class ProductView extends JFrame {
    public ProductView() {
        setTitle("Liste des Produits");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // 🛒 Bandeau supérieur avec bouton panier
        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Produits disponibles", SwingConstants.LEFT);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        topPanel.add(titleLabel, BorderLayout.WEST);

        JButton cartButton = new JButton("Voir le panier 🛒");
        cartButton.setFocusPainted(false);
        topPanel.add(cartButton, BorderLayout.EAST);

        // Action du bouton panier
        cartButton.addActionListener(e -> {
            CartView cartView = new CartView();
            cartView.setVisible(true);
        });

        // 📦 Liste des produits
        List<Product> products = ProductDAO.getAllProducts();

        JPanel productsPanel = new JPanel();
        productsPanel.setLayout(new GridLayout(0, 2, 10, 10));
        for (Product product : products) {
            ProductPanel pp = new ProductPanel(product);
            pp.getAddToCartButton().addActionListener(e -> {
                Cart.getInstance().addProduct(product); // ⬅ panier mis à jour
                JOptionPane.showMessageDialog(ProductView.this, product.getName() + " ajouté au panier !");
                // Tu pourras ici ajouter réellement le produit à un panier partagé
            });
            productsPanel.add(pp);
        }

        JScrollPane scrollPane = new JScrollPane(productsPanel);

        setLayout(new BorderLayout());
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
