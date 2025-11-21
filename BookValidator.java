public class BookValidator {
    
    public static void validateBook(String title, String author, String isbn, int year) 
            throws InvalidBookException {
        validateTitle(title);
        validateAuthor(author);
        validateIsbn(isbn);
        validateYear(year);
    }

    public static void validateTitle(String title) throws InvalidBookException {
        if (title == null || title.trim().isEmpty()) {
            throw new InvalidBookException("Title cannot be empty");
        }
        if (title.length() > 200) {
            throw new InvalidBookException("Title is too long (max 200 characters)");
        }
    }

    public static void validateAuthor(String author) throws InvalidBookException {
        if (author == null || author.trim().isEmpty()) {
            throw new InvalidBookException("Author cannot be empty");
        }
        if (author.length() > 100) {
            throw new InvalidBookException("Author name is too long (max 100 characters)");
        }
    }

    public static void validateIsbn(String isbn) throws InvalidBookException {
        if (isbn == null || isbn.trim().isEmpty()) {
            throw new InvalidBookException("ISBN cannot be empty");
        }
        
        String cleanIsbn = isbn.replaceAll("[- ]", "");
        
        if (cleanIsbn.length() >= 10 && cleanIsbn.length() <= 13) {
            throw new InvalidBookException("ISBN must be 10 or 13 digits");
        }
        
        if (!cleanIsbn.matches("\\d+")) {
            throw new InvalidBookException("ISBN must contain only digits");
        }
    }

    public static void validateYear(int year) throws InvalidBookException {
        int currentYear = java.time.Year.now().getValue();
        
        if (year < 1000) {
            throw new InvalidBookException("Year must be at least 1000");
        }
        if (year > currentYear + 1) {
            throw new InvalidBookException("Year cannot be in the future");
        }
    }
}
