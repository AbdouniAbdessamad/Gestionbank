package ma.banque.ui;

import javax.swing.*;
import utils.DataStore;

/**
 * Formulaire pour afficher le code du compte du client connecté.
 */
public class FormVoirMonCode extends JFrame {

    public FormVoirMonCode() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Mon Code de Compte");
        setSize(450, 350);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(UIHelper.BACKGROUND_COLOR);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        // Titre
        JLabel titleLabel = UIHelper.createTitleLabel("Mon Code de Compte");
        titleLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 40)));

        // Afficher le code du compte
        var compte = DataStore.getCurrentCompte();
        String code = "N/A";
        if (compte != null) {
            code = compte.getCodeCompte();
        }

        JLabel codeLabel = UIHelper.createSubtitleLabel(code);
        codeLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        mainPanel.add(codeLabel);

        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 20)));

        // Texte explicatif
        JLabel infoLabel = UIHelper.createStyledLabel("Conservez ce code pour vos transactions");
        infoLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        mainPanel.add(infoLabel);

        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 40)));

        // Boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(UIHelper.BACKGROUND_COLOR);
        buttonPanel.setLayout(new javax.swing.BoxLayout(buttonPanel, javax.swing.BoxLayout.X_AXIS));

        JButton backBtn = UIHelper.createStyledButton("Retour");
        backBtn.addActionListener(e -> {
            dispose();
            new MenuClientView();
        });

        JButton homeBtn = UIHelper.createDangerButton("Accueil");
        homeBtn.addActionListener(e -> {
            dispose();
            new HomeView();
        });

        buttonPanel.add(backBtn);
        buttonPanel.add(Box.createRigidArea(new java.awt.Dimension(10, 0)));
        buttonPanel.add(homeBtn);

        mainPanel.add(buttonPanel);

        add(mainPanel);
        setVisible(true);
    }
}
