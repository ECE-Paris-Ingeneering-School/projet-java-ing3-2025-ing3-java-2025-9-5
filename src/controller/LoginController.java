package Controller;

import model.User;
import model.UserDAO;
import view.HomeFrame;
import view.LoginView;
import view.ProductView;
import view.CartView;
import view.AdminProductView;
main

public class LoginController {
    private LoginView view;

    public LoginController(LoginView view) {
        this.view = view;
    }

    public void login(String email, String password) {
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            view.showMessage("Veuillez remplir tous les champs.");
            return;
        }

        User user = UserDAO.findUserByEmail(email);

        if (user == null) {
            view.showMessage("Email non trouvé. Veuillez créer un compte.");
        } else if (!user.getPassword().equals(password)) {
            view.showMessage("Mot de passe incorrect.");
        } else {
            String userType = user.getUserType();
            view.showMessage("Connexion réussie en tant que " + userType);
            if (userType.equalsIgnoreCase("client")) {
                HomeFrame home = new HomeFrame(userType);
                home.addProductsButtonListener(e -> {
                    ProductView productView = new ProductView(user);
                    productView.setVisible(true);
                });
                home.addCartButtonListener(e -> {
                    CartView cartView = new CartView(user);
                    cartView.setVisible(true);
                });
                home.setVisible(true);
                view.getTopLevelAncestor().setVisible(false);
            } else if (userType.equalsIgnoreCase("admin")) {
                AdminProductView adminView = new AdminProductView();
                adminView.setVisible(true);
                view.getTopLevelAncestor().setVisible(false);
            }
        }
    }

    public void createAccount(String email, String password) {
        if (email == null || email.isEmpty()) {
            view.showMessage("Veuillez entrer un email.");
            return;
        }
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            view.showMessage("Adresse email invalide.");
            return;
        }
        if (password == null || password.isEmpty()) {
            view.showMessage("Veuillez entrer un mot de passe.");
            return;
        }
        if (UserDAO.findUserByEmail(email) != null) {
            view.showMessage("Cet email est déjà utilisé.");
            return;
        }
        User newUser = new User(email, password, "client");
        UserDAO.addUser(newUser);
        view.showMessage("Compte créé avec succès ! Vous êtes identifié en tant que client.");
    }
}