
package com.foh.re.vo;

public class ReservationStatusDictionaryVO {
    private Long statusId;
    private String statusName;

    // Konstruktor
    public ReservationStatusDictionaryVO(Long statusId, String statusName) {
        this.statusId = statusId;
        this.statusName = statusName;
    }

    // Getteri i setteri
    public Long getStatusId() {
        return statusId;
    }

    public void setStatusId(Long statusId) {
        this.statusId = statusId;
    }

    public String getStatusName() {
        return statusName;
    }

    public void setStatusName(String statusName) {
        this.statusName = statusName;
    }
}
