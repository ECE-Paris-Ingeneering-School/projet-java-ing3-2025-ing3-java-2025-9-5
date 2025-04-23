package model;

public class OrderDetail {
    private int productId;
    private String productName;
    private int quantity;
    private double pricePerUnit;
    private String imagePath;  // nouveau champ

    public OrderDetail(int productId, String productName, int quantity, double pricePerUnit, String imagePath) {
        this.productId = productId;
        this.productName = productName;
        this.quantity = quantity;
        this.pricePerUnit = pricePerUnit;
        this.imagePath = imagePath;
    }

    public int getProductId() {
        return productId;
    }

    public String getProductName() {
        return productName;
    }

    public int getQuantity() {
        return quantity;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public String getImagePath() {
        return imagePath;
    }
}
