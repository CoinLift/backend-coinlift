package com.coinlift.backend.config;

import com.coinlift.backend.entities.user.MyUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messagingTemplate;

    // TODO: display count of notifications when connection is established
    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        String message = "Hello, client! The connection is established.";
        if (getUserIdOrNull() != null) {
            messagingTemplate.convertAndSendToUser(getUserIdOrNull().toString(), "/topic/hello", message);
        }
    }

    private UUID getUserIdOrNull() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof AnonymousAuthenticationToken || authentication == null) {
            return null;
        }
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        return userDetails.user().getId();
    }
}

