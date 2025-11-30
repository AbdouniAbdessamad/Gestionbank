package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Classe utilitaire responsable de la connexion à la base de données MySQL.
 * Toute la couche service doit passer par cette classe pour obtenir une connexion JDBC.
 *
 * À CONFIGURER : Adaptez les paramètres URL, USER et PASSWORD selon votre configuration locale.
 */
public class DBConnection {

    // ========== CONFIGURATION ==========
    // À adapter en fonction de votre configuration locale MySQL
    private static final String URL = "jdbc:mysql://localhost:3306/GestionBanque?useSSL=false&serverTimezone=UTC";
    private static final String USER = "root";
    private static final String PASSWORD = ""; // Mot de passe MySQL (vide par défaut pour root local)

    private DBConnection() {
        // Empêche l'instanciation de cette classe utilitaire
    }

    /**
     * Retourne une nouvelle connexion JDBC à la base GestionBanque.
     *
     * @return une connexion ouverte vers la base de données
     * @throws SQLException en cas de problème de connexion à la base de données
     */
    public static Connection getConnection() throws SQLException {
        try {
            // Charge le driver MySQL
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.err.println("Erreur : Driver MySQL non trouvé. Assurez-vous que mysql-connector-java est dans le classpath.");
            throw new SQLException("Driver MySQL non trouvé", e);
        }

        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    /**
     * Teste la connexion à la base de données.
     * Utile pour déboguer les problèmes de connexion.
     *
     * @return true si la connexion est réussie, false sinon
     */
    public static boolean testConnection() {
        try (Connection conn = getConnection()) {
            if (conn != null && !conn.isClosed()) {
                System.out.println("✓ Connexion à la base de données réussie !");
                return true;
            }
        } catch (SQLException e) {
            System.err.println("✗ Erreur de connexion à la base de données :");
            System.err.println("  URL: " + URL);
            System.err.println("  Utilisateur: " + USER);
            System.err.println("  Message: " + e.getMessage());
        }
        return false;
    }
}


