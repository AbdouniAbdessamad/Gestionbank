package ma.banque.model;

/**
 * Modèle représentant un administrateur/agent bancaire.
 */
public class Admin {
    private int adminId;
    private String username;
    private String password;
    private String role;

    /**
     * Constructeur avec tous les paramètres.
     */
    public Admin(int adminId, String username, String password, String role) {
        this.adminId = adminId;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    /**
     * Constructeur sans ID (pour création).
     */
    public Admin(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // === GETTERS ===
    public int getAdminId() {
        return adminId;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getRole() {
        return role;
    }

    // === SETTERS ===
    public void setAdminId(int adminId) {
        this.adminId = adminId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "adminId=" + adminId +
                ", username='" + username + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}

