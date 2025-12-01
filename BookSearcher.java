import java.util.List;
import java.util.stream.Collectors;

public class BookSearcher {
    private final List<Book> books;

    public BookSearcher(List<Book> books) {
        this.books = books;
    }

    public List<Book> searchByTitle(String title) {
        return books.stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> searchByAuthor(String author) {
        return books.stream()
                .filter(book -> book.getAuthor().toLowerCase().contains(author.toLowerCase()))
                .collect(Collectors.toList());
    }

    public Book searchByIsbn(String isbn) {
        return books.stream()
                .filter(book -> book.getIsbn().equals(isbn))
                .findFirst()
                .orElse(null);
    }

    public List<Book> searchByYear(int year) {
        return books.stream()
                .filter(book -> book.getYear() == year)
                .collect(Collectors.toList());
    }

    public List<Book> searchByYearRange(int startYear, int endYear) {
        return books.stream()
                .filter(book -> book.getYear() >= startYear && book.getYear() <= endYear)
                .collect(Collectors.toList());
    }

    public void displaySearchResults(List<Book> results) {
        if (results.isEmpty()) {
            System.out.println("No books found.");
            return;
        }
        System.out.println("Found " + results.size() + " book(s):");
        for (Book book : results) {
            System.out.println(book);
        }
    }
}
