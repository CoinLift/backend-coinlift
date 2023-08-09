package com.coinlift.backend.dtos.notifications;

import java.util.UUID;

public record NotificationDto(
        UUID userToNotify,

        String userWhoFiredEvent,

        EventDto eventDto
) {
}
