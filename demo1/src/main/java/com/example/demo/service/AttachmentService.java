package com.example.demo.service;

import com.example.demo.model.Attachment;
import com.example.demo.repository.AttachmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttachmentService {

    private final AttachmentRepository attachmentRepository;

    @Autowired
    public AttachmentService(AttachmentRepository attachmentRepository) {
        this.attachmentRepository = attachmentRepository;
    }

    public Attachment saveAttachment(Attachment attachment) {
        return attachmentRepository.save(attachment);
    }

    public Optional<Attachment> findAttachmentById(Long id) {
        return attachmentRepository.findById(id);
    }

    public List<Attachment> findAllAttachments() {
        return attachmentRepository.findAll();
    }

    public void deleteAttachmentById(Long id) {
        attachmentRepository.deleteById(id);
    }
}
