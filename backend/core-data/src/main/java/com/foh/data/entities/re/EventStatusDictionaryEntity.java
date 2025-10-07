package com.foh.data.entities.re;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "eventstatusdictionary",schema="reservations")
public class EventStatusDictionaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventstatusid", updatable = false, nullable = false)
    private Long eventStatusId;

    @Column(name = "eventstatusname", unique = true, nullable = false, length = 50)
    private String eventStatusName;

    // Getters and Setters
    public Long getEventStatusId() {
        return eventStatusId;
    }

    public void setEventStatusId(Long eventStatusId) {
        this.eventStatusId = eventStatusId;
    }

    public String getEventStatusName() {
        return eventStatusName;
    }

    public void setEventStatusName(String eventStatusName) {
        this.eventStatusName = eventStatusName;
    }
}
