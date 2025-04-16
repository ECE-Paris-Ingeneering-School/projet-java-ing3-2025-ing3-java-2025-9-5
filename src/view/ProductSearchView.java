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
    private ProductTableModel tableModel; // Modèle de table personnalisé pour les produits

    public ProductSearchView() {
        setTitle("Recherche de Produits");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        JPanel searchPanel = new JPanel(new GridLayout(2, 5, 10, 10));
        searchPanel.setBorder(BorderFactory.createTitledBorder("Critères de recherche"));

        searchPanel.add(new JLabel("Nom:"));
        nameField = new JTextField();
        searchPanel.add(nameField);

        searchPanel.add(new JLabel("Marque:"));
        brandField = new JTextField();
        searchPanel.add(brandField);

        searchPanel.add(new JLabel("")); // Espace vide

        searchPanel.add(new JLabel("Prix min:"));
        minPriceField = new JTextField();
        searchPanel.add(minPriceField);

        searchPanel.add(new JLabel("Prix max:"));
        maxPriceField = new JTextField();
        searchPanel.add(maxPriceField);

        searchButton = new JButton("Rechercher");
        searchPanel.add(searchButton);

        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(searchPanel, BorderLayout.NORTH);

        tableModel = new ProductTableModel();
        resultsTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        getContentPane().add(scrollPane, BorderLayout.CENTER);
    }

    // Méthode permettant d'ajouter l'écouteur au bouton de recherche
    public void addSearchButtonListener(ActionListener listener) {
        searchButton.addActionListener(listener);
    }

    // Getters pour récupérer les critères de recherche saisis
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
            return 0;
        }
    }

    public double getMaxPrice() {
        try {
            return Double.parseDouble(maxPriceField.getText().trim());
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    // Méthode pour mettre à jour les résultats de la recherche dans le tableau
    public void updateProductResults(List<Product> products) {
        tableModel.setProducts(products);
    }
}
