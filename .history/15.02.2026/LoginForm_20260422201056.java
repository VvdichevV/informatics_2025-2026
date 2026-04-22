// To compile, run in terminal:
// cd /home/vdichev26/Programming/Programming/Java/informatics_2025-2026/15.02.2026
// java -cp .:/home/vdichev26/Programming/Programming/Java/informatics_2025-2026/29.01.2026/mysqljdbcdriver/mysql-connector-j-9.5.0.jar LoginForm

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginForm extends JFrame {

    public LoginForm() {
        super();
    }

    JTextField userTextField;
    JPasswordField passwordField;
    JButton okButton, cancelButton, nextButton, prevButton;
    java.util.List<java.util.List<String>> records = new ArrayList<>();
    int currentIndex = -1;

    static final String DB_URL = System.getenv().getOrDefault("DB_URL",
            "jdbc:mysql://127.0.0.1:3306/users?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC");
    static final String DB_USER = System.getenv().getOrDefault("DB_USER", "root");
    static final String DB_PASS = System.getenv().getOrDefault("DB_PASS", "Password1~");

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ignored) {
        }
    }

    void createAndShowGUI() {

    this.setTitle("Login Form");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setSize(400, 200);
    this.setLayout(new GridLayout(4, 1));

    JPanel userPane = new JPanel();
    userPane.add(new JLabel("Username:"));
    userTextField = new JTextField(20);
    userPane.add(userTextField);

    JPanel passPane = new JPanel();
    passPane.add(new JLabel("Password:"));
    passwordField = new JPasswordField(20);
    passPane.add(passwordField);

    JPanel buttonPane = new JPanel();
    okButton = new JButton("OK");
    cancelButton = new JButton("Cancel");
    JButton registerButton = new JButton("Register");

    JPanel secondButtonPane = new JPanel();
    prevButton = new JButton("<<");
    nextButton = new JButton(">>");

    buttonPane.add(okButton);
    buttonPane.add(cancelButton);
    buttonPane.add(registerButton);

    secondButtonPane.add(prevButton);
    secondButtonPane.add(nextButton);

    this.add(userPane);
    this.add(passPane);
    this.add(buttonPane);
    this.add(secondButtonPane);

    okButton.addActionListener(e -> login());
    cancelButton.addActionListener(e -> System.exit(0));
    nextButton.addActionListener(e -> nextRecord());
    prevButton.addActionListener(e -> prevRecord());

    registerButton.addActionListener(e -> new RegistrationForm());

    this.setLocationRelativeTo(null);
    this.setVisible(true);

    loadUsers();
}

    void login() {

        String username = userTextField.getText();
        String password = new String(passwordField.getPassword());

        if (DatabaseHelper.getInstance().checkLogin(username, password)) {
            new MainForm(username);
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "To use the application, you must register in the system!");
        }
    }

    boolean checkLogin(String user, String pass) {

        if (user == null || user.isEmpty() || pass == null || pass.isEmpty()) {
            return false;
        }

        String[] candidateTables = { "user", "users" };

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS)) {
            for (String tbl : candidateTables) {
                String sql = "SELECT 1 FROM `" + tbl + "` WHERE username = ? AND password = ? LIMIT 1";
                try (PreparedStatement pst = con.prepareStatement(sql)) {
                    pst.setString(1, user);
                    pst.setString(2, pass);
                    try (ResultSet rs = pst.executeQuery()) {
                        if (rs.next()) {
                            return true;
                        }
                    }
                } catch (SQLException e) {

                    if (e.getErrorCode() == 1146 || "42S02".equals(e.getSQLState())) {
                        continue;
                    }
                    throw e;
                }
            }

        } catch (SQLException ex) {
            String msg = ex.getMessage();
            if (msg != null && msg.contains("Public Key Retrieval is not allowed")) {
                msg += " — add allowPublicKeyRetrieval=true to the JDBC URL (for local testing).";
            }
            JOptionPane.showMessageDialog(null, "Database error: " + msg, "DB Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        System.out.println("DB URL: " + DB_URL + "  DB_USER: " + DB_USER);
        SwingUtilities.invokeLater(() -> {
            LoginForm lf = new LoginForm();
            lf.createAndShowGUI();
        });
    }

    void loadUsers() {

        records.clear();
        currentIndex = -1;

        String sql = "SELECT username, password FROM user";

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
                PreparedStatement pst = con.prepareStatement(sql);
                ResultSet rs = pst.executeQuery()) {

            ResultSetMetaData meta = rs.getMetaData();
            int columnCount = meta.getColumnCount();

            while (rs.next()) {

                List<String> row = new ArrayList<>();

                for (int i = 1; i <= columnCount; i++) {
                    row.add(rs.getString(i));
                }

                records.add(row);
            }

            if (!records.isEmpty()) {
                currentIndex = 0;
                showRecord(currentIndex);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading users: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    void showRecord(int index) {

        if (index >= 0 && index < records.size()) {

            List<String> row = records.get(index);

            userTextField.setText(row.get(0));
            passwordField.setText(row.get(1));
        }
    }

    void nextRecord() {

        if (records.isEmpty())
            return;

        if (currentIndex < records.size() - 1) {
            currentIndex++;
            showRecord(currentIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Already at last record.");
        }
    }

    void prevRecord() {

        if (records.isEmpty())
            return;

        if (currentIndex > 0) {
            currentIndex--;
            showRecord(currentIndex);
        } else {
            JOptionPane.showMessageDialog(this, "Already at first record.");
        }
    }

}
