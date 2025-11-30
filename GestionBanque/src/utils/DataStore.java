package utils;

import ma.banque.model.Admin;
import ma.banque.model.Client;
import ma.banque.model.Compte;

/**
 * Classe utilitaire pour stocker les informations de session de l'utilisateur connecté.
 *
 * La persistance des données (clients, comptes, opérations) se fait dans la base MySQL
 * via la couche service. DataStore ne gère que les données temporaires en mémoire.
 */
public class DataStore {

    // === DONNÉES DE SESSION ===
    private static Admin currentAdmin;      // Admin connecté (null si client)
    private static Client currentClient;    // Client connecté (null si admin)
    private static Compte currentCompte;    // Compte actuellement consulté

    private DataStore() {
        // Empêche l'instanciation
    }

    // ========== ADMIN ==========
    public static Admin getCurrentAdmin() {
        return currentAdmin;
    }

    public static void setCurrentAdmin(Admin admin) {
        currentAdmin = admin;
    }

    public static boolean isAdminConnected() {
        return currentAdmin != null;
    }

    // ========== CLIENT ==========
    public static Client getCurrentClient() {
        return currentClient;
    }

    public static void setCurrentClient(Client client) {
        currentClient = client;
    }

    public static boolean isClientConnected() {
        return currentClient != null;
    }

    // ========== COMPTE ==========
    public static Compte getCurrentCompte() {
        return currentCompte;
    }

    public static void setCurrentCompte(Compte compte) {
        currentCompte = compte;
    }

    // ========== LOGOUT ==========
    /**
     * Déconnecte l'utilisateur et efface toutes les données de session.
     */
    public static void logout() {
        currentAdmin = null;
        currentClient = null;
        currentCompte = null;
    }

    /**
     * Affiche l'état de la session (utile pour déboguer).
     */
    public static void printSessionInfo() {
        System.out.println("=== Session Info ===");
        System.out.println("Admin connecté: " + (currentAdmin != null ? currentAdmin.getUsername() : "Non"));
        System.out.println("Client connecté: " + (currentClient != null ? currentClient.getNom() : "Non"));
        System.out.println("Compte courant: " + (currentCompte != null ? currentCompte.getNumeroCompte() : "Non"));
        System.out.println("====================");
    }
}

