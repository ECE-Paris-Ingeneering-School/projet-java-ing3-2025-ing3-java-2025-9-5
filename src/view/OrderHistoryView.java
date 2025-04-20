package view;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.List;
import model.Order;
import model.OrderDetail;
import model.OrderHistoryDAO;

public class OrderHistoryView extends JFrame {
    private JTable ordersTable, detailsTable;
    private OrderTableModel orderModel;
    private OrderDetailTableModel detailModel;

    public OrderHistoryView(int userId) {
        super("Historique des Commandes");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initComponents();
        loadOrders(userId);
    }

    private void initComponents() {
        setLayout(new BorderLayout());

        // Panel pour les deux tableaux
        JSplitPane split = new JSplitPane(JSplitPane.VERTICAL_SPLIT);
        split.setResizeWeight(0.4);

        // 1) Tableau des commandes
        orderModel = new OrderTableModel();
        ordersTable = new JTable(orderModel);
        split.setTopComponent(new JScrollPane(ordersTable));

        // 2) Tableau des détails
        detailModel = new OrderDetailTableModel();
        detailsTable = new JTable(detailModel);
        split.setBottomComponent(new JScrollPane(detailsTable));

        detailModel = new OrderDetailTableModel();
        detailsTable = new JTable(detailModel);
        detailsTable.setRowHeight(60);  // pour afficher l’image
        split.setBottomComponent(new JScrollPane(detailsTable));

        add(split, BorderLayout.CENTER);

        // Quand l'utilisateur sélectionne une commande, on recharge les détails
        ordersTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting() && ordersTable.getSelectedRow() != -1) {
                    int row = ordersTable.getSelectedRow();
                    int orderId = (Integer) orderModel.getValueAt(row, 0);
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
}
