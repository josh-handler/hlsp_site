package com.hlsp.hlsp_site.model;

import java.util.List;
import java.util.UUID;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Exercise {
    
    @Id
    @GeneratedValue
    private UUID exerciseGuid;

    public enum Type{AEROBIC, STRENGTH, STRETCH, BALANCE, MIXED}

    String name;
    
    @Enumerated(EnumType.STRING)
    private Type type;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "siteUser_Id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private SiteUser siteUser;

    @OneToMany(mappedBy = "exercise")
    private List<ExerciseSet> sets;

    public Exercise(UUID exerciseGuid, String name, Type type, SiteUser siteUser, List<ExerciseSet> sets) {
        this.exerciseGuid = exerciseGuid;
        this.name = name;
        this.type = type;
        this.siteUser = siteUser;
        this.sets = sets;
    }

    public Exercise() {
    }

    public UUID getExerciseGuid() {
        return exerciseGuid;
    }

    public void setExerciseGuid(UUID exerciseGuid) {
        this.exerciseGuid = exerciseGuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public SiteUser getSiteUser() {
        return siteUser;
    }

    public void setSiteUser(SiteUser siteUser) {
        this.siteUser = siteUser;
    }

    public List<ExerciseSet> getSets() {
        return sets;
    }

    public void setSets(List<ExerciseSet> sets) {
        this.sets = sets;
    }

}
