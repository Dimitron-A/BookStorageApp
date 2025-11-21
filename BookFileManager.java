import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class BookFileManager {
    private static final String FILE_NAME = "books.dat";

    public static void saveBooks(List<Book> books) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            oos.writeObject(books);
            System.out.println("Books saved successfully.");
        } catch (IOException e) {
            System.err.println("Error saving books: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public static List<Book> loadBooks() {
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            return new ArrayList<>();
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            return (List<Book>) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading books: " + e.getMessage());
            return new ArrayList<>();
        }
    }
}
