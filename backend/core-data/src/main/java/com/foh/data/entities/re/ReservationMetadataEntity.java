
package com.foh.data.entities.re;
import java.time.ZonedDateTime;

import com.foh.data.entities.usermgmt.CompanyDictionaryEntity;
import com.foh.data.entities.usermgmt.UserEntity;

import  jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "reservationmetadata",schema="reservations")
public class ReservationMetadataEntity {

    @Id
    @Column(name = "reservationid")
    private Long reservationId; // same as Reservation PK

    @OneToOne
    @MapsId
    @JoinColumn(name = "reservationid")
    private ReservationEntity reservation;

    @Column(name = "updatedat")
    private ZonedDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "updatedby")
    private UserEntity updatedBy;

    @Column(name = "completedat")
    private ZonedDateTime completedAt;

    @ManyToOne
    @JoinColumn(name = "completedby")
    private UserEntity completedBy;

    @ManyToOne
    @JoinColumn(name = "companyid", nullable = false)
    private CompanyDictionaryEntity company;

    // Getters and Setters
    public Long getReservationId() {
        return reservationId;
    }

    public ReservationEntity getReservation() {
        return reservation;
    }

    public void setReservation(ReservationEntity reservation) {
        this.reservation = reservation;
    }

    public ZonedDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(ZonedDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserEntity getUpdatedBy() {
        return updatedBy;
    }

    public void setUpdatedBy(UserEntity updatedBy) {
        this.updatedBy = updatedBy;
    }

    public ZonedDateTime getCompletedAt() {
        return completedAt;
    }

    public void setCompletedAt(ZonedDateTime completedAt) {
        this.completedAt = completedAt;
    }

    public UserEntity getCompletedBy() {
        return completedBy;
    }

    public void setCompletedBy(UserEntity completedBy) {
        this.completedBy = completedBy;
    }

    public CompanyDictionaryEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyDictionaryEntity company) {
        this.company = company;
    }
}
