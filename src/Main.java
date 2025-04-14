import javax.swing.*;
import java.awt.*;
import View.LoginView;
import View.WelcomeView;
import controller.LoginController;
public class Main extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public Main() {
        setTitle("Application Shopping");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // 🔹 Création de LoginView **avant** le contrôleur
        LoginView loginView = new LoginView();
        LoginController loginController = new LoginController(loginView);

        // 🔹 On connecte la vue et le contrôleur
        loginView.setController(loginController);

        // 🔹 Création de WelcomeView
        WelcomeView welcomeView = new WelcomeView(() -> cardLayout.show(mainPanel, "Login"));

        // 🔹 Ajout des écrans au CardLayout
        mainPanel.add(welcomeView, "Welcome");
        mainPanel.add(loginView, "Login");

        add(mainPanel);
        setVisible(true);

        // 🔹 Affichage automatique après 1.5s
        new Timer(1500, e -> cardLayout.show(mainPanel, "Login")).start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Main::new);
    }
}
