package View;
import javax.swing.*;
import java.awt.*;

public class WelcomeView extends JPanel {
    public WelcomeView(Runnable switchToLogin) {
        setLayout(new BorderLayout()) ;

        // Dégradé personnalisé comme fond
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                Color color1 = new Color(58, 123, 213);  // Bleu roi
                Color color2 = new Color(0, 210, 255);   // Cyan clair
                GradientPaint gp = new GradientPaint(0, 0, color1, 0, getHeight(), color2);
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        gradientPanel.setLayout(new GridBagLayout());

        // Texte stylé
        JLabel welcomeLabel = new JLabel("Bienvenue sur Shopping App !");
        welcomeLabel.setFont(new Font("Segoe UI", Font.BOLD, 36));
        welcomeLabel.setForeground(Color.WHITE);
        welcomeLabel.setOpaque(false);
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);

        // Effet d’ombre sur le texte
        welcomeLabel.setUI(new javax.swing.plaf.basic.BasicLabelUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setColor(new Color(0, 0, 0, 100));
                g2.drawString(welcomeLabel.getText(), 2, c.getHeight() / 2 + 1);
                g2.setColor(c.getForeground());
                g2.setFont(c.getFont());
                g2.drawString(welcomeLabel.getText(), 0, c.getHeight() / 2 - 1);
                g2.dispose();
            }
        });

        gradientPanel.add(welcomeLabel);
        add(gradientPanel, BorderLayout.CENTER);

        // Timer de transition
        new Timer(1500, e -> switchToLogin.run()).start();
    }
}
