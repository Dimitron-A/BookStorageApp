import java.util.ArrayList;
import java.util.List;

public class BookManager {
    private final List<Book> books;

    public BookManager() {
        this.books = BookFileManager.loadBooks();
        System.out.println("Loaded " + books.size() + " book(s) from file.");
    }

    public void insertBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        books.add(book);
        System.out.println("Book inserted: " + book.getTitle());
    }

    public void insertBook(String title, String author, String isbn, int year) throws InvalidBookException {
        BookValidator.validateBook(title, author, isbn, year);
        Book book = new Book(title, author, isbn, year);
        insertBook(book);
    }

    public void saveToFile() {
        BookFileManager.saveBooks(books);
    }

    public boolean deleteBook(String isbn) {
        return books.removeIf(book -> book.getIsbn().equals(isbn));
    }

    public boolean updateBook(String isbn, String newTitle, String newAuthor, int newYear) {
        for (Book book : books) {
            if (book.getIsbn().equals(isbn)) {
                book.setTitle(newTitle);
                book.setAuthor(newAuthor);
                book.setYear(newYear);
                return true;
            }
        }
        return false;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public BookSearcher getSearcher() {
        return new BookSearcher(books);
    }

    public int getBookCount() {
        return books.size();
    }

    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the collection.");
            return;
        }
        System.out.println("\n=== All Books ===");
        for (Book book : books) {
            System.out.println(book);
        }
    }
}
