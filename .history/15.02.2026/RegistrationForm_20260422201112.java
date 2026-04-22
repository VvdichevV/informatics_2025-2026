import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;
import javax.swing.filechooser.FileNameExtensionFilter;

public class RegistrationForm extends JFrame {

    JTextField usernameField, firstNameField, lastNameField, emailField,
            phoneField, addressField, dobField;

    JPasswordField passwordField;

    JButton insertButton, updateButton, deleteButton, saveButton;

    JLabel photoLabel;
    JButton choosePhotoButton;
    byte[] photoBytes;

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
        setLayout(new GridLayout(11, 2));

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

        add(new JLabel("Photo:"));
        photoLabel = new JLabel();
        add(photoLabel);

        choosePhotoButton = new JButton("Choose Photo");
        add(choosePhotoButton);
        choosePhotoButton.addActionListener(e -> choosePhoto());

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
        if (!validateInput()) return;

        User user = new User();
        user.setUsername(usernameField.getText());
        user.setPassword(new String(passwordField.getPassword()));
        user.setFirstName(firstNameField.getText());
        user.setLastName(lastNameField.getText());
        user.setEmail(emailField.getText());
        user.setDateOfBirth(dobField.getText());
        user.setPhoneNumber(phoneField.getText());
        user.setAddress(addressField.getText());
        user.setPicture(photoBytes);

        if (DatabaseHelper.getInstance().insertUser(user)) {
            JOptionPane.showMessageDialog(this, "User Inserted!");
        } else {
            JOptionPane.showMessageDialog(this, "Insert Error!");
        }
    }

    // ================= UPDATE =================
    void updateUser() {

        if (!adminCheck()) return;

        User user = new User();
        user.setUsername(usernameField.getText());
        user.setPassword(new String(passwordField.getPassword()));
        user.setFirstName(firstNameField.getText());
        user.setLastName(lastNameField.getText());
        user.setEmail(emailField.getText());
        user.setDateOfBirth(dobField.getText());
        user.setPhoneNumber(phoneField.getText());
        user.setAddress(addressField.getText());
        user.setPicture(photoBytes);

        if (DatabaseHelper.getInstance().updateUser(user)) {
            JOptionPane.showMessageDialog(this, "User Updated!");
        } else {
            JOptionPane.showMessageDialog(this, "Update Error!");
        }
    }

    void deleteUser() {

        if (!adminCheck()) return;

        if (DatabaseHelper.deleteUser(usernameField.getText())) {
            JOptionPane.showMessageDialog(this, "User Deleted!");
        } else {
            JOptionPane.showMessageDialog(this, "Delete Error!");
        }
    }

    void selectUser() {

        User user = DatabaseHelper.getUser(usernameField.getText());

        if (user != null) {

            passwordField.setText(user.getPassword());
            firstNameField.setText(user.getFirstName());
            lastNameField.setText(user.getLastName());
            emailField.setText(user.getEmail());
            dobField.setText(user.getDateOfBirth());
            phoneField.setText(user.getPhoneNumber());
            addressField.setText(user.getAddress());

            if (user.getPicture() != null) {
                ImageIcon icon = new ImageIcon(user.getPicture());
                Image image = icon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
                photoLabel.setIcon(new ImageIcon(image));
                photoBytes = user.getPicture();
            }

        } else {
            JOptionPane.showMessageDialog(this, "User not found.");
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

    private boolean validateInput() {
        if (usernameField.getText().isEmpty() || new String(passwordField.getPassword()).isEmpty() ||
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