import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Layer 1 Integration Tests for User Management System
 * 
 * Tests the integration between DatabaseHelper, UserDAO, and MySQL database
 * using TestContainers to manage a real MySQL instance during testing.
 * 
 * These tests verify:
 * - User CRUD operations (Create, Read, Update, Delete)
 * - Login verification
 * - Data persistence and integrity
 * - SQL query correctness
 */
@Testcontainers
@Tag("Layer1")
class DatabaseHelperL1Test {

    @Container
    static MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:8.3.0")
            .withDatabaseName("users")
            .withUsername("root")
            .withPassword("Password1~");

    private DatabaseHelper databaseHelper;

    @BeforeAll
    static void waitForMySQLStart() throws Exception {
        // Wait for container to be ready
        mysqlContainer.waitingFor(
            new org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy()
                .withRegEx(".*ready for connections.*")
                .withTimes(2)
                .withStartupTimeout(java.time.Duration.ofSeconds(60))
        );
        
        // Initialize the database schema
        try (Statement stmt = DriverManager.getConnection(
                mysqlContainer.getJdbcUrl(),
                mysqlContainer.getUsername(),
                mysqlContainer.getPassword()
        ).createStatement()) {
            stmt.execute("CREATE TABLE IF NOT EXISTS user (" +
                    "username VARCHAR(50) PRIMARY KEY," +
                    "password VARCHAR(255) NOT NULL," +
                    "FirstName VARCHAR(100)," +
                    "LastName VARCHAR(100)," +
                    "Email VARCHAR(100)," +
                    "DateOfBirth VARCHAR(20)," +
                    "PhoneNumber VARCHAR(20)," +
                    "Address VARCHAR(255)," +
                    "picture LONGBLOB" +
                    ")");
        }
    }

    @org.junit.jupiter.api.BeforeEach
    void setUp() throws Exception {
        // Create a TestDatabaseHelper instance that uses the test container
        databaseHelper = new TestDatabaseHelper(
            mysqlContainer.getJdbcUrl(),
            mysqlContainer.getUsername(),
            mysqlContainer.getPassword()
        );
        
        // Clear the user table before each test
        try (Connection con = DriverManager.getConnection(
                mysqlContainer.getJdbcUrl(),
                mysqlContainer.getUsername(),
                mysqlContainer.getPassword()
        )) {
            try (Statement stmt = con.createStatement()) {
                stmt.execute("TRUNCATE TABLE user");
            }
        }
    }

    @AfterEach
    void cleanup() throws Exception {
        // Cleanup is automatic via Testcontainers
        databaseHelper = null;
    }

    @Test
    void testInsertUser() {
        // Arrange
        User user = new User(
            "johndoe",
            "securepass123",
            "John",
            "Doe",
            "john@example.com",
            "1990-05-15",
            "+1234567890",
            "123 Main St",
            null
        );

        // Act
        boolean result = databaseHelper.insertUser(user);

        // Assert
        assertTrue(result, "User insertion should succeed");
        
        // Verify the user was actually stored
        User retrievedUser = databaseHelper.getUser("johndoe");
        assertNotNull(retrievedUser, "Retrieved user should not be null");
        assertEquals("johndoe", retrievedUser.getUsername());
        assertEquals("John", retrievedUser.getFirstName());
        assertEquals("Doe", retrievedUser.getLastName());
        assertEquals("john@example.com", retrievedUser.getEmail());
    }

    @Test
    void testGetUser() {
        // Arrange - insert a user first
        User user = new User(
            "janedoe",
            "pass456",
            "Jane",
            "Doe",
            "jane@example.com",
            "1992-08-20",
            "+0987654321",
            "456 Oak Ave",
            null
        );
        databaseHelper.insertUser(user);

        // Act
        User retrievedUser = databaseHelper.getUser("janedoe");

        // Assert
        assertNotNull(retrievedUser, "Retrieved user should not be null");
        assertEquals("janedoe", retrievedUser.getUsername());
        assertEquals("Jane", retrievedUser.getFirstName());
        assertEquals("pass456", retrievedUser.getPassword());
        assertEquals("jane@example.com", retrievedUser.getEmail());
    }

    @Test
    void testGetUserNotFound() {
        // Act
        User retrievedUser = databaseHelper.getUser("nonexistent");

        // Assert
        assertNull(retrievedUser, "Non-existent user should return null");
    }

