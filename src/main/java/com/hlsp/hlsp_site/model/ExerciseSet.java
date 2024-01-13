package com.hlsp.hlsp_site.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ExerciseSet {

    @Id
    @GeneratedValue
    private UUID setGuid;

    @ManyToOne
    @JoinColumn(name="sessionGuid", nullable=false)
    private ExerciseSession session;

    @ManyToOne
    @JoinColumn(name="exerciseGuid", nullable=false)
    Exercise exercise;

    private Double weightOrDistance;

    private Double time;

    private Integer reps;

    public ExerciseSet(UUID setGuid, ExerciseSession session, Exercise exercise, Double weightOrDistance, Double time,
            Integer reps) {
        this.setGuid = setGuid;
        this.session = session;
        this.exercise = exercise;
        this.weightOrDistance = weightOrDistance;
        this.time = time;
        this.reps = reps;
    }

    public ExerciseSet() {
    }

    public UUID getSetGuid() {
        return setGuid;
    }

    public void setSetGuid(UUID setGuid) {
        this.setGuid = setGuid;
    }

    public ExerciseSession getSession() {
        return session;
    }

    public void setSession(ExerciseSession session) {
        this.session = session;
    }

    public Exercise getExercise() {
        return exercise;
    }

    public void setExercise(Exercise exercise) {
        this.exercise = exercise;
    }

    public Double getWeightOrDistance() {
        return weightOrDistance;
    }

    public void setWeightOrDistance(Double weightOrDistance) {
        this.weightOrDistance = weightOrDistance;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    // private String goal;
}
