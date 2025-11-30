package ma.banque;

import utils.DBConnection;

/**
 * Classe de test pour vérifier que la connexion à la base de données fonctionne.
 * À exécuter avant de lancer l'application.
 */
public class TestConnexion {
    public static void main(String[] args) {
        System.out.println("=== Test de Connexion à la Base de Données ===\n");
        
        if (DBConnection.testConnection()) {
            System.out.println("\n✓ Vous pouvez maintenant lancer l'application !");
            System.out.println("  Exécutez : java ma.banque.ui.Main");
        } else {
            System.out.println("\n✗ La connexion a échoué.");
            System.out.println("  Veuillez :");
            System.out.println("  1. Vérifier que MySQL est démarré");
            System.out.println("  2. Vérifier la base de données 'GestionBanque' existe");
            System.out.println("  3. Vérifier les paramètres dans DBConnection.java");
        }
    }
}

