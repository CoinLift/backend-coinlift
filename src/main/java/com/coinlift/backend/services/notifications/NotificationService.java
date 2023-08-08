package com.coinlift.backend.services.notifications;

import com.coinlift.backend.entities.notification.EventType;

import java.util.UUID;

public interface NotificationService {

    void notifyUser(String userWhoFiredEvent, UUID userToNotify, EventType eventType);

}
