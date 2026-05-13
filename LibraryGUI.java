import javax.swing.*;
import java.awt.*;
import java.util.*;

public class LibraryGUI {

    static ArrayList<User> users = new ArrayList<>();
    static ArrayList<Book> books = new ArrayList<>();

    static String adminUser = "admin";
    static String adminPass = "1234";

    public static void main(String[] args) {

        books.add(new Book("Java Basics", "James Gosling"));
        books.add(new Book("OOP Concepts", "Bjarne Stroustrup"));
        books.add(new Book("Data Structures", "Mark Allen Weiss"));
        books.add(new Book("Algorithms", "Thomas H. Cormen"));
        books.add(new Book("Clean Code", "Robert C. Martin"));

        showLoginScreen();
    }

    static void showLoginScreen() {

        JFrame frame = new JFrame("DSU Library Login");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ImageIcon bgImage = new ImageIcon("ewu.png");
        Image img = bgImage.getImage();

        JPanel background = new JPanel(new GridBagLayout()) {
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };

        frame.setContentPane(background);

        JPanel mainPanel = new JPanel(null);
        mainPanel.setPreferredSize(new Dimension(500, 320));
        mainPanel.setBackground(new Color(10, 25, 50, 200));
        background.add(mainPanel);

        JLabel title = new JLabel("DSU LIBRARY", JLabel.CENTER);
        title.setBounds(150, 10, 200, 30);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 20));
        mainPanel.add(title);

        JLabel subtitle = new JLabel("Member & Admin Portal", JLabel.CENTER);
        subtitle.setBounds(130, 40, 240, 20);
        subtitle.setForeground(Color.YELLOW);
        mainPanel.add(subtitle);

        JPanel memberPanel = new JPanel(null);
        memberPanel.setBounds(30, 80, 200, 200);
        memberPanel.setBackground(new Color(0, 0, 0, 150));
        mainPanel.add(memberPanel);

        JLabel mUserLabel = new JLabel("Username:");
        mUserLabel.setBounds(20, 30, 80, 20);
        mUserLabel.setForeground(Color.WHITE);
        memberPanel.add(mUserLabel);

        JTextField userField = new JTextField();
        userField.setBounds(20, 50, 150, 25);
        memberPanel.add(userField);

        JLabel mPassLabel = new JLabel("Password:");
        mPassLabel.setBounds(20, 80, 80, 20);
        mPassLabel.setForeground(Color.WHITE);
        memberPanel.add(mPassLabel);

        JPasswordField passField = new JPasswordField();
        passField.setBounds(20, 100, 150, 25);
        memberPanel.add(passField);

        JButton loginBtn = new JButton("LOG IN");
        loginBtn.setBounds(20, 140, 70, 30);
        memberPanel.add(loginBtn);

        JButton registerBtn = new JButton("REGISTER");
        registerBtn.setBounds(100, 140, 90, 30);
        memberPanel.add(registerBtn);

        JPanel adminPanel = new JPanel(null);
        adminPanel.setBounds(260, 80, 200, 200);
        adminPanel.setBackground(new Color(0, 0, 0, 150));
        mainPanel.add(adminPanel);

        JLabel aUserLabel = new JLabel("Username:");
        aUserLabel.setBounds(20, 30, 80, 20);
        aUserLabel.setForeground(Color.WHITE);
        adminPanel.add(aUserLabel);

        JTextField adminUserField = new JTextField();
        adminUserField.setBounds(20, 50, 150, 25);
        adminPanel.add(adminUserField);

        JLabel aPassLabel = new JLabel("Password:");
        aPassLabel.setBounds(20, 80, 80, 20);
        aPassLabel.setForeground(Color.WHITE);
        adminPanel.add(aPassLabel);

        JPasswordField adminPassField = new JPasswordField();
        adminPassField.setBounds(20, 100, 150, 25);
        adminPanel.add(adminPassField);

        JButton adminLoginBtn = new JButton("ADMIN LOGIN");
        adminLoginBtn.setBounds(30, 140, 140, 30);
        adminPanel.add(adminLoginBtn);

        loginBtn.addActionListener(e -> {
            String u = userField.getText();
            String p = new String(passField.getPassword());

            for (User user : users) {
                if (user.username.equals(u) && user.password.equals(p)) {
                    frame.dispose();
                    showDashboard(user);
                    return;
                }
            }
            JOptionPane.showMessageDialog(frame, "Invalid Member Login!");
        });

        registerBtn.addActionListener(e -> {
            users.add(new User(userField.getText(), new String(passField.getPassword())));
            JOptionPane.showMessageDialog(frame, "Registered!");
        });

        adminLoginBtn.addActionListener(e -> {
            String u = adminUserField.getText();
            String p = new String(adminPassField.getPassword());

            if (u.equals(adminUser) && p.equals(adminPass)) {
                frame.dispose();
                showAdminPanel();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid Admin Login!");
            }
        });

        frame.setVisible(true);
    }

    static void showDashboard(User user) {
        JFrame frame = new JFrame("User Dashboard");
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());
        frame.setLocationRelativeTo(null);

        JButton viewBtn = new JButton("View Books");
        JButton borrowBtn = new JButton("Borrow Book");
        JButton returnBtn = new JButton("Return Book");
        JButton logoutBtn = new JButton("Logout");

        frame.add(viewBtn);
        frame.add(borrowBtn);
        frame.add(returnBtn);
        frame.add(logoutBtn);

        viewBtn.addActionListener(e -> showBooks());

        borrowBtn.addActionListener(e -> {
            try {
                int i = Integer.parseInt(JOptionPane.showInputDialog("Enter book index:"));
                if (!books.get(i).isBorrowed) {
                    books.get(i).isBorrowed = true;
                    user.borrowed.add(books.get(i));
                    JOptionPane.showMessageDialog(frame, "Borrowed!");
                } else {
                    JOptionPane.showMessageDialog(frame, "Not Available!");
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input!");
            }
        });

        returnBtn.addActionListener(e -> {
            try {
                int i = Integer.parseInt(JOptionPane.showInputDialog("Enter borrowed index:"));
                Book b = user.borrowed.remove(i);
                b.isBorrowed = false;
                JOptionPane.showMessageDialog(frame, "Returned!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input!");
            }
        });

        logoutBtn.addActionListener(e -> {
            frame.dispose();
            showLoginScreen();
        });

        frame.setVisible(true);
    }

    static void showAdminPanel() {
        JFrame frame = new JFrame("Admin Panel");
        frame.setSize(400, 300);
        frame.setLayout(new FlowLayout());
        frame.setLocationRelativeTo(null);

        JButton viewUsersBtn = new JButton("View Users");
        JButton addBookBtn = new JButton("Add Book");
        JButton removeBookBtn = new JButton("Remove Book");
        JButton logoutBtn = new JButton("Logout");

        frame.add(viewUsersBtn);
        frame.add(addBookBtn);
        frame.add(removeBookBtn);
        frame.add(logoutBtn);

        viewUsersBtn.addActionListener(e -> {
            String list = "";
            for (int i = 0; i < users.size(); i++) {
                list += i + ": " + users.get(i).username + "\n";
            }
            JOptionPane.showMessageDialog(frame, list.isEmpty() ? "No users" : list);
        });

        addBookBtn.addActionListener(e -> {
            String title = JOptionPane.showInputDialog("Enter Title:");
            String author = JOptionPane.showInputDialog("Enter Author:");
            if (title != null && author != null) {
                books.add(new Book(title, author));
                JOptionPane.showMessageDialog(frame, "Book Added!");
            }
        });

        removeBookBtn.addActionListener(e -> {
            try {
                int i = Integer.parseInt(JOptionPane.showInputDialog("Enter book index:"));
                books.remove(i);
                JOptionPane.showMessageDialog(frame, "Book Removed!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input!");
            }
        });

        logoutBtn.addActionListener(e -> {
            frame.dispose();
            showLoginScreen();
        });

        frame.setVisible(true);
    }

    static void showBooks() {
        String list = "";
        for (int i = 0; i < books.size(); i++) {
            list += i + ": " + books.get(i) + "\n";
        }
        JOptionPane.showMessageDialog(null, list);
    }
}

class User {
    String username, password;
    ArrayList<Book> borrowed = new ArrayList<>();

    User(String u, String p) {
        username = u;
        password = p;
    }
}

class Book {
    String title, author;
    boolean isBorrowed = false;

    Book(String t, String a) {
        title = t;
        author = a;
    }

    public String toString() {
        return title + " by " + author +
                (isBorrowed ? " (Not Available)" : " (Available)");
    }
}