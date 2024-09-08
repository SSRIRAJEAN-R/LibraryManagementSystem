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
### Books Table

This table stores information about the books in the library, including the title, author, and the number of available copies.
```sql
CREATE TABLE Books (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255) NOT NULL,
    available_copies INT NOT NULL
);
```
