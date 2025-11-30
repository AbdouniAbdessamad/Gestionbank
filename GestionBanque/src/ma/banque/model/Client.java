package ma.banque.model;

/**
 * Modèle représentant un client de la banque.
 */
public class Client {
    private int clientId;
    private String nom;
    private String email;
    private String telephone;

    /**
     * Constructeur complet.
     */
    public Client(int clientId, String nom, String email, String telephone) {
        this.clientId = clientId;
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
    }

    /**
     * Constructeur pour création (sans ID).
     */
    public Client(String nom, String email, String telephone) {
        this.nom = nom;
        this.email = email;
        this.telephone = telephone;
    }

    // === GETTERS ===
    public int getClientId() {
        return clientId;
    }

    public String getNom() {
        return nom;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephone() {
        return telephone;
    }

    // === SETTERS ===
    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    @Override
    public String toString() {
        return "Client{" +
                "clientId=" + clientId +
                ", nom='" + nom + '\'' +
                ", email='" + email + '\'' +
                ", telephone='" + telephone + '\'' +
                '}';
    }
}
