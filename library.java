import java.util.*;
import java.util.*;
class User {
    String username;
    String password;
    List<Book> borrowedBooks = new ArrayList<>();

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }
}

public class library {
    static Scanner sc = new Scanner(System.in);
    static List<User> users = new ArrayList<>();
    static List<Book> books = new ArrayList<>();

    public static void main(String[] args) {
        // Sample books
        books.add(new Book("Java Basics", "James Gosling"));
        books.add(new Book("OOP Concepts", "Bjarne Stroustrup"));

        while (true) {
            System.out.println("\n===== Library System =====");
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Exit");
            System.out.print("Choose: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    System.exit(0);
            }
        }
    }

    static void register() {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        users.add(new User(username, password));
        System.out.println("Registration successful!");
    }

    static void login() {
        System.out.print("Enter username: ");
        String username = sc.nextLine();
        System.out.print("Enter password: ");
        String password = sc.nextLine();

        for (User u : users) {
            if (u.username.equals(username) && u.password.equals(password)) {
                System.out.println("Login successful!");
                userMenu(u);
                return;
            }
        }
        System.out.println("Invalid login!");
    }

    static void userMenu(User user) {
        while (true) {
            System.out.println("\n--- User Dashboard ---");
            System.out.println("1. View Books");
            System.out.println("2. Borrow Book");
            System.out.println("3. Return Book");
            System.out.println("4. Logout");
            System.out.print("Choose: ");
            int ch = sc.nextInt();

            switch (ch) {
                case 1:
                    showBooks();
                    break;
                case 2:
                    borrowBook(user);
                    break;
                case 3:
                    returnBook(user);
                    break;
                case 4:
                    return;
            }
        }
    }

    static void showBooks() {
        System.out.println("\nAvailable Books:");
        for (int i = 0; i < books.size(); i++) {
            System.out.println(i + ". " + books.get(i));
        }
    }

    static void borrowBook(User user) {
        showBooks();
        System.out.print("Enter book number: ");
        int index = sc.nextInt();

        if (index >= 0 && index < books.size()) {
            Book b = books.get(index);
            if (!b.isBorrowed) {
                b.isBorrowed = true;
                user.borrowedBooks.add(b);
                System.out.println("Book borrowed!");
            } else {
                System.out.println("Already borrowed!");
            }
        }
    }

    static void returnBook(User user) {
        if (user.borrowedBooks.isEmpty()) {
            System.out.println("No borrowed books.");
            return;
        }

        for (int i = 0; i < user.borrowedBooks.size(); i++) {
            System.out.println(i + ". " + user.borrowedBooks.get(i));
        }

        System.out.print("Enter book number to return: ");
        int index = sc.nextInt();

        Book b = user.borrowedBooks.remove(index);
        b.isBorrowed = false;

        System.out.println("Book returned!");
    }
}
class Book {
    String title;
    String author;
    boolean isBorrowed = false;

    public Book(String title, String author) {
        this.title = title;
        this.author = author;
    }

    public String toString() {
        return title + " by " + author + 
               (isBorrowed ? " (Not Available)" : " (Available)");
    }
}