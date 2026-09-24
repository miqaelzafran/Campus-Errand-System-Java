import java.io.*;
import java.util.Scanner;

// --- OBJECT CLASS 1: User (Base Class) ---
class User {
    private int id;
    private String username;
    private String password;
    protected String name;     // Protected so inherited class can access
    protected String contact;

    public User(int id, String username, String password, String name, String contact) {
        this.id = id; this.username = username; this.password = password;
        this.name = name; this.contact = contact;
    }

    public int getId() { return id; }
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }
}

// --- INHERITANCE CLASS: Student ---
class Student extends User {
    private String matricNo;

    public Student(int id, String username, String password, String name, String contact, String matricNo) {
        super(id, username, password, name, contact);
        this.matricNo = matricNo;
    }
    
    // Convert to CSV format for saving
    public String toCSV() {
        return getId() + "," + getUsername() + "," + getPassword() + "," + name + "," + contact + "," + matricNo;
    }
}

// --- OBJECT CLASS 2: Task ---
class Task {
    private int id;
    private int posterId;
    private int assigneeId; // 0 means unassigned
    private String description;
    private double fee;

    public Task(int id, int posterId, int assigneeId, String description, double fee) {
        this.id = id; this.posterId = posterId; this.assigneeId = assigneeId;
        this.description = description; this.fee = fee;
    }

    public int getId() { return id; }
    public int getPosterId() { return posterId; }
    public int getAssigneeId() { return assigneeId; }
    public void setAssigneeId(int id) { this.assigneeId = id; }
    public String getDescription() { return description; }
    public double getFee() { return fee; }

    public String toCSV() {
        return id + "," + posterId + "," + assigneeId + "," + description + "," + fee;
    }
}

