package eventreminder;

public class SaveEvent {

    private EventManager eventManager;

    public SaveEvent(EventManager eventManager) {
        this.eventManager = eventManager;
    }

    // Save the event
    public void saveEvent(Event event) {
        eventManager.saveEvent(event);
    }
}
