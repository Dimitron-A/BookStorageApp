import java.util.List;
import java.util.Scanner;

public class Main {
    private static BookManager manager = new BookManager();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("=== Book Management System ===");
        
        boolean running = true;
        while (running) {
            displayMenu();
            int choice = getIntInput("Enter your choice: ");
            
            switch (choice) {
                case 1:
                    insertBook();
                    break;
                case 2:
                    searchBooks();
                    break;
                case 3:
                    updateBook();
                    break;
                case 4:
                    deleteBook();
                    break;
                case 5:
                    manager.displayAllBooks();
                    break;
                case 6:
                    manager.saveToFile();
                    running = false;
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void displayMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. Insert Book");
        System.out.println("2. Search Books");
        System.out.println("3. Update Book");
        System.out.println("4. Delete Book");
        System.out.println("5. Display All Books");
        System.out.println("6. Exit");
    }

    private static void insertBook() {
        System.out.println("\n--- Insert Book ---");
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Author: ");
        String author = scanner.nextLine();
        System.out.print("ISBN: ");
        String isbn = scanner.nextLine();
        int year = getIntInput("Year: ");
        
        try {
            manager.insertBook(title, author, isbn, year);
            System.out.println("Book added successfully!");
            manager.saveToFile();
        } catch (InvalidBookException e) {
            System.out.println("Validation Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void searchBooks() {
        System.out.println("\n--- Search Books ---");
        System.out.println("1. Search by Title");
        System.out.println("2. Search by Author");
        System.out.println("3. Search by ISBN");
        System.out.println("4. Search by Year");
        
        int choice = getIntInput("Enter search type: ");
        BookSearcher searcher = manager.getSearcher();
        List<Book> results = null;
        
        switch (choice) {
            case 1:
                System.out.print("Enter title: ");
                String title = scanner.nextLine();
                results = searcher.searchByTitle(title);
                break;
            case 2:
                System.out.print("Enter author: ");
                String author = scanner.nextLine();
                results = searcher.searchByAuthor(author);
                break;
            case 3:
                System.out.print("Enter ISBN: ");
                String isbn = scanner.nextLine();
                Book book = searcher.searchByIsbn(isbn);
                results = book != null ? List.of(book) : List.of();
                break;
            case 4:
                int year = getIntInput("Enter year: ");
                results = searcher.searchByYear(year);
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }
        
        if (results != null) {
            searcher.displaySearchResults(results);
        }
    }

    private static void updateBook() {
        System.out.println("\n--- Update Book ---");
        System.out.print("Enter ISBN of book to update: ");
        String isbn = scanner.nextLine();
        System.out.print("New Title: ");
        String title = scanner.nextLine();
        System.out.print("New Author: ");
        String author = scanner.nextLine();
        int year = getIntInput("New Year: ");
        
        try {
            BookValidator.validateBook(title, author, isbn, year);
            if (manager.updateBook(isbn, title, author, year)) {
                System.out.println("Book updated successfully!");
                manager.saveToFile();
            } else {
                System.out.println("Book not found.");
            }
        } catch (InvalidBookException e) {
            System.out.println("Validation Error: " + e.getMessage());
        }
    }

    private static void deleteBook() {
        System.out.println("\n--- Delete Book ---");
        System.out.print("Enter ISBN of book to delete: ");
        String isbn = scanner.nextLine();
        
        if (manager.deleteBook(isbn)) {
            System.out.println("Book deleted successfully!");
            manager.saveToFile();
        } else {
            System.out.println("Book not found.");
        }
    }

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.print(prompt);
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid number. Please try again.");
            }
        }
    }
}
