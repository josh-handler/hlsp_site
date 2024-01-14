package com.hlsp.hlsp_site.model;

public class PortionDTO {
    private String food;

    private Integer portionsEaten;

    public PortionDTO(String food, Integer portionsEaten) {
        this.food = food;
        this.portionsEaten = portionsEaten;
    }

    public PortionDTO() {
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
