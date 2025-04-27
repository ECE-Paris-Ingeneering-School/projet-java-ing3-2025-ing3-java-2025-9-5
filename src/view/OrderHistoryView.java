package view;

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
        JButton viewInvoiceBtn = new JButton("Voir Dernière Facture");
        Style.styleButton(viewInvoiceBtn);
        JPanel buttonPanel = new JPanel();
        Style.stylePanel(buttonPanel);
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        buttonPanel.add(viewInvoiceBtn);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        viewInvoiceBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int row = ordersTable.getSelectedRow();
                if (row == -1) {
                    JOptionPane.showMessageDialog(OrderHistoryView.this,
                            "Sélectionnez d'abord une commande.",
                            "Aucune commande sélectionnée",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                viewInvoiceForCurrentUser();
            }
        });

        ordersTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && ordersTable.getSelectedRow() != -1) {
                    int selectedRow = ordersTable.getSelectedRow();
                    int orderId = (Integer) orderModel.getValueAt(selectedRow, 0);
                    loadDetails(orderId);
                }
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
