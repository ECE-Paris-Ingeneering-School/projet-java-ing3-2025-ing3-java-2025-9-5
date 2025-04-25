package view;

import javax.swing.*;
import java.awt.*;
import utils.Style;

public class WelcomeView extends JPanel {
    public WelcomeView(Runnable switchToLogin) {
        setLayout(new GridBagLayout()); // pour un centrage élégant
        Style.stylePanel(this);

        JLabel welcomeLabel = new JLabel("Bienvenue sur Shopping App !");
        welcomeLabel.setFont(new Font("Serif", Font.BOLD, 22));
        welcomeLabel.setForeground(Style.DEEP_RED);

        add(welcomeLabel);

        // Fade effect simulé par délai
        new Timer(1800, e -> switchToLogin.run()).start();
    }
}
