import javax.swing.*;
import java.awt.*;

public class drag {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Drag and Drop Example");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 200);
            frame.setLayout(new FlowLayout());

            // Champ de texte source
            JTextField textField = new JTextField(15);
            textField.setText("Glissez-moi");
            textField.setDragEnabled(true); // Activation du drag

            // Label cible
            JLabel label = new JLabel("Déposez ici");
            label.setPreferredSize(new Dimension(150, 30));
            label.setBorder(BorderFactory.createLineBorder(Color.BLACK));

            // Gestion du transfert (Drop)
            label.setTransferHandler(new TransferHandler("text"));

            frame.add(textField);
            frame.add(label);

            frame.setVisible(true);
        });
    }
}