package DAO;

// import des packages
import Modele.Utilisateur;
import java.util.ArrayList;

/**
 * On utilise une interface ClientDao pour définir les méthodes d'accès aux données de la table Users,
 * indépendamment de la méthode de stockage. On indique juste des noms de méthodes ici.
 */
public interface UtilisateurDAO {
    /**
     * Récupérer de la base de données tous les objets des users dans une liste
     * @return : liste retournée des objets des users récupérés
     */
    public ArrayList<Utilisateur> getAll();

    /**
     Ajouter un nouveau users en paramètre dans la base de données
     @params : users = objet de Users à insérer dans la base de données
     */
    public void ajouter(Utilisateur utilisateur) ;

    /**
     * Permet de chercher et récupérer un objet de Users dans la base de données via son id en paramètre
     * @param : id
     * @return : objet de classe Users cherché et retourné
     */
    public Utilisateur chercher(int id);

    /**
     * Permet de modifier les données du nom de l'objet de la classe Client en paramètre
     * dans la base de données à partir de l'id de cet objet en paramètre
     * @param : client = objet en paramètre de la classe Client à mettre à jour
     * @return : objet client en paramètre mis à jour  dans la base de données à retourner
     */
    public Utilisateur modifier(Utilisateur utilisateur);

    /**
     * Supprimer un objet de la classe Client en paramètre dans la base de données en respectant la contrainte
     * d'intégrité référentielle : en supprimant un client, supprimer aussi en cascade toutes les commandes de la
     * table commander qui ont l'id du client supprimé.
     * @params : client = objet de Client en paramètre à supprimer de la base de données
     */
    public void supprimer (Utilisateur utilisateur);

}
