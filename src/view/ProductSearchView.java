package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import model.Product;

public class ProductSearchView extends JFrame {
    private JTextField nameField;
    private JTextField brandField;
    private JTextField minPriceField;
    private JTextField maxPriceField;
    private JButton searchButton;
    private JTable resultsTable;
    private ProductTableModel tableModel;

    public ProductSearchView() {
        setTitle("Recherche de Produits");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel searchPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        searchPanel.setBorder(BorderFactory.createTitledBorder("Critères de recherche"));

        // Nom
        gbc.gridx = 0;
        gbc.gridy = 0;
        searchPanel.add(new JLabel("Nom :"), gbc);
        nameField = new JTextField(15);
        gbc.gridx = 1;
        searchPanel.add(nameField, gbc);

        // Marque
        gbc.gridx = 0;
        gbc.gridy = 1;
        searchPanel.add(new JLabel("Marque :"), gbc);
        brandField = new JTextField(15);
        gbc.gridx = 1;
        searchPanel.add(brandField, gbc);

        // Prix min
        gbc.gridx = 0;
        gbc.gridy = 2;
        searchPanel.add(new JLabel("Prix min :"), gbc);
        minPriceField = new JTextField(10);
        gbc.gridx = 1;
        searchPanel.add(minPriceField, gbc);

        // Prix max
        gbc.gridx = 0;
        gbc.gridy = 3;
        searchPanel.add(new JLabel("Prix max :"), gbc);
        maxPriceField = new JTextField(10);
        gbc.gridx = 1;
        searchPanel.add(maxPriceField, gbc);

        // Bouton de recherche
        searchButton = new JButton("Rechercher");
        gbc.gridx = 1;
        gbc.gridy = 4;
        gbc.anchor = GridBagConstraints.CENTER;
        searchPanel.add(searchButton, gbc);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(searchPanel, BorderLayout.NORTH);

        // Table des résultats
        tableModel = new ProductTableModel();
        resultsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    public void addSearchButtonListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    public String getNameKeyword() {
        return nameField.getText().trim();
    }

    public String getBrand() {
        return brandField.getText().trim();
    }

    public double getMinPrice() {
        try {
            return Double.parseDouble(minPriceField.getText().trim());
        } catch (NumberFormatException e) {
            return 0;  // Ou une autre valeur par défaut ou une gestion d'erreur
        }
    }

    public double getMaxPrice() {
        try {
            return Double.parseDouble(maxPriceField.getText().trim());
        } catch (NumberFormatException e) {
            return Double.MAX_VALUE; // Exemple, ou tu peux aussi gérer l'erreur autrement
        }
    }

    public void updateProductResults(List<Product> products) {
        tableModel.setProducts(products);
    }
}
