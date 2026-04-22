import javax.swing.*;
import java.awt.*;

public class ProfileForm extends JFrame {

    public ProfileForm(String username) {
        setTitle("User Profile - " + username);
        setSize(500, 400);
        setLayout(new GridLayout(0, 2));

        User user = DatabaseHelper.getInstance().getUser(username);
        if (user == null) {
            JOptionPane.showMessageDialog(this, "User not found!");
            return;
        }

        add(new JLabel("Username:"));
        add(new JLabel(user.getUsername()));
        add(new JLabel("Password:"));
        add(new JLabel(user.getPassword()));
        add(new JLabel("First Name:"));
        add(new JLabel(user.getFirstName()));
        add(new JLabel("Last Name:"));
        add(new JLabel(user.getLastName()));
        add(new JLabel("Email:"));
        add(new JLabel(user.getEmail()));
        add(new JLabel("Date of Birth:"));
        add(new JLabel(user.getDateOfBirth()));
        add(new JLabel("Phone:"));
        add(new JLabel(user.getPhoneNumber()));
        add(new JLabel("Address:"));
        add(new JLabel(user.getAddress()));

        if (user.getPicture() != null) {
            ImageIcon icon = new ImageIcon(user.getPicture());
            Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            add(new JLabel(new ImageIcon(image)));
        }

        JButton closeButton = new JButton("Close");
        add(closeButton);
        closeButton.addActionListener(e -> this.dispose());

        setLocationRelativeTo(null);
        setVisible(true);
    }
}