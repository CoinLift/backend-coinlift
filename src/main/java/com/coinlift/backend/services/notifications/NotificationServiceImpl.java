package com.coinlift.backend.services.notifications;

import com.coinlift.backend.dtos.notifications.EventDto;
import com.coinlift.backend.dtos.notifications.NotificationDto;
import com.coinlift.backend.entities.notification.Event;
import com.coinlift.backend.entities.notification.EventType;
import com.coinlift.backend.entities.notification.Notification;
import com.coinlift.backend.repositories.EventRepository;
import com.coinlift.backend.repositories.NotificationRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final SimpMessagingTemplate messagingTemplate;

    private final EventRepository eventRepository;

    private final NotificationRepository notificationRepository;

    public NotificationServiceImpl(SimpMessagingTemplate messagingTemplate, EventRepository eventRepository, NotificationRepository notificationRepository) {
        this.messagingTemplate = messagingTemplate;
        this.eventRepository = eventRepository;
        this.notificationRepository = notificationRepository;
    }

    @Override
    public void notifyUser(String userWhoFiredEvent, UUID userToNotify, EventType eventType) {
        Event event = eventRepository.findEventByType(eventType);
        EventDto eventDto = new EventDto(eventType, event.getText());
        NotificationDto notificationDto = new NotificationDto(userToNotify, userWhoFiredEvent, eventDto);
        Notification notification = new Notification(userToNotify, userWhoFiredEvent, event, false);

        notificationRepository.save(notification);

        messagingTemplate.convertAndSendToUser(
                userToNotify.toString(),
                "/queue/notifications",
                notificationDto
        );
    }

}
