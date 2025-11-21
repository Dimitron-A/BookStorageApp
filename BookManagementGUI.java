import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class BookManagementGUI extends JFrame {
    private BookManager manager;
    private JTable bookTable;
    private DefaultTableModel tableModel;
    private JTextField searchField;

    public BookManagementGUI() {
        manager = new BookManager();
        initializeUI();
        loadBooksToTable();
    }

    private void initializeUI() {
        setTitle("Book Management System");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        mainPanel.add(createTopPanel(), BorderLayout.NORTH);
        mainPanel.add(createTablePanel(), BorderLayout.CENTER);
        mainPanel.add(createButtonPanel(), BorderLayout.SOUTH);

        add(mainPanel);
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        
        JLabel titleLabel = new JLabel("Book Collection");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel);

        return panel;
    }

    private JScrollPane createTablePanel() {
        String[] columns = {"Title", "Author", "ISBN", "Year"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        bookTable = new JTable(tableModel);
        bookTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        bookTable.getTableHeader().setReorderingAllowed(false);
        
        return new JScrollPane(bookTable);
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        JButton addButton = new JButton("Add Book");
        JButton updateButton = new JButton("Update Book");
        JButton deleteButton = new JButton("Delete Book");
        JButton searchButton = new JButton("Search");
        JButton refreshButton = new JButton("Refresh");

        addButton.addActionListener(e -> showAddBookDialog());
        updateButton.addActionListener(e -> showUpdateBookDialog());
        deleteButton.addActionListener(e -> deleteSelectedBook());
        searchButton.addActionListener(e -> showSearchDialog());
        refreshButton.addActionListener(e -> loadBooksToTable());

        panel.add(addButton);
        panel.add(updateButton);
        panel.add(deleteButton);
        panel.add(searchButton);
        panel.add(refreshButton);

        return panel;
    }

    private void loadBooksToTable() {
        tableModel.setRowCount(0);
        List<Book> books = manager.getAllBooks();
        for (Book book : books) {
            tableModel.addRow(new Object[]{
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getYear()
            });
        }
    }

    private void showAddBookDialog() {
        JDialog dialog = new JDialog(this, "Add New Book", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField titleField = new JTextField();
        JTextField authorField = new JTextField();
        JTextField isbnField = new JTextField();
        JTextField yearField = new JTextField();

        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Author:"));
        panel.add(authorField);
        panel.add(new JLabel("ISBN:"));
        panel.add(isbnField);
        panel.add(new JLabel("Year:"));
        panel.add(yearField);

        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            try {
                String title = titleField.getText();
                String author = authorField.getText();
                String isbn = isbnField.getText();
                int year = Integer.parseInt(yearField.getText());

                manager.insertBook(title, author, isbn, year);
                manager.saveToFile();
                loadBooksToTable();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Book added successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid year format!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (InvalidBookException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        panel.add(saveButton);
        panel.add(cancelButton);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void showUpdateBookDialog() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to update!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String currentIsbn = (String) tableModel.getValueAt(selectedRow, 2);
        String currentTitle = (String) tableModel.getValueAt(selectedRow, 0);
        String currentAuthor = (String) tableModel.getValueAt(selectedRow, 1);
        int currentYear = (int) tableModel.getValueAt(selectedRow, 3);

        JDialog dialog = new JDialog(this, "Update Book", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JTextField titleField = new JTextField(currentTitle);
        JTextField authorField = new JTextField(currentAuthor);
        JTextField isbnField = new JTextField(currentIsbn);
        isbnField.setEditable(false);
        JTextField yearField = new JTextField(String.valueOf(currentYear));

        panel.add(new JLabel("Title:"));
        panel.add(titleField);
        panel.add(new JLabel("Author:"));
        panel.add(authorField);
        panel.add(new JLabel("ISBN:"));
        panel.add(isbnField);
        panel.add(new JLabel("Year:"));
        panel.add(yearField);

        JButton saveButton = new JButton("Update");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> {
            try {
                String title = titleField.getText();
                String author = authorField.getText();
                int year = Integer.parseInt(yearField.getText());

                BookValidator.validateBook(title, author, currentIsbn, year);
                manager.updateBook(currentIsbn, title, author, year);
                manager.saveToFile();
                loadBooksToTable();
                dialog.dispose();
                JOptionPane.showMessageDialog(this, "Book updated successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid year format!", "Error", JOptionPane.ERROR_MESSAGE);
            } catch (InvalidBookException ex) {
                JOptionPane.showMessageDialog(dialog, ex.getMessage(), "Validation Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        panel.add(saveButton);
        panel.add(cancelButton);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void deleteSelectedBook() {
        int selectedRow = bookTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a book to delete!", "Warning", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String isbn = (String) tableModel.getValueAt(selectedRow, 2);
        String title = (String) tableModel.getValueAt(selectedRow, 0);

        int confirm = JOptionPane.showConfirmDialog(this, 
            "Are you sure you want to delete:\n" + title + "?", 
            "Confirm Delete", 
            JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            manager.deleteBook(isbn);
            manager.saveToFile();
            loadBooksToTable();
            JOptionPane.showMessageDialog(this, "Book deleted successfully!");
        }
    }

    private void showSearchDialog() {
        JDialog dialog = new JDialog(this, "Search Books", true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        String[] searchTypes = {"Title", "Author", "ISBN", "Year"};
        JComboBox<String> searchTypeCombo = new JComboBox<>(searchTypes);
        JTextField searchField = new JTextField();

        panel.add(new JLabel("Search by:"));
        panel.add(searchTypeCombo);
        panel.add(new JLabel("Search term:"));
        panel.add(searchField);

        JButton searchButton = new JButton("Search");
        JButton cancelButton = new JButton("Cancel");

        searchButton.addActionListener(e -> {
            String searchType = (String) searchTypeCombo.getSelectedItem();
            String searchTerm = searchField.getText();

            if (searchTerm.trim().isEmpty()) {
                JOptionPane.showMessageDialog(dialog, "Please enter a search term!", "Warning", JOptionPane.WARNING_MESSAGE);
                return;
            }

            BookSearcher searcher = manager.getSearcher();
            List<Book> results = null;

            try {
                switch (searchType) {
                    case "Title":
                        results = searcher.searchByTitle(searchTerm);
                        break;
                    case "Author":
                        results = searcher.searchByAuthor(searchTerm);
                        break;
                    case "ISBN":
                        Book book = searcher.searchByIsbn(searchTerm);
                        results = book != null ? List.of(book) : List.of();
                        break;
                    case "Year":
                        int year = Integer.parseInt(searchTerm);
                        results = searcher.searchByYear(year);
                        break;
                }

                displaySearchResults(results);
                dialog.dispose();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(dialog, "Invalid year format!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelButton.addActionListener(e -> dialog.dispose());

        panel.add(searchButton);
        panel.add(cancelButton);

        dialog.add(panel);
        dialog.setVisible(true);
    }

    private void displaySearchResults(List<Book> results) {
        tableModel.setRowCount(0);
        if (results.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No books found!", "Search Results", JOptionPane.INFORMATION_MESSAGE);
            loadBooksToTable();
            return;
        }

        for (Book book : results) {
            tableModel.addRow(new Object[]{
                book.getTitle(),
                book.getAuthor(),
                book.getIsbn(),
                book.getYear()
            });
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            BookManagementGUI gui = new BookManagementGUI();
            gui.setVisible(true);
        });
    }
}
