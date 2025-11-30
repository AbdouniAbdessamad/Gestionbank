package ma.banque.ui;

import javax.swing.*;
import ma.banque.model.Client;
import ma.banque.service.BanqueService;

/**
 * Formulaire pour supprimer un client existant.
 */
public class FormSupprimerClient extends JFrame {

    private final BanqueService banqueService = new BanqueService();
    private int clientId = 0;
    private JTextField nomField;
    private JTextField emailField;
    private JTextField telephoneField;
    private MenuAdminView menuAdminView;

    public FormSupprimerClient() {
        this(0, null);
    }

    public FormSupprimerClient(int clientId) {
        this(clientId, null);
    }

    public FormSupprimerClient(int clientId, MenuAdminView menuAdminView) {
        this.clientId = clientId;
        this.menuAdminView = menuAdminView;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Supprimer un Client");
        setSize(550, 450);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);

        // Panneau principal
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(UIHelper.BACKGROUND_COLOR);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Titre
        JLabel titleLabel = UIHelper.createTitleLabel("Supprimer un Client");
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

        // Champs d'affichage (non-modifiables)
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
        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 15)));

        // Panneau d'avertissement
        JPanel warningPanel = new JPanel();
        warningPanel.setBackground(new java.awt.Color(255, 200, 200));
        warningPanel.setBorder(BorderFactory.createLineBorder(new java.awt.Color(255, 0, 0), 2));
        warningPanel.setLayout(new BoxLayout(warningPanel, BoxLayout.Y_AXIS));
        warningPanel.setMaximumSize(new java.awt.Dimension(500, 80));

        JLabel warningLabel = new JLabel("⚠️ Attention : Cette action est irréversible !");
        warningLabel.setFont(UIHelper.FONT_LABEL.deriveFont(java.awt.Font.BOLD));
        warningLabel.setForeground(new java.awt.Color(139, 0, 0));
        warningPanel.add(warningLabel);

        JLabel infoLabel = new JLabel("Tous les comptes et opérations seront supprimés.");
        infoLabel.setFont(UIHelper.FONT_LABEL);
        infoLabel.setForeground(new java.awt.Color(139, 0, 0));
        warningPanel.add(infoLabel);

        mainPanel.add(warningPanel);
        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 15)));

        // Boutons d'action
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(UIHelper.BACKGROUND_COLOR);
        buttonPanel.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER));

        JButton deleteBtn = UIHelper.createDangerButton("Supprimer le Client");
        deleteBtn.addActionListener(e -> {
            if (clientId > 0) {
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Êtes-vous sûr de vouloir supprimer ce client ?\nCette action est irréversible !",
                        "Confirmation de suppression",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.WARNING_MESSAGE);

                if (confirm == JOptionPane.YES_OPTION) {
                    deleteClient();
                    nomField.setText("");
                    emailField.setText("");
                    telephoneField.setText("");
                    clientId = 0;
                }
            } else {
                UIHelper.showError(this, "Erreur", "Veuillez d'abord chercher un client");
            }
        });

        JButton cancelBtn = UIHelper.createStyledButton("Annuler");
        cancelBtn.addActionListener(e -> dispose());

        buttonPanel.add(deleteBtn);
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

            UIHelper.showSuccess(this, "Succès", "Client trouvé. Vérifiez les informations avant de supprimer");
        } catch (NumberFormatException ex) {
            UIHelper.showError(this, "Erreur", "ID client invalide");
        }
    }

    private void deleteClient() {
        if (banqueService.deleteClient(clientId)) {
            UIHelper.showSuccess(this, "Succès", "Client supprimé avec succès");
        } else {
            UIHelper.showError(this, "Erreur", "Erreur lors de la suppression du client");
        }
    }
}
