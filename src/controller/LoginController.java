package controller;

import model.User;
import model.UserDAO;
import view.LoginView;

public class LoginController {
    private LoginView view;

    public LoginController(LoginView view) {
        this.view = view;
    }

    public void login(String email, String password) {
        // Vérification si les champs ne sont pas vides
        if (email == null || password == null || email.isEmpty() || password.isEmpty()) {
            view.showMessage("Veuillez remplir tous les champs.");
            return;
        }

        User user = UserDAO.findUserByEmail(email);

        // Vérification si l'utilisateur existe
        if (user == null) {
            view.showMessage("Email non trouvé. Veuillez créer un compte.");
        } else if (!user.getPassword().equals(password)) {
            view.showMessage("Mot de passe incorrect.");
        } else {
            view.showMessage("Connexion réussie en tant que " + user.getUserType());
        }
    }

    public void createAccount(String email, String password) {
        // Vérification email vide
        if (email == null || email.isEmpty()) {
            view.showMessage("Veuillez entrer un email.");
            return;
        }

        // Vérification format email
        if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$")) {
            view.showMessage("Adresse email invalide.");
            return;
        }

        // Vérification mot de passe vide
        if (password == null || password.isEmpty()) {
            view.showMessage("Veuillez entrer un mot de passe.");
            return;
        }

        // Vérifier si l'email est déjà utilisé
        if (UserDAO.findUserByEmail(email) != null) {
            view.showMessage("Cet email est déjà utilisé.");
            return;
        }

        // Création de l'utilisateur
        UserDAO.addUser(email, password, "client"); // 🔹 Correction de l'appel à addUser()

        view.showMessage("Compte créé avec succès ! Vous êtes identifié en tant que client.");
    }

}
