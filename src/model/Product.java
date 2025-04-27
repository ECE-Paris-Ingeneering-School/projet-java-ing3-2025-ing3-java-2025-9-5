package model;
/**
 * Modèle représentant un produit dans la boutique.
 * Contient les informations liées à un produit telles que le nom, la marque,
 * le prix, la description et le chemin de l'image associée.
 */

public class Product {
    private int productId;
    private String name;
    private String brand;
    private double price;
    private String imagePath;
    private String description;

    // Constructeur pour la récupération depuis la BDD (avec productId)
    public Product(int productId, String name, String brand, double price, String imagePath, String description) {
        this.productId = productId;
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.imagePath = imagePath;
        this.description = description;
    }

    // Constructeur pour l'ajout (on ne renseigne pas l'ID qui est auto-incrémenté dans la BDD)
    public Product(String name, String brand, double price, String imagePath, String description) {
        this.name = name;
        this.brand = brand;
        this.price = price;
        this.imagePath = imagePath;
        this.description = description;
    }

    // Getters et setters
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
