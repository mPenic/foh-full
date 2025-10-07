package com.foh.data.entities.re;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZonedDateTime;

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
@Table(name = "reservations",schema="reservations")
public class ReservationEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservationid")
    private Long reservationId;

    @ManyToOne
    @JoinColumn(name = "eventid", nullable = true)
    private EventEntity event;

    @ManyToOne
    @JoinColumn(name = "tableid", nullable = true)
    private TableEntity table;

    @Column(name = "reservationdate", nullable = false)
    private LocalDate reservationDate;

    @ManyToOne
    @JoinColumn(name = "reservationstatusid", nullable = false)
    private ReservationStatusDictionaryEntity status;

    @Column(name = "reservationname", nullable = false, length = 255)
    private String reservationName;

    @Column(name = "moneyspent", precision = 10, scale = 2)
    private BigDecimal moneySpent;

    @Column(name = "createdat", nullable = false, insertable = false, updatable = false)
    private ZonedDateTime createdAt;

    @ManyToOne
    @JoinColumn(name = "createdby", nullable = true)
    private UserEntity createdBy;

    @ManyToOne
    @JoinColumn(name = "companyid", nullable = false)
    private CompanyDictionaryEntity company;

    // Getteri i setteri

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }
    public EventEntity getEvent() {
        return event;
    }

    public void setEvent(EventEntity event) {
        this.event = event;
    }

    public TableEntity getTable() {
        return table;
    }

    public void setTable(TableEntity table) {
        this.table = table;
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public ReservationStatusDictionaryEntity getStatus() {
        return status;
    }

    public void setStatus(ReservationStatusDictionaryEntity status) {
        this.status = status;
    }

    public String getReservationName() {
        return reservationName;
    }

    public void setReservationName(String reservationName) {
        this.reservationName = reservationName;
    }

    public BigDecimal getMoneySpent() {
        return moneySpent;
    }

    public void setMoneySpent(BigDecimal moneySpent) {
        this.moneySpent = moneySpent;
    }

    public ZonedDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(ZonedDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public UserEntity getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(UserEntity createdBy) {
        this.createdBy = createdBy;
    }

    public CompanyDictionaryEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyDictionaryEntity company) {
        this.company = company;
    }
}
