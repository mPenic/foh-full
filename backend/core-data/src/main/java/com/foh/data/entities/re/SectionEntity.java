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
@Table(name = "sections",schema="reservations")
public class SectionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sectionid", updatable = false, nullable = false)
    private Long sectionId;

    @Column(name = "isspecial", nullable = false)
    private Boolean isSpecial = false;

    @Column(name = "sectionnumber", nullable = false)
    private Integer sectionNumber;

    @ManyToOne
    @JoinColumn(name = "companyid", nullable = false)
    private CompanyDictionaryEntity company;

    // Getters and Setters
    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public Boolean getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(Boolean isSpecial) {
        this.isSpecial = isSpecial;
    }

    public Integer getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(Integer sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public CompanyDictionaryEntity getCompany() {
        return company;
    }

    public void setCompany(CompanyDictionaryEntity company) {
        this.company = company;
    }
}