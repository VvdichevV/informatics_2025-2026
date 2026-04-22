import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper implements UserDAO {

    public DatabaseHelper() {
        super();
    }
    static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/users?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    static final String DB_USER = "root";
    static final String DB_PASS = "Password1~";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ignored) {}
    }

    public User getUser(String username) {
        String sql = "SELECT * FROM user WHERE username = ?";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, username);
            ResultSet rs = pst.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setEmail(rs.getString("Email"));
                user.setDateOfBirth(rs.getString("DateOfBirth"));
                user.setPhoneNumber(rs.getString("PhoneNumber"));
                user.setAddress(rs.getString("Address"));
                user.setPicture(rs.getBytes("picture"));
                return user;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public boolean insertUser(User user) {
        String sql = "INSERT INTO user (username,password,FirstName,LastName,Email,DateOfBirth,PhoneNumber,Address,picture) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, user.getUsername());
            pst.setString(2, user.getPassword());
            pst.setString(3, user.getFirstName());
            pst.setString(4, user.getLastName());
            pst.setString(5, user.getEmail());
            pst.setString(6, user.getDateOfBirth());
            pst.setString(7, user.getPhoneNumber());
            pst.setString(8, user.getAddress());
            pst.setBytes(9, user.getPicture());
            pst.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean updateUser(User user) {
        String sql = "UPDATE user SET password=?, FirstName=?, LastName=?, Email=?, DateOfBirth=?, PhoneNumber=?, Address=?, picture=? WHERE username=?";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, user.getPassword());
            pst.setString(2, user.getFirstName());
            pst.setString(3, user.getLastName());
            pst.setString(4, user.getEmail());
            pst.setString(5, user.getDateOfBirth());
            pst.setString(6, user.getPhoneNumber());
            pst.setString(7, user.getAddress());
            pst.setBytes(8, user.getPicture());
            pst.setString(9, user.getUsername());
            pst.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public boolean deleteUser(String username) {
        String sql = "DELETE FROM user WHERE username=?";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, username);
            pst.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM user";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setFirstName(rs.getString("FirstName"));
                user.setLastName(rs.getString("LastName"));
                user.setEmail(rs.getString("Email"));
                user.setDateOfBirth(rs.getString("DateOfBirth"));
                user.setPhoneNumber(rs.getString("PhoneNumber"));
                user.setAddress(rs.getString("Address"));
                user.setPicture(rs.getBytes("picture"));
                users.add(user);
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return users;
    }

    public boolean checkLogin(String username, String password) {
        String sql = "SELECT 1 FROM user WHERE username = ? AND password = ? LIMIT 1";
        try (Connection con = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, username);
            pst.setString(2, password);
            ResultSet rs = pst.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}