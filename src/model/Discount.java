package model;

/**
 * Represents a bulk discount for a product.
 */
public class Discount {
    private int discountId;
    private int productId;
    private int bulkQuantity;
    private double bulkPrice;

    /**
     * Constructor including discountId, to use when reading from the database
     */
    public Discount(int discountId, int productId, int bulkQuantity, double bulkPrice) {
        this.discountId = discountId;
        this.productId = productId;
        this.bulkQuantity = bulkQuantity;
        this.bulkPrice = bulkPrice;
    }

    /**
     * Constructor without discountId, for new discounts before insertion in the database
     */
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
