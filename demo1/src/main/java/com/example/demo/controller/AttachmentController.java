package com.example.demo.controller;

import com.example.demo.model.Attachment;
import com.example.demo.service.AttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/attachments")
public class AttachmentController {

    private final AttachmentService attachmentService;

    @Autowired
    public AttachmentController(AttachmentService attachmentService) {
        this.attachmentService = attachmentService;
    }

    @PostMapping
    public ResponseEntity<Attachment> createAttachment(@RequestBody Attachment attachment) {
        return ResponseEntity.ok(attachmentService.saveAttachment(attachment));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Attachment> getAttachmentById(@PathVariable Long id) {
        return attachmentService.findAttachmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Attachment> getAllAttachments() {
        return attachmentService.findAllAttachments();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Attachment> updateAttachment(@PathVariable Long id, @RequestBody Attachment updatedAttachment) {
        return attachmentService.findAttachmentById(id)
                .map(attachment -> {
                    attachment.setFileName(updatedAttachment.getFileName());
                    attachment.setFileType(updatedAttachment.getFileType());
                    attachment.setData(updatedAttachment.getData());
                    return ResponseEntity.ok(attachmentService.saveAttachment(attachment));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttachmentById(@PathVariable Long id) {
        attachmentService.deleteAttachmentById(id);
        return ResponseEntity.noContent().build();
    }
}
