package com.company.lyskraft.repository;

import com.company.lyskraft.entity.CallbackRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CallbackRequestRepository extends JpaRepository<CallbackRequest, Long> {

}