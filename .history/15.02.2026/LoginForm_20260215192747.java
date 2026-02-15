import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginForm {

    static JTextField userTextField;
    static JPasswordField passwordField;
    static JButton okButton, cancelButton, nextButton, prevButton;

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

    static void createAndShowGUI() {

        JFrame frame = new JFrame("Login Form");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 200);
        frame.setLayout(new GridLayout(3, 1));

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

        JPanel secondButtonPane = new JPanel();
        prevButton = new JButton("<<");
        nextButton = new JButton(">>");
        

        buttonPane.add(okButton);
        buttonPane.add(cancelButton);
        secondButtonPane.add(nextButton);
        secondButtonPane.add(prevButton);

        frame.add(userPane);
        frame.add(passPane);
        frame.add(buttonPane)
        frame.add(secondButtonPane);

        okButton.addActionListener(e -> login());
        cancelButton.addActionListener(e -> System.exit(0));
        nextButton.addActionListener(e -> System.out.println("Next button clicked"));
        prevButton.addActionListener(e -> System.out.println("Previous button clicked"));

        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    static void login() {

        String username = userTextField.getText();
        String password = new String(passwordField.getPassword());

        if (checkLogin(username, password)) {
            JOptionPane.showMessageDialog(null, "OK");
            System.out.println("OK");
        } else {
            JOptionPane.showMessageDialog(null, "Wrong username or password!");
        }
    }

    static boolean checkLogin(String user, String pass) {

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
        SwingUtilities.invokeLater(LoginForm::createAndShowGUI);
    }
}
