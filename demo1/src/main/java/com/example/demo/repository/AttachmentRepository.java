package com.example.demo.repository;

import com.example.demo.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment, Long> {
    // İhtiyaç halinde özel sorgular eklenebilir
}