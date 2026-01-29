import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
public class LoadDriver {
private static Connection conn;
public static void main(String[] args) {
try {
conn =
DriverManager.getConnection("jdbc:mysql://localhost/users?user=root&password=My$ql_$erveR@2024");
// Do something with the Connection
} catch (SQLException ex) {
// handle any errors
System.out.println("SQLException: " + ex.getMessage());
System.out.println("SQLState: " + ex.getSQLState());
System.out.println("VendorError: " + ex.getErrorCode());
}
}
}