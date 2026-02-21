import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.Scanner;

public class RegistrationForm extends JFrame {

    JTextField usernameField, firstNameField, lastNameField, emailField,
            phoneField, addressField, dobField;

    JPasswordField passwordField;

    JButton insertButton, updateButton, deleteButton, saveButton;

    static final String DB_URL = LoginForm.DB_URL;
    static final String DB_USER = LoginForm.DB_USER;
    static final String DB_PASS = LoginForm.DB_PASS;

    static final String ADMIN_PASSWORD;

    static {
        System.out.print("Enter Admin Password: ");
        Scanner sc = new Scanner(System.in);
        ADMIN_PASSWORD = sc.nextLine();
    }

    public RegistrationForm() {

        setTitle("User Registration");
        setSize(500, 400);
        setLayout(new GridLayout(10, 2));

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        firstNameField = new JTextField();
        lastNameField = new JTextField();
        emailField = new JTextField();
        dobField = new JTextField();
        phoneField = new JTextField();
        addressField = new JTextField();

        add(new JLabel("Username:")); add(usernameField);
        add(new JLabel("Password:")); add(passwordField);
        add(new JLabel("First Name:")); add(firstNameField);
        add(new JLabel("Last Name:")); add(lastNameField);
        add(new JLabel("Email:")); add(emailField);
        add(new JLabel("Date of Birth (YYYY-MM-DD):")); add(dobField);
        add(new JLabel("Phone:")); add(phoneField);
        add(new JLabel("Address:")); add(addressField);

        insertButton = new JButton("Insert");
        updateButton = new JButton("Update");
        deleteButton = new JButton("Delete");
        saveButton = new JButton("Save");

        add(insertButton); add(updateButton);
        add(deleteButton); add(saveButton);

        insertButton.addActionListener(e -> insertUser());
        updateButton.addActionListener(e -> updateUser());
        deleteButton.addActionListener(e -> deleteUser());
        saveButton.addActionListener(e -> selectUser());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    // ================= INSERT =================
    void insertUser() {

        String sql = "INSERT INTO user " +
                "(username,password,FirstName,LastName,Email,DateOfBirth,PhoneNumber,Address) " +
                "VALUES (?,?,?,?,?,?,?,?)";

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, usernameField.getText());
            pst.setString(2, new String(passwordField.getPassword()));
            pst.setString(3, firstNameField.getText());
            pst.setString(4, lastNameField.getText());
            pst.setString(5, emailField.getText());
            pst.setString(6, dobField.getText());
            pst.setString(7, phoneField.getText());
            pst.setString(8, addressField.getText());

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "User Inserted!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Insert Error: " + ex.getMessage());
        }
    }

    // ================= UPDATE =================
    void updateUser() {

        if (!adminCheck()) return;

        String sql = "UPDATE user SET password=?, FirstName=?, LastName=?, Email=?, " +
                "DateOfBirth=?, PhoneNumber=?, Address=? WHERE username=?";

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, new String(passwordField.getPassword()));
            pst.setString(2, firstNameField.getText());
            pst.setString(3, lastNameField.getText());
            pst.setString(4, emailField.getText());
            pst.setString(5, dobField.getText());
            pst.setString(6, phoneField.getText());
            pst.setString(7, addressField.getText());
            pst.setString(8, usernameField.getText());

            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "User Updated!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Update Error: " + ex.getMessage());
        }
    }

    void deleteUser() {

        if (!adminCheck()) return;

        String sql = "DELETE FROM user WHERE username=?";

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, usernameField.getText());
            pst.executeUpdate();

            JOptionPane.showMessageDialog(this, "User Deleted!");

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Delete Error: " + ex.getMessage());
        }
    }

    void selectUser() {

        String sql = "SELECT * FROM user WHERE username=?";

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, usernameField.getText());

            ResultSet rs = pst.executeQuery();

            if (rs.next()) {

                passwordField.setText(rs.getString("password"));
                firstNameField.setText(rs.getString("FirstName"));
                lastNameField.setText(rs.getString("LastName"));
                emailField.setText(rs.getString("Email"));
                dobField.setText(rs.getString("DateOfBirth"));
                phoneField.setText(rs.getString("PhoneNumber"));
                addressField.setText(rs.getString("Address"));

            } else {
                JOptionPane.showMessageDialog(this, "User not found.");
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Select Error: " + ex.getMessage());
        }
    }

    boolean adminCheck() {

        String input = JOptionPane.showInputDialog(this, "Enter Admin Password:");

        if (input == null || !input.equals(ADMIN_PASSWORD)) {
            JOptionPane.showMessageDialog(this, "Wrong Admin Password!");
            return false;
        }

        return true;
    }
}
