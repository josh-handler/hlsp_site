package com.hlsp.hlsp_site.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity
public class WorkEvent {
    @Id
    @GeneratedValue
    private UUID workEventGuid;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    Date workEventDate;

    @Temporal(TemporalType.TIME)
    Date workStartTime;

    @Temporal(TemporalType.TIME)
    Date workEndTime;

    String stressLevel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "siteUser_Id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private SiteUser siteUser;

    public WorkEvent(UUID workEventGuid, Date workEventDate, Date workStartTime, Date workEndTime, String stressLevel,
            SiteUser siteUser) {
        this.workEventGuid = workEventGuid;
        this.workEventDate = workEventDate;
        this.workStartTime = workStartTime;
        this.workEndTime = workEndTime;
        this.stressLevel = stressLevel;
        this.siteUser = siteUser;
    }

    public WorkEvent() {
    }

    public WorkEvent(WorkEventDTO dto, SiteUser siteUser) throws ParseException{
        DateFormat eventDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat timeFormat = new SimpleDateFormat("kk:mm");
        this.workEventDate = eventDateFormat.parse(dto.getEventDate());
        this.workStartTime = timeFormat.parse(dto.getEventStart());;
        this.workEndTime = timeFormat.parse(dto.getEventEnd());
        this.stressLevel=dto.getStressLevel();
        this.siteUser=siteUser;
    }

    public WorkEventDTO createDto(){
        WorkEventDTO workDto = new WorkEventDTO();

        DateFormat eventDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat timeFormat = new SimpleDateFormat("kk:mm");
        
        if(workEventDate!=null){
            workDto.setEventDate(eventDateFormat.format(workEventDate));
        }
        if(workEventDate!=null){
            workDto.setEventStart(timeFormat.format(workEventDate));
        }
        if(workEventDate!=null){
            workDto.setEventEnd(timeFormat.format(workEventDate));
        }
        workDto.setStressLevel(stressLevel);
        
        return workDto;
    }
}
