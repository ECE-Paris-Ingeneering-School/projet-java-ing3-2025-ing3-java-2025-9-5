// view/CartView.java
package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.Cart;
import model.CartItem;
import model.DiscountDAO;
import model.OrderDAO;
import model.Product;
import model.User;

public class CartView extends JFrame {
    private JPanel productsPanel;
    private JLabel totalLabel;
    private User user;

    public CartView(User user) {
        this.user = user;
        setTitle("Mon Panier");
        setSize(600, 700);
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
        clearCartButton.addActionListener(e -> {
            Cart.getInstance().clear();
            refreshCart();
        });

        JButton orderButton = new JButton("Passer commande");
        orderButton.addActionListener(e -> {
            boolean success = OrderDAO.placeOrder(user, Cart.getInstance());
            if (success) {
                JOptionPane.showMessageDialog(CartView.this, "Commande passée avec succès !");
                // Affiche immédiatement la fenêtre facture, incluant réductions
                InvoiceView.showInvoiceForUser(user);
                Cart.getInstance().clear();
                refreshCart();
            } else {
                JOptionPane.showMessageDialog(CartView.this, "Erreur lors du passage de la commande.");
            }
        });

        JButton invoiceButton = new JButton("Générer facture");
        invoiceButton.addActionListener(e -> InvoiceView.showInvoiceForUser(user));

        buttonPanel.add(clearCartButton);
        buttonPanel.add(orderButton);
        buttonPanel.add(invoiceButton);

        bottomPanel.add(buttonPanel, BorderLayout.EAST);
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);
        setContentPane(mainPanel);

        refreshCart();
    }

    public void refreshCart() {
        productsPanel.removeAll();
        List<CartItem> items = Cart.getInstance().getItems();
        double grandTotal = 0;

        if (items.isEmpty()) {
            productsPanel.add(new JLabel("🛒 Votre panier est vide."));
        } else {
            for (CartItem item : items) {
                Product p = item.getProduct();
                int qty = item.getQuantity();
                double unitPrice = p.getPrice();

                // Vérification promotion
                int threshold = DiscountDAO.getBulkQuantity(p.getProductId());
                double discountedUnit = DiscountDAO.getDiscountedPrice(p.getProductId(), qty);
                double lineTotal;

                JLabel lineLabel;
                if (threshold > 0 && qty >= threshold && discountedUnit > 0) {
                    // promotion applicable
                    lineTotal = qty * discountedUnit;
                    grandTotal += lineTotal;
                    String text = String.format(
                            "<html>%s x%d : <strike>€%.2f</strike> <span style='color:red;'>€%.2f</span> = €%.2f</html>",
                            p.getName(), qty, unitPrice, discountedUnit, lineTotal
                    );
                    lineLabel = new JLabel(text);
                } else {
                    lineTotal = qty * unitPrice;
                    grandTotal += lineTotal;
                    String text = String.format(
                            "%s x%d : €%.2f = €%.2f",
                            p.getName(), qty, unitPrice, lineTotal
                    );
                    lineLabel = new JLabel(text);
                }

                JPanel productRow = new JPanel(new BorderLayout());
                productRow.add(lineLabel, BorderLayout.CENTER);

                JButton removeButton = new JButton("Retirer");
                removeButton.addActionListener(e -> {
                    Cart.getInstance().removeProduct(p);
                    refreshCart();
                });
                productRow.add(removeButton, BorderLayout.EAST);

                productsPanel.add(productRow);
            }
        }

        totalLabel.setText(String.format("Total: €%.2f", grandTotal));
        productsPanel.revalidate();
        productsPanel.repaint();
    }
}
