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

        // Tableau des produits
        tableModel = new ProductTableModel();
        productTable = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(productTable);
        add(tableScrollPane, BorderLayout.CENTER);

        // Panneau de contrôle en bas : zones de saisie et boutons d'action
        JPanel controlPanel = new JPanel();
        controlPanel.setLayout(new GridLayout(3, 4, 10, 10));

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

        controlPanel.add(new JLabel("Image Path:"));
        imagePathField = new JTextField();
        controlPanel.add(imagePathField);

        addButton = new JButton("Ajouter");
        removeButton = new JButton("Retirer");
        controlPanel.add(addButton);
        controlPanel.add(removeButton);

        add(controlPanel, BorderLayout.SOUTH);

        // Action pour ajouter un produit
        addButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String brand = brandField.getText().trim();
                double price = 0;
                try {
                    price = Double.parseDouble(priceField.getText().trim());
                } catch(NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AdminProductView.this, "Prix invalide.");
                    return;
                }
                String description = descriptionField.getText().trim();
                String imagePath = imagePathField.getText().trim();
                // Vous pouvez également gérer le stock ici (stock initial, par exemple 100)
                Product newProduct = new Product(name, brand, price, imagePath, description);
                boolean success = ProductDAO.addProduct(newProduct);
                if(success) {
                    JOptionPane.showMessageDialog(AdminProductView.this, "Produit ajouté avec succès !");
                    clearFields();
                    loadProducts();
                } else {
                    JOptionPane.showMessageDialog(AdminProductView.this, "Erreur lors de l'ajout du produit.");
                }
            }
        });

        // Action pour retirer un produit
        removeButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = productTable.getSelectedRow();
                if(selectedRow == -1) {
                    JOptionPane.showMessageDialog(AdminProductView.this, "Sélectionnez un produit à retirer.");
                } else {
                    // Supposons que l'ID du produit se trouve dans la première colonne
                    int productId = (Integer) tableModel.getValueAt(selectedRow, 0);
                    boolean success = ProductDAO.removeProduct(productId);
                    if(success) {
                        JOptionPane.showMessageDialog(AdminProductView.this, "Produit retiré avec succès !");
                        loadProducts();
                    } else {
                        JOptionPane.showMessageDialog(AdminProductView.this, "Erreur lors du retrait du produit.");
                    }
                }
            }
        });
    }

    private void loadProducts() {
        List<Product> products = ProductDAO.getAllProducts();
        tableModel.setProducts(products);
    }

    private void clearFields() {
        nameField.setText("");
        brandField.setText("");
        priceField.setText("");
        descriptionField.setText("");
        imagePathField.setText("");
    }
}
