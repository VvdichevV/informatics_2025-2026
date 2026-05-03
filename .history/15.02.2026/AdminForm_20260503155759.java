import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminForm extends JFrame {

    private UserTableModel tableModel;
    private JTable userTable;
    private JButton editButton, deleteButton;

    public AdminForm() {
        setTitle("Admin Panel");
        setSize(600, 400);
        setLayout(new BorderLayout());

        tableModel = new UserTableModel();
        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        editButton = new JButton("Edit User");
        deleteButton = new JButton("Delete User");
        JButton refreshButton = new JButton("Refresh");
        JButton closeButton = new JButton("Close");

        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);
        buttonPanel.add(closeButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Listeners
        editButton.addActionListener(e -> editSelectedUser());
        deleteButton.addActionListener(e -> deleteSelectedUser());
        refreshButton.addActionListener(e -> loadUsers());
        closeButton.addActionListener(e -> this.dispose());

        // ListSelectionListener for JTable
        userTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                boolean selected = userTable.getSelectedRow() >= 0;
                editButton.setEnabled(selected);
                deleteButton.setEnabled(selected);
            }
        });

        // Initially disable buttons
        editButton.setEnabled(false);
        deleteButton.setEnabled(false);

        loadUsers();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadUsers() {
        tableModel.setUsers(DatabaseHelper.getInstance().getAllUsers());
    }

    private void editSelectedUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow >= 0) {
            User user = tableModel.getUserAt(selectedRow);
            new EditProfileForm(user.getUsername());
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user.");
        }
    }

    private void deleteSelectedUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow >= 0) {
            User user = tableModel.getUserAt(selectedRow);
            int confirm = JOptionPane.showConfirmDialog(this, "Delete user " + user.getUsername() + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (DatabaseHelper.getInstance().deleteUser(user.getUsername())) {
                    JOptionPane.showMessageDialog(this, "User deleted!");
                    loadUsers();
                } else {
                    JOptionPane.showMessageDialog(this, "Delete failed!");
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user.");
        }
    }

    private class UserTableModel extends AbstractTableModel {
        private List<User> users;
        private String[] columnNames = {"Username", "First Name", "Last Name", "Email"};

        public void setUsers(List<User> users) {
            this.users = users;
            fireTableDataChanged();
        }

        @Override
        public int getRowCount() {
            return users == null ? 0 : users.size();
        }

        @Override
        public int getColumnCount() {
            return columnNames.length;
        }

        @Override
        public String getColumnName(int column) {
            return columnNames[column];
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            User user = users.get(rowIndex);
            switch (columnIndex) {
                case 0: return user.getUsername();
                case 1: return user.getFirstName();
                case 2: return user.getLastName();
                case 3: return user.getEmail();
                default: return null;
            }
        }

        public User getUserAt(int rowIndex) {
            return users.get(rowIndex);
        }
    }
}