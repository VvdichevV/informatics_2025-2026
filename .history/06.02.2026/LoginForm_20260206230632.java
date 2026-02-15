import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class LoginForm {

    static JTextField userTextField;
    static JPasswordField passwordField;
    static JButton okButton, cancelButton;

    static final String DB_URL =
            "jdbc:mysql://localhost:3306/users?useSSL=false&serverTimezone=UTC";
    static final String DB_USER = "root";
    static final String DB_PASS = "";

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

        buttonPane.add(okButton);
        buttonPane.add(cancelButton);

        frame.add(userPane);
        frame.add(passPane);
        frame.add(buttonPane);

        okButton.addActionListener(e -> login());
        cancelButton.addActionListener(e -> System.exit(0));

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

        String sql = "SELECT * FROM user WHERE username=? AND password=?";

        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pst = con.prepareStatement(sql)) {

            pst.setString(1, user);
            pst.setString(2, pass);

            ResultSet rs = pst.executeQuery();
            return rs.next();  

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginForm::createAndShowGUI);
    }
}
