import java.sql.*;

/**
 * Test-specific DatabaseHelper that allows overriding the database connection
 * for use in integration tests with TestContainers.
 * 
 * This extends the production DatabaseHelper to allow tests to provide
 * a custom database URL, username, and password from TestContainers.
 */
public class TestDatabaseHelper extends DatabaseHelper {
    
    private String testDbUrl;
    private String testDbUser;
    private String testDbPassword;

    public TestDatabaseHelper(String dbUrl, String dbUser, String dbPassword) {
        super();
        this.testDbUrl = dbUrl;
        this.testDbUser = dbUser;
        this.testDbPassword = dbPassword;
    }

    @Override
    public User getUser(String username) {
        String sql = "SELECT * FROM user WHERE username = ?";
        try (Connection con = DriverManager.getConnection(testDbUrl, testDbUser, testDbPassword);
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

    @Override
    public boolean insertUser(User user) {
        String sql = "INSERT INTO user (username,password,FirstName,LastName,Email,DateOfBirth,PhoneNumber,Address,picture) VALUES (?,?,?,?,?,?,?,?,?)";
        try (Connection con = DriverManager.getConnection(testDbUrl, testDbUser, testDbPassword);
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

    @Override
    public boolean updateUser(User user) {
        String sql = "UPDATE user SET password=?, FirstName=?, LastName=?, Email=?, DateOfBirth=?, PhoneNumber=?, Address=?, picture=? WHERE username=?";
        try (Connection con = DriverManager.getConnection(testDbUrl, testDbUser, testDbPassword);
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

    @Override
    public boolean deleteUser(String username) {
        String sql = "DELETE FROM user WHERE username = ?";
        try (Connection con = DriverManager.getConnection(testDbUrl, testDbUser, testDbPassword);
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, username);
            pst.executeUpdate();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkLogin(String username, String password) {
        String sql = "SELECT * FROM user WHERE username=? AND password=?";
        try (Connection con = DriverManager.getConnection(testDbUrl, testDbUser, testDbPassword);
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

    @Override
    public java.util.List<User> getAllUsers() {
        String sql = "SELECT * FROM user";
        java.util.List<User> users = new java.util.ArrayList<>();
        try (Connection con = DriverManager.getConnection(testDbUrl, testDbUser, testDbPassword);
             Statement st = con.createStatement()) {
            ResultSet rs = st.executeQuery(sql);
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
}
