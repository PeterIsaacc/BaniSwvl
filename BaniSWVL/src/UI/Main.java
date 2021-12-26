package UI;

import System.*;
import Users.*;
import java.util.Scanner;

public class Main {
    static User admin1 = new Admin(new Info("shehab", "010000000", "0000000000", "00"));
    static User admin2 = new Admin(new Info("peter", "010000000", "0000000000", "00"));
    static User admin3 = new Admin(new Info("david", "010000000", "0000000000", "00"));
    static User admin4 = new Admin(new Info("abdallah", "010000000", "0000000000", "00"));


    static MainSystem system = new MemorySystem();
    static User currentUser = null;
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        system.addAdmin(admin1);
        system.addAdmin(admin2);
        system.addAdmin(admin3);
        system.addAdmin(admin4);

        while (true) {
            while (currentUser == null) {
                System.out.println("--------------------" + "BaniSWVL" + "--------------------");
                System.out.println("""
                        1. Login
                        2. Register
                        """);
                switch (input.nextLine()) {
                    case "1" -> {
                        System.out.println("--------------------" + "Login" + "--------------------");
                        System.out.print("Username: ");
                        String username = input.nextLine();
                        System.out.print("Password: ");
                        currentUser = system.login(username, input.nextLine());
                    }
                    case "2" -> {
                        System.out.println("----------" + "Choose Account Type" + "----------");
                        System.out.println("""
                                1. Register as Driver
                                2. Register as Client
                                """);
                        System.out.println("----------" + "Enter Info" + "----------");
                        String choice = input.nextLine();
                        System.out.print("Username: ");
                        String userName = input.nextLine();
                        System.out.print("Mobile Number: ");
                        String mobileNumber = input.nextLine();
                        System.out.print("Email: ");
                        String email = input.nextLine();
                        System.out.print("Password: ");
                        String password = input.nextLine();
                        Info data = new Info(userName, mobileNumber, email, password);
                        switch (choice) {
                            case "1" -> {
                                System.out.println("----------" + "Extra Driver Info" + "----------");
                                System.out.print("Driver's License: ");
                                String driverLicense = input.nextLine();
                                System.out.print("National ID: ");
                                String nationalId = input.nextLine();
                                currentUser = system.register(new DriverInfo(data, driverLicense, nationalId));
                            }
                            case "2" -> currentUser = system.register(data);
                            default -> throw new IllegalStateException("Unexpected value: " + choice);
                        }
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + input.nextInt());
                }
            }

            while (currentUser != null) {
                currentUser = currentUser.displayMenu(system);
            }

        }
    }
}

