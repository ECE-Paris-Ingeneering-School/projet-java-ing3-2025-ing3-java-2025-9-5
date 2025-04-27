package controller;
/**
 * Contrôleur de la vue de connexion.
 * Gère :
 * - l’authentification des utilisateurs (client ou admin),
 * - la création de compte (avec la vue CreateAccountView),
 * - la navigation vers les vues appropriées après connexion,
 * - la réouverture de la fenêtre de login après déconnexion.
 */
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
        // Ferme la fenêtre de login
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(view);
        topFrame.dispose();

        if (role.equalsIgnoreCase("client")) {
            HomeFrame home = new HomeFrame("Client");
            home.addProductsButtonListener(e -> new ProductView(user).setVisible(true));
            home.addCartButtonListener(e -> new CartView(user).setVisible(true));
            home.addHistoryButtonListener(e -> {
                OrderHistoryView history = new OrderHistoryView(user);
                history.setVisible(true);
            });
            home.addLogoutButtonListener(e -> {
                home.dispose();
                reopenLogin();
            });
            home.setVisible(true);
        } else { // admin
            AdminView adminUsers = new AdminView();
            adminUsers.addLogoutButtonListener(e -> {
                adminUsers.dispose();
                reopenLogin();
            });
            adminUsers.addDeleteListener(e -> {
                int id = adminUsers.getSelectedUserId();
                if (id == -1) {
                    JOptionPane.showMessageDialog(adminUsers, "Sélectionnez d'abord un utilisateur.");
                    return;
                }
                if (UserDAO.deleteUser(id)) {
                    JOptionPane.showMessageDialog(adminUsers, "Utilisateur supprimé.");
                    adminUsers.loadUsers();
                }
            });
            adminUsers.addPromoteListener(e -> {
                int id = adminUsers.getSelectedUserId();
                if (id == -1) {
                    JOptionPane.showMessageDialog(adminUsers, "Sélectionnez d'abord un utilisateur.");
                    return;
                }
                String currentRole = adminUsers.getSelectedUserRole();
                String newRole = currentRole.equalsIgnoreCase("admin") ? "client" : "admin";
                if (UserDAO.updateUserRole(id, newRole)) {
                    JOptionPane.showMessageDialog(adminUsers, "Rôle mis à jour en " + newRole);
                    adminUsers.loadUsers();
                }
            });

            adminUsers.addHistoryListener(e -> {
                int id = adminUsers.getSelectedUserId();
                if (id == -1) {
                    JOptionPane.showMessageDialog(adminUsers, "Sélectionnez d'abord un utilisateur.");
                    return;
                }
                User u = UserDAO.findUserById(id);
                OrderHistoryView hv = new OrderHistoryView(u);
                hv.setVisible(true);
            });
            adminUsers.setVisible(true);
        }
    }

    public void startCreateAccount(String email, String password) {

        final CreateAccountView cav = new CreateAccountView(email, password);
        cav.addConfirmListener(e -> finishCreateAccount(cav));
        cav.setVisible(true);

        if (UserDAO.findUserByEmail(email) != null) {
            view.showMessage("Un utilisateur existe déjà à cette adresse.");
            return;
        }
    }

    /** Traite la validation du formulaire avancé */
    private void finishCreateAccount(CreateAccountView cav) {
        String email     = cav.getEmail();
        String password  = cav.getPassword();
        String firstName = cav.getFirstName();
        String lastName  = cav.getLastName();
        cav.dispose();

        // Validation
        if (firstName.isEmpty() || lastName.isEmpty()) {
            JOptionPane.showMessageDialog(cav,
                    "Prénom et nom obligatoires.",
                    "Champs manquants",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        // Création
        User u = new User(email, password, "client", firstName, lastName);
        if (UserDAO.addUser(u)) {
            JOptionPane.showMessageDialog(cav,
                    "Compte créé ! Veuillez vous reconnecter.");
            // on remet à jour le formulaire principal
            view.setEmail(email);
            view.setPassword(password);
            cav.dispose();               // ferme CreateAccountView
        } else {
            JOptionPane.showMessageDialog(cav,
                    "Erreur lors de la création du compte.",
                    "Erreur",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
    private void reopenLogin() {
        // Rouvre la fenêtre login
        LoginView lv = new LoginView();
        new LoginController(lv);
        JFrame frame = new JFrame("Connexion");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setContentPane(lv);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
