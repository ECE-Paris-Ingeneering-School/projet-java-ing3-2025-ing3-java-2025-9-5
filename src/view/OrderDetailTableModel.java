package view;
/**
 * Modèle de table pour afficher les détails d'une commande sous forme de tableau.
 * Ce modèle de table est utilisé pour gérer et afficher les informations liées aux détails d'une commande,
 * y compris l'image du produit, l'ID, le nom, la quantité et le prix unitaire.
 */
import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import model.OrderDetail;

public class OrderDetailTableModel extends AbstractTableModel {
    private List<OrderDetail> details = new ArrayList<>();
    private final String[] cols = {"Image", "ID Produit", "Nom Produit", "Quantité", "Prix Unitaire (€)"};

    public void setDetails(List<OrderDetail> details) {
        this.details = details;
        fireTableDataChanged();
    }

    @Override public int getRowCount() { return details.size(); }
    @Override public int getColumnCount() { return cols.length; }
    @Override public String getColumnName(int i) { return cols[i]; }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        if (columnIndex == 0) {
            return ImageIcon.class;
        }
        return super.getColumnClass(columnIndex);
    }

    @Override
    public Object getValueAt(int row, int col) {
        OrderDetail d = details.get(row);
        switch (col) {
            case 0:
                String path = d.getImagePath();
                if (path != null && !path.isEmpty()) {
                    ImageIcon icon = new ImageIcon(path);
                    
                    Image img = icon.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
                    return new ImageIcon(img);
                }
                return null;
            case 1: return d.getProductId();
            case 2: return d.getProductName();
            case 3: return d.getQuantity();
            case 4: return d.getPricePerUnit();
            default: return null;
        }
    }
}
