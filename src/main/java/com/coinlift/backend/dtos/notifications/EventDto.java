package com.coinlift.backend.dtos.notifications;

import com.coinlift.backend.entities.notification.EventType;

public record EventDto(
        EventType type,

        String text
) {
}
