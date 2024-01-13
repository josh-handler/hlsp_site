package com.hlsp.hlsp_site.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.hlsp.hlsp_site.model.Portion;

import jakarta.transaction.Transactional;

@Repository
public interface PortionRepository extends CrudRepository<Portion, Integer>{
    
    List<Portion> findByMealMealGuid(UUID meal_Guid);

    @Transactional
    void deleteByMealMealGuid(UUID meal_Guid);

}
