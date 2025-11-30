package ma.banque.ui;

import java.awt.*;
import javax.swing.*;

/**
 * Classe utilitaire pour faciliter la création d'UI moderne et cohérente en Swing.
 */
public class UIHelper {

    // ========== COULEURS ==========
    public static final Color PRIMARY_COLOR = new Color(52, 152, 219);        // Bleu
    public static final Color PRIMARY_DARK = new Color(41, 128, 185);         // Bleu foncé
    public static final Color SUCCESS_COLOR = new Color(46, 213, 115);        // Vert
    public static final Color DANGER_COLOR = new Color(231, 76, 60);          // Rouge
    public static final Color WARNING_COLOR = new Color(241, 196, 15);        // Orange
    public static final Color BACKGROUND_COLOR = new Color(236, 240, 241);    // Gris clair
    public static final Color TEXT_COLOR = new Color(52, 73, 94);             // Gris foncé
    public static final Color LIGHT_GRAY = new Color(189, 195, 199);          // Gris moyen

    // ========== POLICES ==========
    public static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 24);
    public static final Font FONT_SUBTITLE = new Font("SansSerif", Font.BOLD, 18);
    public static final Font FONT_LABEL = new Font("SansSerif", Font.PLAIN, 13);
    public static final Font FONT_BUTTON = new Font("SansSerif", Font.BOLD, 14);

    /**
     * Crée un bouton stylisé et moderne.
     */
    public static JButton createStyledButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(UIHelper.FONT_BUTTON);
        btn.setFocusPainted(false);
        btn.setBackground(UIHelper.PRIMARY_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);

        // Hover effect
        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(UIHelper.PRIMARY_DARK);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(UIHelper.PRIMARY_COLOR);
            }
        });

        return btn;
    }

    /**
     * Crée un bouton de danger (rouge).
     */
    public static JButton createDangerButton(String text) {
        JButton btn = new JButton(text);
        btn.setFont(UIHelper.FONT_BUTTON);
        btn.setFocusPainted(false);
        btn.setBackground(UIHelper.DANGER_COLOR);
        btn.setForeground(Color.WHITE);
        btn.setBorder(BorderFactory.createEmptyBorder(10, 25, 10, 25));
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btn.setOpaque(true);

        btn.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                btn.setBackground(new Color(192, 57, 43));
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                btn.setBackground(UIHelper.DANGER_COLOR);
            }
        });

        return btn;
    }

    /**
     * Crée un champ de texte stylisé.
     */
    public static JTextField createStyledTextField() {
        JTextField field = new JTextField();
        field.setFont(UIHelper.FONT_LABEL);
        field.setMargin(new Insets(8, 8, 8, 8));
        field.setBorder(BorderFactory.createLineBorder(UIHelper.LIGHT_GRAY, 1));
        return field;
    }

    /**
     * Crée un champ de mot de passe stylisé.
     */
    public static JPasswordField createStyledPasswordField() {
        JPasswordField field = new JPasswordField();
        field.setFont(UIHelper.FONT_LABEL);
        field.setMargin(new Insets(8, 8, 8, 8));
        field.setBorder(BorderFactory.createLineBorder(UIHelper.LIGHT_GRAY, 1));
        return field;
    }

    /**
     * Crée un label stylisé.
     */
    public static JLabel createStyledLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UIHelper.FONT_LABEL);
        label.setForeground(UIHelper.TEXT_COLOR);
        return label;
    }

    /**
     * Crée un titre stylisé.
     */
    public static JLabel createTitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UIHelper.FONT_TITLE);
        label.setForeground(UIHelper.TEXT_COLOR);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    /**
     * Crée un sous-titre stylisé.
     */
    public static JLabel createSubtitleLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(UIHelper.FONT_SUBTITLE);
        label.setForeground(UIHelper.TEXT_COLOR);
        label.setHorizontalAlignment(SwingConstants.CENTER);
        return label;
    }

    /**
     * Affiche une boîte de dialogue d'erreur.
     */
    public static void showError(Component parent, String title, String message) {
        JOptionPane.showMessageDialog(
                parent,
                message,
                title,
                JOptionPane.ERROR_MESSAGE
        );
    }

    /**
     * Affiche une boîte de dialogue de succès.
     */
    public static void showSuccess(Component parent, String title, String message) {
        JOptionPane.showMessageDialog(
                parent,
                message,
                title,
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    /**
     * Affiche une boîte de dialogue de confirmation.
     */
    public static boolean showConfirmation(Component parent, String title, String message) {
        int result = JOptionPane.showConfirmDialog(
                parent,
                message,
                title,
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );
        return result == JOptionPane.YES_OPTION;
    }

    /**
     * Centre une fenêtre sur l'écran.
     */
    public static void centerOnScreen(JFrame frame) {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        int x = (screenSize.width - frame.getWidth()) / 2;
        int y = (screenSize.height - frame.getHeight()) / 2;
        frame.setLocation(x, y);
    }

    /**
     * Crée un JComboBox stylisé.
     */
    public static <T> JComboBox<T> createStyledComboBox(T[] items) {
        JComboBox<T> combo = new JComboBox<>(items);
        combo.setFont(UIHelper.FONT_LABEL);
        return combo;
    }
}
