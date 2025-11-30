package ma.banque.ui;

import javax.swing.*;
import ma.banque.model.Client;
import utils.DataStore;

/**
 * Menu principal pour les clients.
 */
public class MenuClientView extends JFrame {

    public MenuClientView() {
        initializeUI();
    }

    private void initializeUI() {
        // Récupérer le client connecté
        Client client = DataStore.getCurrentClient();
        String clientName = client != null ? client.getNom() : "Client";

        setTitle("Menu Client - " + clientName);
        setSize(500, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Panneau principal
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(UIHelper.BACKGROUND_COLOR);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Titre avec nom du client
        JLabel titleLabel = UIHelper.createTitleLabel("Bienvenue, " + clientName);
        titleLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 25)));

        // Boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(UIHelper.BACKGROUND_COLOR);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton btnSolde = UIHelper.createStyledButton("Consulter mon solde");
        btnSolde.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        btnSolde.setMaximumSize(new java.awt.Dimension(350, 40));
        btnSolde.addActionListener(e -> {
            dispose();
            new FormConsulterSolde();
        });

        JButton btnDeposer = UIHelper.createStyledButton("Effectuer un dépôt");
        btnDeposer.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        btnDeposer.setMaximumSize(new java.awt.Dimension(350, 40));
        btnDeposer.addActionListener(e -> {
            dispose();
            new FormDeposer();
        });

        JButton btnRetirer = UIHelper.createStyledButton("Effectuer un retrait");
        btnRetirer.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        btnRetirer.setMaximumSize(new java.awt.Dimension(350, 40));
        btnRetirer.addActionListener(e -> {
            dispose();
            new FormRetirer();
        });

        JButton btnHistorique = UIHelper.createStyledButton("Voir mon historique");
        btnHistorique.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        btnHistorique.setMaximumSize(new java.awt.Dimension(350, 40));
        btnHistorique.addActionListener(e -> {
            dispose();
            new FormHistorique();
        });

        JButton btnCode = UIHelper.createStyledButton("Voir mon code");
        btnCode.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        btnCode.setMaximumSize(new java.awt.Dimension(350, 40));
        btnCode.addActionListener(e -> {
            dispose();
            new FormVoirMonCode();
        });

        JButton btnLogout = UIHelper.createDangerButton("Déconnexion");
        btnLogout.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        btnLogout.setMaximumSize(new java.awt.Dimension(350, 40));
        btnLogout.addActionListener(e -> {
            DataStore.logout();
            dispose();
            new HomeView();
        });

        JButton btnHome = UIHelper.createDangerButton("Accueil");
        btnHome.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        btnHome.setMaximumSize(new java.awt.Dimension(350, 40));
        btnHome.addActionListener(e -> {
            dispose();
            new HomeView();
        });

        buttonPanel.add(btnSolde);
        buttonPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 10)));
        buttonPanel.add(btnDeposer);
        buttonPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 10)));
        buttonPanel.add(btnRetirer);
        buttonPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 10)));
        buttonPanel.add(btnHistorique);
        buttonPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 10)));
        buttonPanel.add(btnCode);
        buttonPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 10)));
        buttonPanel.add(btnHome);
        buttonPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 10)));
        buttonPanel.add(btnLogout);

        mainPanel.add(buttonPanel);

        add(mainPanel);
        setVisible(true);
    }
}
