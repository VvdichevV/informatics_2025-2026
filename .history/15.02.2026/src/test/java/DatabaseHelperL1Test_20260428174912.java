import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Layer 1 Integration Tests for User Management System
 * 
 * Tests the integration between DatabaseHelper, UserDAO, and database operations.
 * These tests use the actual database configured in the application.
 * 
 * These tests verify:
 * - User CRUD operations (Create, Read, Update, Delete)
 * - Login verification
 * - Data persistence and integrity
 * - SQL query correctness
 */
@Tag("Layer1")
class DatabaseHelperL1Test {

    private DatabaseHelper databaseHelper;

    @BeforeEach
    void setUp() {
        // Initialize the DatabaseHelper instance
        databaseHelper = DatabaseHelper.getInstance();
    }

    @AfterEach
    void cleanup() {
        // Cleanup resources if needed
        databaseHelper = null;
    }

    @Test
    void testInsertUser() {
        // Arrange
        User user = new User(
            "testuser_insert_" + System.currentTimeMillis(),
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
        User retrievedUser = databaseHelper.getUser(user.getUsername());
        assertNotNull(retrievedUser, "Retrieved user should not be null");
        assertEquals(user.getUsername(), retrievedUser.getUsername());
        assertEquals("John", retrievedUser.getFirstName());
        assertEquals("Doe", retrievedUser.getLastName());
        assertEquals("john@example.com", retrievedUser.getEmail());
    }

    @Test
    void testGetUser() {
        // Arrange - insert a user first
        String uniqueUsername = "testuser_get_" + System.currentTimeMillis();
        User user = new User(
            uniqueUsername,
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
        User retrievedUser = databaseHelper.getUser(uniqueUsername);

        // Assert
        assertNotNull(retrievedUser, "Retrieved user should not be null");
        assertEquals(uniqueUsername, retrievedUser.getUsername());
        assertEquals("Jane", retrievedUser.getFirstName());
        assertEquals("pass456", retrievedUser.getPassword());
        assertEquals("jane@example.com", retrievedUser.getEmail());
    }

    @Test
    void testGetUserNotFound() {
        // Act
        User retrievedUser = databaseHelper.getUser("nonexistent_user_" + System.nanoTime());

        // Assert
        assertNull(retrievedUser, "Non-existent user should return null");
    }

    @Test
    void testUpdateUser() {
        // Arrange - insert a user first
        String uniqueUsername = "testuser_update_" + System.currentTimeMillis();
        User user = new User(
            uniqueUsername,
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
        User updatedUser = databaseHelper.getUser(uniqueUsername);
        assertNotNull(updatedUser);
        assertEquals("newpass123", updatedUser.getPassword());
        assertEquals("UpdatedName", updatedUser.getFirstName());
        assertEquals("new@example.com", updatedUser.getEmail());
    }

    @Test
    void testDeleteUser() {
        // Arrange - insert a user first
        String uniqueUsername = "testuser_delete_" + System.currentTimeMillis();
        User user = new User(
            uniqueUsername,
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
        assertNotNull(databaseHelper.getUser(uniqueUsername));

        // Act
        boolean result = databaseHelper.deleteUser(uniqueUsername);

        // Assert
        assertTrue(result, "User deletion should succeed");
        
        // Verify user is deleted
        User deletedUser = databaseHelper.getUser(uniqueUsername);
        assertNull(deletedUser, "Deleted user should not be found");
    }

    @Test
    void testCheckLogin_SuccessfulLogin() {
        // Arrange
        String uniqueUsername = "testuser_login_success_" + System.currentTimeMillis();
        User user = new User(
            uniqueUsername,
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
        boolean result = databaseHelper.checkLogin(uniqueUsername, "correctpass");

        // Assert
        assertTrue(result, "Login with correct credentials should succeed");
    }

    @Test
    void testCheckLogin_FailedLogin() {
        // Arrange
        String uniqueUsername = "testuser_login_fail_" + System.currentTimeMillis();
        User user = new User(
            uniqueUsername,
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
        boolean result = databaseHelper.checkLogin(uniqueUsername, "wrongpass");

        // Assert
        assertFalse(result, "Login with incorrect password should fail");
    }

    @Test
    void testGetAllUsers() {
        // Arrange - insert multiple users
        String user1Name = "user_all_1_" + System.currentTimeMillis();
        String user2Name = "user_all_2_" + System.currentTimeMillis();
        String user3Name = "user_all_3_" + System.currentTimeMillis();
        
        User user1 = new User(user1Name, "pass1", "First", "User", "user1@example.com", "1990-01-01", "+1", "Addr1", null);
        User user2 = new User(user2Name, "pass2", "Second", "User", "user2@example.com", "1991-02-02", "+2", "Addr2", null);
        User user3 = new User(user3Name, "pass3", "Third", "User", "user3@example.com", "1992-03-03", "+3", "Addr3", null);

        databaseHelper.insertUser(user1);
        databaseHelper.insertUser(user2);
        databaseHelper.insertUser(user3);

        // Act
        List<User> allUsers = databaseHelper.getAllUsers();

        // Assert
        assertNotNull(allUsers, "User list should not be null");
        assertTrue(allUsers.size() >= 3, "Should have at least 3 users");
        assertTrue(allUsers.stream().anyMatch(u -> u.getUsername().equals(user1Name)));
        assertTrue(allUsers.stream().anyMatch(u -> u.getUsername().equals(user2Name)));
        assertTrue(allUsers.stream().anyMatch(u -> u.getUsername().equals(user3Name)));
    }

    @Test
    void testUserDataIntegrity() {
        // Test that all user fields are preserved through CRUD operations
        String uniqueUsername = "testuser_integrity_" + System.currentTimeMillis();
        User originalUser = new User(
            uniqueUsername,
            "integpass",
            "Integ",
            "User",
            "integ@test.com",
            "1985-06-15",
            "+5555555555",
            "Integrity Test Ave",
            null
        );

        // Insert and retrieve
        databaseHelper.insertUser(originalUser);
        User retrievedUser = databaseHelper.getUser(uniqueUsername);

        // Verify all fields
        assertNotNull(retrievedUser);
        assertEquals(originalUser.getUsername(), retrievedUser.getUsername());
        assertEquals(originalUser.getPassword(), retrievedUser.getPassword());
        assertEquals(originalUser.getFirstName(), retrievedUser.getFirstName());
        assertEquals(originalUser.getLastName(), retrievedUser.getLastName());
        assertEquals(originalUser.getEmail(), retrievedUser.getEmail());
        assertEquals(originalUser.getDateOfBirth(), retrievedUser.getDateOfBirth());
        assertEquals(originalUser.getPhoneNumber(), retrievedUser.getPhoneNumber());
        assertEquals(originalUser.getAddress(), retrievedUser.getAddress());
    }

    @Test
    void testMultipleOperationsSequence() {
        // Test a realistic sequence of operations
        String uniqueUsername = "testuser_sequence_" + System.currentTimeMillis();
        
        // 1. Create user
        User user = new User(uniqueUsername, "initialpass", "Seq", "Test", "seq@test.com", "1988-01-01", "+9999999999", "Sequence St", null);
        assertTrue(databaseHelper.insertUser(user), "Insert should succeed");
        
        // 2. Read user
        User inserted = databaseHelper.getUser(uniqueUsername);
        assertNotNull(inserted, "User should be retrievable after insert");
        
        // 3. Login with initial password
        assertTrue(databaseHelper.checkLogin(uniqueUsername, "initialpass"), "Initial login should work");
        
        // 4. Update password
        user.setPassword("updatedpass");
        assertTrue(databaseHelper.updateUser(user), "Update should succeed");
        
        // 5. Verify old password doesn't work
        assertFalse(databaseHelper.checkLogin(uniqueUsername, "initialpass"), "Old password should not work");
        
        // 6. Verify new password works
        assertTrue(databaseHelper.checkLogin(uniqueUsername, "updatedpass"), "New password should work");
        
        // 7. Delete user
        assertTrue(databaseHelper.deleteUser(uniqueUsername), "Delete should succeed");
        
        // 8. Verify deletion
        assertNull(databaseHelper.getUser(uniqueUsername), "User should not exist after deletion");
    }
}
