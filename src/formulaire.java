import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

public class formulaire {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new RegistrationWindow());
    }
}

class RegistrationWindow extends JFrame {
    private JTextField usernameField, emailField;
    private JPasswordField passwordField;
    private JLabel errorLabel;
    private JButton showPasswordButton;

    public RegistrationWindow() {
        setTitle("Formulaire d'Inscription");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Username:"));
        usernameField = new JTextField();
        add(usernameField);

        add(new JLabel("Email:"));
        emailField = new JTextField();
        add(emailField);

        add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        add(passwordField);

        showPasswordButton = new JButton("👁");
        add(showPasswordButton);

        JButton registerButton = new JButton("S'inscrire");
        add(registerButton);

        errorLabel = new JLabel("", SwingConstants.CENTER);
        errorLabel.setForeground(Color.RED);
        add(errorLabel);

        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateForm();
            }
        });

        showPasswordButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                passwordField.setEchoChar((char) 0);
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                passwordField.setEchoChar('*');
            }
        });

        setVisible(true);
    }

    private void validateForm() {
        String username = usernameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        if (!username.matches("^[^\\s]{2,}$")) {
            errorLabel.setText("Username invalide (min. 2 caractères, sans espace)");
            return;
        }

        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
        if (!Pattern.matches(emailRegex, email)) {
            errorLabel.setText("Email invalide");
            return;
        }

        String passwordRegex = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$";
        if (!Pattern.matches(passwordRegex, password)) {
            errorLabel.setText("Mot de passe invalide (8+ caractères, 1 maj, 1 min, 1 chiffre)");
            return;
        }

        errorLabel.setForeground(Color.GREEN);
        errorLabel.setText("Inscription réussie !");
    }
}