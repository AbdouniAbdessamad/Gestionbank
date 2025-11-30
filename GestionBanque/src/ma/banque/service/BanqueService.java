package ma.banque.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import ma.banque.model.Client;
import ma.banque.model.Compte;
import ma.banque.model.Operation;
import utils.DBConnection;
import utils.DataStore;

/**
 * Service métier pour gérer les comptes et les opérations bancaires.
 * Toute la persistance est réalisée via JDBC (pas de couche DAO séparée).
 */
public class BanqueService {

    /**
     * Crée un nouveau client ET son compte bancaire.
     *
     * @param nom          nom du client
     * @param email        email du client
     * @param telephone    téléphone du client
     * @param soldeInitial solde initial du compte
     * @return le compte créé, ou null en cas d'erreur
     */
    public Compte createClientAndCompte(String nom, String email, String telephone, double soldeInitial) {
        if (nom == null || nom.trim().isEmpty()) {
            System.err.println("Erreur : nom du client obligatoire");
            return null;
        }

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            // 1. Créer le client
            String sqlClient = "INSERT INTO Client(nom, email, telephone) VALUES (?, ?, ?)";
            int clientId;

            try (PreparedStatement ps = conn.prepareStatement(sqlClient, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, nom);
                ps.setString(2, email != null ? email : "");
                ps.setString(3, telephone != null ? telephone : "");
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (!rs.next()) {
                        conn.rollback();
                        return null;
                    }
                    clientId = rs.getInt(1);
                }
            }

            // 2. Générer un code compte unique
            String codeCompte = generateCodeCompte();

            // 3. Créer le compte
            String sqlCompte = "INSERT INTO Compte(code_compte, solde, client_id) VALUES (?, ?, ?)";
            int numeroCompte;

            try (PreparedStatement ps = conn.prepareStatement(sqlCompte, PreparedStatement.RETURN_GENERATED_KEYS)) {
                ps.setString(1, codeCompte);
                ps.setDouble(2, Math.max(soldeInitial, 0.0));
                ps.setInt(3, clientId);
                ps.executeUpdate();

                try (ResultSet rs = ps.getGeneratedKeys()) {
                    if (!rs.next()) {
                        conn.rollback();
                        return null;
                    }
                    numeroCompte = rs.getInt(1);
                }
            }

            // 4. Enregistrer l'opération de création
            insererOperation(conn, numeroCompte, "CREATION", soldeInitial);

            conn.commit();

