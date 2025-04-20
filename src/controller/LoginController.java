package controller;

import model.User;
import model.UserDAO;
import view.*;

import javax.swing.*;

public class LoginController {
    private LoginView view;

    public LoginController(LoginView view) {
        this.view = view;
        this.view.setController(this);
    }

    public void login(String email, String password) {
        if (email.isEmpty() || password.isEmpty()) {
            view.showMessage("Veuillez remplir tous les champs.");
            return;
        }
        User user = UserDAO.findUserByEmail(email);
        if (user == null) {
            view.showMessage("Email non trouvé. Veuillez créer un compte.");
            return;
        }
        if (!user.getPassword().equals(password)) {
            view.showMessage("Mot de passe incorrect.");
            return;
        }

        String role = user.getUserType();
        view.showMessage("Connexion réussie en tant que " + role);
        // Ferme la card login
        SwingUtilities.getWindowAncestor(view).setVisible(false);

        if (role.equalsIgnoreCase("client")) {
            HomeFrame home = new HomeFrame("Client");
            home.addProductsButtonListener(e -> new ProductView(user).setVisible(true));
            home.addCartButtonListener(e -> new CartView(user).setVisible(true));
            home.addHistoryButtonListener(e -> {
                OrderHistoryView history = new OrderHistoryView(user.getUserId());
                history.setVisible(true);
            });

            home.addLogoutButtonListener(e -> {
                home.dispose();
                reopenLogin();
            });
            home.setVisible(true);
        } else {
            AdminProductView admin = new AdminProductView();
            admin.addLogoutButtonListener(e -> {
                admin.dispose();
                reopenLogin();
            });
            admin.setVisible(true);
        }
    }

    public void createAccount(String email, String password) {
        if (email.isEmpty()) {
            view.showMessage("Veuillez entrer un email.");
            return;
        }
        if (!email.matches("^[\\w._%+-]+@[\\w.-]+\\.[A-Za-z]{2,}$")) {
            view.showMessage("Adresse email invalide.");
            return;
        }
        if (password.isEmpty()) {
            view.showMessage("Veuillez entrer un mot de passe.");
            return;
        }
        if (UserDAO.findUserByEmail(email) != null) {
            view.showMessage("Cet email est déjà utilisé.");
            return;
        }
        User u = new User(email, password, "client");
        UserDAO.addUser(u);
        view.showMessage("Compte créé avec succès !");
    }

    private void reopenLogin() {
        // Rouvre la fenêtre login
        view.getTopLevelAncestor().disable(); // au cas où
        LoginView lv = new LoginView();
        LoginController lc = new LoginController(lv);
        lv.showMessage("Déconnecté, reconnectez-vous.");
        JFrame frame = new JFrame("Connexion");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(lv);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
