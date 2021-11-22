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
                                currentUser = system.register(new DriverInfo(data, driverLicense, nationalId));
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
            {
                if(currentUser instanceof  Client)
                {
                    displayClientMenu();
                    int choice = input.nextInt();
                    switch (choice)
                    {
                        case 1->{
                            clientRequestingRide();
                        }
                        case 2->{
                            clientRatingDriver();
                        }
                        case 3-> {
                            currentUser = null;
                        }
                        default -> throw new IllegalStateException("Unexpected value: " + choice);
                    }

                }
                else if(currentUser instanceof Driver)
                {
                    displayDriverMenu();
                    int choice = input.nextInt();
                    switch (choice)
                    {
                        case 1-> {
                          driverAddingArea();
                        }
                        case 2-> {
                            ((Driver) currentUser).listUserRatings();
                        }

                        default -> throw new IllegalStateException("Unexpected value: " + choice);
                    }

                }
            }
        }
    }
    public static void displayClientMenu()
    {
        System.out.println("1- to request a ride enter 1");
        System.out.println("2- to rate a driver enter 2");
        System.out.println("3- to logout enter 3");
    }
    public static void clientRequestingRide()
    {
        System.out.println("Enter Source: ");
        String source = input.nextLine();
        System.out.println("Enter Destination: ");
        String destination = input.nextLine();
        Client cl = (Client) currentUser;
        RideRequest rideRequest = cl.rideRequest(source,destination);
        boolean success = system.notifyDrivers(rideRequest);
        if(success) System.out.println("Drivers are notified by your request");
        else System.out.println("Area doesnt exist in our database");
    }
    public static void clientRatingDriver()
    {
        System.out.println("Enter driver user name you want to rate: ");
        String userName = input.nextLine();
        System.out.println("Enter rating from 1 to 5");
        int rating = input.nextInt();
        System.out.println("Enter a comment:");
        String comment = input.nextLine();

        Client cl = (Client) currentUser;
        UserRating userRating = cl.rateDriver(rating,comment);
        boolean success = system.rateAdriver(userRating,userName);
        if(success) System.out.println("rating is added to driver " + userName);
        else System.out.println("no such driver exists");
    }
    public static void displayDriverMenu()
    {
        System.out.println("1- to add an area enter 1");
        System.out.println("2- to list user ratings enter 2");
    }
    public static void driverAddingArea()
    {
        System.out.println("Enter Area you want to add to you list");
        String area = input.nextLine();
        area = area.toLowerCase();
        boolean success = system.addAreaToDriver(area,(Driver)currentUser);
        if(success) System.out.println("Area is added to your list");
        else System.out.println("Area already exists");
    }
}

