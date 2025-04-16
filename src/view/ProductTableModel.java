package view;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import model.Product;

public class ProductTableModel extends AbstractTableModel {
    private List<Product> products;
    private final String[] columnNames = {"ID", "Nom", "Marque", "Prix", "Description", "Image Path"};

    public ProductTableModel() {
        products = new ArrayList<>();
    }

    public void setProducts(List<Product> products) {
        this.products = products;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return products.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Product p = products.get(rowIndex);
        switch (columnIndex) {
            case 0: return p.getProductId();
            case 1: return p.getName();
            case 2: return p.getBrand();
            case 3: return p.getPrice();
            case 4: return p.getDescription();
            case 5: return p.getImagePath();
            default: return null;
        }
    }

    @Override
    public String getColumnName(int col) {
        return columnNames[col];
    }
}
