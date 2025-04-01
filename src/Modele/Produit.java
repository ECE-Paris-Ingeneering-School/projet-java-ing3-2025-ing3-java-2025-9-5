package Modele;
public class Produit {
    private int produitId;
    private String produitNom;
    private double produitPrix;

    // constructeur
    public Produit(int produitId, String produitNom, double produitPrix) {
        this.produitId = produitId;
        this.produitNom = produitNom;
        this.produitPrix = produitPrix;
    }

    // getters
    public int getProduitId() { return produitId; }
    public String getProduitNom() { return produitNom; }
    public double getProduitPrix() { return produitPrix; }

    //Question 7
    // setter
    public void setProduitPrix(double produitPrix) {
        this.produitPrix = produitPrix;
    }
}
