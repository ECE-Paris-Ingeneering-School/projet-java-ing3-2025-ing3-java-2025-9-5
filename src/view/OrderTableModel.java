package view;

import javax.swing.table.AbstractTableModel;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.DiscountDAO;
import model.Order;
import model.OrderDetail;
import model.OrderHistoryDAO;

public class OrderTableModel extends AbstractTableModel {
    private List<Order> orders = new ArrayList<>();
    private String[] cols = {"ID Commande", "Date", "Total promo (€)"};
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
            case 0:
                return o.getOrderId();
            case 1:
                return fmt.format(o.getOrderDate());
            case 2:
                List<OrderDetail> details = OrderHistoryDAO.getOrderDetails(o.getOrderId());
                double totalPromo = 0;
                for (OrderDetail d : details) {
                    int pid       = d.getProductId();
                    int qty       = d.getQuantity();
                    double up     = d.getPricePerUnit();
                    int bulkQty   = DiscountDAO.getBulkQuantity(pid);
                    double promoPU= DiscountDAO.getDiscountedPrice(pid, qty);
                    double line   = (bulkQty > 0 && promoPU > 0)
                            ? promoPU * qty
                            : up * qty;
                    totalPromo   += line;
                }
                return String.format("%.2f", totalPromo);
            default:
                return null;
        }
    }

    public Order getOrderAt(int row) {
        return orders.get(row);
    }
}
