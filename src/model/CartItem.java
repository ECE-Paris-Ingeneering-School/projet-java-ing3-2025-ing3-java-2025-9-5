package model;
/**
 * Représente une ligne d’élément dans le panier,
 * associant un produit à une quantité.
 */
public class CartItem {
    // Produit ajouté au panier
    private Product product;

    // Quantité de ce produit dans le panier
    private int quantity;

    // Constructeur
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Getters et setter
    public Product getProduct() {
        return product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
