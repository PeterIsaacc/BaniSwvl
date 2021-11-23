package Rides;

public class RideRequest {
    private String source;
    private String destination;
    private String clientUserName;
    public RideRequest(String src, String dest, String userName)
    {
        source = src;
        destination = dest;
        clientUserName = userName;
    }

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getClientUserName() {
        return clientUserName;
    }
    public String toString()
    {
      return ("user name: " + clientUserName +"\nsource: " + source +"\ndestination: " + destination);
    }
}
