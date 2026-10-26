import java.sql.Connection;
import java.sql.SQLException;

public class TestConnection {

    public static void main(String[] args) {
        try (Connection con = DBConnection.getConnection()) {
            System.out.println("MySQL Connected Successfully!");
        } catch (SQLException e) {
            System.out.println("Connection Failed!");
            System.out.println(e.getMessage());
        }
    }
}