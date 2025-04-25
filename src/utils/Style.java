package utils;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class Style {
    public static final Color DEEP_RED = Color.decode("#780000");
    public static final Color SOFT_RED = Color.decode("#C1121F");
    public static final Color CREAM = Color.decode("#FDF0D5");
    public static final Color DARK_BLUE = Color.decode("#003049");
    public static final Color SKY_BLUE = Color.decode("#669BBC");
    public static final Color PRIMARY_BG = CREAM;

    // Load fonts
    private static Font loadFont(String path, float size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, new File(path)).deriveFont(size);
        } catch (Exception e) {
            System.err.println("Erreur chargement font : " + path + " → " + e.getMessage());
            return new Font("SansSerif", Font.PLAIN, (int) size);
        }
    }

    public static final Font TITLE_FONT = loadFont("src/ressources/fonts/Modak-Regular.ttf", 34f);
    public static final Font DEFAULT_FONT = loadFont("src/ressources/fonts/Inter-Regular.ttf", 16f);
    public static final Font BUTTON_FONT = loadFont("src/ressources/fonts/Manrope-Regular.ttf", 14f);

    public static void stylePanel(JPanel panel) {
        panel.setBackground(PRIMARY_BG);
    }

    public static void styleLabel(JLabel label) {
        label.setForeground(DARK_BLUE);
        label.setFont(DEFAULT_FONT);
    }

    public static void styleTitle(JLabel label) {
        label.setFont(TITLE_FONT);
        label.setForeground(DEEP_RED);
    }

    public static void styleButton(JButton button) {
        button.setBackground(DEEP_RED);
        button.setForeground(Color.WHITE);
        button.setFont(BUTTON_FONT);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
    }
}
