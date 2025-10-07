
package com.foh.data.entities.re;

import com.foh.data.entities.usermgmt.CompanyDictionaryEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
@Entity
@Table(name = "tables",schema="reservations")
public class TableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "tableid", updatable = false, nullable = false)
    private Long tableId;

    @Column(name = "tablenumber", nullable = false)
    private Integer tableNumber;

    @ManyToOne
    @JoinColumn(name = "sectionid", nullable = true)
    private SectionEntity section;

    @ManyToOne
    @JoinColumn(name = "companyid", nullable = false)
    private CompanyDictionaryEntity company;

    // Getters and Setters
    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public Integer getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(Integer tableNumber) {
        this.tableNumber = tableNumber;
    }

    public SectionEntity getSection() {
        return section;
    }

    public void setSection(SectionEntity section) {
        this.section = section;
    }

    public CompanyDictionaryEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyDictionaryEntity company) {
        this.company = company;
    }
}
