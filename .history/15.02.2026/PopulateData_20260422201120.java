public class PopulateData {

    public static void main(String[] args) {
        // Sample data
        User user1 = new User("john", "pass123", "John", "Doe", "john@example.com", "1990-01-01", "123456789", "123 Main St", null);
        User user2 = new User("jane", "pass456", "Jane", "Smith", "jane@example.com", "1992-02-02", "987654321", "456 Elm St", null);
        User user3 = new User("admin", "admin", "Admin", "User", "admin@example.com", "1980-01-01", "000000000", "Admin St", null);

        DatabaseHelper.getInstance().insertUser(user1);
        DatabaseHelper.getInstance().insertUser(user2);
        DatabaseHelper.getInstance().insertUser(user3);

        System.out.println("Sample data inserted.");
    }
}