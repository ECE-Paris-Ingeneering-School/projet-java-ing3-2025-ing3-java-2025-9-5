package Vue;

// import des packages

import Dao.ClientDAOImpl;
import Dao.DaoFactory;
import Dao.ProduitDAOImpl;
import Modele.Client;
import Modele.Commander;
import Modele.Produit;

import java.util.ArrayList;

public class VueCommander {
    /**
     * Méthode qui affiche une commande
     *
     * @param achat objet de la classe Commander et dao objet de la classe DaoFactory
     */
    public void afficherCommande(Commander achat, DaoFactory dao) {
        // Récupérer un clientID du getter getClientId de l'objet achat
        int clientID = achat.getClientId();

        // Instancier un objet de la classe ClientDAOImpl avec l'objet dao de DaoFactoru en paramètre
        ClientDAOImpl clidaoimpl = new ClientDAOImpl(dao);

        // Instancier un objet de la classe Client avec le clientID en paramètre
        Client client = clidaoimpl.chercher(clientID);

        // Récupérer un produitID du getter getProduitId de l'objet achat
        int produitID = achat.getProduitId();

        // Récupérer la quantite du getter getQuantite de l'objet achat
        int quantite = achat.getQuantite();

        // Instancier un objet de la classe ProduitDAOImpl avec l'objet dao de DaoFactoru en paramètre
        ProduitDAOImpl prodaoimpl = new ProduitDAOImpl(dao);

        // Instancier un objet de la classe Produit avec le produitID en paramètre
        Produit product = prodaoimpl.chercher(produitID);

        // Afficher les informations du client et du produit pour l'objet achat en paramètre
        System.out.println("Id Client : " + clientID + " Nom : " + client.getclientNom() + " Mail : "+ client.getclientMail()
                           + " commande Id Produit : " + produitID + " Nom : " + product.getProduitNom()
                           + " prix = " + product.getProduitPrix() + " quantité = " + quantite);
    }

    /**
     * Méthode qui affiche la liste des commandes
     * @param achats liste des produits et dao objet de la classe DaoFactory
     */

    public void afficherListeCommandes(ArrayList<Commander> achats, DaoFactory dao) {
        // Afficher la liste des produits
        for (Commander achat : achats) {
            //afficherCommande(achat);
            afficherCommande(achat, dao);
        }
    }
}
