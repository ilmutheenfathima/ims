package com.company.ims.entity;

import io.jmix.core.entity.annotation.JmixGeneratedValue;
import io.jmix.core.entity.annotation.JmixId;
import io.jmix.ui.component.calendar.SimpleCalendarEvent;

import java.time.LocalDateTime;
import java.util.UUID;

public class UnifiedCalendarEvent extends SimpleCalendarEvent<LocalDateTime> {
    @JmixGeneratedValue
    @JmixId
    private UUID id;

    public static UnifiedCalendarEvent getEvent(Classroom classroom, LocalDateTime startTime, LocalDateTime endTime) {
        UnifiedCalendarEvent event = new UnifiedCalendarEvent();
        event.setStart(startTime);
        event.setEnd(endTime);
        event.setCaption(classroom.getIntakeModule().getModuleName());
        event.setDescription(classroom.getLecturer().getFullName());
        event.setAllDay(false);
        return event;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}