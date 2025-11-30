package ma.banque.ui;

import javax.swing.*;
import ma.banque.service.BanqueService;

/**
 * Formulaire pour que l'admin crée un nouveau client et son compte bancaire.
 */
public class FormCreerCompte extends JFrame {

    private JTextField nomField;
    private JTextField emailField;
    private JTextField telephoneField;
    private JSpinner soldeSpinner;

    public FormCreerCompte() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Créer un Nouveau Compte Client");
        setSize(450, 550);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(UIHelper.BACKGROUND_COLOR);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel titleLabel = UIHelper.createTitleLabel("Créer un Client");
        titleLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 25)));

        // Champ Nom
        mainPanel.add(UIHelper.createStyledLabel("Nom complet du client"));
        nomField = UIHelper.createStyledTextField();
        nomField.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 35));
        mainPanel.add(nomField);

        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 12)));

        // Champ Email
        mainPanel.add(UIHelper.createStyledLabel("Adresse email"));
        emailField = UIHelper.createStyledTextField();
        emailField.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 35));
        mainPanel.add(emailField);

        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 12)));

        // Champ Téléphone
        mainPanel.add(UIHelper.createStyledLabel("Numéro de téléphone"));
        telephoneField = UIHelper.createStyledTextField();
        telephoneField.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 35));
        mainPanel.add(telephoneField);

        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 12)));

        // Champ Solde initial
        mainPanel.add(UIHelper.createStyledLabel("Solde initial du compte (DH)"));
        soldeSpinner = new JSpinner(new SpinnerNumberModel(0.0, 0.0, 999999999.0, 100.0));
        soldeSpinner.setMaximumSize(new java.awt.Dimension(Integer.MAX_VALUE, 35));
        mainPanel.add(soldeSpinner);

        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 25)));

        // Boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(UIHelper.BACKGROUND_COLOR);
        buttonPanel.setLayout(new javax.swing.BoxLayout(buttonPanel, javax.swing.BoxLayout.X_AXIS));

        JButton createBtn = UIHelper.createStyledButton("Créer le Compte");
        createBtn.addActionListener(e -> handleCreate());

        JButton backBtn = UIHelper.createDangerButton("Retour");
        backBtn.addActionListener(e -> {
            dispose();
            new MenuAdminView();
        });

        JButton homeBtn = UIHelper.createDangerButton("Accueil");
        homeBtn.addActionListener(e -> {
            dispose();
            new HomeView();
        });

        buttonPanel.add(createBtn);
        buttonPanel.add(Box.createRigidArea(new java.awt.Dimension(10, 0)));
        buttonPanel.add(backBtn);
        buttonPanel.add(Box.createRigidArea(new java.awt.Dimension(10, 0)));
        buttonPanel.add(homeBtn);

        mainPanel.add(buttonPanel);

        add(mainPanel);
        setVisible(true);
    }

    private void handleCreate() {
        String nom = nomField.getText();
        String email = emailField.getText();
        String telephone = telephoneField.getText();
        double solde = (Double) soldeSpinner.getValue();

        if (nom.isEmpty()) {
            UIHelper.showError(this, "Erreur", "Le nom du client est obligatoire");
            return;
        }

        BanqueService service = new BanqueService();
        var compte = service.createClientAndCompte(nom, email, telephone, solde);

        if (compte != null) {
            UIHelper.showSuccess(this, "Succès",
                    "Compte créé avec succès !\n" +
                    "Code de compte : " + compte.getCodeCompte() + "\n" +
                    "Solde initial : " + compte.getSolde() + " DH");
            dispose();
            new MenuAdminView();
        } else {
            UIHelper.showError(this, "Erreur", "Erreur lors de la création du compte");
        }
    }
}
