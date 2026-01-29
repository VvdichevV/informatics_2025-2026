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
        content.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        userLabel = new JLabel("Username");
        userTextField = new JTextField(20);
        userLabel.setLabelFor(userTextField);
        gbc.gridx = 0;
        gbc.gridy = 0;
        content.add(userLabel, gbc);
        gbc.gridx = 1;
        content.add(userTextField, gbc);

        passwordLabel = new JLabel("Password");
        passwordTextField = new JPasswordField(20);
        passwordLabel.setLabelFor(passwordTextField);
        gbc.gridx = 0;
        gbc.gridy = 1;
        content.add(passwordLabel, gbc);
        gbc.gridx = 1;
        content.add(passwordTextField, gbc);

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