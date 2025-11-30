package ma.banque.ui;

import javax.swing.*;
import ma.banque.service.BanqueService;
import utils.DataStore;

/**
 * Formulaire pour effectuer un retrait sur le compte.
 */
public class FormRetirer extends JFrame {

    private JSpinner montantSpinner;

    public FormRetirer() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Effectuer un Retrait");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(UIHelper.BACKGROUND_COLOR);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel titleLabel = UIHelper.createTitleLabel("Faire un Retrait");
        titleLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 30)));

        // Champ montant
        mainPanel.add(UIHelper.createStyledLabel("Montant à retirer (DH)"));
        montantSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 999999999.0, 100.0));
        montantSpinner.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 35));
        mainPanel.add(montantSpinner);

        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 25)));

        // Boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(UIHelper.BACKGROUND_COLOR);
        buttonPanel.setLayout(new javax.swing.BoxLayout(buttonPanel, javax.swing.BoxLayout.X_AXIS));

        JButton confirmBtn = UIHelper.createStyledButton("Confirmer le Retrait");
        confirmBtn.addActionListener(e -> handleRetirer());

        JButton backBtn = UIHelper.createDangerButton("Retour");
        backBtn.addActionListener(e -> {
            dispose();
            new MenuClientView();
        });

        JButton homeBtn = UIHelper.createDangerButton("Accueil");
        homeBtn.addActionListener(e -> {
            dispose();
            new HomeView();
        });

        buttonPanel.add(confirmBtn);
        buttonPanel.add(Box.createRigidArea(new java.awt.Dimension(10, 0)));
        buttonPanel.add(backBtn);
        buttonPanel.add(Box.createRigidArea(new java.awt.Dimension(10, 0)));
        buttonPanel.add(homeBtn);

        mainPanel.add(buttonPanel);

        add(mainPanel);
        setVisible(true);
    }

    private void handleRetirer() {
        double montant = (Double) montantSpinner.getValue();

        if (montant <= 0) {
            UIHelper.showError(this, "Erreur", "Le montant doit être positif");
            return;
        }

        var compte = DataStore.getCurrentCompte();
        if (compte == null) {
            UIHelper.showError(this, "Erreur", "Aucun compte connecté");
            return;
        }

        BanqueService service = new BanqueService();
        if (service.retirer(compte.getNumeroCompte(), montant)) {
            UIHelper.showSuccess(this, "Succès", "Retrait de " + montant + " DH effectué");
            // Mettre à jour le compte en session
            service.chargerCompte(compte.getNumeroCompte());
            dispose();
            new MenuClientView();
        } else {
            UIHelper.showError(this, "Erreur", "Solde insuffisant ou erreur lors du retrait");
        }
    }
}
