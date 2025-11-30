package ma.banque.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import ma.banque.model.Admin;
import ma.banque.model.Client;
import utils.DBConnection;
import utils.DataStore;

/**
 * Service d'authentification pour Admin et Client.
 * Toute interaction avec la base de données utilise JDBC avec DBConnection.
 */
public class AuthService {

    /**
     * Authentifie un administrateur/agent bancaire.
     *
     * @param username nom d'utilisateur
     * @param password mot de passe
     * @return true si l'authentification réussit
     */
    public static boolean loginAdmin(String username, String password) {
        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            return false;
        }

        String sql = "SELECT admin_id, username, password, role FROM Admin WHERE username = ? AND password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, password);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int adminId = rs.getInt("admin_id");
                    String role = rs.getString("role");

                    Admin admin = new Admin(adminId, username, password, role);
                    DataStore.setCurrentAdmin(admin);
                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'authentification admin : " + e.getMessage());
        }
        return false;
    }

    /**
     * Authentifie un client en vérifiant son nom et son code de compte.
     *
     * @param nom nom du client
     * @param codeCompte code du compte (depuis la table Compte)
     * @return true si le client existe avec ce code de compte
     */
    public static boolean loginClient(String nom, String codeCompte) {
        if (nom == null || nom.trim().isEmpty() || 
            codeCompte == null || codeCompte.trim().isEmpty()) {
            return false;
        }

        String sql = "SELECT c.client_id, c.nom, c.email, c.telephone, co.numero_compte " +
                     "FROM Client c " +
                     "JOIN Compte co ON c.client_id = co.client_id " +
                     "WHERE c.nom = ? AND co.code_compte = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nom);
            ps.setString(2, codeCompte);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    int clientId = rs.getInt("client_id");
                    String nomClient = rs.getString("nom");
                    String email = rs.getString("email");
                    String telephone = rs.getString("telephone");
                    int numeroCompte = rs.getInt("numero_compte");

                    Client client = new Client(clientId, nomClient, email, telephone);
                    DataStore.setCurrentClient(client);

                    // Charger le compte dans la session
                    BanqueService service = new BanqueService();
                    service.chargerCompte(numeroCompte);

                    return true;
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'authentification client : " + e.getMessage());
        }
        return false;
    }

    /**
     * Crée un nouveau compte admin.
     *
     * @param username nom d'utilisateur
     * @param password mot de passe
     * @param role rôle (par défaut "ADMIN")
     * @return l'admin créé ou null en cas d'erreur
     */
    public static Admin createAdmin(String username, String password, String role) {
        if (username == null || username.trim().isEmpty() || 
            password == null || password.trim().isEmpty()) {
            return null;
        }

        String sql = "INSERT INTO Admin(username, password, role) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, role != null ? role : "ADMIN");

            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int adminId = rs.getInt(1);
                        return new Admin(adminId, username, password, role != null ? role : "ADMIN");
                    }
                }
            }
        } catch (SQLException e) {
            if (e.getMessage().contains("Duplicate entry")) {
                System.err.println("Erreur : Ce nom d'utilisateur existe déjà.");
            } else {
                System.err.println("Erreur lors de la création d'admin : " + e.getMessage());
            }
        }
        return null;
    }

    /**
     * Enregistre un nouveau client.
     *
     * @param nom nom du client
     * @param email email du client
     * @param telephone numéro de téléphone
     * @return le client créé ou null en cas d'erreur
     */
    public static Client registerClient(String nom, String email, String telephone) {
        if (nom == null || nom.trim().isEmpty()) {
            return null;
        }

        String sql = "INSERT INTO Client(nom, email, telephone) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)) {

            ps.setString(1, nom);
            ps.setString(2, email != null ? email : "");
            ps.setString(3, telephone != null ? telephone : "");

            int affected = ps.executeUpdate();
            if (affected > 0) {
                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (rs.next()) {
                        int clientId = rs.getInt(1);
                        return new Client(clientId, nom, email != null ? email : "", telephone != null ? telephone : "");
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'enregistrement du client : " + e.getMessage());
        }
        return null;
    }

    /**
     * Vérifie si un client existe par son nom.
     */
    public static boolean clientExists(String nom) {
        if (nom == null || nom.trim().isEmpty()) {
            return false;
        }

        String sql = "SELECT 1 FROM Client WHERE nom = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nom);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification du client : " + e.getMessage());
        }
        return false;
    }

    /**
     * Vérifie si un code de compte existe.
     */
    public static boolean compteExists(String codeCompte) {
        if (codeCompte == null || codeCompte.trim().isEmpty()) {
            return false;
        }

        String sql = "SELECT 1 FROM Compte WHERE code_compte = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, codeCompte);

            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification du compte : " + e.getMessage());
        }
        return false;
    }
}

