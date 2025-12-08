public class DebugISBN {
    public static void main(String[] args) {
        String testIsbn = "9780451524935";
        
        System.out.println("Original ISBN: '" + testIsbn + "'");
        System.out.println("Length: " + testIsbn.length());
        
        String cleanIsbn = testIsbn.replaceAll("[- ]", "");
        System.out.println("Clean ISBN: '" + cleanIsbn + "'");
        System.out.println("Clean Length: " + cleanIsbn.length());
        System.out.println("Is digits only? " + cleanIsbn.matches("\\d+"));
        
        // Test with spaces
        testIsbn = "978 0 451 52493 5";
        System.out.println("\nOriginal ISBN: '" + testIsbn + "'");
        System.out.println("Length: " + testIsbn.length());
        
        cleanIsbn = testIsbn.replaceAll("[- ]", "");
        System.out.println("Clean ISBN: '" + cleanIsbn + "'");
        System.out.println("Clean Length: " + cleanIsbn.length());
        System.out.println("Is digits only? " + cleanIsbn.matches("\\d+"));
    }
}
