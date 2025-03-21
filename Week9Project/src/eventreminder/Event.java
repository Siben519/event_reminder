package eventreminder;

public class Event {
private String title;
private String description;
private String date;
private String notificationTime;

// Constructor
public Event(String title, String description, String date, String notificationTime) {
this.title = title;
this.description = description;
this.date = date;
this.notificationTime = notificationTime;
   }

    // Getters
    public String getTitle() {
       return title;
   }
		
    public String getDescription() {
    	return description;
			    }
			
			    public String getDate() {
			        return date;
			    }
			
			    public String getNotificationTime() {
			        return notificationTime;
			    }
			
			    @Override
			    public String toString() {
			        return "Event{" +
			                "title='" + title + '\'' +
			                ", description='" + description + '\'' +
			                ", date='" + date + '\'' +
			                ", notificationTime='" + notificationTime + '\'' +
			                '}';
			    }
			}
