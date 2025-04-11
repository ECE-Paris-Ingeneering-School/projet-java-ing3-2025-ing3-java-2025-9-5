package DAO;

// import des packages
import Modele.Utilisateur;
import java.sql.*;
import java.util.ArrayList;

/**
 * implémentation MySQL du stockage dans la base de données des méthodes définies dans l'interface
 * ClientDao.
 */
public class UtilisateurDAOImpl implements UtilisateurDAO {
    // attribut privé pour l'objet du DaoFactory
    private DaoFactory daoFactory;

    // constructeur dépendant de la classe DaoFactory
    public UtilisateurDAOImpl(DaoFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    @Override
    /**
     * Récupérer de la base de données tous les objets des clients dans une liste
     * @return : liste retournée des objets des clients récupérés
     */
    public ArrayList<Utilisateur> getAll() {
        ArrayList<Utilisateur> listeUtilisateur = new ArrayList<Utilisateur>();

        /*
            Récupérer la liste des clients de la base de données dans listeUtilisateur
        */
        try {
            // connexion
            Connection connexion = daoFactory.getConnection();;
            Statement statement = connexion.createStatement();

            // récupération des produits de la base de données avec la requete SELECT
            ResultSet resultats = statement.executeQuery("select * from Users");

            // 	Se déplacer sur le prochain enregistrement : retourne false si la fin est atteinte
            while (resultats.next()) {
                // récupérer les champs de la table users dans la base de données
                int user_id = resultats.getInt(1);
                String email = resultats.getString(2);
                String password = resultats.getString(3);
                String first_name = resultats.getString(4);
                String last_name = resultats.getString(5);
                String user_type = resultats.getString(6);


                // instancier un objet de Produit avec ces 3 champs en paramètres
                Utilisateur utilisateur = new Utilisateur(user_id,email,password,first_name,last_name,user_type);

                // ajouter ce produit à listeProduits
                listeUtilisateur.add(utilisateur);
            }
        }
        catch (SQLException e) {
            //traitement de l'exception
            e.printStackTrace();
            System.out.println("Création de la liste de clients impossible");
        }

        return listeUtilisateur;
    }

    /**
     Ajouter un nouveau client en paramètre dans la base de données
     @params : client = objet de Client à insérer dans la base de données
     */
    public void ajouter(Utilisateur utilisateur){
        try {
            Connection connexion = daoFactory.getConnection();
            PreparedStatement preparedStatement = connexion.prepareStatement("INSERT INTO Users (first_name,last_name,email,password) VALUES (?, ?, ?, ?)");
            preparedStatement.setString(1, utilisateur.getUtilisateurNom());
            preparedStatement.setString(2, utilisateur.getUtilisateurEmail());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ajout du client impossible");
        }

    }

    /**
     * Permet de chercher et récupérer un objet de Client dans la base de données via son id en paramètre
     * @param : id
     * @return : objet de classe Client cherché et retourné
     */
    public Utilisateur chercher(int id)  {
        Utilisateur client = null;

        try {
            // connexion
            Connection connexion = daoFactory.getConnection();;
            Statement statement = connexion.createStatement();

            // Exécution de la requête SELECT pour récupérer le client de l'id dans la base de données
            ResultSet resultats = statement.executeQuery("select * from clients where clientID="+id);

            // 	Se déplacer sur le prochain enregistrement : retourne false si la fin est atteinte
            while (resultats.next()) {
                // récupérer les 3 champs de la table produits dans la base de données
                int user_id = resultats.getInt(1);
                String email = resultats.getString(2);
                String password = resultats.getString(3);
                String first_name = resultats.getString(4);
                String last_name = resultats.getString(5);
                String user_type = resultats.getString(6);

                // Si l'id du client est trouvé, l'instancier et sortir de la boucle
                if (id == user_id) {
                    // instancier un objet de Produit avec ces 3 champs en paramètres
                    client = new Utilisateur(user_id,email,password,first_name,last_name,user_type);
                    break;
                }
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Client non trouvé dans la base de données");
        }

        return client;
    }

    /**
     * Permet de modifier les données du nom de l'objet de la classe Client en paramètre
     * dans la base de données à partir de l'id de cet objet en paramètre
     * @param : client = objet en paramètre de la classe Client à mettre à jour à partir de son id
     * @return : objet client en paramètre mis à jour  dans la base de données à retourner
     */
    public Utilisateur modifier(Utilisateur utilisateur) {
        try {
            Connection connexion = daoFactory.getConnection();
            PreparedStatement preparedStatement = connexion.prepareStatement("UPDATE clients SET nom = ?, email = ? WHERE clientID = ?");
            preparedStatement.setString(1, utilisateur.getUtilisateurNom());
            preparedStatement.setString(2, utilisateur.getUtilisateurEmail());
            preparedStatement.setInt(3, utilisateur.getUtilisateurId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Modification du client impossible");
        }
        return utilisateur;
    }

    @Override
    /**
     * Supprimer un objet de la classe Client en paramètre dans la base de données en respectant la contrainte
     * d'intégrité référentielle : en supprimant un client, supprimer aussi en cascade toutes les commandes de la
     * table commander qui ont l'id du client supprimé.
     * @params : client = objet de Client en paramètre à supprimer de la base de données
     */
    public void supprimer (Utilisateur utilisateur) {
        try {
            Connection connexion = daoFactory.getConnection();
            PreparedStatement deleteCommandes = connexion.prepareStatement("DELETE FROM commander WHERE clientID = ?");
            deleteCommandes.setInt(1, utilisateur.getUtilisateurId());
            deleteCommandes.executeUpdate();

            PreparedStatement deleteClient = connexion.prepareStatement("DELETE FROM clients WHERE clientID = ?");
            deleteClient.setInt(1, utilisateur.getUtilisateurId());
            deleteClient.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Suppression du client impossible");
        }
    }
}


