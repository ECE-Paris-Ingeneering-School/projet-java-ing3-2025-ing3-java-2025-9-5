package Vue;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



public class FenetreLogin extends JFrame {
    // Attributs
    private JTextField EmailField;
    private JTextField PasswordField;
    private JButton LogButton;
    private JButton SignButton;

    // Constructeures
    public FenetreLogin() {
        setTitle("Connexion");
        setSize(300,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        EmailField = new JTextField(15);
        JLabel nom = new JLabel("Email :");

        PasswordField = new JTextField(15);
        JLabel password = new JLabel("Mot de passe :");

        LogButton = new JButton("Se connecter");
        SignButton = new JButton ("S'inscrire");

        SignButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SignWindow();
            }
                                    });
        add(nom);
        add(EmailField);

        add(password);
        add(PasswordField);

        add(LogButton);
        add(SignButton);
        setVisible(true);
    }

 public class SignWindow extends JFrame {
        private JTextField NomField;
        private JTextField PrenomField;
        private JTextField EmailField;
        private JTextField PasswordField;


        public SignWindow() {
            setTitle("Fenêtre d'Inscription");
            setSize(300, 300);
            setLayout(new GridLayout(10,1));

            JLabel titre = new JLabel("Bienvenue dans la fenêtre d'inscription\n\n");

            JLabel nom = new JLabel("Nom : ");
            NomField = new JTextField(15);

            JLabel prenom = new JLabel("Prénom : ");
            PrenomField = new JTextField(15);

            JLabel email = new JLabel("Email :");
            EmailField = new JTextField(15);

            JLabel password = new JLabel("Mot de passe :");
            PasswordField = new JTextField(15);

            add(titre);
            add(nom);
            add(NomField);
            add(prenom);
            add(PrenomField);
            add(email);
            add(EmailField);
            add(password);
            add(PasswordField);

            setVisible(true);
        }
    }
}
