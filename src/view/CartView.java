package view;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import model.Cart;
import model.Product;

public class CartView extends JFrame {
    public CartView() {
        setTitle("Mon Panier");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        List<Product> cartProducts = Cart.getInstance().getProducts();

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        if (cartProducts.isEmpty()) {
            panel.add(new JLabel("🛒 Votre panier est vide."));
        } else {
            for (Product p : cartProducts) {
                panel.add(new JLabel(p.getName() + " - €" + p.getPrice()));
            }
        }

        JScrollPane scrollPane = new JScrollPane(panel);
        add(scrollPane, BorderLayout.CENTER);
    }
}
