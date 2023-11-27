package com.company.lyskraft.repository;

import com.company.lyskraft.entity.ChatBody;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatBodyRepository extends JpaRepository<ChatBody, Long> {
    Iterable<ChatBody> findAllByAttachmentUrl(String attachmentUrl);
}