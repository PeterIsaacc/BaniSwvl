package com.example.BaniSWVL;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.example.Log.Event;
import com.example.Log.Log;
import com.example.Rides.Offer;
import com.example.Rides.RideRequest;
import com.example.Users.*;
import com.example.System.MainSystem;
import com.example.System.MemorySystem;
import org.springframework.expression.ParseException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class BaniSwvlController {
    private MainSystem system;
    private User currentUser;

    public BaniSwvlController() {
        system = new MemorySystem(); currentUser = null;
        Date dummyDob = new Date();
        User admin1 = new Admin(new Info("shehab", "010000000", "0000000000", "00", dummyDob));
        User admin2 = new Admin(new Info("peter", "010000000", "0000000000", "00", dummyDob));
        User admin3 = new Admin(new Info("david", "010000000", "0000000000", "00", dummyDob));
        User admin4 = new Admin(new Info("a", "010000000", "0000000000", "0", dummyDob));
        User testclient = new Client(new Info("c", "010000000", "0000000000", "0", dummyDob));
        User testdriver = new Driver(new Info("d", "010000000", "0000000000", "0", dummyDob));
        system.addAdmin(admin1);
        system.addAdmin(admin2);
        system.addAdmin(admin3);
        system.addAdmin(admin4);
        testdriver.setState(State.Available);
        system.addAreaToDriver("giza", (Driver) testdriver);
        system.addUser(testdriver);
        system.addUser(testclient);
    }

//    authentication
    @PostMapping("/signup/user/client")
    public String singupClient(@RequestBody Info info) {
        if(currentUser != null)
            return "you are already logged in please logout to make a new account";
        currentUser = system.getUser(info.getUserName());
        if(currentUser != null) {
            currentUser = null;
            return "username already exist";
        }
        currentUser =  system.register(info);
        system.listAllUsers();
        currentUser = system.getUser(info.getUserName());
        return "Success! your account is created and logged in";
    }

    @PostMapping("/signup/user/driver")
    public String signupDriver(@RequestBody Map<String, String> json) throws ParseException, java.text.ParseException {
        System.out.println(json);
        if(currentUser != null)
            return "you are already logged in please logout to make a new account";
        String userName = json.get("userName");
        String mobileNumber = json.get("mobileNumber");
        String email = json.get("email");
        String password = json.get("password");

        Date dob=new SimpleDateFormat("yyyy-MM-dd").parse(json.get("dob"));

        String driverLicense = json.get("driverLicense");
        String nationalId = json.get("nationalId");
        DriverInfo info = new DriverInfo((new Info(userName,mobileNumber,email,password,dob)),driverLicense,nationalId);
        currentUser = system.getUser(info.getUserName());
        if(currentUser != null) {
            currentUser = null;
            return "username already exist";
        }
        currentUser =  system.register(info);
        system.listAllUsers();
        currentUser = system.getUser(info.getUserName());
        return "Success! your account is created and logged in";
    }
    @PostMapping("/login")
    public String login(@RequestBody Map<String, String> json)
    {
        System.out.println(json);
        if(currentUser != null)
        {
            return "You are already logged in";
        }
        currentUser = system.login(json.get("username"),json.get("password"));
        if(currentUser == null)
        {
            return "username or password is wrong";
        }

        if(currentUser instanceof Driver) {
            if (((Driver) currentUser).getState() == State.Suspended) {
                currentUser = null;
                return "This account is suspended";
            } else if (((Driver) currentUser).getState() == State.Pending) {
                currentUser = null;
                return "This account is pending";
            }
        }
        if (currentUser instanceof Client) {
            if (((Client) currentUser).getState() == State.Suspended) {
                currentUser = null;
                System.out.println("This account is suspended");
            }
        }
        String msg = "Welcome back " + currentUser.getUserData().getUserName() + " !";
        if(currentUser instanceof Driver)
        {
            if(((Driver)currentUser).getCurrentOffer() != null)
            {
                msg+= ", you have an offer: ";
                ArrayList<Offer> offers = ((Driver)currentUser).getOffers();
                Offer currentOffer = ((Driver)currentUser).getCurrentOffer();

                if (!offers.contains(currentOffer))
                    msg+=currentOffer + "\nDiscounted Price: " + currentOffer.getPrice();
                else
                    msg+=currentOffer;
            }
        }
        return msg;
    }

    @GetMapping("/logout")
    public String logout() {
        if(currentUser == null)
            return "You are already logged out";
        currentUser = null;
        return "Success! you are logged out";

    }

//    driver
    @GetMapping("/driver/getrides")
    public List<RideRequest> getRideRequests(){
        if (currentUser == null)
        {
            return null;
        }
        if(!(currentUser instanceof Driver))
            return null;
        List<RideRequest> rideRequests = new ArrayList<RideRequest>();
        for (RideRequest rideRequest : system.getRideRequests()) {
            if (system.checkdriver((Driver) currentUser, rideRequest.getSource()))
                rideRequests.add(rideRequest);
        }
        return rideRequests;
    }
    @PostMapping("/driver/makeoffer")
    public String makeOffer(@RequestBody Map<String,String> json) {
        if (currentUser == null)
        {
            return "you are not logged in";
        }
        if(!(currentUser instanceof Driver))
            return "you are not a driver";
        if(((Driver) currentUser).getState() == State.Busy)
        {
            return "you are busy now!";
        }
        int index = Integer.parseInt(json.get("index"));
        double price = Double.parseDouble(json.get("price"));
        List<RideRequest> rideRequests = new ArrayList<RideRequest>();
        for (RideRequest rideRequest : system.getRideRequests()) {
            if (system.checkdriver((Driver) currentUser, rideRequest.getSource()))
                rideRequests.add(rideRequest);
        }
        if(index > rideRequests.size())
            return "please enter a valid index";
        boolean success = system.driverMakingOffer((Driver) currentUser,rideRequests.get(index),price);
        if(success)
            return "Success! Offer is added and client is notified";
        else
            return "Failure";
    }

    @GetMapping("/driver/getoffers")
    public List<String> getOffersDriver()
    {
        if (currentUser == null)
        {
            return null;
        }
        if(!(currentUser instanceof Driver))
            return null;
        ArrayList<Offer> offers = ((Driver) currentUser).getOffers();
        ArrayList<String> offersStr = new ArrayList<String>();
        for(int i = 0; i < offers.size(); i++)
        {
            offersStr.add(offers.get(i).toString());
        }
        return offersStr;
    }
    @GetMapping("/driver/notifyarrival")
    public String notifyArrival()
    {
        if (currentUser == null)
        {
            return "your are not logged in";
        }
        if(!(currentUser instanceof Driver))
            return "you are not a driver";
        if(((Driver)currentUser).getState() == State.Available)
            return "you are not busy with a ride";
        ((Driver)currentUser).arrivalAtLocation(system);
        return "client has been notified by your arrival";
    }

    @GetMapping("/driver/listratings")
    public ArrayList<UserRating> getRatings(){
        if(currentUser instanceof Driver){return ((Driver) currentUser).ListUserRatings();}
        else{return null;}
    }

    @PostMapping("/driver/addArea")
    public String addArea(@RequestBody Map<String, String> json){
        if(currentUser instanceof Driver){
            String area = json.get("area");
            boolean success = system.addAreaToDriver(area, (Driver) currentUser);
            if (success) {return "area is added successfully";}
            else{return "Area already exists!";}
        }
        else {return "Error";}
    }

    @GetMapping("/driver/endride")
    public String endRide()
    {
        if (currentUser == null)
        {
            return "your are not logged in";
        }
        if(!(currentUser instanceof Driver))
            return "you are not a driver";
        if(((Driver)currentUser).getState() == State.Available)
            return "you are not busy with a ride";
        ((Driver)currentUser).endRide(system);
        return "Success! ride has been finished";
    }


// client
   @PostMapping("/client/ratedriver")
   public String rateDriver(@RequestBody Map<String,String> json){
     if(currentUser == null)
         return "you are not logged in";
     if(!(currentUser instanceof Client))
     {
         return "you are not a client";
     }
     int rating = Integer.parseInt(json.get("rating"));
     String driverUserName = json.get("driverUserName");
     String comment = "";
     if(rating < 1 || rating > 5)
           return "Rating should be from 1 to 5";
     if(json.containsKey("comment")) {
         comment = json.get("comment");
     }
     UserRating userRating = ((Client)currentUser).rateDriver(rating, comment);
     boolean success = system.rateDriver(userRating, driverUserName);
     if(success)
         return "Rating submitted for" + driverUserName;
     else
         return "no such driver exists";
    }

    @GetMapping("/client/getoffers")
    public List<String> getOffersClient()
    {
        if(currentUser == null)
            return  null;
        if(!(currentUser instanceof Client))
        {
            return null;
        }
        ArrayList<String> offersStr = new ArrayList<String>();
        ArrayList<Offer> offers = ((Client)currentUser).getOffers();
        for(int i = 0; i < offers.size(); i++)
        {
            offersStr.add(offers.get(i).toString());
        }
        return offersStr;
    }

    @PostMapping("/client/requestride")
    public String requestRide(@RequestBody RideRequest rideRequest) {
        if (currentUser instanceof Client) {
            new RideRequest(rideRequest.getSource(), rideRequest.getDestination(),
                    rideRequest.getClientUserName(), rideRequest.getNumberOfPassengers());
            boolean success = system.updateSystemRideRequests(rideRequest);
            if (success) {return "Relevant drivers have been notified...";}
            else {return ("Area doesn't exist in our database!");}
        }
        else{return"Error";}
    }

    @PostMapping("/client/acceptOffer")
    public String acceptOffer(@RequestBody Map<String,String> json) {
        if(currentUser == null)
            return  null;
        if(!(currentUser instanceof Client))
        {
            return null;
        }
        int offerIndex = Integer.parseInt(json.get("index"));

        ArrayList<Offer> offers = ((Client)currentUser).getOffers();
        if(offerIndex > offers.size() || offerIndex < 1)
            return "please enter a valid index";
        system.clientAcceptOffer(offers.get(offerIndex - 1), (Client) currentUser);
        ((Client) currentUser).removeOffer(offerIndex - 1);
        ((Client)currentUser).incrementRide();
        return "Success! Driver is on his way";
    }





//    admin
   @GetMapping("/admin/PendingDrivers")
   public ArrayList<Driver> getPendingDrivers(){
     if(currentUser instanceof Admin){return system.getPendingDrivers();}
     else {return null;} }
    @GetMapping("/admin/getusers")
    public ArrayList<User> getAllUsers(){
        if(currentUser instanceof Admin){return system.getAllUsers();}
        else {return null;}
    }

    @PostMapping("/admin/verifyDriver")
    public String verifyDriver(@RequestBody Map<String,String> json){
        String userName = json.get("userName");
        if(currentUser instanceof Admin){
            if(userName!=null && system.checkPendingDrivers(userName)) {
                ((Admin) currentUser).verifyDriverRegistration(userName, getPendingDrivers());
                return "Driver is accepted successfully!";
            }
            else{return "Driver doesn't exist!";}
        }
        else{return "Error";}
    }
    @PostMapping("/admin/addDiscount")
    public String VerifyDriver(@RequestBody Map<String,String> json){
        if(currentUser == null)
            return "you are not logged in";
        if(currentUser instanceof Admin){
            String area = json.get("area");
            if(system.addAreaDiscounts(area))
            {
                return "Area added successfully...";
            }
            else
            {
                return "Area already discounted...";
            }
        }
        else{
            return "Error: you must be an admin";
        }
    }

    @PostMapping("/admin/suspend")
    public String suspendDriver(@RequestBody Map<String, String> json){
        if(currentUser instanceof Admin){
            String userName= json.get("userName");
            if(userName!=null && system.getUser(userName)!=null ) {
                User user = system.getUser(userName);
                ((Admin) currentUser).suspendUser(user);
                if(user.setState(State.Suspended)){return "Driver is already suspended";}
                else {return "Driver is suspended successfully!";}
            }
            else{return "Driver doesn't exist!";}
        }
        else{return "Error";}
    }

    @GetMapping("/admin/getlogs")
    public ArrayList<String> getAllLogs(){
        if(currentUser instanceof Admin){
            ArrayList<String> eventsStr = new ArrayList<String>();
            Log logs = system.getLogs();
            logs.printLogs();
            ArrayList<Event> events = logs.getEvents();
            for(int i = 0; i < events.size(); i++)
                eventsStr.add(events.get(i).toString());
            return eventsStr;
        }
        else
            return null;
    }
}