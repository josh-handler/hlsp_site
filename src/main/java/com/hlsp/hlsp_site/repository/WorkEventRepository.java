package com.hlsp.hlsp_site.repository;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.data.repository.CrudRepository;
import com.hlsp.hlsp_site.model.WorkEvent;
import jakarta.transaction.Transactional;

@Repository
public interface WorkEventRepository extends CrudRepository<WorkEvent, Integer> {

    List<WorkEvent> findBySiteUserUserId(int siteUser_Id);

    @Transactional
    void deleteBySiteUserUserId(int siteUser_Id);
}
