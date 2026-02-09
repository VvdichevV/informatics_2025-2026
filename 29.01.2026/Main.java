import java.sql.Connection;
import java.sql.DriverManager;

public class Main {
    public static void main(String[] args) throws Exception {
        String url =
            "jdbc:mysql://localhost:3306/testdb" +
            "?useSSL=false" +
            "&allowPublicKeyRetrieval=true" +
            "&serverTimezone=UTC";

        String user = "javauser";
        String password = "Str0ng!Java@2025";

        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("✅ JDBC connection successful");
        }
    }
}
