package model;
import java.sql.*;

public class UserDAO {
    private static final String URL = "jdbc:mysql://localhost:3306/ShoppingDB";
    private static final String USER = "root"; // Modifier si besoin
    private static final String PASSWORD = "password"; // Modifier si besoin

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static User findUserByEmail(String email) {
        String query = "SELECT * FROM Users WHERE email = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return new User(
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("user_type")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean addUser(User user) {
        String query = "INSERT INTO Users (email, password, user_type) VALUES (?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, user.getEmail());
            stmt.setString(2, user.getPassword()); // Hasher avec bcrypt avant !
            stmt.setString(3, user.getUserType());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}