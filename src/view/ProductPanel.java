package View;

import javax.swing.*;
import java.awt.*;

import Model.Product;


public class ProductPanel extends JPanel {
    private Product product;
    private JLabel imageLabel;
    private JLabel nameLabel;
    private JLabel brandLabel;
    private JLabel priceLabel;
    private JLabel descriptionLabel;
    private JButton addToCartButton;

    public ProductPanel(Product product) {
        this.product = product;
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setLayout(new BorderLayout(5, 5));

        // Image du produit
        imageLabel = new JLabel();
        ImageIcon icon = new ImageIcon(product.getImagePath());
        // Redimensionnement de l'image (optionnel)
        Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(img));
        add(imageLabel, BorderLayout.WEST);

        // Détails du produit
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        nameLabel = new JLabel("Nom : " + product.getName());
        brandLabel = new JLabel("Marque : " + product.getBrand());
        priceLabel = new JLabel("Prix : €" + product.getPrice());
        // Utilisation de HTML pour formater la description
        descriptionLabel = new JLabel("<html><p style='width:200px;'>" + product.getDescription() + "</p></html>");
        detailsPanel.add(nameLabel);
        detailsPanel.add(brandLabel);
        detailsPanel.add(priceLabel);
        detailsPanel.add(descriptionLabel);
        add(detailsPanel, BorderLayout.CENTER);

        // Bouton "Ajouter au panier" avec un smiley panier
        addToCartButton = new JButton("Ajouter au panier 🛒");
        add(addToCartButton, BorderLayout.SOUTH);
    }



    public JButton getAddToCartButton() {
        return addToCartButton;
    }
}
