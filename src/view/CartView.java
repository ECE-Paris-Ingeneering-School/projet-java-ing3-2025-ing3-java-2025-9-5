package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import model.Cart;
import model.CartItem;
import model.OrderDAO;
import model.Product;
import model.User;
import java.util.List;

public class CartView extends JFrame {
    private JPanel productsPanel;
    private JLabel totalLabel;
    private User user;

    public CartView(User user) {
        this.user = user;
        setTitle("Mon Panier");
        setSize(400, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel(new BorderLayout());

        // Panneau pour afficher les produits du panier
        productsPanel = new JPanel();
        productsPanel.setLayout(new BoxLayout(productsPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(productsPanel);
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Panneau du bas pour le total et les boutons d'actions
        JPanel bottomPanel = new JPanel(new BorderLayout());
        totalLabel = new JLabel();
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        bottomPanel.add(totalLabel, BorderLayout.WEST);

        // Panneau pour les boutons
        JPanel buttonPanel = new JPanel(new FlowLayout());
        JButton clearCartButton = new JButton("Vider le panier");
        clearCartButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Cart.getInstance().clear();
                refreshCart();
            }
        });
        JButton orderButton = new JButton("Passer commande");
        orderButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean success = OrderDAO.placeOrder(user, Cart.getInstance());
                if (success) {
                    JOptionPane.showMessageDialog(CartView.this, "Commande passée avec succès !");
                    Cart.getInstance().clear();
                    refreshCart();
                } else {
                    JOptionPane.showMessageDialog(CartView.this, "Erreur lors du passage de la commande.");
                }
            }
        });
        buttonPanel.add(clearCartButton);
        buttonPanel.add(orderButton);
        bottomPanel.add(buttonPanel, BorderLayout.EAST);

        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        add(mainPanel);

        refreshCart();
    }

    public void refreshCart() {
        productsPanel.removeAll();
        // Utilisation de getItems() qui renvoie une List<CartItem>
        List<CartItem> items = Cart.getInstance().getItems();
        if (items.isEmpty()) {
            productsPanel.add(new JLabel("🛒 Votre panier est vide."));
        } else {
            for (CartItem item : items) {
                Product product = item.getProduct();
                int quantity = item.getQuantity();
                JPanel productRow = new JPanel(new BorderLayout());
                JLabel productLabel = new JLabel(product.getName() + " (x" + quantity + ") - €" + product.getPrice());
                productRow.add(productLabel, BorderLayout.CENTER);
                JButton removeButton = new JButton("Retirer");
                removeButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        // Pour simplifier, retirer le produit entier
                        Cart.getInstance().removeProduct(product);
                        refreshCart();
                    }
                });
                productRow.add(removeButton, BorderLayout.EAST);
                productsPanel.add(productRow);
            }
        }
        double total = Cart.getInstance().getTotalPrice();
        totalLabel.setText("Total: €" + String.format("%.2f", total));
        productsPanel.revalidate();
        productsPanel.repaint();
    }
}
