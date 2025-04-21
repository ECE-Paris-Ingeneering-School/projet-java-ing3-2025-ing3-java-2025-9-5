package model;

public class User {
    private int userId;
    private String email;
    private String password;
    private String userType;
    private String firstName;
    private String lastName;

    /**
     * Constructeur complet incluant userId, à utiliser lors de la récupération depuis la BDD
     */
    public User(int userId, String email, String password, String userType, String firstName, String lastName) {
        this.userId = userId;
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public User(String email, String password, String userType, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.userType = userType;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    /**
     * Constructeur sans userId, lors de la création d'un nouveau compte
     */
    public User(String email, String password, String userType) {
        this.email = email;
        this.password = password;
        this.userType = userType;
    }

    // Getters et Setters

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
