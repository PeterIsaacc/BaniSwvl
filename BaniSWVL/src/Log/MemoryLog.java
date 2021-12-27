package Log;

import java.util.ArrayList;

public class MemoryLog implements Log {
    ArrayList<Event> events;


    @Override
    public void printLogs() {
        for (Event e : events) {
            System.out.println(e.toString());
        }
    }

    @Override
    public void addEvent(Event e) {
        events.add(e);
    }
}
