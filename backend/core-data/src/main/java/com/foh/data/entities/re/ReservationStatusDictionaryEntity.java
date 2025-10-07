package com.foh.data.entities.re;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
@Entity
@Table(name = "reservationstatusdictionary",schema="reservations") // Naziv tabele točno prema bazi podataka
public class ReservationStatusDictionaryEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservationstatusid", updatable = false, nullable = false)
    private Long reservationStatusId;

    @Column(name = "reservationstatusname", unique = true, nullable = false, length = 20)
    private String reservationStatusName;

    // Getters and Setters
    public Long getReservationStatusId() {
        return reservationStatusId;
    }

    public void setReservationStatusId(Long reservationStatusId) {
        this.reservationStatusId = reservationStatusId;
    }

    public String getReservationStatusName() {
        return reservationStatusName;
    }

    public void setReservationStatusName(String reservationStatusName) {
        this.reservationStatusName = reservationStatusName;
    }
}
