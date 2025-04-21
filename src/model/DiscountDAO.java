// model/DiscountDAO.java
package model;

import java.sql.*;

public class DiscountDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/shopping";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean addDiscount(int productId, int bulkQuantity, double bulkPrice) {
        String sql = "INSERT INTO discounts (product_id, bulk_quantity, bulk_price) VALUES (?, ?, ?) ";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            stmt.setInt(2, bulkQuantity);
            stmt.setDouble(3, bulkPrice);
            stmt.executeUpdate();
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static double getDiscountedPrice(int productId, int quantity) {
        String sql = "SELECT bulk_price FROM discounts WHERE product_id = ? AND ? >= bulk_quantity";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);
            stmt.setInt(2, quantity);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getDouble("bulk_price");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Pas de remise applicable
    }
}