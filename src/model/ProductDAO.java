package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/shopping";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch(ClassNotFoundException e){
            e.printStackTrace();
        }
    }
    public static Product findProductById(int productId) {
        String query = "SELECT * FROM Products WHERE product_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productId);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getDouble("price"),
                        rs.getString("image_path"),
                        rs.getString("description")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Récupère la liste complète des produits
    public static List<Product> getAllProducts() {
        List<Product> products = new ArrayList<>();
        String query = "SELECT * FROM Products";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Product p = new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getDouble("price"),
                        rs.getString("image_path"),
                        rs.getString("description")  
                );
                products.add(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }

    // Ajoute un produit dans la base
    public static boolean addProduct(Product p) {
        String query = "INSERT INTO Products (name, brand, price, image_path, description, stock) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, p.getName());
            stmt.setString(2, p.getBrand());
            stmt.setDouble(3, p.getPrice());
            stmt.setString(4, p.getImagePath());
            stmt.setString(5, p.getDescription());
            stmt.setInt(6, 100); // Par exemple, on initialise le stock à 100
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Supprime un produit à partir de son identifiant
    public static boolean removeProduct(int productId) {
        String query = "DELETE FROM Products WHERE product_id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, productId);
            int rows = stmt.executeUpdate();
            return rows > 0;
        } catch(SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
