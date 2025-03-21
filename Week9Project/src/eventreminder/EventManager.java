package eventreminder;

import java.util.ArrayList;
import java.util.List;

public class EventManager {
    private List<Event> events = new ArrayList<>();

    // Save event
    public void saveEvent(Event event) {
        events.add(event);
        System.out.println("Event saved: " + event);
    }

    // Get all events
    public List<Event> getAllEvents() {
        return events;
    }

    // Delete event
    public void deleteEvent(int index) {
        if (index >= 0 && index < events.size()) {
            Event event = events.get(index);
            events.remove(index);
            System.out.println("Event deleted: " + event);
        }
    }
}
