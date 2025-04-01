package Modele;
public class Client {
        private int clientId;
        private String clientNom;
        private String clientMail;

        // constructeur
        public Client (int clientId, String clientNom, String clientMail) {
                this.clientId = clientId;
                this.clientNom = clientNom;
                this.clientMail = clientMail;
        }

        // getters
        public int getClientId() { return clientId; }
        public String getclientNom() { return clientNom; }
        public String getclientMail() { return clientMail; }
}

