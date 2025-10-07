package com.foh.data.entities.re;

import java.time.LocalDate;

import com.foh.data.entities.usermgmt.CompanyDictionaryEntity;
import com.foh.data.entities.usermgmt.UserEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "events",schema="reservations") // Precizan naziv tabele iz baze podataka
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eventid")
    private Long eventId;

    @Column(name = "eventname", nullable = false, length = 255) // Ograničenje veličine prema definiciji baze
    private String eventName;

    @Column(name = "eventdate", nullable = false)
    private LocalDate eventDate; // Upotreba LocalDate zbog boljeg upravljanja datumima u JPA-u

    @ManyToOne
    @JoinColumn(name = "createdby", nullable = true) // Omogućavanje veze s korisnikom koji je kreirao događaj
    private UserEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "eventstatusid", nullable = false)
    private EventStatusDictionaryEntity eventStatus;

    @ManyToOne
    @JoinColumn(name = "companyid", nullable = false)
    private CompanyDictionaryEntity company;


    // Getteri i setteri

    public Long getEventId() {
        return eventId;
    }

    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public LocalDate getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDate eventDate) {
        this.eventDate = eventDate;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }
    
    public EventStatusDictionaryEntity getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(EventStatusDictionaryEntity eventStatus) {
        this.eventStatus = eventStatus;
    }

    public CompanyDictionaryEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyDictionaryEntity company) {
        this.company = company;
    }
}
