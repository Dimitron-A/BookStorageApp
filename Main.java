import java.util.List;
import java.util.Scanner;

public class Main {
    private static final BookManager manager = new BookManager();
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try (scanner) {
            IO.println("=== Book Management System ===");
            
            boolean running = true;
            while (running) {
                displayMenu();
                int choice = getIntInput("Enter your choice: ");
                
                switch (choice) {
                    case 1 -> insertBook();
                    case 2 -> searchBooks();
                    case 3 -> updateBook();
                    case 4 -> deleteBook();
                    case 5 -> manager.displayAllBooks();
                    case 6 -> {
                        manager.saveToFile();
                        running = false;
                        IO.println("Goodbye!");
                    }
                    default -> IO.println("Invalid choice. Please try again.");
                }
            }   }
    }

    private static void displayMenu() {
    IO.println("\n--- Menu ---");
    IO.println("1. Insert Book");
    IO.println("2. Search Books");
    IO.println("3. Update Book");
    IO.println("4. Delete Book");
    IO.println("5. Display All Books");
    IO.println("6. Exit");
}

    private static void insertBook() {
    IO.println("\n--- Insert Book ---");
    IO.print("Title: ");
    String title = scanner.nextLine();
    IO.print("Author: ");
    String author = scanner.nextLine();
    IO.print("ISBN: ");
    String isbn = scanner.nextLine();
    int year = getIntInput("Year: ");

    try {
        manager.insertBook(title, author, isbn, year);
        IO.println("Book added successfully!");
        manager.saveToFile();
    } catch (InvalidBookException e) {
        IO.println("Validation Error: " + e.getMessage());
    } catch (Exception e) {
        IO.println("Error: " + e.getMessage());
    }
}

    private static void searchBooks() {
    IO.println("\n--- Search Books ---");
    IO.println("1. Search by Title");
    IO.println("2. Search by Author");
    IO.println("3. Search by ISBN");
    IO.println("4. Search by Year");

    int choice = getIntInput("Enter search type: ");
    BookSearcher searcher = manager.getSearcher();
    List<Book> results;

    switch (choice) {
        case 1 -> {
            IO.print("Enter title: ");
            String title = scanner.nextLine();
            results = searcher.searchByTitle(title);
        }
        case 2 -> {
            IO.print("Enter author: ");
            String author = scanner.nextLine();
            results = searcher.searchByAuthor(author);
        }
        case 3 -> {
            IO.print("Enter ISBN: ");
            String isbn = scanner.nextLine();
            Book book = searcher.searchByIsbn(isbn);
            results = book != null ? List.of(book) : List.of();
        }
        case 4 -> {
            int year = getIntInput("Enter year: ");
            results = searcher.searchByYear(year);
        }
        default -> {
            IO.println("Invalid choice.");
            return;
        }
    }

    searcher.displaySearchResults(results);
}

    private static void updateBook() {
    IO.println("\n--- Update Book ---");
    IO.print("Enter ISBN of book to update: ");
    String isbn = scanner.nextLine();
    IO.print("New Title: ");
    String title = scanner.nextLine();
    IO.print("New Author: ");
    String author = scanner.nextLine();
    int year = getIntInput("New Year: ");

    try {
        BookValidator.validateBook(title, author, isbn, year);
        if (manager.updateBook(isbn, title, author, year)) {
            IO.println("Book updated successfully!");
            manager.saveToFile();
        } else {
            IO.println("Book not found.");
        }
    } catch (InvalidBookException e) {
        IO.println("Validation Error: " + e.getMessage());
    }
}

    private static void deleteBook() {
    IO.println("\n--- Delete Book ---");
    IO.print("Enter ISBN of book to delete: ");
    String isbn = scanner.nextLine();

    if (manager.deleteBook(isbn)) {
        IO.println("Book deleted successfully!");
        manager.saveToFile();
    } else {
        IO.println("Book not found.");
    }
}

    private static int getIntInput(String prompt) {
        while (true) {
            try {
                IO.print(prompt);
                String input = scanner.nextLine();
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                IO.println("Invalid number. Please try again.");
            }
        }
    }
}
