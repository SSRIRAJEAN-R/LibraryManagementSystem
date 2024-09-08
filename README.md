# Library Database Project

This project creates a simple library database with three main tables: `Books`, `Users`, and `Transactions`. These tables are used to store information about the books, users, and transactions of borrowing and returning books.

## Prerequisites

- MySQL or any other SQL-compatible database management system
- SQL client (like MySQL Workbench or command-line client)

## Database Creation and Setup

### Step 1: Create the Database

```sql
CREATE DATABASE LibraryDB;
USE LibraryDB;
```
## Step 2: Create the Tables
### 1.Books Table

This table stores information about the books in the library, including the title, author, and the number of available copies.
```sql
CREATE TABLE Books (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    available_copies INT NOT NULL
);
```
### 2.Users Table

This table stores information about the users of the library, including their name and unique email address.
```sql
CREATE TABLE Users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE
);
```
### 3.Transactions Table

This table records transactions of book borrowing and returning, including the book ID, user ID, issue date, and return date.
```sql
CREATE TABLE Transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    book_id INT,
    user_id INT,
    issue_date DATE,
    return_date DATE,
    FOREIGN KEY (book_id) REFERENCES Books(book_id),
    FOREIGN KEY (user_id) REFERENCES Users(user_id)
);
```
## Step 3: Query the Database
### To retrieve all records from the Books table:
```sql
USE LibraryDB;
SELECT * FROM Books;
```
## License
### This project is licensed under the MIT License - see the LICENSE file for details.
```sql
This `README.md` file format is designed to guide users on how to set up and query your library database system.
```

