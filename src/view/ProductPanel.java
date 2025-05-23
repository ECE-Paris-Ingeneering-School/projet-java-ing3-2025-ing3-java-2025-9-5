package view;
/**
 * Panneau d'affichage pour un produit dans l'interface graphique.
 * Ce panneau affiche l'image, le nom, la marque, le prix et la description d'un produit,
 * ainsi qu'un spinner permettant de sélectionner la quantité et un bouton pour ajouter le produit au panier.
 */
import javax.swing.*;
import java.awt.*;

import model.Product;


public class ProductPanel extends JPanel {
    private Product product;
    private JLabel imageLabel;
    private JLabel nameLabel;
    private JLabel brandLabel;
    private JLabel priceLabel;
    private JLabel descriptionLabel;
    private JButton addToCartButton;
    private JSpinner quantitySpinner; // Composant pour choisir la quantité

    public ProductPanel(Product product) {
        this.product = product;
        setBorder(BorderFactory.createLineBorder(Color.GRAY));
        setLayout(new BorderLayout(5, 5));

        // Panneau supérieur pour le spinner de quantité
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.add(new JLabel("Quantité : "));
        quantitySpinner = new JSpinner(new SpinnerNumberModel(1, 1, 100, 1));
        topPanel.add(quantitySpinner);
        add(topPanel, BorderLayout.NORTH);

        // Image du produit
        imageLabel = new JLabel();
        ImageIcon icon = new ImageIcon(product.getImagePath());
        Image img = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        imageLabel.setIcon(new ImageIcon(img));
        add(imageLabel, BorderLayout.WEST);

        // Panneau central pour les détails
        JPanel detailsPanel = new JPanel();
        detailsPanel.setLayout(new BoxLayout(detailsPanel, BoxLayout.Y_AXIS));
        nameLabel = new JLabel("Nom : " + product.getName());
        brandLabel = new JLabel("Marque : " + product.getBrand());
        priceLabel = new JLabel("Prix : €" + product.getPrice());
        descriptionLabel = new JLabel("<html><p style='width:200px;'>" + product.getDescription() + "</p></html>");
        detailsPanel.add(nameLabel);
        detailsPanel.add(brandLabel);
        detailsPanel.add(priceLabel);
        detailsPanel.add(descriptionLabel);
        add(detailsPanel, BorderLayout.CENTER);

        // Bouton "Ajouter au panier"
        addToCartButton = new JButton("Ajouter au panier 🛒");
        add(addToCartButton, BorderLayout.SOUTH);
    }



    public JButton getAddToCartButton() {
        return addToCartButton;
    }

    // Ajoute une méthode pour récupérer la quantité sélectionnée
    public int getSelectedQuantity() {
        return (Integer) quantitySpinner.getValue();
    }
}
