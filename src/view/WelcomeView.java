package view;
import javax.swing.*;
import java.awt.*;

public class WelcomeView extends JPanel {
    public WelcomeView(Runnable switchToLogin) {
        setLayout(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Bienvenue sur Shopping App !", SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.CENTER);

        // Timer pour afficher la page de connexion après 1.5 sec
        new Timer(1500, e -> switchToLogin.run()).start();
    }
}
