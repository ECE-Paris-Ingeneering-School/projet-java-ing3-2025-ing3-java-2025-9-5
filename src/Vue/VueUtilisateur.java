package Vue;

import java.util.ArrayList;
import Modele.Utilisateur;


public class VueUtilisateur {

    public void afficherUtilisateur(Utilisateur utilisateur) {
        // Afficher un produit
        System.out.println("Utilisateur ID : " + utilisateur.getUtilisateurId() + " Nom : " + utilisateur.getUtilisateurNom()
                + " Prénom : " + utilisateur.getUtilisateurPrenom() + "Email: " + utilisateur.getUtilisateurEmail()
                + "Mot de passe: " + utilisateur.getUtilisateurPassword() + "Type d'Utilisateur" + utilisateur.getUtilisateurType());
    }


    public void afficherListeProduits(ArrayList<Utilisateur> utilisateurs) {
        // Afficher la liste des produits
        for (Utilisateur utilisateur : utilisateurs) {
            afficherUtilisateur(utilisateur);
        }
    }
}
