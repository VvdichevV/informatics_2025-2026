# User Management System Project

## Overview
This is a Java Swing application for user management, including login, registration, profile management, and admin functionalities. It uses MySQL database to store user information, including photos as BLOB objects.

## Requirements
- Java 8 or higher
- MySQL Server
- MySQL Connector/J (included in the project)

## Database Setup
1. Create a MySQL database named `users`.
2. Create a table `user` with the following structure:
   ```sql
   CREATE TABLE user (
       username VARCHAR(50) PRIMARY KEY,
       password VARCHAR(100),
       FirstName VARCHAR(50),
       LastName VARCHAR(50),
       Email VARCHAR(100),
       DateOfBirth DATE,
       PhoneNumber VARCHAR(20),
       Address VARCHAR(200),
       picture BLOB
   );
   ```
3. Ensure MySQL is running and accessible with user `root` and password `Password1~` (or update the constants in the code).

## Compilation
Run the following command in the project directory:
```
javac -cp .:/path/to/mysql-connector-j-9.5.0.jar *.java
```
Replace `/path/to/mysql-connector-j-9.5.0.jar` with the actual path to the JAR file.

## Running the Application
1. Populate the database with sample data:
   ```
   java -cp .:/path/to/mysql-connector-j-9.5.0.jar PopulateData
   ```
2. Start the application:
   ```
   java -cp .:/path/to/mysql-connector-j-9.5.0.jar LoginForm
   ```

## Usage
- **Login**: Enter username and password. Use sample data: john/pass123, jane/pass456, admin/admin.
- **Register**: Click "Register" to open the registration form.
- **Main Form**: After login, view profile, edit profile, or access admin panel (for admin user).
- **Admin Panel**: Manage all users (edit/delete).

## Files to Submit
- All Java source files (.java)
- This README.md
- MySQL Connector JAR (if required)
- Database schema (SQL script)

## Features Implemented
- Login with database validation
- User registration with photo upload
- Profile viewing and editing
- Admin panel for user management
- Photo storage as BLOB
- Sample data population