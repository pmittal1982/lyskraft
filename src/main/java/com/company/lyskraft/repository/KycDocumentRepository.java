package com.company.lyskraft.repository;

import com.company.lyskraft.entity.KycDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KycDocumentRepository extends JpaRepository<KycDocument, Long> {
}