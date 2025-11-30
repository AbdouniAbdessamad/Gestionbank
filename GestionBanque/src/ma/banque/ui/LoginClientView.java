package ma.banque.ui;

import javax.swing.*;
import ma.banque.service.AuthService;

/**
 * Écran de connexion pour les clients.
 */
public class LoginClientView extends JFrame {

    private JTextField nomField;
    private JTextField codeCompteField;

    public LoginClientView() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Connexion - Client");
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
        JLabel titleLabel = UIHelper.createTitleLabel("Connexion Client");
        titleLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 30)));

        // Champ Nom
        mainPanel.add(UIHelper.createStyledLabel("Votre nom"));
        nomField = UIHelper.createStyledTextField();
        nomField.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 35));
        mainPanel.add(nomField);

        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 15)));

        // Champ Code Compte
        mainPanel.add(UIHelper.createStyledLabel("Code de compte"));
        codeCompteField = UIHelper.createStyledTextField();
        codeCompteField.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 35));
        mainPanel.add(codeCompteField);

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
        String nom = nomField.getText();
        String codeCompte = codeCompteField.getText();

        if (nom.isEmpty() || codeCompte.isEmpty()) {
            UIHelper.showError(this, "Erreur", "Veuillez remplir tous les champs");
            return;
        }

        if (AuthService.loginClient(nom, codeCompte)) {
            UIHelper.showSuccess(this, "Succès", "Bienvenue, " + nom + " !");
            dispose();
            new MenuClientView();
        } else {
            UIHelper.showError(this, "Erreur", "Nom ou code de compte incorrect");
            codeCompteField.setText("");
        }
    }
}
