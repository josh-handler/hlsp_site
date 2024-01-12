package com.hlsp.hlsp_site.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.hlsp.hlsp_site.model.SiteUser;
import com.hlsp.hlsp_site.model.SleepEvent;

import jakarta.transaction.Transactional;

@Repository
public interface SleepEventRepository extends CrudRepository<SleepEvent, Integer> {

    List<SleepEvent> findBySiteUserUserId(int siteUser_Id);

    @Transactional
    void deleteBySiteUserUserId(int siteUser_Id);

}