package com.coinlift.backend.entities.notification;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private UUID userToNotify;

    private String userWhoFiredEvent;

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "id", foreignKey = @ForeignKey(name = "fk_event_notification"))
    private Event event;

    private boolean seenByUser;

    public Notification(UUID id, UUID userToNotify, String userWhoFiredEvent, Event event, boolean seenByUser) {
        this.id = id;
        this.userToNotify = userToNotify;
        this.userWhoFiredEvent = userWhoFiredEvent;
        this.event = event;
        this.seenByUser = seenByUser;
    }

    public Notification(UUID userToNotify, String userWhoFiredEvent, Event event, boolean seenByUser) {
        this.userToNotify = userToNotify;
        this.userWhoFiredEvent = userWhoFiredEvent;
        this.event = event;
        this.seenByUser = seenByUser;
    }

    public Notification() {
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getUserToNotify() {
        return userToNotify;
    }

    public void setUserToNotify(UUID userToNotify) {
        this.userToNotify = userToNotify;
    }

    public String getUserWhoFiredEvent() {
        return userWhoFiredEvent;
    }

    public void setUserWhoFiredEvent(String userWhoFiredEvent) {
        this.userWhoFiredEvent = userWhoFiredEvent;
    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public boolean isSeenByUser() {
        return seenByUser;
    }

    public void setSeenByUser(boolean seenByUser) {
        this.seenByUser = seenByUser;
    }
}

