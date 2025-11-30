package ma.banque.model;

/**
 * Modèle représentant un compte bancaire.
 */
public class Compte {
    private int numeroCompte;
    private String codeCompte;
    private double solde;
    private int clientId;

    /**
     * Constructeur complet.
     */
    public Compte(int numeroCompte, String codeCompte, double solde, int clientId) {
        this.numeroCompte = numeroCompte;
        this.codeCompte = codeCompte;
        this.solde = solde;
        this.clientId = clientId;
    }

    /**
     * Constructeur simplisé.
     */
    public Compte(int numeroCompte, double solde, int clientId) {
        this.numeroCompte = numeroCompte;
        this.solde = solde;
        this.clientId = clientId;
    }

    // === GETTERS ===
    public int getNumeroCompte() {
        return numeroCompte;
    }

    public String getCodeCompte() {
        return codeCompte;
    }

    public double getSolde() {
        return solde;
    }

    public int getClientId() {
        return clientId;
    }

    // === SETTERS ===
    public void setNumeroCompte(int numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public void setCodeCompte(String codeCompte) {
        this.codeCompte = codeCompte;
    }

    public void setSolde(double solde) {
        this.solde = solde;
    }

    public void setClientId(int clientId) {
        this.clientId = clientId;
    }

    @Override
    public String toString() {
        return "Compte{" +
                "numeroCompte=" + numeroCompte +
                ", codeCompte='" + codeCompte + '\'' +
                ", solde=" + solde +
                ", clientId=" + clientId +
                '}';
    }
}
