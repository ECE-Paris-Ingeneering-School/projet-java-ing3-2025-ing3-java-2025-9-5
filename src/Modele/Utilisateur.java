package Modele;
public class Utilisateur {
    private int UtilisateurId;
    private String UtilisateurNom;
    private String UtilisateurPrenom;
    private String UtilisateurEmail;
    private String UtilisateurPassword;
    private String UtilisateurType ;

    // Constructeur
    public Utilisateur (int UtilisateurId, String UtilisateurNom, String UtilisateurPrenom,String UtilisateurEmail,String UtilisateurPassword,String UtilisateurType) {
        this.UtilisateurId = UtilisateurId;
        this.UtilisateurNom = UtilisateurNom;
        this.UtilisateurPrenom = UtilisateurPrenom;
        this.UtilisateurEmail = UtilisateurEmail;
        this.UtilisateurPassword=UtilisateurPassword;
        this.UtilisateurType = UtilisateurType;
    }

    // Getters
    public int getUtilisateurId() { return UtilisateurId; }
    public String getUtilisateurNom() { return UtilisateurNom; }
    public String getUtilisateurPrenom() { return UtilisateurPrenom; }
    public String getUtilisateurEmail() { return UtilisateurEmail; }
    public String getUtilisateurPassword() { return UtilisateurPassword; }
    public String getUtilisateurType() { return UtilisateurType; }


}
