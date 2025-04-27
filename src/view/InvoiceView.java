package view;
/**
 * Classe représentant la vue de la facture d'une commande.
 * Cette vue affiche les informations relatives à la commande d'un utilisateur, y compris les détails des articles commandés,
 * les totaux, ainsi que des informations légales.
 */
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;
import model.Order;
import model.OrderDetail;
import model.OrderHistoryDAO;
import model.User;
import model.DiscountDAO;
import model.UserDAO;
import utils.Style;

public class InvoiceView extends JFrame {

    public InvoiceView(Order order, List<OrderDetail> details, String clientName) {
        super("Facture n° " + order.getOrderId());
        setSize(800, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        JPanel content = new JPanel();
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        content.setBorder(new EmptyBorder(20, 20, 20, 20));
        Style.stylePanel(content);

        // 1) En-tête
        JLabel title = new JLabel("FACTURE", SwingConstants.CENTER);
        Style.styleTitle(title);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        content.add(title);

        // 2) Coordonnées et date
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        JPanel infoPanel = new JPanel(new GridLayout(2, 2, 10, 10));
        Style.stylePanel(infoPanel);
        infoPanel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JLabel dateLabel = new JLabel("Date : " + order.getOrderDate().format(fmt));
        JLabel clientLabel = new JLabel("Client : " + clientName);
        JLabel invoiceNumberLabel = new JLabel("Facture n° : " + order.getOrderId());
        JLabel emptyLabel = new JLabel(" ");

        Style.styleLabel(dateLabel);
        Style.styleLabel(clientLabel);
        Style.styleLabel(invoiceNumberLabel);
        Style.styleLabel(emptyLabel);

        infoPanel.add(dateLabel);
        infoPanel.add(clientLabel);
        infoPanel.add(invoiceNumberLabel);
        infoPanel.add(emptyLabel);

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
        table.setFont(Style.DEFAULT_FONT);
        table.setForeground(Style.DARK_BLUE);
        table.setBackground(Style.CREAM);
        table.setRowHeight(24);
        table.setFillsViewportHeight(true);
        table.setGridColor(Style.SKY_BLUE);
        table.setShowGrid(true);

        JTableHeader header = table.getTableHeader();
        header.setFont(Style.BUTTON_FONT.deriveFont(15f));
        header.setBackground(Style.SOFT_RED);
        header.setForeground(Color.WHITE);
        header.setOpaque(true);

        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setBorder(BorderFactory.createEmptyBorder());
        tableScroll.getViewport().setBackground(Style.CREAM);

        content.add(Box.createRigidArea(new Dimension(0,10)));
        content.add(tableScroll);

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
        Style.stylePanel(totalPanel);

        JLabel totalLabel = new JLabel("Total TTC (promo) : " + String.format("%.2f €", totalPromo));
        totalLabel.setFont(Style.BUTTON_FONT.deriveFont(18f));
        totalLabel.setForeground(Style.DEEP_RED);

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
        legal.setFont(Style.DEFAULT_FONT.deriveFont(Font.ITALIC, 12f));
        legal.setForeground(Style.DARK_BLUE);
        legal.setBackground(Style.CREAM);
        legal.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
        legal.setLineWrap(true);
        legal.setWrapStyleWord(true);

        content.add(Box.createRigidArea(new Dimension(0,10)));
        content.add(legal);

        JScrollPane scrollContent = new JScrollPane(content);
        scrollContent.setBorder(BorderFactory.createEmptyBorder());
        scrollContent.getViewport().setBackground(Style.CREAM);
        setContentPane(scrollContent);
    }

    public static void showInvoiceForUser(User user) {
        String clientName = user.getFirstName() + " " + user.getLastName();

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

        InvoiceView iv = new InvoiceView(order, details, clientName);
        iv.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            User u = UserDAO.findUserById(1);
            if (u != null) showInvoiceForUser(u);
            else JOptionPane.showMessageDialog(null, "Utilisateur de test non trouvé.");
        });
    }
}
