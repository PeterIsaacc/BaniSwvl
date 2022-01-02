package com.example.Log;

import java.util.ArrayList;

public interface Log {
    public void printLogs();
    public ArrayList<Event> getEvents();
    public void addEvent(Event e);
}

