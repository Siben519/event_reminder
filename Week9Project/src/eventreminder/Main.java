package eventreminder;

import javafx.util.StringConverter;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class Main extends Application {
    private List<Event> events = new ArrayList<>();
    private EventManager eventManager = new EventManager();

    @Override
    public void start(Stage primaryStage) {
        showOpeningScreen(primaryStage);
    }

    // Opening screen (Main menu)
    private void showOpeningScreen(Stage primaryStage) {
        primaryStage.setTitle("Welcome to Event Reminder");

        VBox openingLayout = new VBox(20);
        openingLayout.setPadding(new Insets(20));
        openingLayout.setStyle("-fx-background-color: #ffcc80; -fx-alignment: center;");

        Label welcomeLabel = new Label("Welcome to Event Reminder");
        welcomeLabel.setStyle("-fx-font-size: 20px; -fx-text-fill: #4CAF50;");

        Button startButton = new Button("Start");
        startButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        startButton.setOnAction(e -> showEventEntryScreen(primaryStage));

        Button viewEventsButton = new Button("View Events");
        viewEventsButton.setStyle("-fx-background-color: #1976d2; -fx-text-fill: white;");
        viewEventsButton.setOnAction(e -> showSavedEventsScreen(primaryStage));

        openingLayout.getChildren().addAll(welcomeLabel, startButton, viewEventsButton);
        primaryStage.setScene(new Scene(openingLayout, 400, 300));
        primaryStage.show();
    }

    // Event entry screen
    private void showEventEntryScreen(Stage primaryStage) {
        primaryStage.setTitle("Enter Event Details");

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20));
        grid.setStyle("-fx-background-color: #e3f2fd;");

        TextField eventTitleField = new TextField();
        eventTitleField.setPromptText("Enter event title");

        TextArea eventDescriptionField = new TextArea();
        eventDescriptionField.setPromptText("Enter event description");

        Button nextButton = new Button("Next");
        nextButton.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white;");
        nextButton.setOnAction(e -> {
            String eventTitle = eventTitleField.getText();
            String eventDescription = eventDescriptionField.getText();
            showDateAndNotificationScreen(primaryStage, eventTitle, eventDescription);
        });

        grid.add(new Label("Event Title:"), 0, 0);
        grid.add(eventTitleField, 1, 0);
        grid.add(new Label("Event Description:"), 0, 1);
        grid.add(eventDescriptionField, 1, 1);
        grid.add(nextButton, 1, 2);

        primaryStage.setScene(new Scene(grid, 500, 400));
    }

    private void showDateAndNotificationScreen(Stage primaryStage, String eventTitle, String eventDescription) {
        primaryStage.setTitle("Set Date & Notification");

        GridPane grid = new GridPane();
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(20));
        grid.setStyle("-fx-background-color: #fff3e0;");

        DatePicker eventDatePicker = new DatePicker();
        eventDatePicker.setPromptText("Select event date");

        ComboBox<LocalTime> notificationTimePicker = new ComboBox<>();
        notificationTimePicker.setPromptText("Select or enter notification time");

        // Populate clock times in 30-minute intervals for quick selection
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
        for (int hour = 0; hour < 24; hour++) {
            for (int min = 0; min < 60; min += 30) {
                notificationTimePicker.getItems().add(LocalTime.of(hour, min));
            }
        }
        
        // Set StringConverter for manual time input
        notificationTimePicker.setConverter(new StringConverter<LocalTime>() {
            @Override
            public String toString(LocalTime time) {
                return (time == null) ? "" : time.format(formatter);
            }

            @Override
            public LocalTime fromString(String string) {
                try {
                    if (string != null && !string.isEmpty()) {
                        return LocalTime.parse(string, formatter); // Parse the time entered manually
                    }
                } catch (Exception e) {
                    System.out.println("Invalid time format, please use hh:mm AM/PM.");
                }
                return null; // Return null if the format is invalid
            }
        });

        // Allow the user to input time manually
        notificationTimePicker.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                // Validate time format manually entered
                LocalTime enteredTime = LocalTime.parse(newValue, formatter);
                notificationTimePicker.getEditor().setStyle(""); // Reset style if valid
            } catch (Exception e) {
                // If the input is invalid, highlight the field with red
                notificationTimePicker.getEditor().setStyle("-fx-border-color: red;");
            }
        });

        Button saveButton = new Button("Save Event");
        saveButton.setStyle("-fx-background-color: #00796b; -fx-text-fill: white;");
        saveButton.setOnAction(e -> {
            String eventDate = eventDatePicker.getValue().toString();
            String notificationTime = notificationTimePicker.getValue() != null ?
                    notificationTimePicker.getValue().format(formatter) : notificationTimePicker.getEditor().getText();
            
            // If time is invalid, don't save
            if (notificationTime == null || notificationTime.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR, "Invalid notification time entered. Please check the format.", ButtonType.OK);
                alert.showAndWait();
                return;
            }
            
            Event event = new Event(eventTitle, eventDescription, eventDate, notificationTime);
            eventManager.saveEvent(event);
            showSavedEventsScreen(primaryStage);
        });

        grid.add(new Label("Event Date:"), 0, 0);
        grid.add(eventDatePicker, 1, 0);
        grid.add(new Label("Notification Time:"), 0, 1);
        grid.add(notificationTimePicker, 1, 1);
        grid.add(saveButton, 1, 2);

        primaryStage.setScene(new Scene(grid, 500, 400));
    }


    // View saved events
    private void showSavedEventsScreen(Stage primaryStage) {
        primaryStage.setTitle("Saved Events");

        VBox layout = new VBox(10);
        layout.setPadding(new Insets(20));
        layout.setStyle("-fx-background-color: #dcedc8;");

        Label title = new Label("Saved Events");
        title.setStyle("-fx-font-size: 18px; -fx-text-fill: #2e7d32;");

        ListView<String> eventListView = new ListView<>();
        for (Event event : eventManager.getAllEvents()) {
            eventListView.getItems().add(event.getTitle() + " - " + event.getDate());
        }

        Button deleteButton = new Button("Delete Selected");
        deleteButton.setStyle("-fx-background-color: #d32f2f; -fx-text-fill: white;");
        deleteButton.setOnAction(e -> {
            int selectedIndex = eventListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                eventManager.deleteEvent(selectedIndex);
                eventListView.getItems().remove(selectedIndex);
            }
        });

        Button backButton = new Button("Back");
        backButton.setStyle("-fx-background-color: #1976d2; -fx-text-fill: white;");
        backButton.setOnAction(e -> showOpeningScreen(primaryStage));

        layout.getChildren().addAll(title, eventListView, deleteButton, backButton);
        primaryStage.setScene(new Scene(layout, 400, 400));
    }

    public static void main(String[] args) {
        launch(args);
    }
}
