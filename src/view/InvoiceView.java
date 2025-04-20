package view;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Order;
import model.OrderDetail;
import model.OrderHistoryDAO;
import model.UserDAO;


public class InvoiceView extends JFrame {
    public InvoiceView(Order order, List<OrderDetail> details, String clientName) {
        super("Facture n° " + order.getOrderId());
        setSize(600, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(20, 20, 20, 20));

        // 1) En-tête
        JLabel title = new JLabel("FACTURE", SwingConstants.CENTER);
        title.setFont(new Font("Serif", Font.BOLD, 24));
        content.add(title);

        // 2) Coordonnées et date
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        JPanel infoPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        infoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));
        infoPanel.add(new JLabel("Date : " + order.getOrderDate().format(fmt)));
        infoPanel.add(new JLabel("Client : " + clientName));
        infoPanel.add(new JLabel("Facture n° : " + order.getOrderId()));
        infoPanel.add(new JLabel(" "));
        content.add(Box.createRigidArea(new Dimension(0,10)));
        content.add(infoPanel);

        // 3) Tableau des lignes
        String[] cols = {"Réf", "Description", "Qté", "PU (€)", "Total (€)"};
        DefaultTableModel tm = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        for (OrderDetail d : details) {
            tm.addRow(new Object[]{
                    d.getProductId(),
                    d.getProductName(),
                    d.getQuantity(),
                    String.format("%.2f", d.getPricePerUnit()),
                    String.format("%.2f", d.getQuantity() * d.getPricePerUnit())
            });
        }
        JTable table = new JTable(tm);
        table.setFillsViewportHeight(true);
        content.add(Box.createRigidArea(new Dimension(0,10)));
        content.add(new JScrollPane(table));

        // 4) Totaux
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel totalLabel = new JLabel("Total TTC : " + String.format("%.2f €", order.getTotalAmount()));
        totalLabel.setFont(new Font("Serif", Font.BOLD, 16));
        totalPanel.add(totalLabel);
        content.add(Box.createRigidArea(new Dimension(0,10)));
        content.add(totalPanel);

        // 5) Mentions légales
        JTextArea legal = new JTextArea(
                "Merci pour votre commande.\n" +
                        "Paiement à réception. Toute réclamation sous 7 jours.\n" +
                        "TVA non applicable, art. 293B du CGI."
        );
        legal.setEditable(false);
        legal.setFont(new Font("Serif", Font.ITALIC, 12));
        legal.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));
        content.add(legal);

        setContentPane(new JScrollPane(content));
    }

    /** Méthode utilitaire pour ouvrir directement depuis un contrôleur client */
    public static void showInvoiceForUser(int userId) {
        // On récupère la dernière commande
        Order order = OrderHistoryDAO.getOrdersForUser(userId).stream()
                .findFirst()
                .orElse(null);
        if (order == null) {
            JOptionPane.showMessageDialog(null, "Aucune commande trouvée.", "Erreur", JOptionPane.WARNING_MESSAGE);
            return;
        }
        List<OrderDetail> details = OrderHistoryDAO.getOrderDetails(order.getOrderId());
        InvoiceView iv = new InvoiceView(order, details, UserDAO.findUserById(userId).getFirstName());
        iv.setVisible(true);
    }
}
