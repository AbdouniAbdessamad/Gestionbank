package ma.banque.ui;

import javax.swing.*;
import ma.banque.service.AuthService;

/**
 * Écran de connexion pour les administrateurs.
 */
public class LoginAdminView extends JFrame {

    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginAdminView() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Connexion - Agent Bancaire");
        setSize(450, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Panneau principal
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(UIHelper.BACKGROUND_COLOR);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Titre
        JLabel titleLabel = UIHelper.createTitleLabel("Connexion Agent");
        titleLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 30)));

        // Champ Username
        mainPanel.add(UIHelper.createStyledLabel("Nom d'utilisateur"));
        usernameField = UIHelper.createStyledTextField();
        usernameField.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 35));
        mainPanel.add(usernameField);

        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 15)));

        // Champ Password
        mainPanel.add(UIHelper.createStyledLabel("Mot de passe"));
        passwordField = UIHelper.createStyledPasswordField();
        passwordField.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 35));
        mainPanel.add(passwordField);

        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 25)));

        // Boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(UIHelper.BACKGROUND_COLOR);
        buttonPanel.setLayout(new javax.swing.BoxLayout(buttonPanel, javax.swing.BoxLayout.X_AXIS));

        JButton loginBtn = UIHelper.createStyledButton("Se Connecter");
        loginBtn.addActionListener(e -> handleLogin());

        JButton backBtn = UIHelper.createDangerButton("Retour");
        backBtn.addActionListener(e -> {
            dispose();
            new HomeView();
        });

        JButton homeBtn = UIHelper.createDangerButton("Accueil");
        homeBtn.addActionListener(e -> {
            dispose();
            new HomeView();
        });

        buttonPanel.add(loginBtn);
        buttonPanel.add(Box.createRigidArea(new java.awt.Dimension(10, 0)));
        buttonPanel.add(backBtn);
        buttonPanel.add(Box.createRigidArea(new java.awt.Dimension(10, 0)));
        buttonPanel.add(homeBtn);

        mainPanel.add(buttonPanel);

        add(mainPanel);
        setVisible(true);
    }

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());

        if (username.isEmpty() || password.isEmpty()) {
            UIHelper.showError(this, "Erreur", "Veuillez remplir tous les champs");
            return;
        }

        if (AuthService.loginAdmin(username, password)) {
            UIHelper.showSuccess(this, "Succès", "Bienvenue, " + username + " !");
            dispose();
            new MenuAdminView();
        } else {
            UIHelper.showError(this, "Erreur", "Nom d'utilisateur ou mot de passe incorrect");
            passwordField.setText("");
        }
    }
}
