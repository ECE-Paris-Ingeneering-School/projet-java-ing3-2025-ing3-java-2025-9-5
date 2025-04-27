package model;
/**
 * DAO pour l'historique des commandes.
 * Permet de récupérer les commandes passées par un utilisateur
 * ainsi que le détail des produits commandés.
 */

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class OrderHistoryDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/shopping";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try { Class.forName("com.mysql.cj.jdbc.Driver"); }
        catch (ClassNotFoundException e) { e.printStackTrace(); }
    }

    /** Retourne la liste des commandes passées par userId, triées par date décroissante */
    public static List<Order> getOrdersForUser(int userId) {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT order_id, order_date, total_amount FROM Orders WHERE user_id = ? ORDER BY order_date DESC";
        try (Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, userId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int oid = rs.getInt("order_id");
                    Timestamp ts = rs.getTimestamp("order_date");
                    LocalDateTime date = ts.toLocalDateTime();
                    double total = rs.getDouble("total_amount");
                    orders.add(new Order(oid, date, total));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return orders;
    }

    /** Retourne les détails (produit, quantité, prix) pour une commande donnée */
    public static List<OrderDetail> getOrderDetails(int orderId) {
        List<OrderDetail> details = new ArrayList<>();
        String sql =
                "SELECT od.product_id, p.name, od.quantity, od.price_per_unit, p.image_path " +
                        "FROM OrderDetails od " +
                        "JOIN Products p ON od.product_id = p.product_id " +
                        "WHERE od.order_id = ?";
        try (Connection c = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = c.prepareStatement(sql)) {
            ps.setInt(1, orderId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    details.add(new OrderDetail(
                            rs.getInt("product_id"),
                            rs.getString("name"),
                            rs.getInt("quantity"),
                            rs.getDouble("price_per_unit"),
                            rs.getString("image_path")    // on fait passer le chemin
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return details;
    }
}
