package com.coinlift.backend.repositories;

import com.coinlift.backend.entities.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
}
