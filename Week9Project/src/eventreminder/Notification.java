package eventreminder;

public class Notification {
    private String message;
    private String time;

    // Constructor
    public Notification(String message, String time) {
        this.message = message;
        this.time = time;
    }

    // Getters
    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "message='" + message + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
