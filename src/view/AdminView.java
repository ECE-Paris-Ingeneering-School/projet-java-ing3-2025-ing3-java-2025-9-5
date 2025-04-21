// view/AdminUserView.java
package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import model.User;
import model.UserDAO;
import model.Product;
import model.ProductDAO;

public class AdminView extends JFrame {
    private JTable userTable;
    private UserTableModel tableModel;
    private JButton deleteButton;
    private JButton promoteButton;
    private JButton viewHistoryButton;
    private JButton logoutButton;

    private JTable productTable;
    private ProductTableModel productTableModel;
    private JTextField nameField, brandField, priceField, descriptionField, imagePathField;
    private JButton addButton, removeButton;

    public AdminView() {
        super("Gestion Produits et Utilisateurs - Admin");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        loadUsers();
        loadProducts();
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Bandeau supérieur
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutButton = new JButton("Déconnexion");
        topPanel.add(logoutButton);
        add(topPanel, BorderLayout.NORTH);

        // Split Pane central : Produits à gauche, Utilisateurs à droite
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        splitPane.setResizeWeight(0.5);

        // --- Partie gauche : produits avec formulaire ---
        JPanel leftPanel = new JPanel(new BorderLayout());
        productTableModel = new ProductTableModel();
        productTable = new JTable(productTableModel);
        leftPanel.add(new JScrollPane(productTable), BorderLayout.CENTER);

        JPanel productControlPanel = new JPanel(new GridLayout(3, 4, 10, 10));
        productControlPanel.setBorder(BorderFactory.createTitledBorder("Ajouter / Retirer un produit"));

        nameField = new JTextField(); brandField = new JTextField(); priceField = new JTextField();
        descriptionField = new JTextField(); imagePathField = new JTextField();

        productControlPanel.add(new JLabel("Nom:")); productControlPanel.add(nameField);
        productControlPanel.add(new JLabel("Marque:")); productControlPanel.add(brandField);
        productControlPanel.add(new JLabel("Prix:")); productControlPanel.add(priceField);
        productControlPanel.add(new JLabel("Description:")); productControlPanel.add(descriptionField);
        productControlPanel.add(new JLabel("Image Path (facultatif):")); productControlPanel.add(imagePathField);

        addButton = new JButton("Ajouter");
        removeButton = new JButton("Retirer");
        productControlPanel.add(addButton);
        productControlPanel.add(removeButton);

        leftPanel.add(productControlPanel, BorderLayout.SOUTH);
        splitPane.setLeftComponent(leftPanel);

        // --- Partie droite : utilisateurs ---
        JPanel rightPanel = new JPanel(new BorderLayout());
        tableModel = new UserTableModel();
        userTable = new JTable(tableModel);
        rightPanel.add(new JScrollPane(userTable), BorderLayout.CENTER);

        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        deleteButton = new JButton("Supprimer");
        promoteButton = new JButton("Promouvoir/Démote");
        viewHistoryButton = new JButton("Voir Historique");
        actionPanel.add(deleteButton);
        actionPanel.add(promoteButton);
        actionPanel.add(viewHistoryButton);
        rightPanel.add(actionPanel, BorderLayout.SOUTH);

        splitPane.setRightComponent(rightPanel);
        add(splitPane, BorderLayout.CENTER);

        // --- Actions ---
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText().trim();
                String brand = brandField.getText().trim();
                String desc = descriptionField.getText().trim();
                String imgPath = imagePathField.getText().trim();

                if (name.isEmpty() || brand.isEmpty() || desc.isEmpty()) {
                    JOptionPane.showMessageDialog(AdminView.this, "Veuillez remplir les champs Nom, Marque et Description.", "Champs manquants", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                double price;
                try {
                    price = Double.parseDouble(priceField.getText().trim());
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(AdminView.this, "Prix invalide. Veuillez entrer un nombre.", "Erreur de prix", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                Product newProduct = new Product(name, brand, price, imgPath, desc);
                boolean success = ProductDAO.addProduct(newProduct);
                if (success) {
                    JOptionPane.showMessageDialog(AdminView.this, "Produit ajouté avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    clearFields();
                    loadProducts();
                } else {
                    JOptionPane.showMessageDialog(AdminView.this, "Erreur lors de l'ajout du produit.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        removeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int sel = productTable.getSelectedRow();
                if (sel == -1) {
                    JOptionPane.showMessageDialog(AdminView.this, "Sélectionnez un produit à retirer.", "Aucun produit sélectionné", JOptionPane.WARNING_MESSAGE);
                    return;
                }
                int productId = (Integer) productTableModel.getValueAt(sel, 0);
                boolean success = ProductDAO.removeProduct(productId);
                if (success) {
                    JOptionPane.showMessageDialog(AdminView.this, "Produit retiré avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                    loadProducts();
                } else {
                    JOptionPane.showMessageDialog(AdminView.this, "Erreur lors du retrait du produit.", "Erreur", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void loadUsers() {
        List<User> users = UserDAO.getAllUsers();
        tableModel.setUsers(users);
    }

    public void loadProducts() {
        List<Product> products = ProductDAO.getAllProducts();
        productTableModel.setProducts(products);
    }

    public void clearFields() {
        nameField.setText("");
        brandField.setText("");
        priceField.setText("");
        descriptionField.setText("");
        imagePathField.setText("");
    }

    public void addDeleteListener(ActionListener l) {
        deleteButton.addActionListener(l);
    }
    public void addPromoteListener(ActionListener l) {
        promoteButton.addActionListener(l);
    }
    public void addHistoryListener(ActionListener l) {
        viewHistoryButton.addActionListener(l);
    }
    public void addLogoutButtonListener(ActionListener l) {
        logoutButton.addActionListener(l);
    }

    public int getSelectedUserId() {
        int row = userTable.getSelectedRow();
        if (row == -1) return -1;
        return (Integer) tableModel.getValueAt(row, 0);
    }

    public String getSelectedUserRole() {
        int row = userTable.getSelectedRow();
        if (row == -1) return null;
        return (String) tableModel.getValueAt(row, 4);
    }
}