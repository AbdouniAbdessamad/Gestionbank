package ma.banque.ui;

import javax.swing.*;
import utils.DataStore;

/**
 * Formulaire pour consulter le solde du compte du client.
 */
public class FormConsulterSolde extends JFrame {

    private JLabel soldeLabel;

    public FormConsulterSolde() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Consulter le Solde");
        setSize(450, 320);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(UIHelper.BACKGROUND_COLOR);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(40, 40, 40, 40));

        JLabel titleLabel = UIHelper.createTitleLabel("Mon Solde");
        titleLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 40)));

        // Afficher le solde
        var compte = DataStore.getCurrentCompte();
        double solde = 0;
        if (compte != null) {
            solde = compte.getSolde();
        }

        soldeLabel = UIHelper.createSubtitleLabel(String.format("%.2f DH", solde));
        soldeLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        mainPanel.add(soldeLabel);

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
