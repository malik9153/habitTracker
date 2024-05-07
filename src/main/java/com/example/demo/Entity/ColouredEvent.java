package com.example.demo.Entity;

import com.example.demo.CalendarQuickstart;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class ColouredEvent extends BaseEventEntity {
    private final String startDateTime;
    private final String timeForReminder;
    Random rand = new Random();
    private Event event;

    public ColouredEvent(String summary, String location, String description, String startDateTime, String timeForReminder) {
        super(summary, location, description);
        this.startDateTime = startDateTime;
        this.timeForReminder = timeForReminder;

    }

    @Override
    public void createEvent() {
        int colour = rand.nextInt(12) + 1;

        this.event = new Event()
                .setSummary(summary)
                .setLocation(location)
                .setDescription(description)
                .setColorId(Integer.toString(colour));
    }

    public void createAndSendReminder() throws GeneralSecurityException, IOException {
        DateTime startDateTimeForReminder = new DateTime(startDateTime + "T" + timeForReminder + ":00-00:00");
        LocalTime timeForReminderParsed = LocalTime.parse(timeForReminder, DateTimeFormatter.ofPattern("HH:mm")).plusMinutes(15);
        DateTime endDateTimeForReminder = new DateTime(startDateTime + "T" + timeForReminderParsed.toString() + ":00-00:00");
        CalendarQuickstart.createReminder(event, startDateTimeForReminder, endDateTimeForReminder);

    }

}
