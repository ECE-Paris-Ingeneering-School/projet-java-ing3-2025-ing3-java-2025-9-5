package model;

public class Product {
    private int productId;
    private String name;
    private String brand;
    private double price;
    private String imagePath;    // Chemin vers l'image du produit
    private String description;  // Description du produit

    public Product(int productId, String name, String brand, double price, String imagePath, String description) {
        this.productId = productId;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.imagePath = imagePath;
        this.description = description;
    }

    // Getters
    public int getProductId() {
        return productId;
    }
    public String getName() {
        return name;
    }
    public String getBrand() {
        return brand;
    }
    public double getPrice() {
        return price;
    }
    public String getImagePath() {
        return imagePath;
    }
    public String getDescription() {
        return description;
    }
}
