import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
    static User admin1 = new Admin(new Info("shehab","010000000","0000000000","00"));
    static User admin2 = new Admin(new Info("peter","010000000","0000000000","00"));
    static User admin3 = new Admin(new Info("david","010000000","0000000000","00"));
    static User admin4 = new Admin(new Info("abdallah","010000000","0000000000","00"));

    Map<String, User> database = new HashMap<String, User>();
    static system system = new system();
    static User currentUser = null;
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        system.addAdmin(admin1);
        system.addAdmin(admin2);
        system.addAdmin(admin3);
        system.addAdmin(admin4);

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
                    input.nextLine();
                    switch (choice)
                    {
                        case 1->{
                            clientRequestingRide();
                        }
                        case 2->{
                            clientRatingDriver();
                        }
                        case 3-> {
                            ((Client) currentUser).listOffers();
                        }
                        case 4-> {
                            currentUser = null;
                        }
                        default -> throw new IllegalStateException("Unexpected value: " + choice);
                    }

                }
                else if(currentUser instanceof Driver)
                {
                    displayDriverMenu();
                    int choice = input.nextInt();
                    input.nextLine();
                    switch (choice)
                    {
                        case 1-> {
                          driverAddingArea();
                        }
                        case 2-> {
                            ((Driver) currentUser).listUserRatings();
                        }
                        case 3-> {
                            driverListingRides();
                        }
                        case  4-> {
                            ((Driver) currentUser).listOffers();
                        }
                        case 5-> {
                            currentUser = null;
                        }
                        default -> throw new IllegalStateException("Unexpected value: " + choice);
                    }

                }
                else if(currentUser instanceof Admin)
                {
                    displayAdminMenu();
                    int choice = input.nextInt();
                    input.nextLine();
                    switch (choice)
                    {
                        case 1->{
                            system.listPendingDrivers();
                        }
                        case 2-> {
                            system.listAllUsers();
                        }
                        case 3-> {
                            AdminAcceptingDriver();
                        }
                        case 4-> {
                            AdminSuspendingUser();
                        }
                        case 5-> {
                            currentUser = null;
                        }

                    }
                }
            }
        }
    }
    public static void displayClientMenu()
    {
        System.out.println("1- to request a ride enter 1");
        System.out.println("2- to rate a driver enter 2");
        System.out.println("3- to list offers enter 3");
        System.out.println("4- to logout enter 4");
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
        input.nextLine();
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
        System.out.println("3- to list rides or make an offer enter 3");
        System.out.println("4- to list offer you made");
        System.out.println("5- to logout enter 5");
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
    public static void driverListingRides()
    {
        Driver driver = (Driver) currentUser;
        boolean empty = driver.listRides();
        if(empty) return;
        System.out.println("Do you want to make an offer ? (y/n)");
        String choice = input.nextLine();
        if(choice.equals("y"))
        {
            System.out.println("Which ride you want to make an offer to ? (enter an index starting from 1)");
            int index = input.nextInt();
            index--;
            System.out.println("Enter price: ");
            double price = input.nextDouble();
            input.nextLine();
            boolean success = system.driverMakingOffer((Driver) currentUser,index,price);
            if(success) System.out.println("Offer made successfully");
            else System.out.println("invalid index");
        }
    }
    public static void displayAdminMenu()
    {
        System.out.println("1- to list all pending drivers enter 1");
        System.out.println("2- to list all users enter 2");
        System.out.println("3- to accept a driver request enter 3");
        System.out.println("4- to suspend a user enter 4");
        System.out.println("5- to logout enter 5");

    }
    public static void AdminAcceptingDriver()
    {
        Admin admin = (Admin) currentUser;
        system.listPendingDrivers();
        System.out.println();
        System.out.println("Enter driver username you want to accept: ");
        String userName = input.nextLine();
        admin.verfiyDriverRegistration(userName, system.getPendingDrivers());
        System.out.println("Driver " + userName + " is accepted");
    }
    public static void AdminSuspendingUser()
    {
        system.listAllUsers();
        System.out.println("Enter user name you want to suspend");
        String userName = input.nextLine();
        User user = system.getUser(userName);
        if(user instanceof Admin)
        {
            System.out.println("You cant suspend an admin!");
            System.out.println();
            return;
        }
        Admin admin = (Admin) currentUser;
        admin.suspendUser(user);
        System.out.println("User " + userName +" is suspended");
    }
}

