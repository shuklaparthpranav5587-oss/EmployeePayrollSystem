import java.io.*;
import java.util.Scanner;

// ================= EMPLOYEE CLASS =================
class Employee{
    private int employeeId;
    private String employeeName;
    private double basicSalary;

    // Default Constructor
    public Employee() {
        employeeId = 0;
        employeeName = "Not Set";
        basicSalary = 0.0;
    }

    // Parameterized Constructor
    public Employee(int id, String name, double salary) {
        this.employeeId = id;
        this.employeeName = name;
        this.basicSalary = salary;
    }

    // Method to calculate net salary (HRA + DA added)
    public double calculateNetSalary() {
        double hra = basicSalary * 0.20;
        double da = basicSalary * 0.10;
        return basicSalary + hra + da;
    }

    // Overloaded method → Net salary + Bonus
    public double calculateNetSalary(double bonus) {
        return calculateNetSalary() + bonus;
    }

    // Convert object data into file format
    public String toFileString() {
        return employeeId + "," + employeeName + "," + basicSalary + "," + calculateNetSalary();
    }
}


// ================= MAIN CLASS =================
public class Main {

    // File where employee data is stored
    private static final String FILE_NAME = "payroll_data.txt";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n===== Employee Payroll Menu =====");
            System.out.println("1. Add New Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Search Employee By ID");
            System.out.println("4. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    addEmployee(scanner);
                    break;

                case 2:
                    viewAllEmployees();
                    break;

                case 3:
                    searchEmployee(scanner);
                    break;

                case 4:
                    System.out.println("Exiting Program...");
                    System.exit(0);

                default:
                    System.out.println("Invalid choice. Try again.");
            }
        }
    }


    // ========== ADD EMPLOYEE ==========
    public static void addEmployee(Scanner scanner) {

        try (
            FileWriter fw = new FileWriter(FILE_NAME, true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter writer = new PrintWriter(bw)
        ) {

            System.out.print("Enter Employee ID: ");
            int id = scanner.nextInt();
            scanner.nextLine(); // clear buffer

            System.out.print("Enter Employee Name: ");
            String name = scanner.nextLine();

            System.out.print("Enter Basic Salary: ");
            double salary = scanner.nextDouble();

            Employee emp = new Employee(id, name, salary);

            writer.println(emp.toFileString());

            System.out.println("Employee saved successfully!");

        } catch (IOException e) {
            System.out.println("Error while saving employee data.");
        }
    }


    // ========== VIEW ALL EMPLOYEES ==========
    public static void viewAllEmployees() {

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            System.out.println("\nID\tName\tBasic Salary\tNet Salary");
            System.out.println("------------------------------------------------");

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                System.out.println(
                        data[0] + "\t" +
                        data[1] + "\t" +
                        data[2] + "\t\t" +
                        data[3]
                );
            }

        } catch (FileNotFoundException e) {
            System.out.println("No employee records found yet.");

        } catch (IOException e) {
            System.out.println("Error reading employee file.");
        }
    }


    // ========== SEARCH EMPLOYEE ==========
    public static void searchEmployee(Scanner scanner) {

        System.out.print("Enter Employee ID to search: ");
        int searchId = scanner.nextInt();

        boolean isFound = false;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {

            String line;

            while ((line = reader.readLine()) != null) {

                String[] data = line.split(",");

                int fileId = Integer.parseInt(data[0]);

                if (fileId == searchId) {
                    System.out.println("\nEmployee Found!");
                    System.out.println("Name: " + data[1]);
                    System.out.println("Net Salary: " + data[3]);
                    isFound = true;
                    break;
                }
            }

            if (!isFound) {
                System.out.println("Employee not found.");
            }

        } catch (IOException e) {
            System.out.println("Error while searching employee.");
        }
    }
}