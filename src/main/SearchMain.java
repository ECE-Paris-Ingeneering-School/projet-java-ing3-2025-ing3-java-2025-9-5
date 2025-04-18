package main;

import controller.ProductSearchController;
import view.ProductSearchView;
import javax.swing.SwingUtilities;

public class SearchMain {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ProductSearchView view = new ProductSearchView();
            new ProductSearchController(view);
            view.setVisible(true);
        });
    }
}
