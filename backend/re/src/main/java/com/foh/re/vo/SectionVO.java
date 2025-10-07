
package com.foh.re.vo;

public class SectionVO {
    private Long sectionId;
    private int sectionNumber;
    private Boolean isSpecial; 
    
    public SectionVO() {
        // Defaults can be set here if needed
    }

    public SectionVO(Long sectionId, int sectionNumber, Boolean isSpecial) {
        this.sectionId = sectionId;
        this.sectionNumber = sectionNumber;
        this.isSpecial = isSpecial;
    }

    public Long getSectionId() {
        return sectionId;
    }

    public void setSectionId(Long sectionId) {
        this.sectionId = sectionId;
    }

    public int getSectionNumber() {
        return sectionNumber;
    }

    public void setSectionNumber(int sectionNumber) {
        this.sectionNumber = sectionNumber;
    }

    public Boolean getIsSpecial() {
        return isSpecial;
    }

    public void setIsSpecial(Boolean isSpecial) {
        this.isSpecial = isSpecial;
    }
}
