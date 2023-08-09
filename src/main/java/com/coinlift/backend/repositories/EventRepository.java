package com.coinlift.backend.repositories;

import com.coinlift.backend.entities.notification.Event;
import com.coinlift.backend.entities.notification.EventType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EventRepository extends JpaRepository<Event, Integer> {
    @Query("""
            SELECT e FROM Event e WHERE e.type = :type
            """)
    Event findEventByType(EventType type);
}
