import java.sql.*;

public class LoadDriver {
public static void main(String[] args) {
try (
// Step 1: Connect to the database via a &#39;Connection&#39; object called &#39;conn&#39;
Connection conn = DriverManager.getConnection(
"jdbc:mysql://localhost:3306/school",
"root", "My$ql_$erveR@2024"); // For MySQL only
// The format is: "jdbc:mysql://hostname:port/databaseName", "username", "password"
// Step 2: Construct a &#39;Statement&#39; object called &#39;stmt&#39; inside the Connection created
Statement stmt = conn.createStatement();
) {
// Step 3: Write a SQL query string. Execute the SQL query via the &#39;Statement&#39;.
// The query result is returned in a &#39;ResultSet&#39; object called &#39;rset&#39;.
String strSelect = "select * from towns;";
System.out.println("The SQL statement is: " + strSelect +
"\n"); // Echo For debugging
ResultSet rset = stmt.executeQuery(strSelect);
// Step 4: Process the &#39;ResultSet&#39; by scrolling the cursor forward via next().
// For each row, retrieve the contents of the cells with getXxx(columnName).
System.out.println("The records selected are:");
int rowCount = 0;
// Row-cursor initially positioned before the first row of the &#39;ResultSet&#39;.
// rset.next() inside the whole-loop repeatedly moves the cursor to the next row.
// It returns false if no more rows.
while(rset.next()) {
int id = rset.getInt("town_id");
int country = rset.getInt("country_id");
String name = rset.getString("name");
int phone = rset.getInt("phone_code");
System.out.println(name);
++rowCount;
}
System.out.println("Total number of records = " + rowCount);
} catch(SQLException ex) {
ex.printStackTrace();
} 

}
}