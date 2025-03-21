package eventreminder;

public class DeleteEvent {

    private EventManager eventManager;

    public DeleteEvent(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    // Delete event by index
    public void deleteEventByIndex(int index) {
        eventManager.deleteEvent(index);
    }
}
