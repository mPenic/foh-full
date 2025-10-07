package com.foh.re.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.foh.re.services.EventService;
import com.foh.re.vo.EventVO;

@Controller
public class EventController {

    private final EventService eventService;

    public EventController(EventService eventService) {
        this.eventService = eventService;
    }

    @MessageMapping("/event/create")
    @SendTo("/topic/event")
    public EventVO createEvent(EventVO eventVO) {
        return eventService.createEvent(eventVO, null);
    }

    @MessageMapping("/event/update")
    @SendTo("/topic/event")
    public EventVO updateEvent(Long eventId, EventVO eventVO) {
        return eventService.updateEvent(eventId, eventVO);
    }

    @MessageMapping("/event/delete")
    @SendTo("/topic/event")
    public String deleteEvent(Long eventId) {
        eventService.deleteEvent(eventId);
        return "Event deleted: " + eventId;
    }
}
