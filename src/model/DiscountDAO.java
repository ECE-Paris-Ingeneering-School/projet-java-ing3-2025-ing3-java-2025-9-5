package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO for managing bulk discounts in the database.
 */
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

    /**
     * Inserts a new discount. Returns true on success.
     */
    public static boolean addDiscount(int productId, int bulkQuantity, double bulkPrice) {
        String sql = "INSERT INTO discounts (product_id, bulk_quantity, bulk_price) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            stmt.setInt(2, bulkQuantity);
            stmt.setDouble(3, bulkPrice);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Deletes a discount by its unique ID.
     */
    public static boolean removeDiscountById(int discountId) {
        String sql = "DELETE FROM discounts WHERE discount_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, discountId);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Retrieves all discounts from the database with their actual IDs.
     */
    public static List<Discount> getAllDiscounts() {
        List<Discount> discounts = new ArrayList<>();
        String sql = "SELECT discount_id, product_id, bulk_quantity, bulk_price FROM discounts";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                int discountId   = rs.getInt("discount_id");
                int productId    = rs.getInt("product_id");
                int quantity     = rs.getInt("bulk_quantity");
                double bulkPrice = rs.getDouble("bulk_price");
                // Use the constructor with discountId
                discounts.add(new Discount(discountId, productId, quantity, bulkPrice));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return discounts;
    }

    /**
     * Fetches the applicable bulk price for a given product and quantity.
     */
    public static double getDiscountedPrice(int productId, int quantity) {
        String sql = "SELECT bulk_price FROM discounts WHERE product_id = ? AND ? >= bulk_quantity " +
                "ORDER BY bulk_quantity DESC LIMIT 1";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            stmt.setInt(2, quantity);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("bulk_price");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * Retrieves the highest bulk quantity threshold for a product.
     */
    public static int getBulkQuantity(int productId) {
        String sql = "SELECT bulk_quantity FROM discounts WHERE product_id = ? " +
                "ORDER BY bulk_quantity DESC LIMIT 1";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("bulk_quantity");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
