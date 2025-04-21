package view;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import model.Order;
import model.OrderDetail;
import model.OrderHistoryDAO;
import model.User;

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
        setLayout(new BorderLayout());

        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        split.setResizeWeight(0.4);

        // Tableau des commandes
        orderModel = new OrderTableModel();
        ordersTable = new JTable(orderModel);
        split.setTopComponent(new JScrollPane(ordersTable));

        // Tableau des détails
        detailModel = new OrderDetailTableModel();
        detailsTable = new JTable(detailModel);
        detailsTable.setRowHeight(60);
        split.setBottomComponent(new JScrollPane(detailsTable));

        add(split, BorderLayout.CENTER);

        // Bouton pour afficher la facture
        JButton viewInvoiceBtn = new JButton("Voir Facture");
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
                // Ouvre la facture pour l'utilisateur
                viewInvoiceForCurrentUser();
            }
        });
        add(viewInvoiceBtn, BorderLayout.SOUTH);

        // Lorsque l'on sélectionne une commande, charger ses détails
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
