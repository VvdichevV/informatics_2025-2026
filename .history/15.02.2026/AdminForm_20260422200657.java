import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class AdminForm extends JFrame {

    private UserTableModel tableModel;
    private JTable userTable;

    public AdminForm() {
        setTitle("Admin Panel");
        setSize(600, 400);
        setLayout(new BorderLayout());

        tableModel = new UserTableModel();
        userTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(userTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        JButton editButton = new JButton("Edit User");
        JButton deleteButton = new JButton("Delete User");
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

        loadUsers();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadUsers() {
        tableModel.setUsers(DatabaseHelper.getAllUsers());
    }

    private void editSelectedUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow >= 0) {
            String username = (String) tableModel.getValueAt(selectedRow, 0);
            new EditProfileForm(username);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user.");
        }
    }

    private void deleteSelectedUser() {
        int selectedRow = userTable.getSelectedRow();
        if (selectedRow >= 0) {
            String username = (String) tableModel.getValueAt(selectedRow, 0);
            int confirm = JOptionPane.showConfirmDialog(this, "Delete user " + username + "?", "Confirm", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                if (DatabaseHelper.deleteUser(username)) {
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
}