package model;

import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/shopping";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }

    public static boolean placeOrder(User user, Cart cart) {
        Connection conn = null;
        PreparedStatement orderStmt = null;
        PreparedStatement orderDetailStmt = null;
        ResultSet generatedKeys = null;
        try {
            conn = DriverManager.getConnection(URL, USER, PASSWORD);
            conn.setAutoCommit(false); // début de transaction

            // Insertion dans Orders
            String orderQuery = "INSERT INTO Orders (user_id, total_amount) VALUES (?, ?)";
            orderStmt = conn.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, user.getUserId());
            double totalAmount = cart.getTotalPrice();
            orderStmt.setDouble(2, totalAmount);
            int affectedRows = orderStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Création de la commande échouée, aucune ligne ajoutée.");
            }
            generatedKeys = orderStmt.getGeneratedKeys();
            int orderId = -1;
            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Création de la commande échouée, aucun ID récupéré.");
            }

            // Calculer la quantité de chaque produit (on regroupe par product_id)
            Map<Integer, Integer> productQuantities = new HashMap<>();
            List<Product> cartProducts = cart.getProducts();
            for (Product p : cartProducts) {
                int id = p.getProductId();
                productQuantities.put(id, productQuantities.getOrDefault(id, 0) + 1);
            }

            // Insertion dans OrderDetails
            String detailQuery = "INSERT INTO OrderDetails (order_id, product_id, quantity, price_per_unit) VALUES (?, ?, ?, ?)";
            orderDetailStmt = conn.prepareStatement(detailQuery);
            for (Map.Entry<Integer, Integer> entry : productQuantities.entrySet()) {
                int productId = entry.getKey();
                int quantity = entry.getValue();
                double unitPrice = 0;
                // Recherche du prix de base parmi les produits (supposons qu'ils sont homogènes)
                for (Product p : cartProducts) {
                    if (p.getProductId() == productId) {
                        unitPrice = p.getPrice();
                        break;
                    }
                }
                orderDetailStmt.setInt(1, orderId);
                orderDetailStmt.setInt(2, productId);
                orderDetailStmt.setInt(3, quantity);
                orderDetailStmt.setDouble(4, unitPrice);
                orderDetailStmt.addBatch();
            }
            orderDetailStmt.executeBatch();
            conn.commit();
            return true;
        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
        } finally {
            try { if (generatedKeys != null) generatedKeys.close(); } catch(SQLException ex){}
            try { if (orderStmt != null) orderStmt.close(); } catch(SQLException ex){}
            try { if (orderDetailStmt != null) orderDetailStmt.close(); } catch(SQLException ex){}
            try { if (conn != null) { conn.setAutoCommit(true); conn.close(); } } catch(SQLException ex){}
        }
        return false;
    }
}
