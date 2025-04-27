package model;
/**
 * Singleton représentant le panier d'achat de l'utilisateur.
 * Permet d'ajouter, supprimer, vider des produits et de calculer le total.
 * Utilise le pattern singleton pour garantir une seule instance globale.
 */
import java.util.ArrayList;
import java.util.List;

// Classe représentant le panier d'achat (singleton)
public class Cart {
    // Instance unique de la classe Cart
    private static Cart instance;

    // Liste des articles dans le panier
    private List<CartItem> items;

    // Constructeur privé pour empêcher l'instanciation directe
    private Cart() {
        items = new ArrayList<>();
    }

    // Retourne l'instance unique du panier
    public static Cart getInstance() {
        if (instance == null) {
            instance = new Cart();
        }
        return instance;
    }

    // Retourne la liste des articles du panier
    public List<CartItem> getItems() {
        return items;
    }

    // Ajoute un produit au panier. Si le produit est déjà présent, augmente la quantité.
    public void addProduct(Product product, int quantity) {
        for (CartItem item : items) {
            if (item.getProduct().getProductId() == product.getProductId()) {
                item.setQuantity(item.getQuantity() + quantity);
                return;
            }
        }
        items.add(new CartItem(product, quantity));
    }

    // Supprime un produit du panier
    public void removeProduct(Product product) {
        items.removeIf(item -> item.getProduct().getProductId() == product.getProductId());
    }

    // Vide le panier
    public void clear() {
        items.clear();
    }

    // Calcule le prix total du panier
    public double getTotalPrice() {
        double total = 0;
        for (CartItem item : items) {
            total += item.getProduct().getPrice() * item.getQuantity();
        }
        return total;
    }
}
