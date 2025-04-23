package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;
import javax.swing.table.DefaultTableModel;

import model.User;
import model.UserDAO;
import model.Product;
import model.ProductDAO;
import model.DiscountDAO;
import model.Discount;

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
    private JTextField discountQuantityField, discountPercentField;
    private JButton createDiscountButton;
    private JTextArea discountListArea;
    private JTable discountTable;
    private DefaultTableModel discountTableModel;
    private JButton deleteDiscountButton;

    public AdminView() {
        super("Gestion Produits et Utilisateurs - Admin");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initComponents();
        loadUsers();
        loadProducts();
        loadDiscounts();
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

        // Tableau produits (gauche)
        productTableModel = new ProductTableModel();
        productTable = new JTable(productTableModel);

        JPanel leftPanel = new JPanel(new BorderLayout());
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

        JPanel discountPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        discountPanel.setBorder(BorderFactory.createTitledBorder("Créer une réduction"));
        discountPanel.add(new JLabel("Quantité min:"));
        discountQuantityField = new JTextField();
        discountPanel.add(discountQuantityField);
        discountPanel.add(new JLabel("% de réduction:"));
        discountPercentField = new JTextField();
        discountPanel.add(discountPercentField);
        createDiscountButton = new JButton("Appliquer la réduction");
        discountTableModel = new DefaultTableModel(
                new Object[]{"ID", "Produit ID", "Qté Min", "Prix Réduit"},
                0
        );
        discountTable = new JTable(discountTableModel);

// 2) Encapsulation dans un JScrollPane
        JScrollPane discountScroll = new JScrollPane(discountTable);
        discountScroll.setBorder(BorderFactory.createTitledBorder("Réductions existantes"));
        deleteDiscountButton = new JButton("Supprimer la réduction");

        discountPanel.add(new JLabel(""));
        discountPanel.add(createDiscountButton);

        discountListArea = new JTextArea();
        discountListArea.setEditable(false);
        discountListArea.setBorder(BorderFactory.createTitledBorder("Promotions en cours"));

        JPanel bottomLeftPanel = new JPanel(new BorderLayout());
        bottomLeftPanel.add(productControlPanel, BorderLayout.NORTH);
        bottomLeftPanel.add(discountPanel, BorderLayout.CENTER);
        JPanel discountContainer = new JPanel(new BorderLayout());
        JPanel southPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        southPanel.add(discountScroll);
        southPanel.add(new JScrollPane(discountListArea));
        bottomLeftPanel.add(southPanel, BorderLayout.SOUTH);
        splitPane.setResizeWeight(0.6);
        splitPane.setDividerLocation(0.6);
        discountContainer.add(discountScroll, BorderLayout.CENTER);
        discountContainer.add(deleteDiscountButton, BorderLayout.SOUTH);
        bottomLeftPanel.add(discountContainer, BorderLayout.SOUTH);
        leftPanel.add(bottomLeftPanel, BorderLayout.SOUTH);
        splitPane.setLeftComponent(leftPanel);

        // Panneau utilisateur (droite)
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

        // Actions
        addButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            String brand = brandField.getText().trim();
            String desc = descriptionField.getText().trim();
            String imgPath = imagePathField.getText().trim();

            if (name.isEmpty() || brand.isEmpty() || desc.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Veuillez remplir les champs Nom, Marque et Description.", "Champs manquants", JOptionPane.WARNING_MESSAGE);
                return;
            }

            double price;
            try {
                price = Double.parseDouble(priceField.getText().trim());
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Prix invalide. Veuillez entrer un nombre.", "Erreur de prix", JOptionPane.ERROR_MESSAGE);
                return;
            }

            Product newProduct = new Product(name, brand, price, imgPath, desc);
            boolean success = ProductDAO.addProduct(newProduct);
            if (success) {
                JOptionPane.showMessageDialog(this, "Produit ajouté avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                clearFields();
                loadProducts();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout du produit.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        removeButton.addActionListener(e -> {
            int sel = productTable.getSelectedRow();
            if (sel == -1) {
                JOptionPane.showMessageDialog(this, "Sélectionnez un produit à retirer.", "Aucun produit sélectionné", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int productId = (Integer) productTableModel.getValueAt(sel, 0);
            boolean success = ProductDAO.removeProduct(productId);
            if (success) {
                JOptionPane.showMessageDialog(this, "Produit retiré avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                loadProducts();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors du retrait du produit.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        createDiscountButton.addActionListener(e -> {
            int selectedProductId = getSelectedProductId();
            if (selectedProductId == -1) {
                JOptionPane.showMessageDialog(this, "Veuillez sélectionner un produit.", "Erreur", JOptionPane.WARNING_MESSAGE);
                return;
            }
            int quantity = getDiscountQuantity();
            double percent = getDiscountPercentage();
            if (quantity <= 0 || percent <= 0) {
                JOptionPane.showMessageDialog(this, "Veuillez saisir des valeurs valides pour la quantité et la réduction.", "Erreur", JOptionPane.WARNING_MESSAGE);
                return;
            }
            Product product = ProductDAO.findProductById(selectedProductId);
            if (product == null) {
                JOptionPane.showMessageDialog(this, "Produit introuvable.", "Erreur", JOptionPane.ERROR_MESSAGE);
                return;
            }
            double bulkPrice = product.getPrice() * (1 - percent / 100);
            boolean success = DiscountDAO.addDiscount(selectedProductId, quantity, bulkPrice);
            if (success) {
                JOptionPane.showMessageDialog(this, "Réduction appliquée avec succès !", "Succès", JOptionPane.INFORMATION_MESSAGE);
                loadDiscounts();
            } else {
                JOptionPane.showMessageDialog(this, "Erreur lors de l'application de la réduction.", "Erreur", JOptionPane.ERROR_MESSAGE);
            }
        });

        deleteDiscountButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = discountTable.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(AdminView.this, "Sélectionnez une réduction à supprimer.");
                    return;
                }

                int discountId = (Integer) discountTableModel.getValueAt(row, 0);
                boolean success = DiscountDAO.removeDiscountById(discountId);
                if (success) {
                    JOptionPane.showMessageDialog(AdminView.this, "Réduction supprimée.");
                    loadDiscounts();
                } else {
                    JOptionPane.showMessageDialog(AdminView.this, "Erreur lors de la suppression.");
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

    public void loadDiscounts() {
        discountTableModel.setRowCount(0);
        List<Discount> discounts = DiscountDAO.getAllDiscounts();
        for (Discount d : DiscountDAO.getAllDiscounts()) {
            discountTableModel.addRow(new Object[]{
                    d.getDiscountId(),
                    d.getProductId(),
                    d.getBulkQuantity(),
                    String.format("%.2f €", d.getBulkPrice())
            });
        }
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
    public void addCreateDiscountListener(ActionListener l) {
        createDiscountButton.addActionListener(l);
    }

    public int getSelectedUserId() {
        int row = userTable.getSelectedRow();
        if (row == -1) return -1;
        return (Integer) tableModel.getValueAt(row, 0);
    }

    public int getSelectedProductId() {
        int row = productTable.getSelectedRow();
        if (row == -1) return -1;
        return (Integer) productTableModel.getValueAt(row, 0);
    }

    public int getDiscountQuantity() {
        try {
            return Integer.parseInt(discountQuantityField.getText().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public double getDiscountPercentage() {
        try {
            return Double.parseDouble(discountPercentField.getText().trim());
        } catch (NumberFormatException e) {
            return -1;
        }
    }

    public String getSelectedUserRole() {
        int row = userTable.getSelectedRow();
        if (row == -1) return null;
        return (String) tableModel.getValueAt(row, 4);
    }

}
