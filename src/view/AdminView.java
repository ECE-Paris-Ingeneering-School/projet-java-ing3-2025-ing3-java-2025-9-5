package view;
/**
 * Vue d'administration principale pour la gestion du magasin.
 * Fournit une interface Swing permettant de gérer :
 * - les produits (ajout, suppression, modification),
 * - les réductions sur produits,
 * - les utilisateurs (suppression, modification de rôle, consultation de l'historique),
 * - et l’affichage de statistiques de ventes.
 */
import utils.Style;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.util.List;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import model.*;

public class AdminView extends JFrame {

    // Composants
    private JTable userTable, productTable, discountTable;
    private UserTableModel tableModel;
    private ProductTableModel productTableModel;
    private DefaultTableModel discountTableModel;
    private JTextField nameField, brandField, priceField, descriptionField, imagePathField;
    private JTextField discountQuantityField, discountPercentField;
    private JButton deleteButton, promoteButton, viewHistoryButton;
    private JButton addButton, removeButton, createDiscountButton, deleteDiscountButton;
    private JButton logoutButton;
    private JButton statsButton;


    public AdminView() {
        super("Administration - Gestion du Magasin");
        setSize(1400, 900);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        initUI();
        loadUsers();
        loadProducts();
        loadDiscounts();
    }

    private void initUI() {
        // Configuration principale
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(240, 245, 250));
        setContentPane(mainPanel);

