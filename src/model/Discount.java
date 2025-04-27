package model;
/**
 * Représente une réduction appliquée sur l'achat en grande quantité d'un produit.
 * Permet d'appliquer un prix spécial lorsqu'une quantité minimale est atteinte.
 *
 * Exemple : 10 produits pour 50€ au lieu de 10 * prixUnitaire.
 */
public class Discount {
    private int discountId;
    private int productId;
    private int bulkQuantity;
    private double bulkPrice;

    public Discount(int discountId, int productId, int bulkQuantity, double bulkPrice) {
        this.discountId = discountId;
        this.productId = productId;
        this.bulkQuantity = bulkQuantity;
        this.bulkPrice = bulkPrice;
    }

    public Discount(int productId, int bulkQuantity, double bulkPrice) {
        this(0, productId, bulkQuantity, bulkPrice);
    }

    // Getters and setters

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getBulkQuantity() {
        return bulkQuantity;
    }

    public void setBulkQuantity(int bulkQuantity) {
        this.bulkQuantity = bulkQuantity;
    }

    public double getBulkPrice() {
        return bulkPrice;
    }

    public void setBulkPrice(double bulkPrice) {
        this.bulkPrice = bulkPrice;
    }
}
