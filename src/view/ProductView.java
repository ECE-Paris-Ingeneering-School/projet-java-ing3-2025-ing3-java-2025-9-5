package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Product;
import model.ProductDAO;

public class ProductView extends JFrame {
    public ProductView() {
        setTitle("Liste des Produits");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Récupération des produits depuis la base de données
        List<Product> products = ProductDAO.getAllProducts();

        JPanel productsPanel = new JPanel();
        // Utilisation d'une grille à 2 colonnes (adapter selon le nombre de produits)
        productsPanel.setLayout(new GridLayout(0, 2, 10, 10));
        for (Product product : products) {
            ProductPanel pp = new ProductPanel(product);
            pp.getAddToCartButton().addActionListener(e -> {
                // Pour l'instant, afficher simplement un message
                JOptionPane.showMessageDialog(ProductView.this, product.getName() + " ajouté au panier !");
            });
            productsPanel.add(pp);
        }

        JScrollPane scrollPane = new JScrollPane(productsPanel);
        add(scrollPane, BorderLayout.CENTER);
    }
}
