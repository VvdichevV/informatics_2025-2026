# User Management System - Project Documentation

## 1. Project Specifications

- Project Title: User Management System
- Developer: [Your Name/Team]
- Java Version: JDK 11
- Database Engine: MySQL 8.3.0
- GUI Library: Java Swing

### Executive Summary
- Application Type: Desktop CRUD Application
- Architecture: Model-View-Controller (MVC)
- Primary Goal: To provide a graphical interface for managing user data stored in a relational database.
- Domain Entity: users

This system enables registration, login, profile viewing, profile editing, and administrative user management through a Swing-based desktop interface.

## 2. System Architecture

The application uses a layered architecture where the graphical interface is separated from database access logic.

### Presentation Layer
- Implemented with Java Swing windows and forms.
- Primary classes: `LoginForm`, `RegistrationForm`, `MainForm`, `ProfileForm`, `EditProfileForm`, `AdminForm`.
- These classes render `JFrame`, `JPanel`, `JTextField`, `JPasswordField`, `JButton`, and `JTable` components.

### Data Access Layer (DAO)
- Implemented by `DatabaseHelper` and the `UserDAO` interface.
- Responsible for SQL queries: `SELECT`, `INSERT`, `UPDATE`, `DELETE`.
- Uses `PreparedStatement` for safe parameter binding.

### Database Layer
- Physical database tables stored in MySQL.
- The application connects using JDBC and executes SQL statements directly.

### MVC Overview
- Model: POJOs such as `User` and DAO classes such as `DatabaseHelper`.
- View: Swing forms and GUI objects.
- Controller: Action listeners in forms that validate input and call DAO methods.

This separation ensures UI logic is isolated from database access logic.

## 3. Database Design (ERD)

### Entity Relationship Diagram (Textual)
- Entity: `user`
- Primary Key: `username`
- Relationship: single table application model, no additional foreign keys.

### Table Definitions

#### Table: `user`
- `username` VARCHAR(50) PRIMARY KEY
- `password` VARCHAR(255)
- `FirstName` VARCHAR(100)
- `LastName` VARCHAR(100)
- `Email` VARCHAR(100)
- `DateOfBirth` VARCHAR(20)
- `PhoneNumber` VARCHAR(20)
- `Address` VARCHAR(255)
- `picture` LONGBLOB

This table stores all user personal data, authentication credentials, and photo content.

## 4. Connection Configuration

The application connects to MySQL using JDBC.

- Driver Class: `com.mysql.cj.jdbc.Driver`
- Connection URL: `jdbc:mysql://127.0.0.1:3306/users?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC`
- Database User: `root`
- Database Password: `Password1~`

### Notes
- The current implementation stores credentials in static constants inside `DatabaseHelper` and `LoginForm`.
- Best practice: avoid hardcoding passwords in source code. Use environment variables or a properties file instead.
  - Example placeholder URL: `jdbc:mysql://localhost:3306/db_name`
  - Example username: `${DB_USER}`
  - Example password: `${DB_PASS}`

## 5. Implementation Logic

### Database Access
`DatabaseHelper` implements `UserDAO` and provides direct JDBC access for all CRUD operations.

#### Singleton Pattern
- `DatabaseHelper` uses a singleton pattern through `getInstance()`.
- This ensures only one helper instance is created, though each method opens a new JDBC connection.

#### JDBC Code Example
```java
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
} catch (SQLException ex) {
    ex.printStackTrace();
}
```

### Data Binding
- The GUI loads user rows via `ResultSet` and stores them in Java `List<List<String>>` structures.
- `LoginForm.loadUsers()` reads `username` and `password` values and displays them in the login UI.
- `MainForm` builds profile UI components from a `User` object returned by `DatabaseHelper.getUser()`.

### Prepared Statements
- All SQL operations use `PreparedStatement`.
- This prevents SQL injection and improves parameter safety.

### DAO Methods
- `insertUser(User user)`
- `getUser(String username)`
- `updateUser(User user)`
- `deleteUser(String username)`
- `getAllUsers()`
- `checkLogin(String username, String password)`

## 6. Use Cases

### Actors
- **Regular User**: Can login, view/edit own profile, register.
- **Special User**: Not implemented (could be moderator; currently admin handles extra).
- **Administrator**: Can manage all users (view, edit, delete).

### Use Cases
1. **User Registration**:
   - Actor: Regular User
   - Precondition: User not logged in.
   - Flow: User fills registration form (username, password, details, photo), validates input, inserts to DB, shows success message.
   - GUI: RegistrationForm with fields and buttons.

2. **User Login**:
   - Actor: Regular User/Admin
   - Precondition: User registered.
   - Flow: Enter username/password, validate against DB, open MainForm or show error.
   - GUI: LoginForm.

