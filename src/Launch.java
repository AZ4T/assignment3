import java.sql.*;
import java.util.Scanner;

public class Launch {

    private static void printMenu(){
        System.out.println("1. Add new student");
        System.out.println("2. Show list of students");
        System.out.println("3. Update list");
        System.out.println("4. Delete student");
        System.out.println("5. Exit");
        System.out.print("selected action: ");
    }

    private static void showStudents(Connection conn) throws SQLException {
        String sql = "SELECT id, name, surname, age, email FROM aitu";
        try (Statement statement = conn.createStatement()) {
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                String surname = resultSet.getString("surname");
                int age = resultSet.getInt("age");
                String email = resultSet.getString("email");
                System.out.println("ID: " + id + ", name: " + name + ", surname: " + surname + ", age: " + age + ", email: " + email);
            }
        }
    }

    private static void deleteStudent(Connection conn, Scanner scanner) throws SQLException {
        String sql = "delete from aitu where id = ?";
        try (Statement statement = conn.createStatement()) {
            PreparedStatement prep = conn.prepareStatement(sql);
            int id = scanner.nextInt();
            prep.setInt(1 , id);
            prep.executeUpdate();
        }
    }

    private static void createStudent(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter surname: ");
        String surname = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        scanner.nextLine(); // Consume newline
        String sql = "INSERT INTO aitu (id, name, surname, age, email) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, surname);
            preparedStatement.setInt(4, age);
            preparedStatement.setString(5, email);
            preparedStatement.executeUpdate();
            System.out.println("New Student has been successfully added");
        }
    }

    private static void updateStudent(Connection conn, Scanner scanner) throws SQLException {
        System.out.print("Enter ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter surname: ");
        String surname = scanner.nextLine();
        System.out.print("Enter age: ");
        int age = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        scanner.nextLine(); // Consume newline
        String sql = "UPDATE aitu set name = ? , surname = ?, age = ?, email = ? where id = ?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setString(1, name);
        pst.setString(2, surname);
        pst.setInt(3, age);
        pst.setString(4, email);
        pst.setInt(5,id);
        pst.executeUpdate();
    }

    public static void main(String[] args) throws Exception{
        Scanner scanner = new Scanner(System.in);

        Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:4141/project" , "postgres" , "admin");

        PreparedStatement pst = con.prepareStatement("delete from aitu where id = ?");

        String sql = "select * from aitu";

        Statement stmt = con.createStatement();

        ResultSet rs = stmt.executeQuery(sql);

        while(true){
            printMenu();
            int id = scanner.nextInt();
            if(id == 1){
                createStudent(con, scanner);
            } else if(id == 2){
                showStudents(con);
            } else if(id == 3){
                updateStudent(con, scanner);
            } else if(id == 4){
                deleteStudent(con, scanner);
            } else if(id == 5){
                System.out.println("Goodbye!");
                System.exit(0);
            }
        }




    }

}