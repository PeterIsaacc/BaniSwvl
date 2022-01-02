package com.example.Rides;

public class RideRequest {
    private final String source;
    private final String destination;
    private final String clientUserName;
    public int numberOfPassengers;

    public RideRequest(String src, String dest, String userName, int numberOfPassengers)
    {
        source = src;
        destination = dest;
        clientUserName = userName;
        this.numberOfPassengers = numberOfPassengers;
    }

    public int getNumberOfPassengers() {
        return numberOfPassengers;
    }

    public void setNumberOfPassengers(int numberOfPassengers) {
        this.numberOfPassengers = numberOfPassengers;
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
      return ("user name: " + clientUserName +", source: " + source +", destination: " + destination + ", Passengers: " + numberOfPassengers);
    }
}
