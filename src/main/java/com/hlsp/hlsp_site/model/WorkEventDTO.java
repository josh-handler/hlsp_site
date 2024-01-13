package com.hlsp.hlsp_site.model;

public class WorkEventDTO {
    private String eventDate;
    private String eventStart;
    private String eventEnd;
    private String stressLevel;


    

    public WorkEventDTO(String eventDate, String eventStart, String eventEnd, String stressLevel) {
        this.eventDate = eventDate;
        this.eventStart = eventStart;
        this.eventEnd = eventEnd;
        this.stressLevel = stressLevel;
    }


    public WorkEventDTO() {
    }

    
    public String getEventDate() {
        return eventDate;
    }
    public void setEventDate(String eventDate) {
        this.eventDate = eventDate;
    }
    public String getEventStart() {
        return eventStart;
    }
    public void setEventStart(String eventStart) {
        this.eventStart = eventStart;
    }
    public String getEventEnd() {
        return eventEnd;
    }
    public void setEventEnd(String eventEnd) {
        this.eventEnd = eventEnd;
    }
    public String getStressLevel() {
        return stressLevel;
    }
    public void setStressLevel(String stressLevel) {
        this.stressLevel = stressLevel;
    }
}
