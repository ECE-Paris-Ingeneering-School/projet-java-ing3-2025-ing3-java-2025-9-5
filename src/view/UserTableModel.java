package view;
/**
 * Table model représentant la liste des utilisateurs pour affichage dans un tableau.
 * Ce modèle est utilisé pour afficher les informations des utilisateurs dans une JTable, telles que leur ID,
 * prénom, nom, email et rôle (type d'utilisateur).
 */
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.List;
import model.User;

public class UserTableModel extends AbstractTableModel {
    private List<User> users = new ArrayList<>();
    private final String[] columns = {"ID", "Prénom", "Nom", "Email", "Rôle"};

    public void setUsers(List<User> users) {
        this.users = users;
        fireTableDataChanged();
    }

    @Override
    public int getRowCount() {
        return users.size();
    }

    @Override
    public int getColumnCount() {
        return columns.length;
    }

    @Override
    public String getColumnName(int column) {
        return columns[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        User u = users.get(rowIndex);
        switch (columnIndex) {
            case 0: return u.getUserId();
            case 1: return u.getFirstName();
            case 2: return u.getLastName();
            case 3: return u.getEmail();
            case 4: return u.getUserType();
            default: return null;
        }
    }
}
