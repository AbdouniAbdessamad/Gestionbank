package ma.banque.ui;

import javax.swing.*;
import ma.banque.model.Client;
import ma.banque.service.BanqueService;

/**
 * Formulaire pour modifier les informations d'un client existant.
 */
public class FormModifierClient extends JFrame {

    private final BanqueService banqueService = new BanqueService();
    private int clientId = 0;
    private JTextField nomField;
    private JTextField emailField;
    private JTextField telephoneField;
    private MenuAdminView menuAdminView;

    public FormModifierClient() {
        this(0, null);
    }

    public FormModifierClient(int clientId) {
        this(clientId, null);
    }

    public FormModifierClient(int clientId, MenuAdminView menuAdminView) {
        this.clientId = clientId;
        this.menuAdminView = menuAdminView;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Modifier un Client");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Panneau principal
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(UIHelper.BACKGROUND_COLOR);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Titre
        JLabel titleLabel = UIHelper.createTitleLabel("Modifier un Client");
        titleLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);
        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 20)));

        // Panel pour la saisie de l'ID client
        JPanel idPanel = new JPanel();
        idPanel.setBackground(UIHelper.BACKGROUND_COLOR);
        idPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));

        JLabel idLabel = UIHelper.createStyledLabel("ID Client :");
        JTextField idField = new JTextField(15);
        idField.setFont(UIHelper.FONT_LABEL);

        JButton searchBtn = UIHelper.createStyledButton("Chercher");
        searchBtn.addActionListener(e -> searchClient(idField.getText()));

        idPanel.add(idLabel);
        idPanel.add(idField);
        idPanel.add(searchBtn);
        mainPanel.add(idPanel);
        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 15)));

        // Champs du formulaire
        JLabel nomLabel = UIHelper.createStyledLabel("Nom :");
        nomField = new JTextField(20);
        nomField.setFont(UIHelper.FONT_LABEL);
        nomField.setEnabled(false);

        JLabel emailLabel = UIHelper.createStyledLabel("Email :");
        emailField = new JTextField(20);
        emailField.setFont(UIHelper.FONT_LABEL);
        emailField.setEnabled(false);

        JLabel telephoneLabel = UIHelper.createStyledLabel("Téléphone :");
        telephoneField = new JTextField(20);
        telephoneField.setFont(UIHelper.FONT_LABEL);
        telephoneField.setEnabled(false);

        // Ajouter les champs
        JPanel formPanel = new JPanel();
        formPanel.setBackground(UIHelper.BACKGROUND_COLOR);
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));

        JPanel nomPanel = new JPanel();
        nomPanel.setBackground(UIHelper.BACKGROUND_COLOR);
        nomPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        nomPanel.add(nomLabel);
        nomPanel.add(nomField);
        formPanel.add(nomPanel);

        formPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 10)));

        JPanel emailPanel = new JPanel();
        emailPanel.setBackground(UIHelper.BACKGROUND_COLOR);
        emailPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        emailPanel.add(emailLabel);
        emailPanel.add(emailField);
        formPanel.add(emailPanel);

        formPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 10)));

        JPanel telephonePanel = new JPanel();
        telephonePanel.setBackground(UIHelper.BACKGROUND_COLOR);
        telephonePanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT));
        telephonePanel.add(telephoneLabel);
        telephonePanel.add(telephoneField);
        formPanel.add(telephonePanel);

        mainPanel.add(formPanel);
        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 20)));

        // Boutons d'action
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(UIHelper.BACKGROUND_COLOR);
        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER));

        JButton updateBtn = UIHelper.createStyledButton("Mettre à jour");
        updateBtn.addActionListener(e -> {
            if (clientId > 0) {
                updateClient(nomField.getText(), emailField.getText(), telephoneField.getText());
                nomField.setText("");
                emailField.setText("");
                telephoneField.setText("");
                nomField.setEnabled(false);
                emailField.setEnabled(false);
                telephoneField.setEnabled(false);
                clientId = 0;
            } else {
                UIHelper.showError(this, "Erreur", "Veuillez d'abord chercher un client");
            }
        });

        JButton cancelBtn = UIHelper.createDangerButton("Annuler");
        cancelBtn.addActionListener(e -> dispose());

        buttonPanel.add(updateBtn);
        buttonPanel.add(Box.createRigidArea(new java.awt.Dimension(10, 0)));
        buttonPanel.add(cancelBtn);

        JButton returnBtn = UIHelper.createDangerButton("Retour");
        returnBtn.addActionListener(e -> {
            dispose();
            if (menuAdminView != null) {
                menuAdminView.setVisible(true);
            }
        });
        buttonPanel.add(Box.createRigidArea(new java.awt.Dimension(10, 0)));
        buttonPanel.add(returnBtn);

        mainPanel.add(buttonPanel);

        add(mainPanel);
        setVisible(true);

        // Si un clientId a été fourni, charger automatiquement le client
        if (clientId > 0) {
            loadClient(clientId);
        }
    }

    private void loadClient(int id) {
        Client client = banqueService.getClientById(id);
        if (client != null) {
            this.clientId = id;
            nomField.setText(client.getNom());
            emailField.setText(client.getEmail());
            telephoneField.setText(client.getTelephone());

            nomField.setEnabled(true);
            emailField.setEnabled(true);
            telephoneField.setEnabled(true);
        }
    }

    private void searchClient(String idStr) {
        try {
            int id = Integer.parseInt(idStr.trim());
            Client client = banqueService.getClientById(id);

            if (client == null) {
                UIHelper.showError(this, "Erreur", "Client non trouvé");
                return;
            }

            this.clientId = id;
            nomField.setText(client.getNom());
            emailField.setText(client.getEmail());
            telephoneField.setText(client.getTelephone());

            nomField.setEnabled(true);
            emailField.setEnabled(true);
            telephoneField.setEnabled(true);

            UIHelper.showSuccess(this, "Succès", "Client trouvé. Vous pouvez maintenant modifier les informations");
        } catch (NumberFormatException ex) {
            UIHelper.showError(this, "Erreur", "ID client invalide");
        }
    }

    private void updateClient(String nom, String email, String telephone) {
        if (nom == null || nom.trim().isEmpty()) {
            UIHelper.showError(this, "Erreur", "Le nom ne peut pas être vide");
            return;
        }

        if (banqueService.modifyClient(clientId, nom, email, telephone)) {
            UIHelper.showSuccess(this, "Succès", "Client modifié avec succès");
        } else {
            UIHelper.showError(this, "Erreur", "Erreur lors de la modification du client");
        }
    }
}
