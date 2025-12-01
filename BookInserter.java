import java.util.ArrayList;
import java.util.List;

public class BookInserter {
    private final List<Book> books;

    public BookInserter() {
        this.books = new ArrayList<>();
    }

    public void insertBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        books.add(book);
        System.out.println("Book inserted: " + book.getTitle());
    }

    public void insertBook(String title, String author, String isbn, int year) {
        Book book = new Book(title, author, isbn, year);
        insertBook(book);
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    public int getBookCount() {
        return books.size();
    }

    public void displayAllBooks() {
        if (books.isEmpty()) {
            System.out.println("No books in the collection.");
            return;
        }
        System.out.println("Books in collection:");
        for (Book book : books) {
            System.out.println(book);
        }
    }
}
