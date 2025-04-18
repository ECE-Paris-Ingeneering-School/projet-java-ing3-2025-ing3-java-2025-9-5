package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import model.Product;
import model.ProductDAO;

public class AdminProductView extends JFrame {
    private JTable productTable;
    private ProductTableModel tableModel;
    private JButton logoutButton;
    private JTextField nameField, brandField, priceField, descriptionField, imagePathField;
    private JButton addButton, removeButton;

    public AdminProductView() {
        setTitle("Gestion des Produits - Administration");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        loadProducts();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // --- Bandeau supérieur avec Déconnexion ---
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutButton = new JButton("Déconnexion");
        topPanel.add(logoutButton);
        add(topPanel, BorderLayout.NORTH);

        // --- Tableau des produits ---
        tableModel = new ProductTableModel();
        productTable = new JTable(tableModel);
        add(new JScrollPane(productTable), BorderLayout.CENTER);

        // --- Panneau de contrôle en bas ---
        JPanel controlPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        controlPanel.setBorder(BorderFactory.createTitledBorder("Ajouter / Retirer un produit"));

        controlPanel.add(new JLabel("Nom:"));
        nameField = new JTextField();
        controlPanel.add(nameField);

        controlPanel.add(new JLabel("Marque:"));
        brandField = new JTextField();
        controlPanel.add(brandField);

        controlPanel.add(new JLabel("Prix:"));
        priceField = new JTextField();
        controlPanel.add(priceField);

        controlPanel.add(new JLabel("Description:"));
        descriptionField = new JTextField();
        controlPanel.add(descriptionField);

        controlPanel.add(new JLabel("Image Path (facultatif):"));
        imagePathField = new JTextField();
        controlPanel.add(imagePathField);

        addButton = new JButton("Ajouter");
        removeButton = new JButton("Retirer");
        controlPanel.add(addButton);
        controlPanel.add(removeButton);

        add(controlPanel, BorderLayout.SOUTH);

        // Action pour ajouter un produit
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Validation des champs obligatoires
                String name = nameField.getText().trim();
                String brand = brandField.getText().trim();
                String desc = descriptionField.getText().trim();
                String imgPath = imagePathField.getText().trim(); // facultatif

                if (name.isEmpty() || brand.isEmpty() || desc.isEmpty()) {
                    JOptionPane.showMessageDialog(
                            AdminProductView.this,
                            "Veuillez remplir les champs Nom, Marque et Description.",
                            "Champs manquants",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }

                double price;
                try {
                    price = Double.parseDouble(priceField.getText().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(
                            AdminProductView.this,
                            "Prix invalide. Veuillez entrer un nombre.",
                            "Erreur de prix",
                            JOptionPane.ERROR_MESSAGE
                    );
                    return;
                }

                // Création et insertion
                Product newProduct = new Product(name, brand, price, imgPath, desc);
                boolean success = ProductDAO.addProduct(newProduct);
                if (success) {
                    JOptionPane.showMessageDialog(
                            AdminProductView.this,
                            "Produit ajouté avec succès !",
                            "Succès",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    clearFields();
                    loadProducts();
                } else {
                    JOptionPane.showMessageDialog(
                            AdminProductView.this,
                            "Erreur lors de l'ajout du produit.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        // Action pour retirer un produit
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int sel = productTable.getSelectedRow();
                if (sel == -1) {
                    JOptionPane.showMessageDialog(
                            AdminProductView.this,
                            "Sélectionnez un produit à retirer.",
                            "Aucun produit sélectionné",
                            JOptionPane.WARNING_MESSAGE
                    );
                    return;
                }
                int productId = (Integer) tableModel.getValueAt(sel, 0);
                boolean success = ProductDAO.removeProduct(productId);
                if (success) {
                    JOptionPane.showMessageDialog(
                            AdminProductView.this,
                            "Produit retiré avec succès !",
                            "Succès",
                            JOptionPane.INFORMATION_MESSAGE
                    );
                    loadProducts();
                } else {
                    JOptionPane.showMessageDialog(
                            AdminProductView.this,
                            "Erreur lors du retrait du produit.",
                            "Erreur",
                            JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });
    }

    /** Charge et affiche les produits depuis la BDD */
    private void loadProducts() {
        List<Product> products = ProductDAO.getAllProducts();
        tableModel.setProducts(products);
    }

    /** Vide les champs de saisie */
    private void clearFields() {
        nameField.setText("");
        brandField.setText("");
        priceField.setText("");
        descriptionField.setText("");
        imagePathField.setText("");
    }

    /** Permet au contrôleur d’ajouter un listener au bouton Déconnexion */
    public void addLogoutButtonListener(ActionListener listener) {
        logoutButton.addActionListener(listener);
    }
}