    @Test
    void testUpdateUser() {
        // Arrange - insert a user first
        User user = new User(
            "updatetest",
            "oldpass",
            "Update",
            "Test",
            "old@example.com",
            "1995-03-10",
            "+1111111111",
            "Old Address",
            null
        );
        databaseHelper.insertUser(user);

        // Modify the user
        user.setPassword("newpass123");
        user.setFirstName("UpdatedName");
        user.setEmail("new@example.com");

        // Act
        boolean result = databaseHelper.updateUser(user);

        // Assert
        assertTrue(result, "User update should succeed");

        // Verify the update
        User updatedUser = databaseHelper.getUser("updatetest");
        assertNotNull(updatedUser);
        assertEquals("newpass123", updatedUser.getPassword());
        assertEquals("UpdatedName", updatedUser.getFirstName());
        assertEquals("new@example.com", updatedUser.getEmail());
    }

    @Test
    void testDeleteUser() {
        // Arrange - insert a user first
        User user = new User(
            "deletetest",
            "pass789",
            "Delete",
            "Test",
            "delete@example.com",
            "1994-12-01",
            "+2222222222",
            "Delete Address",
            null
        );
        databaseHelper.insertUser(user);

        // Verify user exists
        assertNotNull(databaseHelper.getUser("deletetest"));

        // Act
        boolean result = databaseHelper.deleteUser("deletetest");

        // Assert
        assertTrue(result, "User deletion should succeed");
        
        // Verify user is deleted
        User deletedUser = databaseHelper.getUser("deletetest");
        assertNull(deletedUser, "Deleted user should not be found");
    }

    @Test
    void testCheckLogin_SuccessfulLogin() {
        // Arrange
        User user = new User(
            "loginuser",
            "correctpass",
            "Login",
            "User",
            "login@example.com",
            "1996-07-22",
            "+3333333333",
            "Login Address",
            null
        );
        databaseHelper.insertUser(user);

        // Act
        boolean result = databaseHelper.checkLogin("loginuser", "correctpass");

        // Assert
        assertTrue(result, "Login with correct credentials should succeed");
    }

    @Test
    void testCheckLogin_FailedLogin() {
        // Arrange
        User user = new User(
            "loginuser2",
            "correctpass",
            "Login",
            "User",
            "login2@example.com",
            "1996-07-22",
            "+3333333333",
            "Login Address",
            null
        );
        databaseHelper.insertUser(user);

        // Act
        boolean result = databaseHelper.checkLogin("loginuser2", "wrongpass");

        // Assert
        assertFalse(result, "Login with incorrect password should fail");
    }

    @Test
    void testGetAllUsers() {
        // Arrange - insert multiple users
        User user1 = new User("user1", "pass1", "First", "User", "user1@example.com", "1990-01-01", "+1", "Addr1", null);
        User user2 = new User("user2", "pass2", "Second", "User", "user2@example.com", "1991-02-02", "+2", "Addr2", null);
        User user3 = new User("user3", "pass3", "Third", "User", "user3@example.com", "1992-03-03", "+3", "Addr3", null);

        databaseHelper.insertUser(user1);
        databaseHelper.insertUser(user2);
        databaseHelper.insertUser(user3);

        // Act
        List<User> allUsers = databaseHelper.getAllUsers();

        // Assert
        assertNotNull(allUsers, "User list should not be null");
        assertEquals(3, allUsers.size(), "Should have 3 users");
        assertTrue(allUsers.stream().anyMatch(u -> u.getUsername().equals("user1")));
        assertTrue(allUsers.stream().anyMatch(u -> u.getUsername().equals("user2")));
        assertTrue(allUsers.stream().anyMatch(u -> u.getUsername().equals("user3")));
    }

    @Test
    void testConcurrentUserOperations() throws InterruptedException {
        // Test that multiple operations can happen safely
        Thread t1 = new Thread(() -> {
            User user = new User("thread1", "pass1", "Thread", "One", "t1@example.com", "1990-01-01", "+1", "Addr1", null);
            assertTrue(databaseHelper.insertUser(user));
        });

        Thread t2 = new Thread(() -> {
            User user = new User("thread2", "pass2", "Thread", "Two", "t2@example.com", "1991-02-02", "+2", "Addr2", null);
            assertTrue(databaseHelper.insertUser(user));
        });

        // Act
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        // Assert
        assertNotNull(databaseHelper.getUser("thread1"));
        assertNotNull(databaseHelper.getUser("thread2"));
        assertEquals(2, databaseHelper.getAllUsers().size());
    }
}
