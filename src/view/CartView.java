package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Cart;
import model.Product;
import java.util.List;

public class CartView extends JFrame {
    private JPanel productsPanel;
    private JLabel totalLabel;

    public CartView() {
        setTitle("Mon Panier");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panneau principal avec BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panneau pour afficher la liste des produits dans le panier
        productsPanel = new JPanel();
        productsPanel.setLayout(new BoxLayout(productsPanel, BoxLayout.Y_AXIS));

        JScrollPane scrollPane = new JScrollPane(productsPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Panneau du bas pour afficher le total et le bouton "Vider le panier"
        JPanel bottomPanel = new JPanel(new BorderLayout());

        totalLabel = new JLabel();
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        bottomPanel.add(totalLabel, BorderLayout.WEST);

        JButton clearCartButton = new JButton("Vider le panier");
        clearCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cart.getInstance().clear();
                refreshCart();
            }
        });
        bottomPanel.add(clearCartButton, BorderLayout.EAST);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // Afficher initialement le contenu du panier
        refreshCart();
    }

    /**
     * Rafraîchit l'affichage du panier en se basant sur le contenu actuel de Cart.
     */
    public void refreshCart() {
        productsPanel.removeAll();

        List<Product> cartProducts = Cart.getInstance().getProducts();

        if (cartProducts.isEmpty()) {
            productsPanel.add(new JLabel("🛒 Votre panier est vide."));
        } else {
            for (Product product : cartProducts) {
                JPanel productRow = new JPanel(new BorderLayout());
                JLabel productLabel = new JLabel(product.getName() + " - €" + product.getPrice());
                productRow.add(productLabel, BorderLayout.CENTER);

                JButton removeButton = new JButton("Retirer");
                removeButton.addActionListener(e -> {
                    Cart.getInstance().removeProduct(product);
                    refreshCart();
                });
                productRow.add(removeButton, BorderLayout.EAST);
                productsPanel.add(productRow);
            }
        }

        // Mettre à jour l'étiquette du total
        double total = Cart.getInstance().getTotalPrice();
        totalLabel.setText("Total: €" + String.format("%.2f", total));

        // Rafraîchir l'affichage
        productsPanel.revalidate();
        productsPanel.repaint();
    }
}
