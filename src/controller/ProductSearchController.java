package controller;
/**
 * Contrôleur pour la vue de recherche de produits.
 * Gère la liaison entre les entrées de la ProductSearchView
 * et la DAO de recherche de produits (ProductSearchDAO).
 * Récupère les critères de recherche (mot-clé, marque, prix min/max)
 * et met à jour la vue avec les résultats correspondants.
 */
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import model.Product;
import model.ProductSearchDAO;
import view.ProductSearchView;

public class ProductSearchController {
    private ProductSearchView view;

    public ProductSearchController(ProductSearchView view) {
        this.view = view;
        this.view.addSearchButtonListener(new SearchButtonListener());
    }

    class SearchButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String nameKeyword = view.getNameKeyword();
            String brand = view.getBrand();
            double minPrice = view.getMinPrice();
            double maxPrice = view.getMaxPrice();

            List<Product> products = ProductSearchDAO.searchProducts(nameKeyword, brand, minPrice, maxPrice);
            view.updateProductResults(products);
        }
    }
}
