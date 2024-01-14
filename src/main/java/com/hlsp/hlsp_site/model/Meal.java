package com.hlsp.hlsp_site.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
public class Meal{

    @Id
    @GeneratedValue
    private UUID mealGuid;

    @OneToMany(mappedBy = "meal")
    private List<Portion> eatenPortions;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "siteUser_Id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private SiteUser siteUser;

    private Date mealDate;

    private String mealType;

    public Meal(MealDTO dto, SiteUser user) throws ParseException{
        DateFormat eventDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd'T'kk:mm");
        this.mealDate = eventDateTimeFormat.parse(dto.getMealDate());
        this.mealType = dto.getMealType();
        this.eatenPortions = new ArrayList<>();
        for (PortionDTO portionDto : dto.getEatenPortions()) {
            eatenPortions.add(new Portion(portionDto));
        }
        this.siteUser = user;
    }

    public Meal(UUID mealGuid, List<Portion> eatenPortions, Date mealDate, String mealType) {
        this.mealGuid = mealGuid;
        this.eatenPortions = eatenPortions;
        this.mealDate = mealDate;
        this.mealType = mealType;
    }

    public Meal() {
    }

    public UUID getMealGuid() {
        return mealGuid;
    }

    public void setMealGuid(UUID mealGuid) {
        this.mealGuid = mealGuid;
    }

    public List<Portion> getEatenPortions() {
        return eatenPortions;
    }

    public void setEatenPortions(List<Portion> eatenPortions) {
        this.eatenPortions = eatenPortions;
    }

    public Date getMealDate() {
        return mealDate;
    }

    public void setMealDate(Date mealDate) {
        this.mealDate = mealDate;
    }

    public String getMealType() {
        return mealType;
    }

    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public MealDTO createDto(){
        return createDTO(this.eatenPortions);
    }

    public MealDTO createDTO(List<Portion> portionList){
        DateFormat eventDateTimeFormat = new SimpleDateFormat("dd/MM/yyyy kk:mm");
        String eventDateForDto = eventDateTimeFormat.format(mealDate);
        List<PortionDTO> portionDTOList = new ArrayList<PortionDTO>();
        for (Portion portion : portionList) {
            portionDTOList.add(portion.createDto());
        }
        return new MealDTO(portionDTOList, eventDateForDto, this.mealType);
    }
}
