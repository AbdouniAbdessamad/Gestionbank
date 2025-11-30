package ma.banque.ui;

import java.time.format.DateTimeFormatter;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import ma.banque.model.Operation;
import ma.banque.service.BanqueService;
import utils.DataStore;

/**
 * Formulaire pour afficher l'historique des opérations du compte.
 */
public class FormHistorique extends JFrame {

    private JTable table;
    private DefaultTableModel model;

    public FormHistorique() {
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Historique des Opérations");
        setSize(700, 480);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        JPanel mainPanel = new JPanel();
        mainPanel.setBackground(UIHelper.BACKGROUND_COLOR);
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = UIHelper.createTitleLabel("Mon Historique");
        titleLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        mainPanel.add(titleLabel);

        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 20)));

        // Tableau des opérations
        String[] columns = {"ID", "Type", "Montant", "Date"};
        model = new DefaultTableModel(columns, 0);
        table = new JTable(model);
        table.setEnabled(false);
        table.setBackground(UIHelper.BACKGROUND_COLOR);

        JScrollPane scrollPane = new JScrollPane(table);
        mainPanel.add(scrollPane);

        mainPanel.add(Box.createRigidArea(new java.awt.Dimension(0, 15)));

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
        loadOperations();
        setVisible(true);
    }

    private void loadOperations() {
        var compte = DataStore.getCurrentCompte();
        if (compte == null) {
            UIHelper.showError(this, "Erreur", "Aucun compte connecté");
            return;
        }

        BanqueService service = new BanqueService();
        List<Operation> operations = service.getOperations(compte.getNumeroCompte());

        model.setRowCount(0);
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        for (Operation op : operations) {
            model.addRow(new Object[]{
                    op.getOperationId(),
                    op.getTypeOperation(),
                    String.format("%.2f", op.getMontant()),
                    op.getDateOperation().format(fmt)
            });
        }
    }
}
