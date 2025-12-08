public class TestISBN {
    public static void main(String[] args) {
        String[] testISBNs = {
            "1234567890",           // 10 digits
            "9780451524935",        // 13 digits
            "978-0-451-52493-5",    // 13 digits with hyphens
            "978 0 451 52493 5"     // 13 digits with spaces
        };
        
        for (String isbn : testISBNs) {
            try {
                BookValidator.validateIsbn(isbn);
                System.out.println("✓ Valid: " + isbn);
            } catch (InvalidBookException e) {
                System.out.println("✗ Invalid: " + isbn + " - " + e.getMessage());
            }
        }
    }
}
