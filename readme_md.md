# Campus Errand System (Java)

A console-based Java application designed to connect university students who need assistance with those willing to complete small tasks for a fee. This platform acts as a mutual marketplace where students can manage profiles, post jobs, and securely connect with others to get things done.

## 🚀 Features

*   **User Authentication:** Students can register and log in using their username, password, and matriculation number.
*   **Job Marketplace:** Browse available tasks posted by other students and choose to accept them.
*   **Task Management:** Post new errands by providing a brief description and a reward fee (in RM).
*   **Contact Sharing:** Once a task is accepted, the system securely shares the assignee's contact number with the task poster to facilitate communication.
*   **Profile Management:** Update contact details or delete profiles directly from the dashboard.
*   **Data Persistence:** Automatically saves and loads all user profiles and task histories using local comma-separated text files (`users_j.txt` and `tasks_j.txt`).

## 🛠️ Technology Stack

*   **Language:** Java
*   **Concepts Used:** Object-Oriented Programming (Classes, Inheritance), File I/O, Array Data Structures
*   **Storage:** Flat-file database (`.txt` files)

## 💻 How to Run

1. Ensure you have the Java Development Kit (JDK) installed on your system.
2. Clone this repository to your local machine.
3. Open your terminal or command prompt and navigate to the project folder.
4. Compile the Java file:
   ```bash
   javac Main.java
   ```
5. Run the application:
   ```bash
   java Main
   ```

## 📁 File Structure

*   `Main.java`: Contains the complete source code, including `User`, `Student`, and `Task` classes, as well as the main system logic.
*   `users_j.txt`: Database file storing registered student profiles.
*   `tasks_j.txt`: Database file storing posted tasks and their assignment statuses.