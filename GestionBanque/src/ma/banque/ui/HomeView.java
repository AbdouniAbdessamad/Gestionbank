package ma.banque.ui;

import java.awt.*;
import javax.swing.*;

/**
 * Écran d'accueil de l'application.
 * Permet au l'utilisateur de choisir entre accès client, admin ou création de
 * compte.
 */
public class HomeView extends JFrame {

    public HomeView() {
        initializeUI();
    }

    private void initializeUI() {
        // Configuration de la fenêtre
        setTitle("Gestion Bancaire - Accueil");
        setSize(550, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Panneau principal
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(UIHelper.BACKGROUND_COLOR);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Titre
        JLabel titleLabel = UIHelper.createTitleLabel("Gestion Bancaire");
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 30)));

        // Sous-titre
        JLabel subtitleLabel = UIHelper.createSubtitleLabel("Système Informatisé");
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(subtitleLabel);

        mainPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // Boutons
        JButton btnClient = UIHelper.createStyledButton("Je suis Client");
        btnClient.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnClient.setMaximumSize(new Dimension(300, 50));

        JButton btnAdmin = UIHelper.createStyledButton("Je suis Agent Bancaire");
        btnAdmin.setAlignmentX(Component.CENTER_ALIGNMENT);
        btnAdmin.setMaximumSize(new Dimension(300, 50));

        // Actions
        btnClient.addActionListener(e -> {
            dispose();
            new LoginClientView();
        });

        btnAdmin.addActionListener(e -> {
            dispose();
            new LoginAdminView();
        });

        // Ajout des boutons
        mainPanel.add(btnClient);
        mainPanel.add(Box.createRigidArea(new Dimension(0, 15)));
        mainPanel.add(btnAdmin);

        add(mainPanel);
        setVisible(true);
    }
}
