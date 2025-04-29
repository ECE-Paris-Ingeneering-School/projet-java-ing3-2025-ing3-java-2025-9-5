package view;
/**
 * Classe représentant la vue du panier d'achat.
 * Permet d'afficher les produits dans le panier, d'afficher le total et d'effectuer des actions sur le panier
 * telles que vider le panier, passer une commande, ou générer une facture.
 */
import model.Cart;
import model.CartItem;
import model.DiscountDAO;
import model.OrderDAO;
import model.Product;
import model.User;
import utils.Style;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class CartView extends JFrame {
    private JPanel productsPanel;
    private JLabel totalLabel;
    private User user;

    public CartView(User user) {
        this.user = user;
        setTitle("Mon Panier");
        setSize(700, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Panneau principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        Style.stylePanel(mainPanel); // <--- STYLE APPLIQUÉ

        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panneau produits
        productsPanel = new JPanel();
        productsPanel.setLayout(new BoxLayout(productsPanel, BoxLayout.Y_AXIS));
        Style.stylePanel(productsPanel); // <--- STYLE APPLIQUÉ SUR PRODUITS

        JScrollPane scrollPane = new JScrollPane(productsPanel);
        scrollPane.getViewport().setBackground(Style.PRIMARY_BG); // <- SCROLLPANE aussi
        scrollPane.setBorder(BorderFactory.createEmptyBorder());
        mainPanel.add(scrollPane, BorderLayout.CENTER);

        // Panneau du bas pour total et boutons
        JPanel bottomPanel = new JPanel(new BorderLayout());
        Style.stylePanel(bottomPanel); // <-- panneau bas aussi

        totalLabel = new JLabel();
        totalLabel.setFont(Style.BUTTON_FONT);
        totalLabel.setForeground(Style.DARK_BLUE);
        bottomPanel.add(totalLabel, BorderLayout.WEST);

        // Panneau des boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        buttonPanel.setOpaque(false);

        JButton clearCartButton = new JButton("Vider le panier");
        Style.styleButton(clearCartButton);
        clearCartButton.addActionListener(e -> {
            Cart.getInstance().clear();
            refreshCart();
        });

        JButton orderButton = new JButton("Passer commande");
        Style.styleButton(orderButton);
        orderButton.addActionListener(e -> {
            boolean success = OrderDAO.placeOrder(user, Cart.getInstance());
            if (success) {
                JOptionPane.showMessageDialog(CartView.this, "Commande passée avec succès !");
                InvoiceView.showInvoiceForUser(user);
                Cart.getInstance().clear();
                refreshCart();
            } else {
                JOptionPane.showMessageDialog(CartView.this, "Erreur lors du passage de la commande.");
            }
        });

        JButton invoiceButton = new JButton("Générer facture");
        Style.styleButton(invoiceButton);
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
            JLabel emptyLabel = new JLabel("Votre panier est vide.");
            emptyLabel.setFont(Style.DEFAULT_FONT);
            emptyLabel.setForeground(Style.DARK_BLUE);
            productsPanel.add(emptyLabel);
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
                    // Promotion applicable
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

                lineLabel.setFont(Style.DEFAULT_FONT);
                lineLabel.setForeground(Style.DARK_BLUE);

                JPanel productRow = new JPanel(new BorderLayout());
                productRow.setOpaque(false);
                productRow.add(lineLabel, BorderLayout.CENTER);

                // Bouton retirer
                JButton removeButton = new JButton("Retirer");
                Style.styleButton(removeButton);
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
