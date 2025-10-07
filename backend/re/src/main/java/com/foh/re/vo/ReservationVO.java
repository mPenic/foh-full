package com.foh.re.vo;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ReservationVO {
    private Long reservationId;
    private Long eventId; // Selected by user from dropdown
    private String reservationName; // Entered by user
    private LocalDate reservationDate; // Display only
    private String createdByUsername; // Display only
    private String statusName; // Display only
    private Integer tableNumber; // null if table not assigned yet
    private BigDecimal moneySpent;// null if table not assigned yet

    public ReservationVO() {
    }

    // Constructor for display
    public ReservationVO(Long reservationId, Long eventId, String reservationName, LocalDate reservationDate,
            String createdByUsername, String statusName, Integer tableNumber, BigDecimal moneySpent) {
        this.reservationId = reservationId;
        this.eventId = eventId;
        this.reservationName = reservationName;
        this.reservationDate = reservationDate;
        this.createdByUsername = createdByUsername;
        this.statusName = statusName;
        this.tableNumber = tableNumber;
        this.moneySpent = moneySpent;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public Long getEventId() {
        return eventId;
    }

    public String getReservationName() {
        return reservationName;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public String getCreatedByUsername() {
        return createdByUsername;
    }

    public String getStatusName() {
        return statusName;
    }
    
    public Integer getTableNumber() {
        return tableNumber;
    }

    public BigDecimal getMoneySpent() {
        return moneySpent;
    }
    // Setters for form fields
    public void setEventId(Long eventId) {
        this.eventId = eventId;
    }

    public void setReservationName(String reservationName) {
        this.reservationName = reservationName;
    }
}
