package com.hlsp.hlsp_site.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hlsp.hlsp_site.model.Meal;

import jakarta.transaction.Transactional;

@Repository
public interface MealRepository extends CrudRepository<Meal, Integer>{

    List<Meal> findBySiteUserUserId(int siteUser_Id);

    @Transactional
    void deleteBySiteUserUserId(int siteUser_Id);
    
}