            return new Compte(numeroCompte, codeCompte, soldeInitial, clientId);

        } catch (SQLException e) {
            System.err.println("Erreur lors de la création du client et compte : " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Effectue un dépôt sur un compte.
     *
     * @param numeroCompte numéro du compte
     * @param montant      montant à déposer
     * @return true si succès, false sinon
     */
    public boolean deposer(int numeroCompte, double montant) {
        if (montant <= 0) {
            System.err.println("Erreur : montant doit être positif");
            return false;
        }

        String sqlUpdate = "UPDATE Compte SET solde = solde + ? WHERE numero_compte = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {

            ps.setDouble(1, montant);
            ps.setInt(2, numeroCompte);

            int rows = ps.executeUpdate();
            if (rows > 0) {
                insererOperation(conn, numeroCompte, "DEPOT", montant);
                return true;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors du dépôt : " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Effectue un retrait sur un compte (avec vérification du solde).
     *
     * @param numeroCompte numéro du compte
     * @param montant      montant à retirer
     * @return true si succès, false sinon
     */
    public boolean retirer(int numeroCompte, double montant) {
        if (montant <= 0) {
            System.err.println("Erreur : montant doit être positif");
            return false;
        }

        String sqlSelect = "SELECT solde FROM Compte WHERE numero_compte = ?";
        String sqlUpdate = "UPDATE Compte SET solde = solde - ? WHERE numero_compte = ?";

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            double solde;
            try (PreparedStatement ps = conn.prepareStatement(sqlSelect)) {
                ps.setInt(1, numeroCompte);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        conn.rollback();
                        System.err.println("Erreur : compte non trouvé");
                        return false;
                    }
                    solde = rs.getDouble("solde");
                }
            }

            if (solde < montant) {
                conn.rollback();
                System.err.println("Erreur : solde insuffisant");
                return false;
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlUpdate)) {
                ps.setDouble(1, montant);
                ps.setInt(2, numeroCompte);
                ps.executeUpdate();
            }

            insererOperation(conn, numeroCompte, "RETRAIT", montant);
            conn.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Erreur lors du retrait : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Effectue un virement entre deux comptes.
     *
     * @param numeroCompteSource compte source
     * @param numeroCompteDest   compte destinataire
     * @param montant            montant à virer
     * @return true si succès, false sinon
     */
    public boolean virement(int numeroCompteSource, int numeroCompteDest, double montant) {
        if (montant <= 0) {
            System.err.println("Erreur : montant doit être positif");
            return false;
        }

        if (numeroCompteSource == numeroCompteDest) {
            System.err.println("Erreur : comptes source et destinataire identiques");
            return false;
        }

        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            // Vérifier que les deux comptes existent et que le solde est suffisant
            String sqlCheck = "SELECT solde FROM Compte WHERE numero_compte = ?";
            double soldeSource;

            try (PreparedStatement ps = conn.prepareStatement(sqlCheck)) {
                ps.setInt(1, numeroCompteSource);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        conn.rollback();
                        System.err.println("Erreur : compte source non trouvé");
                        return false;
                    }
                    soldeSource = rs.getDouble("solde");
                }
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlCheck)) {
                ps.setInt(1, numeroCompteDest);
                try (ResultSet rs = ps.executeQuery()) {
                    if (!rs.next()) {
                        conn.rollback();
                        System.err.println("Erreur : compte destinataire non trouvé");
                        return false;
                    }
                }
            }

            if (soldeSource < montant) {
                conn.rollback();
                System.err.println("Erreur : solde insuffisant");
                return false;
            }

            // Effectuer le virement
            String sqlDebit = "UPDATE Compte SET solde = solde - ? WHERE numero_compte = ?";
            String sqlCredit = "UPDATE Compte SET solde = solde + ? WHERE numero_compte = ?";

            try (PreparedStatement ps = conn.prepareStatement(sqlDebit)) {
                ps.setDouble(1, montant);
                ps.setInt(2, numeroCompteSource);
                ps.executeUpdate();
            }

            try (PreparedStatement ps = conn.prepareStatement(sqlCredit)) {
                ps.setDouble(1, montant);
                ps.setInt(2, numeroCompteDest);
                ps.executeUpdate();
            }

            // Enregistrer les opérations
            insererOperation(conn, numeroCompteSource, "VIREMENT_DEBIT", montant);
            insererOperation(conn, numeroCompteDest, "VIREMENT_CREDIT", montant);

            conn.commit();
            return true;

        } catch (SQLException e) {
            System.err.println("Erreur lors du virement : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Récupère un compte par son numéro.
     */
    public Compte getCompte(int numeroCompte) {
        String sql = "SELECT numero_compte, code_compte, solde, client_id FROM Compte WHERE numero_compte = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, numeroCompte);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Compte(
                            rs.getInt("numero_compte"),
                            rs.getString("code_compte"),
                            rs.getDouble("solde"),
                            rs.getInt("client_id"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du compte : " + e.getMessage());
        }
        return null;
    }

    /**
     * Charge le compte courant dans DataStore en fonction du numéro de compte.
     */
    public boolean chargerCompte(int numeroCompte) {
        Compte compte = getCompte(numeroCompte);
        if (compte != null) {
            DataStore.setCurrentCompte(compte);
            return true;
        }
        return false;
    }

    /**
     * Retourne le solde d'un compte.
     */
    public double getSolde(int numeroCompte) {
        String sql = "SELECT solde FROM Compte WHERE numero_compte = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, numeroCompte);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getDouble("solde");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du solde : " + e.getMessage());
        }
        return -1;
    }

    /**
     * Retourne la liste des opérations pour un compte donné.
     */
    public List<Operation> getOperations(int numeroCompte) {
        List<Operation> list = new ArrayList<>();
        String sql = "SELECT operation_id, numero_compte, type_operation, montant, date_operation " +
                "FROM Operation WHERE numero_compte = ? ORDER BY date_operation DESC";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, numeroCompte);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Timestamp ts = rs.getTimestamp("date_operation");
                    LocalDateTime date = ts != null ? ts.toLocalDateTime() : LocalDateTime.now();
                    list.add(new Operation(
                            rs.getInt("operation_id"),
                            rs.getInt("numero_compte"),
                            rs.getString("type_operation"),
                            rs.getDouble("montant"),
                            date));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des opérations : " + e.getMessage());
        }
        return list;
    }

    /**
     * Retourne la liste de TOUS les comptes de la banque.
     */
    public List<Compte> getAllComptes() {
        List<Compte> comptes = new ArrayList<>();
        String sql = "SELECT numero_compte, code_compte, solde, client_id FROM Compte";

        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Compte compte = new Compte(
                        rs.getInt("numero_compte"),
                        rs.getString("code_compte"),
                        rs.getDouble("solde"),
                        rs.getInt("client_id"));
                comptes.add(compte);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de tous les comptes : " + e.getMessage());
        }
        return comptes;
    }

    /**
     * Retourne la liste de TOUS les clients de la banque.
     */
    public List<Client> getAllClients() {
        List<Client> clients = new ArrayList<>();
        String sql = "SELECT client_id, nom, email, telephone FROM Client";

        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Client client = new Client(
                        rs.getInt("client_id"),
                        rs.getString("nom"),
                        rs.getString("email"),
                        rs.getString("telephone"));
                clients.add(client);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de tous les clients : " + e.getMessage());
        }
        return clients;
    }

    /**
     * Retourne la liste de TOUTES les opérations de la banque.
     */
    public List<Operation> getAllOperations() {
        List<Operation> operations = new ArrayList<>();
        String sql = "SELECT operation_id, numero_compte, type_operation, montant, date_operation " +
                "FROM Operation ORDER BY date_operation DESC";

        try (Connection conn = DBConnection.getConnection();
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Timestamp ts = rs.getTimestamp("date_operation");
                LocalDateTime date = ts != null ? ts.toLocalDateTime() : LocalDateTime.now();
                Operation op = new Operation(
                        rs.getInt("operation_id"),
                        rs.getInt("numero_compte"),
                        rs.getString("type_operation"),
                        rs.getDouble("montant"),
                        date);
                operations.add(op);
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération de toutes les opérations : " + e.getMessage());
        }
        return operations;
    }

    // ========== MÉTHODES UTILITAIRES PRIVÉES ==========

    /**
     * Génère un code de compte unique (4 chiffres numériques).
     */
    private String generateCodeCompte() {
        Random random = new Random();
        String codeCompte;
        boolean exists;

        do {
            codeCompte = String.format("%04d", random.nextInt(10000));
            exists = compteCodeExists(codeCompte);
        } while (exists);

        return codeCompte;
    }

    /**
     * Vérifie si un code de compte existe déjà.
     */
    private boolean compteCodeExists(String codeCompte) {
        String sql = "SELECT 1 FROM Compte WHERE code_compte = ?";
        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, codeCompte);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la vérification du code compte : " + e.getMessage());
        }
        return false;
    }

    /**
     * Insère une opération dans la base de données.
     * Cette méthode utilise la connexion fournie (pour transactionalité).
     */
    private void insererOperation(Connection conn, int numeroCompte, String type, double montant) throws SQLException {
        String sqlOperation = "INSERT INTO Operation(numero_compte, type_operation, montant, date_operation) " +
                "VALUES (?, ?, ?, ?)";
        try (PreparedStatement ps = conn.prepareStatement(sqlOperation)) {
            ps.setInt(1, numeroCompte);
            ps.setString(2, type);
            ps.setDouble(3, montant);
            ps.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            ps.executeUpdate();
        }
    }

    /**
     * Récupère un client avec ses comptes.
     */
    public Client getClientWithComptes(int clientId) {
        String sql = "SELECT client_id, nom, email, telephone FROM Client WHERE client_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clientId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Client(
                            rs.getInt("client_id"),
                            rs.getString("nom"),
                            rs.getString("email"),
                            rs.getString("telephone"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du client : " + e.getMessage());
        }
        return null;
    }

    /**
     * Retourne les comptes d'un client.
     */
    public List<Compte> getComptesOfClient(int clientId) {
        List<Compte> comptes = new ArrayList<>();
        String sql = "SELECT numero_compte, code_compte, solde, client_id FROM Compte WHERE client_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clientId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Compte compte = new Compte(
                            rs.getInt("numero_compte"),
                            rs.getString("code_compte"),
                            rs.getDouble("solde"),
                            rs.getInt("client_id"));
                    comptes.add(compte);
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération des comptes du client : " + e.getMessage());
        }
        return comptes;
    }

    /**
     * Récupère un client par son ID.
     */
    public Client getClientById(int clientId) {
        String sql = "SELECT client_id, nom, email, telephone FROM Client WHERE client_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, clientId);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Client(
                            rs.getInt("client_id"),
                            rs.getString("nom"),
                            rs.getString("email"),
                            rs.getString("telephone"));
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du client : " + e.getMessage());
        }
        return null;
    }

    /**
     * Modifie les informations d'un client.
     */
    public boolean modifyClient(int clientId, String nom, String email, String telephone) {
        if (nom == null || nom.trim().isEmpty()) {
            System.err.println("Erreur : nom du client obligatoire");
            return false;
        }

        String sql = "UPDATE Client SET nom = ?, email = ?, telephone = ? WHERE client_id = ?";

        try (Connection conn = DBConnection.getConnection();
                PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, nom);
            ps.setString(2, email != null ? email : "");
            ps.setString(3, telephone != null ? telephone : "");
            ps.setInt(4, clientId);

            int rows = ps.executeUpdate();
            return rows > 0;

        } catch (SQLException e) {
            System.err.println("Erreur lors de la modification du client : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Supprime un client et tous ses comptes et opérations associés.
     */
    public boolean deleteClient(int clientId) {
        try (Connection conn = DBConnection.getConnection()) {
            conn.setAutoCommit(false);

            try {
                // 1. Récupérer les comptes du client
                String sqlGetComptes = "SELECT numero_compte FROM Compte WHERE client_id = ?";
                List<Integer> compteIds = new ArrayList<>();

                try (PreparedStatement ps = conn.prepareStatement(sqlGetComptes)) {
                    ps.setInt(1, clientId);
                    try (ResultSet rs = ps.executeQuery()) {
                        while (rs.next()) {
                            compteIds.add(rs.getInt("numero_compte"));
                        }
                    }
                }

                // 2. Supprimer les opérations pour chaque compte
                String sqlDelOperations = "DELETE FROM Operation WHERE numero_compte = ?";
                for (int compteId : compteIds) {
                    try (PreparedStatement ps = conn.prepareStatement(sqlDelOperations)) {
                        ps.setInt(1, compteId);
                        ps.executeUpdate();
                    }
                }

                // 3. Supprimer les comptes du client
                String sqlDelComptes = "DELETE FROM Compte WHERE client_id = ?";
                try (PreparedStatement ps = conn.prepareStatement(sqlDelComptes)) {
                    ps.setInt(1, clientId);
                    ps.executeUpdate();
                }

                // 4. Supprimer le client
                String sqlDelClient = "DELETE FROM Client WHERE client_id = ?";
                try (PreparedStatement ps = conn.prepareStatement(sqlDelClient)) {
                    ps.setInt(1, clientId);
                    ps.executeUpdate();
                }

                conn.commit();
                return true;

            } catch (SQLException e) {
                conn.rollback();
                throw e;
            }

        } catch (SQLException e) {
            System.err.println("Erreur lors de la suppression du client : " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }
}
