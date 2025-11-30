package ma.banque.model;

import java.time.LocalDateTime;

/**
 * Modèle représentant une opération bancaire (dépôt, retrait, virement).
 */
public class Operation {
    private int operationId;
    private int numeroCompte;
    private String typeOperation;
    private double montant;
    private LocalDateTime dateOperation;

    /**
     * Constructeur complet.
     */
    public Operation(int operationId, int numeroCompte, String typeOperation, double montant, LocalDateTime dateOperation) {
        this.operationId = operationId;
        this.numeroCompte = numeroCompte;
        this.typeOperation = typeOperation;
        this.montant = montant;
        this.dateOperation = dateOperation;
    }

    /**
     * Constructeur sans ID (pour création).
     */
    public Operation(int numeroCompte, String typeOperation, double montant, LocalDateTime dateOperation) {
        this.numeroCompte = numeroCompte;
        this.typeOperation = typeOperation;
        this.montant = montant;
        this.dateOperation = dateOperation;
    }

    // === GETTERS ===
    public int getOperationId() {
        return operationId;
    }

    public int getNumeroCompte() {
        return numeroCompte;
    }

    public String getTypeOperation() {
        return typeOperation;
    }

    public double getMontant() {
        return montant;
    }

    public LocalDateTime getDateOperation() {
        return dateOperation;
    }

    // === SETTERS ===
    public void setOperationId(int operationId) {
        this.operationId = operationId;
    }

    public void setNumeroCompte(int numeroCompte) {
        this.numeroCompte = numeroCompte;
    }

    public void setTypeOperation(String typeOperation) {
        this.typeOperation = typeOperation;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public void setDateOperation(LocalDateTime dateOperation) {
        this.dateOperation = dateOperation;
    }

    @Override
    public String toString() {
        return "Operation{" +
                "operationId=" + operationId +
                ", numeroCompte=" + numeroCompte +
                ", typeOperation='" + typeOperation + '\'' +
                ", montant=" + montant +
                ", dateOperation=" + dateOperation +
                '}';
    }
}
