package Vue;

// import des packages

import Modele.Produit;

import java.util.ArrayList;

public class VueProduit {
    /**
     * Méthode qui affiche un produit
     * @param product = objet d'un produit
     */
    public void afficherProduit(Produit product) {
        // Afficher un produit
        System.out.println("Id produit : " + product.getProduitId() + " Nom : " + product.getProduitNom()
                           + " prix = " + product.getProduitPrix());
    }

    /**
     * Méthode qui affiche la liste des produits
     * @param produits = liste des produits
     */
    public void afficherListeProduits(ArrayList<Produit> produits) {
        // Afficher la liste des produits
        for (Produit product : produits) {
            afficherProduit(product);
        }
    }
}
