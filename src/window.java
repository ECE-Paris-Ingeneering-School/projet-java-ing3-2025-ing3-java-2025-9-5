import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class window {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainWindow());
    }
}

class MainWindow extends JFrame {
    private JTextField textField;
    private JButton openButton;

    public MainWindow() {
        setTitle("Fenêtre 1");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        textField = new JTextField(15);
        openButton = new JButton("Ouvrir Fenêtre 2");

        openButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SecondWindow(textField.getText());
            }
        });

        add(textField);
        add(openButton);
        setVisible(true);
    }
}

class SecondWindow extends JFrame {
    public SecondWindow(String text) {
        setTitle("Fenêtre 2");
        setSize(300, 150);
        setLayout(new FlowLayout());

        JLabel label = new JLabel("Texte reçu : " + text);
        add(label);

        setVisible(true);
    }
}