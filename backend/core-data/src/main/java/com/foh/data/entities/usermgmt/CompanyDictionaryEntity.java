package com.foh.data.entities.usermgmt;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "companydictionary", schema = "common")
public class CompanyDictionaryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "companyid", updatable = false, nullable = false)
    private Long companyId;

    @Column(name = "companyname", unique = true, nullable = false, length = 100)
    private String companyName;

    // --- New feature-flag fields ---
    @Column(name = "feat_ch", nullable = false)
    private boolean featCh;

    @Column(name = "feat_kb", nullable = false)
    private boolean featKb;

    @Column(name = "feat_re", nullable = false)
    private boolean featRe;

    // Constructors
    public CompanyDictionaryEntity() {}

    // Getters and setters
    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public boolean isFeatCh() {
        return featCh;
    }

    public void setFeatCh(boolean featCh) {
        this.featCh = featCh;
    }

    public boolean isFeatKb() {
        return featKb;
    }

    public void setFeatKb(boolean featKb) {
        this.featKb = featKb;
    }

    public boolean isFeatRe() {
        return featRe;
    }

    public void setFeatRe(boolean featRe) {
        this.featRe = featRe;
    }
}
