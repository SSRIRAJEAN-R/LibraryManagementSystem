package com.SSRIRAJEAN;


import java.sql.*;
import java.util.Scanner;

public class LibraryManagementSystem {

    private static final String DB_URL = "jdbc:mysql://localhost:3306/LibraryDB";
    private static final String USER = "root";
    private static final String PASSWORD = "password"; // Update with your MySQL password

    public static void main(String[] args) {
        try (Connection conn = DriverManager.getConnection(DB_URL, USER, PASSWORD)) {
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("Library Management System");
                System.out.println("1. Add Book");
                System.out.println("2. View Books");
                System.out.println("3. Register User");
                System.out.println("4. Issue Book");
                System.out.println("5. Return Book");
                System.out.println("6. Exit");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        addBook(conn, scanner);
                        break;
                    case 2:
                        viewBooks(conn);
                        break;
                    case 3:
                        registerUser(conn, scanner);
                        break;
                    case 4:
                        issueBook(conn, scanner);
                        break;
                    case 5:
                        returnBook(conn, scanner);
                        break;
                    case 6:
                        System.out.println("Exiting...");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void addBook(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter available copies: ");
        int copies = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String sql = "INSERT INTO Books (title, author, available_copies) VALUES (?, ?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, author);
            stmt.setInt(3, copies);
            stmt.executeUpdate();
            System.out.println("Book added successfully.");
        }
    }

    private static void viewBooks(Connection conn) throws SQLException {
        String sql = "SELECT * FROM Books";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("book_id"));
                System.out.println("Title: " + rs.getString("title"));
                System.out.println("Author: " + rs.getString("author"));
                System.out.println("Available Copies: " + rs.getInt("available_copies"));
                System.out.println();
            }
        }
    }

    private static void registerUser(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter user name: ");
        String name = scanner.nextLine();
        System.out.print("Enter user email: ");
        String email = scanner.nextLine();

        String sql = "INSERT INTO Users (name, email) VALUES (?, ?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, name);
            stmt.setString(2, email);
            stmt.executeUpdate();
            System.out.println("User registered successfully.");
        }
    }

    private static void issueBook(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter book ID: ");
        int bookId = scanner.nextInt();
        System.out.print("Enter user ID: ");
        int userId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String sql = "INSERT INTO Transactions (book_id, user_id, issue_date) VALUES (?, ?, NOW())";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, bookId);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
            updateBookCopies(conn, bookId, -1);
            System.out.println("Book issued successfully.");
        }
    }

    private static void returnBook(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter transaction ID: ");
        int transactionId = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        String sql = "UPDATE Transactions SET return_date = NOW() WHERE transaction_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transactionId);
            stmt.executeUpdate();
            // Get book ID from transaction to update available copies
            int bookId = getBookIdFromTransaction(conn, transactionId);
            updateBookCopies(conn, bookId, 1);
            System.out.println("Book returned successfully.");
        }
    }

    private static int getBookIdFromTransaction(Connection conn, int transactionId) throws SQLException {
        String sql = "SELECT book_id FROM Transactions WHERE transaction_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, transactionId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt("book_id");
                }
            }
        }
        return -1; // If not found
    }

    private static void updateBookCopies(Connection conn, int bookId, int change) throws SQLException {
        String sql = "UPDATE Books SET available_copies = available_copies + ? WHERE book_id = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, change);
            stmt.setInt(2, bookId);
            stmt.executeUpdate();
        }
    }
}

