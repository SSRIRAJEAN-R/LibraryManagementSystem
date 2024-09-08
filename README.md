Library Management System
This project is a basic Library Management System using MySQL. It includes the following tables:

Books: Stores information about available books in the library.
Users: Stores user details.
Transactions: Tracks the issue and return of books.
Database Setup
Create the database:

sql
Copy code
CREATE DATABASE LibraryDB;
Switch to the created database:

sql
Copy code
USE LibraryDB;
Create the Books table:

sql
Copy code
CREATE TABLE Books (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    available_copies INT NOT NULL
);
Create the Users table:

sql
Copy code
CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);
Create the Transactions table:

sql
Copy code
CREATE TABLE Transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT,
    user_id INT,
    issue_date DATE,
    return_date DATE,
    FOREIGN KEY (book_id) REFERENCES Books(book_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);
Verify the setup:

sql
Copy code
USE LibraryDB;
SELECT * FROM Books;
