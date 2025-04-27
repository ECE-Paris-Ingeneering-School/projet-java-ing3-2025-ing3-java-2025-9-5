package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductSearchDAO {
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

    /**
     * Recherche des produits selon les critères suivants :
     * @param nameKeyword mot-clé à rechercher dans le nom du produit (utilisation de LIKE)
     * @param brand mot-clé à rechercher dans la marque du produit (utilisation de LIKE)
     * @param minPrice prix minimum (>=)
     * @param maxPrice prix maximum (<=). Si <= 0, ce critère n'est pas appliqué.
     * @return la liste des produits correspondants
     */
    public static List<Product> searchProducts(String nameKeyword, String brand, double minPrice, double maxPrice) {
        List<Product> products = new ArrayList<>();
        StringBuilder query = new StringBuilder("SELECT * FROM Products WHERE 1=1");
        if (nameKeyword != null && !nameKeyword.trim().isEmpty()) {
            query.append(" AND name LIKE ?");
        }
        if (brand != null && !brand.trim().isEmpty()) {
            query.append(" AND brand LIKE ?");
        }
        if (minPrice >= 0) {
            query.append(" AND price >= ?");
        }
        if (maxPrice > 0) { // maxPrice n'est appliqué que s'il est > 0
            query.append(" AND price <= ?");
        }

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query.toString())) {

            int paramIndex = 1;
            if (nameKeyword != null && !nameKeyword.trim().isEmpty()) {
                stmt.setString(paramIndex++, "%" + nameKeyword + "%");
            }
            if (brand != null && !brand.trim().isEmpty()) {
                stmt.setString(paramIndex++, "%" + brand + "%");
            }
            if (minPrice >= 0) {
                stmt.setDouble(paramIndex++, minPrice);
            }
            if (maxPrice > 0) {
                stmt.setDouble(paramIndex++, maxPrice);
            }
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                Product product = new Product(
                        rs.getInt("product_id"),
                        rs.getString("name"),
                        rs.getString("brand"),
                        rs.getDouble("price"),
                        rs.getString("image_path"), // Assurez-vous que la colonne image_path existe, ou adaptez selon votre schéma
                        rs.getString("description")  // idem pour description
                );
                products.add(product);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return products;
    }
}
