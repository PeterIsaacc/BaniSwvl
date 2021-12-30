package UI;

import System.*;
import Users.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

public class Main {

    static Date dummyDob = new Date();
    static SimpleDateFormat formatDate = new SimpleDateFormat("dd/MM/yyyy");
    static String dummyDateToString = formatDate.format(dummyDob);

    static User admin1 = new Admin(new Info("shehab", "010000000", "0000000000", "00", dummyDateToString));
    static User admin2 = new Admin(new Info("peter", "010000000", "0000000000", "00", dummyDateToString));
    static User admin3 = new Admin(new Info("david", "010000000", "0000000000", "00", dummyDateToString));
    static User admin4 = new Admin(new Info("a", "010000000", "0000000000", "0", dummyDateToString));
    static User testclient = new Client(new Info("c", "010000000", "0000000000", "0", dummyDateToString));
    static User testdriver = new Driver(new Info("d", "010000000", "0000000000", "0", dummyDateToString));


    static MainSystem system = new MemorySystem();
    static User currentUser = null;
    public static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        system.addAdmin(admin1);
        system.addAdmin(admin2);
        system.addAdmin(admin3);
        system.addAdmin(admin4);
        testdriver.setState(State.Available);
        system.addUser(testdriver);
        system.addUser(testclient);
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
                        String choice = input.nextLine();
                        System.out.println("----------" + "Enter Info" + "----------");
                        System.out.print("Username: ");
                        String userName = input.nextLine();
                        System.out.print("Mobile Number: ");
                        String mobileNumber = input.nextLine();

                        int yearOfBirth = 0;
                        int monthOfBirth = 0;
                        int dayOfBirth = 0;
                        System.out.println("Day of Birth: ");
                        String dayOfBirthString = input.nextLine();
                        dayOfBirth = Integer.parseInt(dayOfBirthString);

                        System.out.println("Month of Birth: ");
                        String monthOfBirthString = input.nextLine();
                        monthOfBirth = Integer.parseInt(monthOfBirthString);

                        System.out.println("Year of Birth: ");
                        String yearOfBirthString = input.nextLine();
                        yearOfBirth = Integer.parseInt(yearOfBirthString);

                        Calendar cal = Calendar.getInstance();
                        cal.set(yearOfBirth, monthOfBirth-1, dayOfBirth);
                        Date userDate = new Date();
                        userDate = cal.getTime();
                        String userDateString =formatDate.format(userDate);

                        System.out.print("Email: ");
                        String email = input.nextLine();
                        System.out.print("Password: ");
                        String password = input.nextLine();
                        Info data = new Info(userName, mobileNumber, email, password, userDateString);
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
                        }
                    }
                }
            }

            while (currentUser != null) {
                currentUser = currentUser.displayMenu(system);
            }

        }
    }
}

