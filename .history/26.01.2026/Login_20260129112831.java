import javax.swing.*;
import java.awt.*;

public class Login {
    static JPanel content;
    static JLabel userLabel;
    static JLabel passwordLabel;
    static JTextField userTextField;
    static JPasswordField passwordTextField;

    static void createAndShowGUI() {
        JFrame myFrame = new JFrame("Login Form");
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.setPreferredSize(new Dimension(600, 400));
        myFrame.setResizable(false);

        content = (JPanel) myFrame.getContentPane();
        content.setLayout(new FlowLayout());

        userLabel = new JLabel("Username");
        userTextField = new JTextField(20);
        userLabel.setLabelFor(userTextField);
        content.add(userLabel);
        content.add(userTextField);

        passwordLabel = new JLabel("Password");
        passwordTextField = new JPasswordField(20);
        passwordLabel.setLabelFor(passwordTextField);
        content.add(passwordLabel);
        content.add(passwordTextField);

        myFrame.pack();
        myFrame.setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                UIManager.put("swing.boldMetal", Boolean.FALSE);
                createAndShowGUI();
            }
        });
    }
}