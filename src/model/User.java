package model;

public class User {
    private int userId;
    private String email;
    private String password;
    private String userType;

    // Constructeur incluant userId, à utiliser si vous récupérez l'id depuis la base
    public User(int userId, String email, String password, String userType) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    // Un constructeur sans userId si nécessaire (par exemple, lors de la création d'un compte)
    public User(String email, String password, String userType) {
        this.email = email;
        this.password = password;
        this.userType = userType;
    }
    public int getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getUserType() { // Ajout de cette méthode
        return userType;
    }
}
