package mysqljdbcdriver;

import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/testdb";
        String user = "root";
        String password = "password";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Connected to MySQL!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