3. **View Profile**:
   - Actor: Regular User
   - Precondition: Logged in.
   - Flow: Display user details and photo.
   - GUI: ProfileForm.

4. **Edit Profile**:
   - Actor: Regular User
   - Precondition: Logged in.
   - Flow: Load current data, edit fields/photo, update DB, show success.
   - GUI: EditProfileForm.

5. **Admin Manage Users**:
   - Actor: Administrator
   - Precondition: Logged in as admin.
   - Flow: View user table, select user, edit/delete, confirm actions.
   - GUI: AdminForm with JTable.

6. **Logout**:
   - Actor: All
   - Flow: Close current form, return to LoginForm.

## 7. Activity Diagram

Textual Representation:
- Start -> Login Form -> [Valid Login] -> Main Form -> [View Profile] -> Profile Form -> End
- Main Form -> [Edit Profile] -> Edit Profile Form -> Update DB -> Success Message -> Main Form
- Main Form -> [Admin Panel] -> Admin Form -> Select User -> Edit/Delete -> Confirm -> Update DB -> Refresh Table
- [Invalid Login] -> Error Message -> Login Form
- Login Form -> [Register] -> Registration Form -> Insert DB -> Success -> Login Form

(Use a tool like draw.io to create a visual diagram based on this flow.)

## 8. Key Modules & Class Responsibilities

- `LoginForm.java`: Login UI, database credential check, user navigation, and registration launch.
- `MainForm.java`: Main user dashboard, profile summary, and action buttons.
- `RegistrationForm.java`: User registration and account creation.
- `EditProfileForm.java`: Edit user profile fields and photo.
- `ProfileForm.java`: Read-only display of user details.
- `AdminForm.java`: Admin user table, edit/delete controls, and list refresh.
- `DatabaseHelper.java`: DAO implementation with JDBC CRUD logic.
- `UserDAO.java`: DAO interface defining data operations.
- `User.java`: Entity class mapping the `user` table.
- `PopulateData.java`: Utility to seed or initialize sample data.
- `DatabaseHelperL1Test.java`: Integration tests targeting DAO/database behavior.

## 7. Setup & Environment Configuration

### Prerequisites
- Java Development Kit (JDK) 11 or higher
- MySQL Server (local instance)
- IDE such as IntelliJ IDEA or VS Code with Java support
- Maven installed

### Installation Steps
1. Install MySQL and create the database.
2. Create the `users` database:
   ```sql
   CREATE DATABASE users;
   ```
3. Create the `user` table using the schema above.
4. Ensure the MySQL JDBC connector is available via Maven dependency in `pom.xml`.
5. Build the project:
   ```bash
   mvn clean compile
   ```
6. Run the login UI:
   ```bash
   mvn exec:java -Dexec.mainClass=LoginForm
   ```
   or run `LoginForm.main()` from your IDE.

### Build & Run
- Build: `mvn clean package`
- Execute tests: `mvn verify`

## 8. Known Constraints & Security

- Concurrency: The application is designed for single-user desktop use. It opens a new JDBC connection per operation and does not include fine-grained row locking.
- Security: Passwords are stored in plaintext in the current implementation. This is not secure for production.
- Validation: Basic checks exist in the GUI, but there is no strict regex validation across all fields.

### Recommended Improvements
- Hash passwords with BCrypt or SHA-256 before saving.
- Move DB credentials to environment variables or a configuration file.
- Add stronger input validation for email, date, and phone fields.
- Use a connection pool or singleton connection manager for production.

## 9. CRUD Operations Logic

### Create
- `RegistrationForm` collects user data and calls `DatabaseHelper.insertUser()`.

### Read
- `LoginForm` and `MainForm` use `DatabaseHelper.getUser()` or `getAllUsers()`.
- `MainForm` displays user profile details after successful login.

### Update
- `EditProfileForm` updates user fields and calls `DatabaseHelper.updateUser()`.

### Delete
- `AdminForm` or user profile actions call `DatabaseHelper.deleteUser()`.

## 10. Error Handling

### SQL Exceptions
- `SQLException`: caught in DAO methods and logged with `ex.printStackTrace()`.
- `LoginForm` displays `JOptionPane` alerts for database errors during login.

### Validation Errors
- Empty username/password fields prevent login.
- UI uses dialog alerts for missing or invalid data.

### Data Integrity
- Duplicate key insertion will fail in `insertUser()` if `username` already exists.
- `DatabaseHelper.checkLogin()` returns false when login credentials do not match.

## 11. Additional Notes

- There is no separate configuration file for database credentials; the current application uses hardcoded values or environment variables if set in `LoginForm`.
- The application is primarily a desktop demo for user CRUD management and authentication.
- For production readiness, migrate data storage logic to a configuration-managed connection and secure password storage.
