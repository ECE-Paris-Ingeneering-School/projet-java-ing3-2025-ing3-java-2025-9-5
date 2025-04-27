package view;
/**
 * Vue d'accueil avec un effet d'animation du texte de bienvenue.
 * Affiche un message de bienvenue à l'utilisateur avec un effet de "pop" sur chaque lettre.
 * Après un certain délai, l'écran de login est affiché via un changement de vue.
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import utils.Style;

public class WelcomeView extends JPanel {
    private JLabel welcomeLabel;
    private String welcomeText = "Bienvenue sur Shoppy !"; // Le texte à afficher
    private int currentCharIndex = 0; // Indice pour suivre quelle lettre afficher

    public WelcomeView(Runnable switchToLogin) {
        setLayout(new GridBagLayout()); // pour un centrage élégant
        Style.stylePanel(this);

        // Création du JLabel avec le texte de bienvenue
        welcomeLabel = new JLabel();
        welcomeLabel.setFont(Style.TITLE_FONT);
        welcomeLabel.setForeground(Style.DEEP_RED);

        // Positionner le texte au centre
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.CENTER;
        add(welcomeLabel, gbc);

        // Démarrer l'effet "pop"
        startPopEffect();

        // Transition vers l'écran de login après un délai
        new Timer(3500, e -> switchToLogin.run()).start();
    }

    private void startPopEffect() {
        // Créer un Timer qui affiche chaque lettre avec un effet de "pop"
        Timer popTimer = new Timer(50, new ActionListener() {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                if (currentCharIndex < welcomeText.length()) {
                    // Ajouter la lettre suivante à l'étiquette avec effet "pop"
                    String currentText = welcomeLabel.getText();
                    String nextChar = String.valueOf(welcomeText.charAt(currentCharIndex));

                    // Ajouter le texte actuel + la nouvelle lettre avec une taille plus grande
                    JLabel tempLabel = new JLabel(currentText + nextChar);
                    tempLabel.setFont(Style.TITLE_FONT.deriveFont(48f)); // Taille plus grande pour l'effet "pop"
                    tempLabel.setForeground(Style.DEEP_RED);
                    welcomeLabel.setText(currentText + nextChar);

                    // Animation "pop" (agrandissement puis retour à la taille normale)
                    new Timer(100, e2 -> tempLabel.setFont(Style.TITLE_FONT)).start(); // Retour à la taille normale après un délai

                    // Avancer l'indice de caractère
                    currentCharIndex++;
                } else {
                    // Arrêter le timer une fois que tout le texte est affiché
                    ((Timer) e.getSource()).stop();
                }
            }
        });
        popTimer.start(); // Démarrer l'animation
    }
}
