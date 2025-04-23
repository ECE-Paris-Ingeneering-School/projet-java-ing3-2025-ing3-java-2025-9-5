// view/InvoiceView.java
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
import model.User;
import model.DiscountDAO;
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
        String[] cols = {"Réf", "Description", "Qté", "PU (€)", "Total normal (€)", "Promo PU (€)", "Total promo (€)"};
        DefaultTableModel tm = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        for (OrderDetail d : details) {
            int productId = d.getProductId();
            String desc = d.getProductName();
            int qty = d.getQuantity();
            double unitPrice = d.getPricePerUnit();
            double normalLine = unitPrice * qty;
            int threshold = DiscountDAO.getBulkQuantity(productId);
            double promoPrice = DiscountDAO.getDiscountedPrice(productId, qty);
            String promoPUstr = "-";
            double promoLine;
            if (threshold > 0 && promoPrice > 0) {
                promoPUstr = String.format("%.2f", promoPrice);
                promoLine = promoPrice * qty;
            } else {
                promoLine = normalLine;
            }
            tm.addRow(new Object[]{
                    productId,
                    desc,
                    qty,
                    String.format("%.2f", unitPrice),
                    String.format("%.2f", normalLine),
                    promoPUstr,
                    String.format("%.2f", promoLine)
            });
        }
        JTable table = new JTable(tm);
        table.setFillsViewportHeight(true);
        content.add(Box.createRigidArea(new Dimension(0,10)));
        content.add(new JScrollPane(table));

        // 4) Totaux
        double totalPromo = 0;
        for (OrderDetail d : details) {
            int pid = d.getProductId();
            int qty = d.getQuantity();
            double up = d.getPricePerUnit();
            double lineNormal = up * qty;
            int thr = DiscountDAO.getBulkQuantity(pid);
            double pp = DiscountDAO.getDiscountedPrice(pid, qty);
            double linePromo = (thr > 0 && pp > 0) ? pp * qty : lineNormal;
            totalPromo += linePromo;
        }
        JPanel totalPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JLabel totalLabel = new JLabel("Total TTC (promo) : " + String.format("%.2f €", totalPromo));
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

    /** Lance l'affichage de la dernière facture d'un utilisateur */
    public static void showInvoiceForUser(User user) {
        // On recharge l'utilisateur depuis la BDD pour récupérer firstName/lastName
        String clientName = user.getFirstName() + " " + user.getLastName();

        // Récupération de la dernière commande
        List<Order> orders = OrderHistoryDAO.getOrdersForUser(user.getUserId());
        if (orders.isEmpty()) {
            JOptionPane.showMessageDialog(null,
                    "Aucune commande trouvée.",
                    "Erreur",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        Order order = orders.get(0);
        List<OrderDetail> details = OrderHistoryDAO.getOrderDetails(order.getOrderId());

        // On passe le nom complet au constructeur
        InvoiceView iv = new InvoiceView(order, details, clientName);
        iv.setVisible(true);
    }

    /** méthode main pour tester indépendamment */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Test avec l'utilisateur ID=1
            User u = UserDAO.findUserById(1);
            if (u != null) showInvoiceForUser(u);
            else JOptionPane.showMessageDialog(null, "Utilisateur de test non trouvé.");
        });
    }
}