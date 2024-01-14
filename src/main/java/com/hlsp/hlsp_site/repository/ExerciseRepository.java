package com.hlsp.hlsp_site.repository;

import org.springframework.stereotype.Repository;

import com.hlsp.hlsp_site.model.Exercise;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
@Repository
public interface ExerciseRepository extends CrudRepository<Exercise, Integer>{

}
    