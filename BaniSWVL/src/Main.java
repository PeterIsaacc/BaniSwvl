import java.util.Scanner;

public class Main {
    static system system = new system();
    static User currentUser = null;
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        while (true) {
            while (currentUser == null) {
                System.out.println("""
                        1. Login
                        2. Register
                        """);
                switch (input.nextLine()) {
                    case "1" -> {
                        System.out.print("Username: ");
                        String username = input.nextLine();
                        System.out.print("Password: ");
                        currentUser = system.login(username, input.nextLine());
                    }
                    case "2" -> {
                        System.out.println("""
                                1. Driver
                                2. Client
                                """);
                        String choice = input.nextLine();
                        System.out.print("Username:");
                        String userName = input.nextLine();
                        System.out.print("Mobile Number:");
                        String mobileNumber = input.nextLine();
                        System.out.print("Email:");
                        String email = input.nextLine();
                        System.out.print("Password:");
                        String password = input.nextLine();
                        Info data = new Info(userName, mobileNumber, email, password);
                        switch (choice) {
                            case "1" -> {
                                System.out.print("Driver License:");
                                String driverLicense = input.nextLine();
                                System.out.print("National Id:");
                                String nationalId = input.nextLine();
                                currentUser = system.register(new driverInfo(data, driverLicense, nationalId));
                            }
                            case "2" -> {
                                currentUser = system.register(data);
                            }
                            default -> throw new IllegalStateException("Unexpected value: " + choice);
                        }
                    }
                    default -> throw new IllegalStateException("Unexpected value: " + input.nextInt());
                }
            }
            while (currentUser != null)
                currentUser.menu();
        }
    }
}

