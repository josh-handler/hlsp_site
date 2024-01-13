package com.hlsp.hlsp_site.model;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class ExerciseSession {
    
    @Id
    @GeneratedValue
    private UUID sessionGuid;

    
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "siteUser_Id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private SiteUser siteUser;

    private Date date;

    @OneToMany(mappedBy = "session")
    private List<ExerciseSet> sets;

    public ExerciseSession(UUID sessionGuid, SiteUser siteUser, Date date, List<ExerciseSet> sets) {
        this.sessionGuid = sessionGuid;
        this.siteUser = siteUser;
        this.date = date;
        this.sets = sets;
    }

    public ExerciseSession() {
    }

    public UUID getSessionGuid() {
        return sessionGuid;
    }

    public void setSessionGuid(UUID sessionGuid) {
        this.sessionGuid = sessionGuid;
    }

    public SiteUser getSiteUser() {
        return siteUser;
    }

    public void setSiteUser(SiteUser siteUser) {
        this.siteUser = siteUser;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public List<ExerciseSet> getSets() {
        return sets;
    }

    public void setSets(List<ExerciseSet> sets) {
        this.sets = sets;
    }
}
