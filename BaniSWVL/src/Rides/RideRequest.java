package Rides;

public class RideRequest {
    private final String source;
    private final String destination;
    private final String clientUserName;
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
