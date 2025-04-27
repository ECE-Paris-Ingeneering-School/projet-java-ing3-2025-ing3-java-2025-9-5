package view;
/**
 * Cette classe permet d'afficher l'historique des commandes d'un utilisateur donné
 * et de visualiser les détails des commandes ainsi que les factures associées.
 *
 * Elle comporte :
 * - Un tableau affichant les commandes de l'utilisateur,
 * - Un tableau affichant les détails des articles pour la commande sélectionnée,
 * - Un bouton permettant d'afficher la facture associée à la commande sélectionnée.
 */
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.Order;
import model.OrderDetail;
import model.OrderHistoryDAO;
import model.DiscountDAO;
import model.User;
import utils.Style;

/**
 * Affiche l'historique des commandes pour un utilisateur donné,
 * et permet de visualiser la facture associée.
 */
public class OrderHistoryView extends JFrame {
    private User currentUser;
    private JTable ordersTable;
    private JTable detailsTable;
    private OrderTableModel orderModel;
    private OrderDetailTableModel detailModel;

    public OrderHistoryView(User currentUser) {
        super("Historique des Commandes");
        this.currentUser = currentUser;
        setSize(800, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        loadOrders(currentUser.getUserId());
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        Style.stylePanel(mainPanel);

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        split.setResizeWeight(0.4);
        split.setBorder(null);

        // Tableau des commandes
        orderModel = new OrderTableModel();
        ordersTable = new JTable(orderModel);
        stylizeTable(ordersTable);
        JScrollPane orderScroll = new JScrollPane(ordersTable);
        orderScroll.getViewport().setBackground(Style.PRIMARY_BG);
        split.setTopComponent(orderScroll);

        // Tableau des détails
        detailModel = new OrderDetailTableModel();
        detailsTable = new JTable(detailModel);
        stylizeTable(detailsTable);
        detailsTable.setRowHeight(60);
        JScrollPane detailScroll = new JScrollPane(detailsTable);
        detailScroll.getViewport().setBackground(Style.PRIMARY_BG);
        split.setBottomComponent(detailScroll);

        mainPanel.add(split, BorderLayout.CENTER);

        // Bouton pour afficher la facture
        JButton viewInvoiceBtn = new JButton("Voir la facture");
        Style.styleButton(viewInvoiceBtn);
        JPanel buttonPanel = new JPanel();
        Style.stylePanel(buttonPanel);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.add(viewInvoiceBtn);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        viewInvoiceBtn.addActionListener(e -> {
            int row = ordersTable.getSelectedRow();
            if (row == -1) {
                JOptionPane.showMessageDialog(
                        OrderHistoryView.this,
                        "Sélectionnez d'abord une commande.",
                        "Aucune commande sélectionnée",
                        JOptionPane.WARNING_MESSAGE
                );
                return;
            }
            // Récupère l'objet Order du modèle
            Order selectedOrder = orderModel.getOrderAt(row);
            // Récupère ses détails
            List<OrderDetail> details = OrderHistoryDAO.getOrderDetails(selectedOrder.getOrderId());
            // Ouvre la facture pour cette commande
            InvoiceView iv = new InvoiceView(
                    selectedOrder,
                    details,
                    currentUser.getFirstName()
            );
            iv.setVisible(true);
        });

// Lorsque l’on sélectionne une ligne, on recharge le détail
        ordersTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && ordersTable.getSelectedRow() != -1) {
                int selectedRow = ordersTable.getSelectedRow();
                int orderId = (Integer) orderModel.getValueAt(selectedRow, 0);
                loadDetails(orderId);
            }
        });
    }

    private void stylizeTable(JTable table) {
        table.setFont(Style.DEFAULT_FONT);
        table.setForeground(Style.DARK_BLUE);
        table.setBackground(Style.CREAM);
        table.setGridColor(Style.SKY_BLUE);
        table.setShowGrid(true);
        JTableHeader header = table.getTableHeader();
        header.setFont(Style.BUTTON_FONT.deriveFont(15f));
        header.setBackground(Style.SOFT_RED);
        header.setForeground(Color.WHITE);
        header.setOpaque(true);
    }

    private void loadOrders(int userId) {
        List<Order> orders = OrderHistoryDAO.getOrdersForUser(userId);
        orderModel.setOrders(orders);
    }

    private void loadDetails(int orderId) {
        List<OrderDetail> details = OrderHistoryDAO.getOrderDetails(orderId);
        detailModel.setDetails(details);
    }

    private void viewInvoiceForCurrentUser() {
        InvoiceView.showInvoiceForUser(currentUser);
    }
}
