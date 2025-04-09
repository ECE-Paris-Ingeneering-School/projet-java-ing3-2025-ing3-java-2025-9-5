package controller;

import model.User;
import model.UserDAO;
import view.HomeFrame;
import view.LoginView;
import javax.swing.*;

public class LoginController {
    private LoginView view;

    public LoginController(LoginView view) {
        this.view = view;
    }

    public void login(String email, String password) {
        // Vérification des champs vides
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
            // Ouvrir une nouvelle fenêtre indiquant le statut de connexion
            HomeFrame home = new HomeFrame(userRole);
            home.setVisible(true);
            // Facultatif : fermer la fenêtre de connexion
            // Si la vue est contenue dans une fenêtre (JFrame), vous pouvez la fermer via :
            JFrame parentFrame = (JFrame) javax.swing.SwingUtilities.getWindowAncestor(view);
            if (parentFrame != null) {
                parentFrame.dispose();
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
        // Création d'un objet User et ajout dans la base de données
        User newUser = new User(email, password, "client");
        UserDAO.addUser(newUser);

        view.showMessage("Compte créé avec succès ! Vous êtes identifié en tant que client.");
    }
}