// --- MAIN SYSTEM CLASS ---
public class Main {
    private static Student[] users = new Student[100];
    private static Task[] tasks = new Task[100];
    private static int userCount = 0;
    private static int taskCount = 0;
    private static int loggedInUserId = 0;
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args) {
        loadData();
        authMenu();
    }

    // --- AUTHENTICATION MENU ---
    private static void authMenu() {
        int choice = -1;
        while (choice != 0) {
            System.out.println("\n=== CAMPUS ERRAND SYSTEM (Java) ===");
            System.out.println("1. Login");
            System.out.println("2. Register");
            System.out.println("0. Exit & Save");
            System.out.print("Choice: ");
            choice = scan.nextInt(); scan.nextLine();

            if (choice == 1) {
                loggedInUserId = login();
                if (loggedInUserId != 0) dashboard();
            } else if (choice == 2) {
                register();
            } else if (choice == 0) {
                saveData();
                System.out.println("Data saved. Goodbye!");
            }
        }
    }

    // --- USER DASHBOARD ---
    private static void dashboard() {
        int choice = -1;
        while (choice != 0 && loggedInUserId != 0) {
            System.out.println("\n--- MAIN DASHBOARD ---");
            System.out.println("1. Post a Task");
            System.out.println("2. Find Tasks (Accept a job)");
            System.out.println("3. View Assigned Tasks (My jobs to do)");
            System.out.println("4. View Posted Tasks (Jobs I requested)");
            System.out.println("5. Edit Profile");
            System.out.println("6. Delete Profile");
            System.out.println("0. Logout");
            System.out.print("Choice: ");
            choice = scan.nextInt(); scan.nextLine();

            switch (choice) {
                case 1: postTask(); break;
                case 2: findTasks(); break;
                case 3: viewAssigned(); break;
                case 4: viewPosted(); break;
                case 5: editProfile(); break;
                case 6: deleteProfile(); break;
                case 0: loggedInUserId = 0; System.out.println("Logged out."); break;
                default: System.out.println("Invalid option.");
            }
        }
    }

    // --- METHODS ---
    private static void register() {
        int id = userCount + 1;
        System.out.print("Username: "); String u = scan.nextLine();
        System.out.print("Password: "); String p = scan.nextLine();
        System.out.print("Full Name: "); String n = scan.nextLine();
        System.out.print("Contact: "); String c = scan.nextLine();
        System.out.print("Matric No: "); String m = scan.nextLine();
        
        users[userCount++] = new Student(id, u, p, n, c, m);
        System.out.println("Registration successful!");
    }

    private static int login() {
        System.out.print("Username: "); String u = scan.nextLine();
        System.out.print("Password: "); String p = scan.nextLine();
        
        for (int i = 0; i < userCount; i++) {
            if (users[i].getUsername().equals(u) && users[i].getPassword().equals(p)) {
                System.out.println("Welcome, " + users[i].getName() + "!");
                return users[i].getId();
            }
        }
        System.out.println("Invalid credentials.");
        return 0;
    }

    private static void editProfile() {
        for (int i = 0; i < userCount; i++) {
            if (users[i].getId() == loggedInUserId) {
                System.out.print("Enter New Name (Current: " + users[i].getName() + "): ");
                users[i].setName(scan.nextLine());
                System.out.print("Enter New Contact (Current: " + users[i].getContact() + "): ");
                users[i].setContact(scan.nextLine());
                System.out.println("Profile Updated!");
                return;
            }
        }
    }

    private static void deleteProfile() {
        System.out.print("Are you sure you want to delete your profile? (y/n): ");
        String confirm = scan.nextLine();
        
        if (confirm.equalsIgnoreCase("y")) {
            for (int i = 0; i < userCount; i++) {
                if (users[i].getId() == loggedInUserId) {
                    // Shift the remaining users down to fill the gap
                    for (int j = i; j < userCount - 1; j++) {
                        users[j] = users[j + 1];
                    }
                    users[userCount - 1] = null; // Clear the last element
                    userCount--; // Reduce the total user count
                    loggedInUserId = 0; // Log the user out
                    System.out.println("Profile successfully deleted.");
                    return;
                }
            }
        } else {
            System.out.println("Profile deletion cancelled.");
        }
    }

    private static void postTask() {
        int id = taskCount + 101;
        System.out.print("Task Description: "); String desc = scan.nextLine();
        System.out.print("Fee (RM): "); double fee = scan.nextDouble(); scan.nextLine();
        
        tasks[taskCount++] = new Task(id, loggedInUserId, 0, desc, fee);
        System.out.println("Task Posted!");
    }

    private static void findTasks() {
        System.out.println("\n--- Available Tasks ---");
        boolean found = false;
        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].getAssigneeId() == 0 && tasks[i].getPosterId() != loggedInUserId) {
                System.out.println("ID: " + tasks[i].getId() + " | Desc: " + tasks[i].getDescription() + " | Fee: RM" + tasks[i].getFee());
                found = true;
            }
        }
        if (!found) { System.out.println("No tasks available."); return; }

        System.out.print("\nEnter Task ID to accept (or 0 to cancel): ");
        int acceptId = scan.nextInt(); scan.nextLine();
        if (acceptId != 0) {
            for (int i = 0; i < taskCount; i++) {
                if (tasks[i].getId() == acceptId && tasks[i].getAssigneeId() == 0) {
                    tasks[i].setAssigneeId(loggedInUserId);
                    System.out.println("Task Accepted!");
                    return;
                }
            }
            System.out.println("Invalid Task ID.");
        }
    }

    private static void viewAssigned() {
        System.out.println("\n--- My Assigned Tasks ---");
        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].getAssigneeId() == loggedInUserId) {
                System.out.println("ID: " + tasks[i].getId() + " | Desc: " + tasks[i].getDescription());
            }
        }
    }

    private static void viewPosted() {
        System.out.println("\n--- My Posted Tasks ---");
        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].getPosterId() == loggedInUserId) {
                System.out.print("ID: " + tasks[i].getId() + " | Desc: " + tasks[i].getDescription());
                if (tasks[i].getAssigneeId() == 0) {
                    System.out.println(" | Status: Waiting");
                } else {
                    // Find the user who accepted it
                    for (int j = 0; j < userCount; j++) {
                        if (users[j].getId() == tasks[i].getAssigneeId()) {
                            System.out.println(" | Accepted By: " + users[j].getName() + " (" + users[j].getContact() + ")");
                            break;
                        }
                    }
                }
            }
        }
    }

    // --- FILE I/O ---
    private static void loadData() {
        try (BufferedReader br = new BufferedReader(new FileReader("users_j.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                users[userCount++] = new Student(Integer.parseInt(p[0]), p[1], p[2], p[3], p[4], p[5]);
            }
        } catch (Exception e) {}
        try (BufferedReader br = new BufferedReader(new FileReader("tasks_j.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] p = line.split(",");
                tasks[taskCount++] = new Task(Integer.parseInt(p[0]), Integer.parseInt(p[1]), Integer.parseInt(p[2]), p[3], Double.parseDouble(p[4]));
            }
        } catch (Exception e) {}
    }

    private static void saveData() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("users_j.txt"))) {
            for (int i = 0; i < userCount; i++) { bw.write(users[i].toCSV()); bw.newLine(); }
        } catch (Exception e) {}
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("tasks_j.txt"))) {
            for (int i = 0; i < taskCount; i++) { bw.write(tasks[i].toCSV()); bw.newLine(); }
        } catch (Exception e) {}
    }
}