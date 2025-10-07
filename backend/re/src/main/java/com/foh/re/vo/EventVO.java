
package com.foh.re.vo;

import java.time.LocalDate;

public class EventVO {
    private Long eventId;
    private String eventName;
    private LocalDate eventDate;
    private String createdByUsername;
    private String eventStatusName;

    // Konstruktor
    public EventVO(Long eventId, String eventName, LocalDate eventDate, String createdByUsername, String eventStatusName) {
        this.eventId = eventId;
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.createdByUsername = createdByUsername;
        this.eventStatusName = eventStatusName;
    }

    public EventVO() {
        // Initialize any defaults if needed (e.g., eventDate = LocalDate.now())
    }

    public Long getEventId() {
        return eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public String getCreatedByUsername() {
        return createdByUsername;
    }

    public String getEventStatusName() {
        return eventStatusName;
    }
     // Setters for fields that are populated via the form
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }
}
