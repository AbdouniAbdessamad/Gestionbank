package ma.banque.ui;

import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import ma.banque.model.Client;
import ma.banque.model.Compte;
import ma.banque.service.BanqueService;
import utils.DataStore;

/**
 * Menu principal pour les administrateurs.
 */
public class MenuAdminView extends JFrame {

    private final BanqueService banqueService = new BanqueService();

    public MenuAdminView() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Menu - Agent Bancaire");
        setSize(600, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Panneau principal
        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(UIHelper.BACKGROUND_COLOR);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Titre avec info admin
        String adminName = DataStore.getCurrentAdmin() != null ? DataStore.getCurrentAdmin().getUsername() : "Agent";
        JLabel titleLabel = UIHelper.createTitleLabel("Bienvenue, " + adminName);
        titleLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 20)));

        // Boutons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(UIHelper.BACKGROUND_COLOR);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton btnCreateClient = UIHelper.createStyledButton("Créer un nouveau client");
        btnCreateClient.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        btnCreateClient.setMaximumSize(new java.awt.Dimension(400, 40));
        btnCreateClient.addActionListener(e -> openCreateClientDialog());

        JButton btnModifyClient = UIHelper.createStyledButton("Modifier un client");
        btnModifyClient.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        btnModifyClient.setMaximumSize(new java.awt.Dimension(400, 40));
        btnModifyClient.addActionListener(e -> {
            this.setVisible(false);
            new FormModifierClient(0, this);
        });

        JButton btnDeleteClient = UIHelper.createDangerButton("Supprimer un client");
        btnDeleteClient.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        btnDeleteClient.setMaximumSize(new java.awt.Dimension(400, 40));
        btnDeleteClient.addActionListener(e -> {
            this.setVisible(false);
            new FormSupprimerClient(0, this);
        });

        JButton btnViewClients = UIHelper.createStyledButton("Voir tous les clients");
        btnViewClients.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        btnViewClients.setMaximumSize(new java.awt.Dimension(400, 40));
        btnViewClients.addActionListener(e -> viewAllClients());

        JButton btnViewComptes = UIHelper.createStyledButton("Voir tous les comptes");
        btnViewComptes.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        btnViewComptes.setMaximumSize(new java.awt.Dimension(400, 40));
        btnViewComptes.addActionListener(e -> viewAllComptes());

        JButton btnViewOperations = UIHelper.createStyledButton("Voir toutes les opérations");
        btnViewOperations.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        btnViewOperations.setMaximumSize(new java.awt.Dimension(400, 40));
        btnViewOperations.addActionListener(e -> viewAllOperations());

        JButton btnViewClientCodes = UIHelper.createStyledButton("Voir les codes des clients");
        btnViewClientCodes.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        btnViewClientCodes.setMaximumSize(new java.awt.Dimension(400, 40));
        btnViewClientCodes.addActionListener(e -> viewClientCodes());

        JButton btnLogout = UIHelper.createDangerButton("Déconnexion");
        btnLogout.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        btnLogout.setMaximumSize(new java.awt.Dimension(400, 40));
        btnLogout.addActionListener(e -> {
            DataStore.logout();
            dispose();
            new HomeView();
        });

        JButton btnHome = UIHelper.createDangerButton("Accueil");
        btnHome.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        btnHome.setMaximumSize(new java.awt.Dimension(400, 40));
        btnHome.addActionListener(e -> {
            dispose();
            new HomeView();
        });

        buttonPanel.add(btnCreateClient);
        buttonPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 10)));
        buttonPanel.add(btnModifyClient);
        buttonPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 10)));
        buttonPanel.add(btnDeleteClient);
        buttonPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 10)));
        buttonPanel.add(btnViewClients);
        buttonPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 10)));
        buttonPanel.add(btnViewComptes);
        buttonPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 10)));
        buttonPanel.add(btnViewOperations);
        buttonPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 10)));
        buttonPanel.add(btnViewClientCodes);
        buttonPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 10)));
        buttonPanel.add(btnHome);
        buttonPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 10)));
        buttonPanel.add(btnLogout);

        mainPanel.add(buttonPanel);

        add(mainPanel);
        setVisible(true);
    }

    private void openCreateClientDialog() {
        dispose();
        new FormCreerCompte();
    }

    private void viewAllClients() {
        List<Client> clients = banqueService.getAllClients();

        if (clients.isEmpty()) {
            UIHelper.showError(this, "Info", "Aucun client trouvé");
            return;
        }

        JFrame clientFrame = new JFrame("Liste des Clients");
        clientFrame.setSize(900, 500);
        clientFrame.setLocationRelativeTo(null);
        clientFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(UIHelper.BACKGROUND_COLOR);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Créer un tableau
        String[] columns = { "ID", "Nom", "Email", "Téléphone", "Actions" };
        Object[][] data = new Object[clients.size()][5];

        int row = 0;
        for (Client client : clients) {
            data[row][0] = client.getClientId();
            data[row][1] = client.getNom();
            data[row][2] = client.getEmail();
            data[row][3] = client.getTelephone();
            data[row][4] = "Voir Actions";
            row++;
        }

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        table.setBackground(UIHelper.BACKGROUND_COLOR);
        table.setFont(UIHelper.FONT_LABEL);
        table.setRowHeight(30);

        // Ajouter un listener pour les clics sur la colonne "Actions"
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int col = table.columnAtPoint(evt.getPoint());
                int row = table.rowAtPoint(evt.getPoint());
                if (col == 4 && row >= 0) { // Colonne "Actions"
                    int clientId = (Integer) table.getValueAt(row, 0);
                    showClientActions(clientId, clients.get(row));
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane);

        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 10)));

        // Boutons de navigation
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(UIHelper.BACKGROUND_COLOR);
        buttonPanel.setLayout(new javax.swing.BoxLayout(buttonPanel, javax.swing.BoxLayout.X_AXIS));

        JButton closeBtn = UIHelper.createStyledButton("Fermer");
        closeBtn.addActionListener(e -> clientFrame.dispose());

        JButton backBtn = UIHelper.createDangerButton("Retour Menu");
        backBtn.addActionListener(e -> clientFrame.dispose());

        JButton homeBtn = UIHelper.createDangerButton("Accueil");
        homeBtn.addActionListener(e -> {
            clientFrame.dispose();
            this.dispose();
            new HomeView();
        });

        buttonPanel.add(closeBtn);
        buttonPanel.add(Box.createRigidArea(new java.awt.Dimension(10, 0)));
        buttonPanel.add(backBtn);
        buttonPanel.add(Box.createRigidArea(new java.awt.Dimension(10, 0)));
        buttonPanel.add(homeBtn);

        mainPanel.add(buttonPanel);

        clientFrame.add(mainPanel);
        clientFrame.setVisible(true);
    }

    private void showClientActions(int clientId, Client client) {
        JFrame actionFrame = new JFrame("Actions - " + client.getNom());
        actionFrame.setSize(400, 200);
        actionFrame.setLocationRelativeTo(null);
        actionFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(UIHelper.BACKGROUND_COLOR);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel infoLabel = UIHelper.createSubtitleLabel(client.getNom());
        infoLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        mainPanel.add(infoLabel);
        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 20)));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(UIHelper.BACKGROUND_COLOR);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));

        JButton modifyBtn = UIHelper.createStyledButton("Modifier");
        modifyBtn.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        modifyBtn.setMaximumSize(new java.awt.Dimension(250, 40));
        modifyBtn.addActionListener(e -> {
            openModifyClientDialog(clientId);
            actionFrame.dispose();
        });

        JButton deleteBtn = UIHelper.createDangerButton("Supprimer");
        deleteBtn.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        deleteBtn.setMaximumSize(new java.awt.Dimension(250, 40));
        deleteBtn.addActionListener(e -> {
            deleteClientWithConfirmation(clientId);
            actionFrame.dispose();
        });

        JButton cancelBtn = UIHelper.createStyledButton("Annuler");
        cancelBtn.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        cancelBtn.setMaximumSize(new java.awt.Dimension(250, 40));
        cancelBtn.addActionListener(e -> actionFrame.dispose());

        buttonPanel.add(modifyBtn);
        buttonPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 10)));
        buttonPanel.add(deleteBtn);
        buttonPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 10)));
        buttonPanel.add(cancelBtn);

        mainPanel.add(buttonPanel);

        actionFrame.add(mainPanel);
        actionFrame.setVisible(true);
    }

    private void openModifyClientDialog(int clientId) {
        new FormModifierClient(clientId, this);
    }

    private void deleteClientWithConfirmation(int clientId) {
        int confirm = JOptionPane.showConfirmDialog(this,
                "Êtes-vous sûr de vouloir supprimer ce client ?\nCette action est irréversible !",
                "Confirmation de suppression",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);

        if (confirm == JOptionPane.YES_OPTION) {
            if (banqueService.deleteClient(clientId)) {
                UIHelper.showSuccess(this, "Succès", "Client supprimé avec succès");
                // Rafraîchir la liste
                viewAllClients();
            } else {
                UIHelper.showError(this, "Erreur", "Erreur lors de la suppression du client");
            }
        }
    }

    private void viewAllComptes() {
        List<Compte> comptes = banqueService.getAllComptes();

        if (comptes.isEmpty()) {
            UIHelper.showError(this, "Info", "Aucun compte trouvé");
            return;
        }

        String[] columns = { "Numéro", "Code Compte", "Solde", "Client ID" };
        Object[][] data = new Object[comptes.size()][4];

        int row = 0;
        for (Compte compte : comptes) {
            data[row][0] = compte.getNumeroCompte();
            data[row][1] = compte.getCodeCompte();
            data[row][2] = String.format("%.2f", compte.getSolde());
            data[row][3] = compte.getClientId();
            row++;
        }

        showTableDialog("Liste des Comptes", columns, data);
    }

    private void viewAllOperations() {
        var operations = banqueService.getAllOperations();

        if (operations.isEmpty()) {
            UIHelper.showError(this, "Info", "Aucune opération trouvée");
            return;
        }

        String[] columns = { "ID", "Compte", "Type", "Montant", "Date" };
        Object[][] data = new Object[operations.size()][5];

        int row = 0;
        for (var op : operations) {
            data[row][0] = op.getOperationId();
            data[row][1] = op.getNumeroCompte();
            data[row][2] = op.getTypeOperation();
            data[row][3] = String.format("%.2f", op.getMontant());
            data[row][4] = op.getDateOperation();
            row++;
        }

        showTableDialog("Toutes les Opérations", columns, data);
    }

    private void viewClientCodes() {
        List<Client> clients = banqueService.getAllClients();

        if (clients.isEmpty()) {
            UIHelper.showError(this, "Info", "Aucun client trouvé");
            return;
        }

        String[] columns = { "ID Client", "Nom", "Code Compte" };
        Object[][] data = new Object[clients.size()][3];

        int row = 0;
        for (Client client : clients) {
            // Récupérer les comptes du client
            List<Compte> comptes = banqueService.getComptesOfClient(client.getClientId());
            String codeListe = "";
            for (int i = 0; i < comptes.size(); i++) {
                codeListe += comptes.get(i).getCodeCompte();
                if (i < comptes.size() - 1) {
                    codeListe += ", ";
                }
            }
            data[row][0] = client.getClientId();
            data[row][1] = client.getNom();
            data[row][2] = codeListe.isEmpty() ? "Aucun compte" : codeListe;
            row++;
        }

        showTableDialog("Codes des Comptes par Client", columns, data);
    }

    private void showTableDialog(String title, String[] columns, Object[][] data) {
        JFrame tableFrame = new JFrame(title);
        tableFrame.setSize(700, 450);
        tableFrame.setLocationRelativeTo(null);
        tableFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(UIHelper.BACKGROUND_COLOR);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JTable table = new JTable(new DefaultTableModel(data, columns));
        table.setEnabled(false);
        table.setBackground(UIHelper.BACKGROUND_COLOR);
        table.setFont(UIHelper.FONT_LABEL);

        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane);

        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 10)));

        // Boutons de navigation
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(UIHelper.BACKGROUND_COLOR);
        buttonPanel.setLayout(new javax.swing.BoxLayout(buttonPanel, javax.swing.BoxLayout.X_AXIS));

        JButton closeBtn = UIHelper.createStyledButton("Fermer");
        closeBtn.addActionListener(e -> tableFrame.dispose());

        JButton backBtn = UIHelper.createDangerButton("Retour Menu");
        backBtn.addActionListener(e -> {
            tableFrame.dispose();
        });

        JButton homeBtn = UIHelper.createDangerButton("Accueil");
        homeBtn.addActionListener(e -> {
            tableFrame.dispose();
            this.dispose();
            new HomeView();
        });

        buttonPanel.add(closeBtn);
        buttonPanel.add(Box.createRigidArea(new java.awt.Dimension(10, 0)));
        buttonPanel.add(backBtn);
        buttonPanel.add(Box.createRigidArea(new java.awt.Dimension(10, 0)));
        buttonPanel.add(homeBtn);

        mainPanel.add(buttonPanel);

        tableFrame.add(mainPanel);
        tableFrame.setVisible(true);
    }
}
