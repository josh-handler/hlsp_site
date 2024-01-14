package com.hlsp.hlsp_site.model;

import java.util.ArrayList;
import java.util.List;

public class MealDTO{

    private List<PortionDTO> eatenPortions;

    private String mealDate;

    private String mealType;


    public MealDTO(String mealDate, String mealType, PortionDTO portion){
        this.mealDate = mealDate;
        this.mealType = mealType;
        eatenPortions = new ArrayList<>();
        eatenPortions.add(portion);
    }

    public MealDTO(String mealDate, String mealType){
        this.mealDate = mealDate;
        this.mealType = mealType;
    }

    public MealDTO() {
        this.eatenPortions = new ArrayList<>();
    }

    public MealDTO(List<PortionDTO> eatenPortions, String mealDate, String mealType) {
        this.eatenPortions = eatenPortions;
        this.mealDate = mealDate;
        this.mealType = mealType;
    }

    public List<PortionDTO> getEatenPortions() {
        return eatenPortions;
    }

    public void addEatenPortion(PortionDTO eatenPortion){
        this.eatenPortions.add(eatenPortion);
    }

    public void setEatenPortions(List<PortionDTO> eatenPortions) {
        this.eatenPortions = eatenPortions;
    }

    public String getMealDate() {
        return mealDate;
    }

    public void setMealDate(String mealDate) {
        this.mealDate = mealDate;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    //    //Note: this class has a natural ordering that is inconsistent with equals.
    //    @Override
    //    public int compareTo(Meal otherMeal) {
    //        return this.mealDate.compareTo(otherMeal.getMealDate());
    //    }
       
}