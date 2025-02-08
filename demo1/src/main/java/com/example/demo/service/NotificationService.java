package com.example.demo.service;

import com.example.demo.model.Notification;
import com.example.demo.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    private final NotificationRepository notificationRepository;

    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification saveNotification(Notification notification) {
        return notificationRepository.save(notification);
    }

    public Optional<Notification> findNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    public List<Notification> findAllNotifications() {
        return notificationRepository.findAll();
    }

    public void deleteNotificationById(Long id) {
        notificationRepository.deleteById(id);
    }
}
