package Controleur;

// import des packages

import Dao.ClientDAOImpl;
import Dao.CommanderDAOImpl;
import Dao.DaoFactory;
import Dao.ProduitDAOImpl;
import Modele.Client;
import Modele.Commander;
import Modele.Produit;
import Vue.VueClient;
import Vue.VueCommander;
import Vue.VueProduit;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        // Déclaration et instanciation des objets des classes DaoFactory, ProduitDAOImpl, VueProduit,
        // ClientDAOImpl, VueClient, CommanderDAOImpl et VueCommander
        DaoFactory dao = DaoFactory.getInstance("achats", "root", "");
        ProduitDAOImpl prodao = new ProduitDAOImpl(dao);
        VueProduit vuepro = new VueProduit();
        ClientDAOImpl clidao = new ClientDAOImpl(dao);
        VueClient vuecli = new VueClient();
        CommanderDAOImpl comdao = new CommanderDAOImpl(dao);
        VueCommander vuecom = new VueCommander();

        // Récupérer la liste des produits de la base de données avec l'objet prodao de la classe ProduitDAOImpl
        ArrayList<Produit> produits = prodao.getAll();

        // Afficher la liste des produits récupérés avec l'objet vuepro de la classe VueProduit
        vuepro.afficherListeProduits(produits);

        // Récupérer la liste des clients de la base de données avec l'objet clidao de la classe ClientDAOImpl
        ArrayList<Client> clients = clidao.getAll();

        // Afficher la liste des clients récupérés avec l'objet vuecli de la classe VueClient
        vuecli.afficherListeClients(clients);

        // Récupérer la liste des commandes de la base de données avec l'objet comdao de la classe CommanderDAOImpl
        ArrayList<Commander> achats = comdao.getAll();

        // Afficher la liste des commandes récupérées avec l'objet vuecom de la classe VueCommander
        vuecom.afficherListeCommandes(achats, dao);

        // Question 2
    // Création d'un scanner pour lire l'entrée utilisateur
            Scanner scanner = new Scanner(System.in);

    // Demande à l'utilisateur de saisir un nom de produit
            System.out.print("Entrez le nom du produit : ");
            String nomProduit = scanner.nextLine();

    // Demande à l'utilisateur de saisir un prix de produit
            System.out.print("Entrez le prix du produit : ");
            double prixProduit = scanner.nextDouble();

    // Affichage pour vérifier l'entrée
            System.out.println("Produit saisi : " + nomProduit + " - Prix : " + prixProduit);
            
        // Question 3
    // Instancier un objet de Produit avec ce nom et ce prix.
        // Question 3
// Instancier un objet de Produit avec ce nom et ce prix.
        Produit nouveauProduit = new Produit(0, nomProduit, prixProduit);

        // Question 4
    // Ajouter cet objet dans la table produits de la base de données
            ProduitDAOImpl produitDAO = new ProduitDAOImpl(dao);
            produitDAO.ajouter(nouveauProduit);  // Appel de la méthode ajouter pour insérer le produit dans la BDD

    // Affichage pour vérifier
            System.out.println("Produit ajouté avec succès : " + nouveauProduit.getProduitNom() + " - " + nouveauProduit.getProduitPrix() + "€");

            // Question 5 : Saisir un identifiant de produit
            System.out.print("Entrez l'identifiant du produit à rechercher : ");
            int idProduit = scanner.nextInt();

            // Question 6 : Chercher un produit avec le test d'existence
    // Recherche du produit dans la base de données avec l'identifiant saisi
            Produit produitRecherche = prodao.chercher(idProduit);

    // Vérification si le produit existe
            if (produitRecherche == null) {
                System.out.println("Le produit avec l'identifiant " + idProduit + " n'existe pas dans la base de données.");
            } else {
                // Affichage du produit si trouvé
                vuepro.afficherProduit(produitRecherche);
            }

        // Question 7 : Modifier un produit dans la base de données
    // Modifier le prix du produit (vous pouvez choisir n'importe quel prix)
            nouveauProduit.setProduitPrix(29.99); // Nouveau prix pour le produit

    // Appeler la méthode modifier pour mettre à jour le produit dans la base de données
            Produit produitModifie = prodao.modifier(nouveauProduit);

    // Afficher le produit modifié avec la vue
            vuepro.afficherProduit(produitModifie);

    // Affichage pour vérifier la modification
            System.out.println("Produit modifié : " + produitModifie.getProduitNom() + " - Prix : " + produitModifie.getProduitPrix() + "€");

        /*
            A COMPLETER :

            1) Compléter toutes les méthodes de la classe ProduitDAOImpl avec la mention A COMPLETER.
            2) Saisir un nom de produit (String) et son prix (double).
            3) Instancier un objet de Produit avec ce nom et ce prix.
            4) Ajouter cet objet dans la table produits de la base de données : en appelant la méthode ajouter de
               l'objet prodao (voir plus haut) de la classe ProduitDAOImpl avec en paramètre l'objet instancié du
               Produit à l'étape 3).
            5) Saisir un identifiant (int) de produit.
            6) Dans la table produits de la base de données, chercher un produit avec l'identifiant saisi. Appeler la
               méthode chercher de l'objet prodao (voir plus haut) de la classe ProduitDAOImpl, avec l'identifiant saisi
               en paramètre. Puis récupérer l'objet de la classe Produit. Si cet objet est null (vide), afficher que
               l'identifiant de ce produit n'est pas la table produits. Sinon appeler la méthode afficherProduit de
               l'objet vuepro (voir plus haut) de la classe VueProduit, avec l'objet du Produit récupéré en paramètre.
            7) Modifier un produit dans la base de données : à partir de l'objet instancié de Produit à l'étape 3),
               modifier son prix (comme vous voulez), en appelant la méthode modifier de l'objet prodao (voir plus haut)
               de la classe ProduitDAOImpl, avec en paramètre l'objet du Produit. Récupérer cet objet de Produit après
               l'appel de cette méthode modifier. Puis appeler la méthode afficherProduit de l'objet vuepro (voir plus haut)
               de la classe VueProduit, avec en paramètre l'objet de Produit récupéré.
            8) Supprimer un produit de la base de données : à partir de l'objet prodao (voir plus haut) de la classe
               ProduitDAOImpl, appeler la méthode supprimer de cet objet, avec en paramètre l'objet instancié de
               Produit à l'étape 4).
            9) Compléter toutes les méthodes de la classe ClientDAOImpl avec la mention A COMPLETER.
            10) Reprendre les mêmes instructions des étapes 2) à 9) pour les objets et méthodes des classes
                ClientDAOImpl (voir objet clidao plus haut), VueClient (voir objet vuecli plus haut) et Client avec la
                table clients de la base de données.
            11) Compléter toutes les méthodes de la classe CommanderDAOImpl avec la mention A COMPLETER.
            12) Reprendre les mêmes instructions des étapes 2) à 9) pour les objets et méthodes des classes
                CommanderDAOImpl (voir objet comdao plus haut), VueClient (voir objet vuecom plus haut) et Commander
                avec la table commander de la base de données.
         */

        // Fermer ma connexion
        dao.disconnect();
    }
}