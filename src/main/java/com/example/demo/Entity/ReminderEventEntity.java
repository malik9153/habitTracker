package com.example.demo.Entity;

import com.example.demo.CalendarQuickstart;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.model.Event;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReminderEventEntity extends BaseEventEntity {
    private final String startDateTime;
    private final String timeForReminder;
    private Event event;

    public ReminderEventEntity(String summary, String location, String description, String startDateTime, String timeForReminder) {
        super(summary, location, description);
        this.startDateTime = startDateTime;
        this.timeForReminder = timeForReminder;
    }

    @Override
    public void createEvent() {
        this.event = new Event()
                .setSummary(summary)
                .setLocation(location)
                .setDescription(description);
    }

    public void createAndSendReminder() throws GeneralSecurityException, IOException {
        DateTime startDateTimeForReminder = new DateTime(startDateTime + "T" + timeForReminder + ":00-07:00");
        LocalTime timeForReminderParsed = LocalTime.parse(timeForReminder, DateTimeFormatter.ofPattern("HH:mm")).plusMinutes(15);
        DateTime endDateTimeForReminder = new DateTime(startDateTime + "T" + timeForReminderParsed.toString() + ":00-07:00");
        CalendarQuickstart.createReminder(event, startDateTimeForReminder, endDateTimeForReminder);

    }

}
