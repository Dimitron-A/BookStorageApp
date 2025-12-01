# Book Management System - Distribution Guide

## For Developers

### Building the Application

**Windows:**
```
build-jar.bat
```

**macOS/Linux:**
```
./build-jar.sh
```

This creates `BookManagementSystem.jar` that can be distributed to users.

## For End Users

### Requirements
- Java Runtime Environment (JRE) 17 or higher installed
- Download Java from: https://www.oracle.com/java/technologies/downloads/

### Running the Application

#### Windows:
1. Double-click `BookManagementSystem.jar`
2. If double-click doesn't work, right-click → Open with → Java(TM) Platform SE binary

Or use Command Prompt:
```
java -jar BookManagementSystem.jar
```

#### macOS:
1. Double-click `BookManagementSystem.jar`
2. If prompted, allow the application to run

Or use Terminal:
```
java -jar BookManagementSystem.jar
```

#### Linux:
```
java -jar BookManagementSystem.jar
```

### Features
- Add, update, and delete books
- Search by title, author, ISBN, or year
- Data automatically saved to `books.dat`
- User-friendly graphical interface

### Troubleshooting

**"Java not found" error:**
- Install Java JRE 17+ from the link above
- Restart your computer after installation

**Application won't start:**
- Make sure you have Java installed: `java -version`
- Try running from command line/terminal to see error messages

**Data not saving:**
- Make sure the application has write permissions in its directory
- Check that `books.dat` is not read-only
