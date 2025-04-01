package Dao;

// import des packages

import Modele.Commander;

import java.sql.*;
import java.util.ArrayList;

/**
 * implémentation MySQL du stockage dans la base de données des méthodes définies dans l'interface
 * CommanderDao.
 */
public class CommanderDAOImpl implements CommanderDAO {
    // attribut privé pour l'objet du DaoFactoru
    private DaoFactory daoFactory;

    // constructeur dépendant de la classe DaoFactory
    public CommanderDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    /**
     * Récupérer de la base de données tous les objets des commandes des produits par les clients dans une liste
     * @return : liste retournée des objets des produits récupérés
     */
    public ArrayList<Commander> getAll() {
        ArrayList<Commander> listeCommandes = new  ArrayList<Commander>();

        /*
            Récupérer la liste des clients de la base de données dans listeProduits
        */
        try {
            // connexion
            Connection connexion = daoFactory.getConnection();;
            Statement statement = connexion.createStatement();

            // récupération de l'ordre de la requete
            ResultSet resultats = statement.executeQuery("select * from commander");

            // récupération du résultat de l'ordre
            ResultSetMetaData rsmd = resultats.getMetaData();

            // 	Se déplacer sur le prochain enregistrement : retourne false si la fin est atteinte
            while (resultats.next()) {
                // récupérer les 3 champs de la table commander
                int clientId = resultats.getInt(1);
                int produitId = resultats.getInt(2);
                int quantite  = resultats.getInt(3);

                // instancier un objet de Produit
                Commander achat = new Commander(clientId, produitId,quantite);

                // ajouter ce produit à listeProduits
                listeCommandes.add(achat);
            }
        }
        catch (SQLException e) {
            //traitement de l'exception
            e.printStackTrace();
            System.out.println("Création de la liste des commandes impossible");
        }

        return listeCommandes;
    }

    @Override
    /**
     Ajouter une nouvelle commande d'un produit par un client en paramètre dans la base de données
     @params : achat = objet de la commande en paramètre à insérer dans la base de données
     */
    public void ajouter(Commander achat) {
        /*
            A COMPLETER
         */

    }

    @Override
    /**
     * Permet de chercher et récupérer un objet de Commander dans la base de données via ses clientID et produitID
     * en paramètres
     * @param : clientID et produitID
     * @return : objet de commande cherché et retourné
     */
    public Commander chercher(int clientID, int produitID){
        Commander achat = null;

        /*
            A COMPLETER
         */

        return achat;
    }

    @Override
    /**
     * Permet de modifier les données du nom de l'objet de la classe Commander en paramètre
     * dans la base de données à partir de clientID et produitID de cet objet en paramètre
     * @param : achat = objet en paramètre de la classe Commander à mettre à jour
     * @return : objet achat en paramètre mis à jour  dans la base de données à retourner
     */
    public Commander modifier(Commander achat) {
        /*
            A COMPLETER
         */

        return achat;
    }

    @Override
    /**
     Supprimer un objet de la classe Commander en paramètre dans la base de données
     @params : product = objet de Produit en paramètre à supprimer de la base de données
     */
    public void supprimer (Commander achat){
        /*
            A COMPLETER
         */

    }
}
