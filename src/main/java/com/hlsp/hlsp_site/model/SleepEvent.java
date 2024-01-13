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
public class SleepEvent {
    @Id
    @GeneratedValue
    private UUID sleepEventGuid;

    @Column(nullable = false)
    @Temporal(TemporalType.DATE)
    Date sleepEventDate;

    @Temporal(TemporalType.TIME)
    Date toBedTime;

    @Temporal(TemporalType.TIME)
    Date fellAsleepTime;

    @Temporal(TemporalType.TIME)
    Date wokeUpTime;

    @Temporal(TemporalType.TIME)
    Date gotUpTime;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "siteUser_Id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private SiteUser siteUser;

    public SleepEvent(){

    }

    public SleepEvent(SleepEventDTO dto, SiteUser user) throws ParseException{
        DateFormat eventDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat timeDateFormat = new SimpleDateFormat("kk:mm");
        this.sleepEventDate = eventDateFormat.parse(dto.getSleepEventDate());
        this.toBedTime = timeDateFormat.parse(dto.getToBedTime());
        this.gotUpTime = timeDateFormat.parse(dto.getGotUpTime());
        if(!dto.getFellAsleepTime().isEmpty()){
            this.fellAsleepTime = timeDateFormat.parse(dto.getFellAsleepTime());
            }
        if(!dto.getWokeUpTime().isEmpty()){
        this.wokeUpTime = timeDateFormat.parse(dto.getWokeUpTime());
        }
        siteUser=user;
    }

    public SleepEvent(Date sleepEventDate, Date toBedTime, Date fellAsleepTime, Date wokeUpTime, Date gotUpTime,
            SiteUser siteUser) {
        this.sleepEventDate = sleepEventDate;
        this.toBedTime = toBedTime;
        this.fellAsleepTime = fellAsleepTime;
        this.wokeUpTime = wokeUpTime;
        this.gotUpTime = gotUpTime;
        this.siteUser = siteUser;
    }

    public UUID getSleepEventGuid() {
        return sleepEventGuid;
    }

    public Date getSleepEventDate() {
        return sleepEventDate;
    }

    public void setSleepEventDate(Date sleepEventDate) {
        this.sleepEventDate = sleepEventDate;
    }

    public Date getToBedTime() {
        return toBedTime;
    }

    public void setToBedTime(Date toBedTime) {
        this.toBedTime = toBedTime;
    }

    public Date getFellAsleepTime() {
        return fellAsleepTime;
    }

    public void setFellAsleepTime(Date fellAsleepTime) {
        this.fellAsleepTime = fellAsleepTime;
    }

    public Date getWokeUpTime() {
        return wokeUpTime;
    }

    public void setWokeUpTime(Date wokeUpTime) {
        this.wokeUpTime = wokeUpTime;
    }

    public Date getGotUpTime() {
        return gotUpTime;
    }

    public void setGotUpTime(Date gotUpTime) {
        this.gotUpTime = gotUpTime;
    }

    public SiteUser getSiteUser() {
        return siteUser;
    }

    public void setSiteUser(SiteUser siteUser) {
        this.siteUser = siteUser;
    }

    //In theory we could remove 3 of the checks and still be safe. However, this gaurds against unexpected nulls from the database which stymied development for some time.
    public SleepEventDTO createDto(){
        DateFormat eventDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat timeFormat = new SimpleDateFormat("kk:mm");
        SleepEventDTO sleepDto = new SleepEventDTO();
        if(sleepEventDate!=null){
            sleepDto.setSleepEventDate(eventDateFormat.format(sleepEventDate));
        }
        if(toBedTime!=null){
            sleepDto.setToBedTime(timeFormat.format(toBedTime));
        }
        if(gotUpTime!=null){
            sleepDto.setGotUpTime(timeFormat.format(gotUpTime));
        }
        if(fellAsleepTime!=null){
            sleepDto.setFellAsleepTime(timeFormat.format(fellAsleepTime));
        }
        if(wokeUpTime!=null){
            sleepDto.setWokeUpTime(timeFormat.format(wokeUpTime));
        }
        return sleepDto;
    }
}
