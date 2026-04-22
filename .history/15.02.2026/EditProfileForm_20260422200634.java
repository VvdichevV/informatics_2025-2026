import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import javax.swing.filechooser.FileNameExtensionFilter;

public class EditProfileForm extends JFrame {

    private String username;
    private JTextField firstNameField, lastNameField, emailField, phoneField, addressField, dobField;
    private JPasswordField passwordField;
    private JLabel photoLabel;
    private byte[] photoBytes;

    public EditProfileForm(String username) {
        this.username = username;
        setTitle("Edit Profile - " + username);
        setSize(500, 500);
        setLayout(new GridLayout(0, 2));

        User user = DatabaseHelper.getUser(username);
        if (user == null) {
            JOptionPane.showMessageDialog(this, "User not found!");
            return;
        }

        add(new JLabel("Username:"));
        add(new JLabel(user.getUsername())); // Read-only

        add(new JLabel("Password:"));
        passwordField = new JPasswordField(user.getPassword());
        add(passwordField);

        add(new JLabel("First Name:"));
        firstNameField = new JTextField(user.getFirstName());
        add(firstNameField);

        add(new JLabel("Last Name:"));
        lastNameField = new JTextField(user.getLastName());
        add(lastNameField);

        add(new JLabel("Email:"));
        emailField = new JTextField(user.getEmail());
        add(emailField);

        add(new JLabel("Date of Birth (YYYY-MM-DD):"));
        dobField = new JTextField(user.getDateOfBirth());
        add(dobField);

        add(new JLabel("Phone:"));
        phoneField = new JTextField(user.getPhoneNumber());
        add(phoneField);

        add(new JLabel("Address:"));
        addressField = new JTextField(user.getAddress());
        add(addressField);

        add(new JLabel("Photo:"));
        photoLabel = new JLabel();
        if (user.getPicture() != null) {
            ImageIcon icon = new ImageIcon(user.getPicture());
            Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            photoLabel.setIcon(new ImageIcon(image));
            photoBytes = user.getPicture();
        }
        add(photoLabel);

        JButton choosePhoto = new JButton("Choose Photo");
        add(choosePhoto);
        choosePhoto.addActionListener(e -> choosePhoto());

        JButton saveButton = new JButton("Save");
        add(saveButton);
        saveButton.addActionListener(e -> saveProfile());

        JButton cancelButton = new JButton("Cancel");
        add(cancelButton);
        cancelButton.addActionListener(e -> this.dispose());

        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void choosePhoto() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter("Image files", "jpg", "png", "gif");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            File file = chooser.getSelectedFile();
            try (FileInputStream fis = new FileInputStream(file)) {
                photoBytes = fis.readAllBytes();
                ImageIcon icon = new ImageIcon(photoBytes);
                Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                photoLabel.setIcon(new ImageIcon(image));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(this, "Error loading photo: " + ex.getMessage());
            }
        }
    }

    private void saveProfile() {
        if (!validateInput()) return;

        User user = new User();
        user.setUsername(username);
        user.setPassword(new String(passwordField.getPassword()));
        user.setFirstName(firstNameField.getText());
        user.setLastName(lastNameField.getText());
        user.setEmail(emailField.getText());
        user.setDateOfBirth(dobField.getText());
        user.setPhoneNumber(phoneField.getText());
        user.setAddress(addressField.getText());
        user.setPicture(photoBytes);

        if (DatabaseHelper.updateUser(user)) {
            JOptionPane.showMessageDialog(this, "Profile updated!");
            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Update failed!");
        }
    }

    private boolean validateInput() {
        if (new String(passwordField.getPassword()).isEmpty() ||
            firstNameField.getText().isEmpty() || lastNameField.getText().isEmpty() ||
            emailField.getText().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all required fields!");
            return false;
        }
        if (!emailField.getText().contains("@")) {
            JOptionPane.showMessageDialog(this, "Invalid email format!");
            return false;
        }
        return true;
    }
}