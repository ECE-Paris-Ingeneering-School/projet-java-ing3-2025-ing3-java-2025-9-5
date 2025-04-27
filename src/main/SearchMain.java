package main;
/**
 * Point d’entrée de l’application de recherche de produits.
 * Initialise l’interface Swing pour la recherche de produits
 * en créant la ProductSearchView et son contrôleur associé.
 */
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
