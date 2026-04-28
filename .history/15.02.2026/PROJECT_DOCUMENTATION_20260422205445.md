# User Management System - Project Documentation

## 1. Project Overview
This is a Java Swing-based User Management System designed for an informatics course. The application allows users to register, login, manage their profiles, and includes administrative functionalities for user management. It integrates with a MySQL database to store user information, including photos as BLOB objects.

## 2. Database Schema
### Table: user
- username VARCHAR(50) PRIMARY KEY
- password VARCHAR(100)
- FirstName VARCHAR(50)
- LastName VARCHAR(50)
- Email VARCHAR(100)
- DateOfBirth DATE
- PhoneNumber VARCHAR(20)
- Address VARCHAR(200)
- picture BLOB

## 3. ORM Classes
### User.java
Represents the user entity with fields matching the database table. Includes getters, setters, constructors, and toString() method.

## 4. Participants and Use Cases
### Participants:
- **Regular User**: Students or individuals who need to manage their personal information.
- **Administrator**: System admin who manages all users.

### Use Cases:
1. **Login**: User enters credentials to access the system.
2. **Register**: New user creates an account with personal details and photo.
3. **View Profile**: User views their complete profile information.
4. **Edit Profile**: User updates their profile details and photo.
5. **Admin Manage Users**: Admin views, edits, or deletes user accounts.

### Relationships (Business Logic):
- Login -> Main Dashboard (if successful) or error message
- Register -> Login Form
- Main Dashboard -> View Profile / Edit Profile / Admin Panel (if admin)
- Admin Panel -> Edit User / Delete User



## 5. GUI Components and Design
- **LoginForm**: JTextField (username), JPasswordField (password), JButton (OK, Cancel, Register)
- **RegistrationForm**: Multiple JTextField/JPasswordField for user data, JButton (Insert, Update, Delete, Save), JFileChooser for photo
- **MainForm**: JLabel (welcome), JPanel (info), JLabel (photo), JButton (View Profile, Edit Profile, Logout, Admin Panel)
- **ProfileForm**: Grid of labels for user data, photo display, Close button
- **EditProfileForm**: Editable fields, photo chooser, Save/Cancel buttons
- **AdminForm**: JTable with AbstractTableModel, buttons for Edit/Delete/Refresh/Close

All forms use GridLayout/BorderLayout for clean, responsive design.

## 6. CRUD Operations
All operations are handled in DatabaseHelper (implements UserDAO):
- Create: insertUser()
- Read: getUser(), getAllUsers(), checkLogin()
- Update: updateUser()
- Delete: deleteUser()

## 7. Object-Oriented Design
- **Classes**: User, DatabaseHelper, LoginForm, RegistrationForm, MainForm, ProfileForm, EditProfileForm, AdminForm, PopulateData
- **Interfaces**: UserDAO
- **Inheritance**: All forms extend JFrame
- **Data Structures**: ArrayList for user records, HashMap implicit in DB operations

## 8. Event Listeners
- ActionListener for buttons (login, register, save, delete, etc.)
- ItemListener for potential future combo boxes
- FocusListener for input validation

## 9. Input Validation
- Required fields check
- Email format validation (@ presence)
- JOptionPane for error messages

## 10. Confirmations
- JOptionPane.showConfirmDialog for delete operations
- JOptionPane.showMessageDialog for success/failure notifications

## 11. Data Types Matching
- String fields match VARCHAR
- byte[] for picture matches BLOB
- DateOfBirth as String (could be LocalDate for better type safety)