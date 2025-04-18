package model;

import java.sql.*;
import java.util.List;

public class OrderDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/shopping";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e){
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
            conn.setAutoCommit(false); // Début de transaction

            double totalAmount = cart.getTotalPrice();
            // Vérifier que le panier n'est pas vide
            if (totalAmount <= 0) {
                System.out.println("Le panier est vide.");
                return false;
            }

            // Insertion dans Orders
            String orderQuery = "INSERT INTO Orders (user_id, total_amount) VALUES (?, ?)";
            orderStmt = conn.prepareStatement(orderQuery, Statement.RETURN_GENERATED_KEYS);
            orderStmt.setInt(1, user.getUserId());
            orderStmt.setDouble(2, totalAmount);
            int affectedRows = orderStmt.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Échec de la création de la commande, aucune ligne insérée.");
            }

            generatedKeys = orderStmt.getGeneratedKeys();
            int orderId = -1;
            if (generatedKeys.next()) {
                orderId = generatedKeys.getInt(1);
            } else {
                throw new SQLException("Échec de la création de la commande, aucun ID généré.");
            }

            // On récupère la liste des CartItem
            List<CartItem> items = cart.getItems();

            // Insertion dans OrderDetails pour chaque item
            String detailQuery = "INSERT INTO OrderDetails (order_id, product_id, quantity, price_per_unit) VALUES (?, ?, ?, ?)";
            orderDetailStmt = conn.prepareStatement(detailQuery);
            for (CartItem item : items) {
                int productId = item.getProduct().getProductId();
                int quantity = item.getQuantity();
                double unitPrice = item.getProduct().getPrice();
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
