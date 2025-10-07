








package com.foh.re.vo;

public class TableVO {
    private Long tableId;
    private int tableNumber;
    private Long sectionId; 
    

    public TableVO() {
    }

    public TableVO(Long tableId, int tableNumber, Long sectionId) {
        this.tableId = tableId;
        this.tableNumber = tableNumber;
        this.sectionId = sectionId;
    }

    public Long getTableId() {
        return tableId;
    }

    public void setTableId(Long tableId) {
        this.tableId = tableId;
    }

    public int getTableNumber() {
        return tableNumber;
    }

    public void setTableNumber(int tableNumber) {
        this.tableNumber = tableNumber;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }
}
