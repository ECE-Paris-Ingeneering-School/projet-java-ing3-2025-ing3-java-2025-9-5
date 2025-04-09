package controller;

import model.User;
import model.UserDAO;
import view.HomeFrame;
import view.LoginView;
import view.ProductView;

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
            String userRole = user.getUserType();
            // Création et affichage de la fenêtre d'accueil
            HomeFrame home = new HomeFrame(userRole);
            // Ajout de l'écouteur pour ouvrir la page des produits
            home.addProductsButtonListener(e -> {
                ProductView productView = new ProductView();
                productView.setVisible(true);
            });
            home.setVisible(true);
            // Optionnellement, fermer la fenêtre de connexion
            view.getTopLevelAncestor().setVisible(false);
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
