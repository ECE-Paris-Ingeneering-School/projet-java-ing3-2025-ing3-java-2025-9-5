package view;

import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import model.Order;

public class OrderTableModel extends AbstractTableModel {
    private List<Order> orders = new ArrayList<>();
    private String[] cols = {"ID Commande", "Date", "Montant (€)"};
    private DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public void setOrders(List<Order> orders) {
        this.orders = orders;
        fireTableDataChanged();
    }

    @Override public int getRowCount() { return orders.size(); }
    @Override public int getColumnCount() { return cols.length; }
    @Override public String getColumnName(int i) { return cols[i]; }

    @Override public Object getValueAt(int row, int col) {
        Order o = orders.get(row);
        switch(col) {
            case 0: return o.getOrderId();
            case 1: return fmt.format(o.getOrderDate());
            case 2: return o.getTotalAmount();
            default: return null;
        }
    }
}
