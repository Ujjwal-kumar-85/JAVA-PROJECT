/******************** Importing Essential Libraries ************************/

import java.io.*;
import java.nio.file.Files;
import java.util.*;


/*************************** MENU OF EMS ****************************/

class MainMenu {
    public void menu() {
        System.out.println("\t\t*******************************************");
        System.out.println("\t\t\t  EMPLOYEE MANAGEMENT SYSTEM");
        System.out.println("\t\t*******************************************");
        System.out.println("\t\t\t    --------------------");
        System.out.println("\t\t\t     ~$ V18");
        System.out.println("\t\t\t    --------------------");
        System.out.println("\n\nPress 1 : To Add an Employee Details");
        System.out.println("Press 2 : To See an Employee Details");
        System.out.println("Press 3 : To Remove an Employee");
        System.out.println("Press 4 : To Update Employee Details");
        System.out.println("Press 5 : To Exit the EMS Portal");
    }
}


/************************ ValidationUtils *********************/

class ValidationUtils {
    public static boolean isNonEmpty(String input) {
        return input != null && !input.trim().isEmpty();
    }

    public static boolean isValidEmail(String email) {
        return email.matches("^[\\w.-]+@[\\w.-]+\\.\\w{2,}$");
    }

    public static boolean isValidPhone(String phone) {
        return phone.matches("\\d{10}");
    }

    public static boolean isValidSalary(String salary) {
        return salary.matches("\\d+(\\.\\d{1,2})?");
    }
}


/************************* Taking Employee Details ************************/

class Employee {
    String id, name, fatherName, email, position, salary, contact;

    public boolean inputDetails(Scanner sc) {
        System.out.print("Enter Employee's ID ----------: ");
        id = sc.nextLine().trim();
        if (!ValidationUtils.isNonEmpty(id)) return error("ID");

        System.out.print("Enter Employee's Name --------: ");
        name = sc.nextLine().trim();
        if (!ValidationUtils.isNonEmpty(name)) return error("Name");

        System.out.print("Enter Father's Name ----------: ");
        fatherName = sc.nextLine().trim();
        if (!ValidationUtils.isNonEmpty(fatherName)) return error("Father's Name");

        System.out.print("Enter Email ------------------: ");
        email = sc.nextLine().trim();
        if (!ValidationUtils.isValidEmail(email)) return error("Valid Email");

        System.out.print("Enter Position ---------------: ");
        position = sc.nextLine().trim();
        if (!ValidationUtils.isNonEmpty(position)) return error("Position");

        System.out.print("Enter Contact ----------------: ");
        contact = sc.nextLine().trim();
        if (!ValidationUtils.isValidPhone(contact)) return error("10-digit Phone");

        System.out.print("Enter Salary -----------------: ");
        salary = sc.nextLine().trim();
        if (!ValidationUtils.isValidSalary(salary)) return error("Numeric Salary");

        return true;
    }

    private boolean error(String field) {
        System.out.println("Invalid input. Please enter a valid " + field + ".");
        return false;
    }

    public String toFileString() {
        return "Employee ID       : " + id + "\n" +
               "Employee Name     : " + name + "\n" +
               "Father's Name     : " + fatherName + "\n" +
               "Employee Contact  : " + contact + "\n" +
               "Email Information : " + email + "\n" +
               "Employee Position : " + position + "\n" +
               "Employee Salary   : " + salary;
    }
}


/************************ To add details of Employee *********************/

class EmployeeAdd {
    public void addEmployee(Scanner sc) {
        Employee emp = new Employee();
        if (!emp.inputDetails(sc)) return;

        File dir = new File("data");
        if (!dir.exists()) dir.mkdir();

        File file = new File(dir, "file" + emp.id + ".txt");
        try {
            if (file.createNewFile()) {
                try (FileWriter writer = new FileWriter(file)) {
                    writer.write(emp.toFileString());
                }
                System.out.println("\nEmployee has been added successfully.\n");
            } else {
                System.out.println("\nEmployee already exists.\n");
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}


/************************ To Show details of Employee *********************/

class EmployeeShow {
    public void viewEmployee(String id) {
        File file = new File("data/file" + id + ".txt");
        if (!file.exists()) {
            System.out.println("\nEmployee not found.\n");
            return;
        }

        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNextLine()) {
                System.out.println(sc.nextLine());
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}


/***************************** To Remove Employee *************************/

class EmployeeRemove {
    public void removeEmployee(String id) {
        File file = new File("data/file" + id + ".txt");
        if (file.exists() && file.delete()) {
            System.out.println("\nEmployee removed successfully.\n");
        } else {
            System.out.println("\nEmployee not found.\n");
        }
    }
}


/************************ To Update details of Employee ********************/

class EmployeeUpdate {
    public void updateEmployee(String id, Scanner sc) {
        File file = new File("data/file" + id + ".txt");
        if (!file.exists()) {
            System.out.println("\nEmployee not found.\n");
            return;
        }

        try {
            String content = new String(Files.readAllBytes(file.toPath()));

            System.out.println("Existing Employee Details:\n" + content);
            System.out.print("\nEnter the exact detail to update (copy-paste carefully): ");
            String oldValue = sc.nextLine();
            if (!content.contains(oldValue)) {
                System.out.println("Detail not found in the file.");
                return;
            }

            System.out.print("Enter the new detail to replace with: ");
            String newValue = sc.nextLine();
            content = content.replace(oldValue, newValue);
            Files.write(file.toPath(), content.getBytes());

            System.out.println("\nEmployee details updated successfully.\n");

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}


/************************ To Exit from the EMS Portal *********************/

class CodeExit {
    public void exitApp() {
        System.out.println("\n*****************************************");
        System.out.println("$ Thank You For Using the Software :) ");
        System.out.println("*****************************************");
        System.out.println("\t\t/~ < Developed by V18\n");
        System.exit(0);
    }
}


/***************************** Main Class *******************************/

public class EmployeeManagementSystemUpdated {
    public static void main(String[] args) {
        new File("data").mkdir();
        Scanner sc = new Scanner(System.in);
        MainMenu menu = new MainMenu();

        while (true) {
            menu.menu();
            System.out.print("\nPlease Enter your choice: ");
            String input = sc.nextLine().trim();

            switch (input) {
                case "1":
                    new EmployeeAdd().addEmployee(sc);
                    break;
                case "2":
                    System.out.print("Enter Employee ID: ");
                    new EmployeeShow().viewEmployee(sc.nextLine().trim());
                    break;
                case "3":
                    System.out.print("Enter Employee ID: ");
                    new EmployeeRemove().removeEmployee(sc.nextLine().trim());
                    break;
                case "4":
                    System.out.print("Enter Employee ID: ");
                    new EmployeeUpdate().updateEmployee(sc.nextLine().trim(), sc);
                    break;
                case "5":
                    new CodeExit().exitApp();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }

            System.out.print("\nPress Enter to Continue...");
            sc.nextLine();
            // Optional clear screen message instead of ANSI
            System.out.println("\n---------------------------\n");
        }
    }
}


/****************************** CODED BY V18 TEAM ************************/
