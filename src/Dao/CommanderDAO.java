package Dao;

// import des packages

import Modele.Commander;

import java.util.ArrayList;

/**
 * On utilise une interface CommanderDao pour définir les méthodes d'accès aux données de la table commander,
 * indépendamment de la méthode de stockage. On indique juste des noms de méthodes ici.
 */
public interface CommanderDAO {
     /**
     * Récupérer de la base de données tous les objets des commandes des produits par les clients dans une liste
     * @return : liste retournée des objets des produits récupérés
     */
    public ArrayList<Commander> getAll();

    /**
     Ajouter une nouvelle commande d'un produit par un client en paramètre dans la base de données
     @params : achat = objet de la commande en paramètre à insérer dans la base de données
     */
    public void ajouter(Commander achat);

    /**
     * Permet de chercher et récupérer un objet de Commander dans la base de données via ses clientID et produitID
     * en paramètres
     * @param : clientID et produitID
     * @return : objet de commande cherché et retourné
     */
    public Commander chercher(int clientID, int produitID);

    /**
     * Permet de modifier les données du nom de l'objet de la classe Commander en paramètre
     * dans la base de données à partir de clientID et produitID de cet objet en paramètre
     * @param : achat = objet en paramètre de la classe Commander à mettre à jour
     * @return : objet achat en paramètre mis à jour dans la base de données à retourner
     */
    public Commander modifier(Commander achat);

    /**
     Supprimer un objet de la classe Commander en paramètre dans la base de données
     @params : product = objet de Produit en paramètre à supprimer de la base de données
     */
    public void supprimer (Commander achat);
}
