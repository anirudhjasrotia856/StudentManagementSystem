import java.util.ArrayList;
import java.sql.ResultSet;
import java.util.Scanner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        ArrayList<Student> students = new ArrayList<>();

        while (true) {
            System.out.println("\n===== STUDENT MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Exit");

            System.out.print("Enter your choice: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

             case 1:
    System.out.print("Enter Student ID: ");
    int id = sc.nextInt();
    sc.nextLine();

    System.out.print("Enter Student Name: ");
    String name = sc.nextLine();

    System.out.print("Enter Student Age: ");
    int age = sc.nextInt();

    System.out.print("Enter Student Marks: ");
    double marks = sc.nextDouble();
    sc.nextLine();

    String sql = "INSERT INTO students (id, name, age, marks) VALUES (?, ?, ?, ?)";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, id);
        ps.setString(2, name);
        ps.setInt(3, age);
        ps.setDouble(4, marks);

        ps.executeUpdate();

        System.out.println("Student added to MySQL successfully!");

    } catch (SQLException e) {
    if (e.getErrorCode() == 1062) {
        System.out.println("Student ID already exists! Use a different ID.");
    } else {
        System.out.println("Error adding student: " + e.getMessage());
    }
}

    break;
             case 2:
     sql = "SELECT * FROM students";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql);
         java.sql.ResultSet rs = ps.executeQuery()) {

        boolean found = false;
        int count = 0;

        while (rs.next()) {
            found = true;
            count++;

            System.out.println("--------------------");
            System.out.println("ID: " + rs.getInt("id"));
            System.out.println("Name: " + rs.getString("name"));
            System.out.println("Age: " + rs.getInt("age"));
            System.out.println("Marks: " + rs.getDouble("marks"));
        }

        if (!found) {
            System.out.println("No students found.");
        } else {
            System.out.println("Total Students: " + count);
        }

    } catch (SQLException e) {
        System.out.println("Error fetching students: " + e.getMessage());
    }

    break;
               case 3: {
    System.out.print("Enter Student ID to search: ");
    int searchId = sc.nextInt();

sql = "SELECT * FROM students WHERE id = ?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, searchId);

        try (java.sql.ResultSet rs = ps.executeQuery()) {

            if (rs.next()) {
                System.out.println("Student Found!");
                System.out.println("--------------------");
                System.out.println("ID: " + rs.getInt("id"));
                System.out.println("Name: " + rs.getString("name"));
                System.out.println("Age: " + rs.getInt("age"));
                System.out.println("Marks: " + rs.getDouble("marks"));
            } else {
                System.out.println("Student not found!");
            }
        }

    } catch (SQLException e) {
        System.out.println("Error searching student: " + e.getMessage());
    }

    break;
}
          case 4: {
    System.out.print("Enter Student ID to delete: ");
    int deleteId = sc.nextInt();

     sql = "DELETE FROM students WHERE id = ?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setInt(1, deleteId);

        int rowsDeleted = ps.executeUpdate();

        if (rowsDeleted > 0) {
            System.out.println("Student deleted successfully!");
        } else {
            System.out.println("Student not found!");
        }

    } catch (SQLException e) {
        System.out.println("Error deleting student: " + e.getMessage());
    }

    break;
}
                   

                case 5:
                    System.out.println("Exiting...");
                    sc.close();
                    return;

                default:
                    System.out.println("Invalid choice! Try again.");
            }
        }
    }
}