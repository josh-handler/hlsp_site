package com.hlsp.hlsp_site.model;

import java.util.UUID;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Portion{

    @Id
    @GeneratedValue
    private UUID portionGuid;

    @ManyToOne
    @JoinColumn(name="mealGuid", nullable=false)
    private Meal meal;

    private String food;

    private Integer portionsEaten;

    public Portion(UUID portionGuid, Meal meal, String food, Integer portionsEaten) {
        this.portionGuid = portionGuid;
        this.meal = meal;
        this.food = food;
        this.portionsEaten = portionsEaten;
    }

    public Portion() {
    }

    public UUID getPortionGuid() {
        return portionGuid;
    }

    public void setPortionGuid(UUID portionGuid) {
        this.portionGuid = portionGuid;
    }

    public Meal getMeal() {
        return meal;
    }

    public void setMeal(Meal meal) {
        this.meal = meal;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public Integer getPortionsEaten() {
        return portionsEaten;
    }

    public void setPortionsEaten(Integer portionsEaten) {
        this.portionsEaten = portionsEaten;
    }

}