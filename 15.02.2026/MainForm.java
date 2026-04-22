import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainForm extends JFrame {

    private String username;

    public MainForm(String username) {
        this.username = username;
        setTitle("Main Application - " + username);
        setSize(600, 400);
        setLayout(new BorderLayout());

        User user = DatabaseHelper.getInstance().getUser(username);
        if (user == null) {
            JOptionPane.showMessageDialog(this, "User not found!");
            return;
        }

        JLabel welcomeLabel = new JLabel("Welcome, " + user.getFirstName() + " " + user.getLastName() + "!");
        welcomeLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(welcomeLabel, BorderLayout.NORTH);

        JPanel infoPanel = new JPanel(new GridLayout(0, 2));
        infoPanel.add(new JLabel("Username:"));
        infoPanel.add(new JLabel(user.getUsername()));
        infoPanel.add(new JLabel("Email:"));
        infoPanel.add(new JLabel(user.getEmail()));
        infoPanel.add(new JLabel("Phone:"));
        infoPanel.add(new JLabel(user.getPhoneNumber()));
        infoPanel.add(new JLabel("Address:"));
        infoPanel.add(new JLabel(user.getAddress()));
        add(infoPanel, BorderLayout.CENTER);

        if (user.getPicture() != null) {
            ImageIcon icon = new ImageIcon(user.getPicture());
            Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            JLabel photoLabel = new JLabel(new ImageIcon(image));
            add(photoLabel, BorderLayout.WEST);
        }

        JPanel buttonPanel = new JPanel();
        JButton viewProfile = new JButton("View Full Profile");
        JButton editProfile = new JButton("Edit Profile");
        JButton logout = new JButton("Logout");
        JButton adminPanel = new JButton("Admin Panel");

        buttonPanel.add(viewProfile);
        buttonPanel.add(editProfile);
        buttonPanel.add(logout);

        if ("admin".equals(username)) {
            buttonPanel.add(adminPanel);
        }

        add(buttonPanel, BorderLayout.SOUTH);

        viewProfile.addActionListener(e -> new ProfileForm(username));
        editProfile.addActionListener(e -> new EditProfileForm(username));
        logout.addActionListener(e -> {
            new LoginForm().createAndShowGUI();
            this.dispose();
        });
        adminPanel.addActionListener(e -> new AdminForm());

        setLocationRelativeTo(null);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}