        // Header
        JPanel headerPanel = createHeaderPanel();
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // Contenu principal
        JSplitPane mainSplitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);
        mainSplitPane.setBorder(BorderFactory.createEmptyBorder());
        mainSplitPane.setResizeWeight(0.5); // Répartition égale

        // Partie gauche (Produits + Réductions)
        JPanel leftPanel = new JPanel(new BorderLayout(10, 10));
        leftPanel.setBackground(new Color(240, 245, 250));
        leftPanel.add(createProductPanel(), BorderLayout.NORTH);
        leftPanel.add(createDiscountPanel(), BorderLayout.CENTER);

        // Partie droite (Utilisateurs)
        JPanel rightPanel = createUserPanel();

        mainSplitPane.setLeftComponent(leftPanel);
        mainSplitPane.setRightComponent(rightPanel);

        // Positionnement initial du séparateur
        mainSplitPane.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                mainSplitPane.setDividerLocation(0.6);
                mainSplitPane.removeComponentListener(this);
            }
        });

        mainPanel.add(mainSplitPane, BorderLayout.CENTER);
        setupButtonListeners();
    }

    private void setupButtonListeners() {
        addButton.addActionListener(e -> addProduct());
        removeButton.addActionListener(e -> removeProduct());
        createDiscountButton.addActionListener(e -> createDiscount());
        deleteDiscountButton.addActionListener(e -> deleteDiscount());
    }

    private JPanel createHeaderPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(50, 60, 80)); // Bleu foncé pour le header
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JLabel titleLabel = new JLabel("PANEL D'ADMINISTRATION");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.WEST);

        JPanel eastButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 0));
        eastButtons.setOpaque(false);

        statsButton = new JButton("Statistiques");
        Style.styleButton(statsButton);
        statsButton.addActionListener(e -> showSalesChart());
        statsButton.setBackground(new Color(20, 90, 70));
        statsButton.setForeground(Color.WHITE);

        logoutButton = new JButton("Déconnexion");
        Style.styleButton(logoutButton);
        logoutButton.setBackground(new Color(180, 70, 70));
        logoutButton.setForeground(Color.WHITE);

        eastButtons.add(statsButton);
        eastButtons.add(logoutButton);

        panel.add(eastButtons, BorderLayout.EAST);

        return panel;
    }

    private JPanel createProductPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Gestion des Produits"));
        panel.setBackground(new Color(240, 245, 250));

        // Tableau des produits
        productTableModel = new ProductTableModel();
        productTable = new JTable(productTableModel);
        styleTable(productTable);
        JScrollPane scrollPane = new JScrollPane(productTable);
        scrollPane.setPreferredSize(new Dimension(0, 200));
        panel.add(scrollPane, BorderLayout.CENTER);

        // Formulaire d'ajout
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder("Ajouter/Modifier un produit"));
        formPanel.setBackground(new Color(230, 240, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        String[] labels = {"Nom:", "Marque:", "Prix:", "Description:", "Image:"};
        JTextField[] fields = {
                nameField = new JTextField(15),
                brandField = new JTextField(15),
                priceField = new JTextField(15),
                descriptionField = new JTextField(15),
                imagePathField = new JTextField(15)
        };

        for (int i = 0; i < labels.length; i++) {
            gbc.gridx = 0;
            gbc.gridy = i;
            formPanel.add(createStyledLabel(labels[i]), gbc);

            gbc.gridx = 1;
            formPanel.add(fields[i], gbc);
        }

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonPanel.setBackground(new Color(240, 245, 250));

        addButton = new JButton("Ajouter");
        Style.styleButton(addButton);
        addButton.setBackground(new Color(70, 160, 70));
        addButton.setForeground(Color.WHITE);

        removeButton = new JButton("Supprimer");
        Style.styleButton(removeButton);
        removeButton.setBackground(new Color(180, 70, 70));
        removeButton.setForeground(Color.WHITE);

        buttonPanel.add(addButton);
        buttonPanel.add(removeButton);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(new Color(240, 245, 250));
        southPanel.add(formPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        panel.add(southPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createDiscountPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Gestion des Réductions"));
        panel.setBackground(new Color(240, 245, 250));

        // Tableau des réductions
        discountTableModel = new DefaultTableModel(new Object[]{"ID", "Produit", "Qté Min", "Prix Réduit"}, 0);
        discountTable = new JTable(discountTableModel);
        styleTable(discountTable);
        JScrollPane scrollPane = new JScrollPane(discountTable);
        panel.add(scrollPane, BorderLayout.CENTER);

        // Formulaire de réduction
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(230, 240, 250));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(createStyledLabel("Quantité min:"), gbc);

        gbc.gridx = 1;
        discountQuantityField = new JTextField(8);
        formPanel.add(discountQuantityField, gbc);

        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(createStyledLabel("% réduction:"), gbc);

        gbc.gridx = 1;
        discountPercentField = new JTextField(8);
        formPanel.add(discountPercentField, gbc);

        // Boutons
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonPanel.setBackground(new Color(240, 245, 250));

        createDiscountButton = new JButton("Appliquer");
        Style.styleButton(createDiscountButton);
        createDiscountButton.setBackground(new Color(70, 160, 70));
        createDiscountButton.setForeground(Color.WHITE);

        deleteDiscountButton = new JButton("Supprimer");
        Style.styleButton(deleteDiscountButton);
        deleteDiscountButton.setBackground(new Color(180, 70, 70));
        deleteDiscountButton.setForeground(Color.WHITE);

        buttonPanel.add(createDiscountButton);
        buttonPanel.add(deleteDiscountButton);

        JPanel southPanel = new JPanel(new BorderLayout());
        southPanel.setBackground(new Color(240, 245, 250));
        southPanel.add(formPanel, BorderLayout.CENTER);
        southPanel.add(buttonPanel, BorderLayout.SOUTH);

        panel.add(southPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JPanel createUserPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("Gestion des Utilisateurs"));
        panel.setBackground(new Color(240, 245, 250));

        // Tableau des utilisateurs
        tableModel = new UserTableModel();
        userTable = new JTable(tableModel);
        styleTable(userTable);
        panel.add(new JScrollPane(userTable), BorderLayout.CENTER);

        // Boutons d'action
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonPanel.setBackground(new Color(240, 245, 250));

        deleteButton = new JButton("Supprimer");
        Style.styleButton(deleteButton);
        deleteButton.setBackground(new Color(180, 70, 70));
        deleteButton.setForeground(Color.WHITE);

        promoteButton = new JButton("Modifier rôle");
        Style.styleButton(promoteButton);
        promoteButton.setBackground(new Color(70, 130, 180));
        promoteButton.setForeground(Color.WHITE);

        viewHistoryButton = new JButton("Historique");
        Style.styleButton(viewHistoryButton);
        viewHistoryButton.setBackground(new Color(100, 100, 100));
        viewHistoryButton.setForeground(Color.WHITE);

        buttonPanel.add(viewHistoryButton);
        buttonPanel.add(promoteButton);
        buttonPanel.add(deleteButton);

        panel.add(buttonPanel, BorderLayout.SOUTH);
        return panel;
    }

    private JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        Style.styleLabel(label);
        return label;
    }

    private void styleTable(JTable table) {
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setGridColor(new Color(220, 220, 220));
        table.setSelectionBackground(new Color(200, 220, 255));
        table.setBackground(Color.WHITE);
        table.setFillsViewportHeight(true);

        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(60, 90, 140));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Segoe UI", Font.BOLD, 14));
    }

    // ... (les autres méthodes restent inchangées)
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
        for (Discount d : discounts) {
            discountTableModel.addRow(new Object[]{d.getDiscountId(), d.getProductId(), d.getBulkQuantity(), String.format("%.2f €", d.getBulkPrice())});
        }
    }
    private void showSalesChart() {
        // 1) Prépare le dataset
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String sql =
                "SELECT p.name AS produit, SUM(od.quantity) AS total_qte " +
                        "FROM orderdetails od " +
                        "JOIN products p ON od.product_id = p.product_id " +
                        "GROUP BY p.product_id, p.name";
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/shopping", "root", "");
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String produit = rs.getString("produit");
                int qte = rs.getInt("total_qte");
                dataset.addValue(qte, "Ventes", produit);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Erreur chargement stats : " + ex.getMessage(),
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }
        // 2) Crée et affiche la fenêtre graphique
        JFreeChart barChart = ChartFactory.createBarChart(
                "Ventes par Produit",
                "Produit",
                "Quantité vendue",
                dataset
        );
        ChartPanel chartPanel = new ChartPanel(barChart);
        JFrame chartFrame = new JFrame("Statistiques Ventes");
        chartFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        chartFrame.setContentPane(chartPanel);
        chartFrame.pack();
        chartFrame.setLocationRelativeTo(this);
        chartFrame.setVisible(true);
    }
    public void clearFields() {
        nameField.setText("");
        brandField.setText("");
        priceField.setText("");
        descriptionField.setText("");
        imagePathField.setText("");
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

    private void addProduct() {
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
    }

    private void removeProduct() {
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
    }

    private void createDiscount() {
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
    }

    private void deleteDiscount() {
        int row = discountTable.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Sélectionnez une réduction à supprimer.");
            return;
        }

        int discountId = (Integer) discountTableModel.getValueAt(row, 0);
        boolean success = DiscountDAO.removeDiscountById(discountId);
        if (success) {
            JOptionPane.showMessageDialog(this, "Réduction supprimée.");
            loadDiscounts();
        } else {
            JOptionPane.showMessageDialog(this, "Erreur lors de la suppression.");
        }
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
}
