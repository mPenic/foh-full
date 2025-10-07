package com.foh.re.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.foh.data.entities.usermgmt.CompanyDictionaryEntity;
import com.foh.data.entities.usermgmt.UserEntity;
import com.foh.data.repository.usermgmt.CompanyDictionaryRepository;
import com.foh.data.repository.usermgmt.UserRepository;
import com.foh.security.CurrUserDetails;
import com.foh.data.entities.re.EventEntity;
import com.foh.data.entities.re.EventStatusDictionaryEntity;
import com.foh.data.repository.re.EventRepository;
import com.foh.data.repository.re.EventStatusDictionaryRepository;
import com.foh.re.vo.EventVO;

@Service
public class EventService {
    private final EventRepository eventRepository;
    private final UserRepository userRepository;
    private final EventStatusDictionaryRepository eventStatusDictionaryRepository;
    private final CompanyDictionaryRepository companyDictionaryRepository;

    public EventService(EventRepository eventRepository,
                        UserRepository userRepository,
                        EventStatusDictionaryRepository eventStatusDictionaryRepository,
                        CompanyDictionaryRepository companyDictionaryRepository) {
        this.eventRepository = eventRepository;
        this.userRepository = userRepository;
        this.eventStatusDictionaryRepository = eventStatusDictionaryRepository;
        this.companyDictionaryRepository = companyDictionaryRepository;
    }

    public EventVO createEvent(EventVO eventVO, CurrUserDetails currentUser) {
        EventEntity eventEntity = new EventEntity();
        eventEntity.setEventName(eventVO.getEventName());
        eventEntity.setEventDate(eventVO.getEventDate());

        UserEntity user = userRepository.findById(currentUser.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found"));
        eventEntity.setCreatedBy(user);

        EventStatusDictionaryEntity defaultStatus = eventStatusDictionaryRepository.findById(1L)
                .orElseThrow(() -> new IllegalArgumentException("Default Event Status not found"));
        eventEntity.setEventStatus(defaultStatus);

        CompanyDictionaryEntity company = companyDictionaryRepository.findById(currentUser.getCompanyId())
                .orElseThrow(() -> new IllegalArgumentException("Company not found"));
        eventEntity.setCompany(company);

        return mapToVO(eventRepository.save(eventEntity));
    }

    public EventVO updateEvent(Long eventId, EventVO eventVO) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));
        event.setEventName(eventVO.getEventName());
        event.setEventDate(eventVO.getEventDate());
        return mapToVO(eventRepository.save(event));
    }

    public void deleteEvent(Long eventId) {
        if (!eventRepository.existsById(eventId)) {
            throw new IllegalArgumentException("Event not found");
        }
        eventRepository.deleteById(eventId);
    }

    public EventVO getEventById(Long eventId) {
        EventEntity event = eventRepository.findById(eventId)
                .orElseThrow(() -> new IllegalArgumentException("Event not found"));
        return mapToVO(event);
    }

    public List<EventVO> getAllEvents() {
        return eventRepository.findAll().stream()
                .map(this::mapToVO)
                .collect(Collectors.toList());
    }

    private EventVO mapToVO(EventEntity eventEntity) {
        String createdByUsername = eventEntity.getCreatedBy() != null ? eventEntity.getCreatedBy().getUsername() : "Unknown";
        String eventStatusName = eventEntity.getEventStatus() != null ? eventEntity.getEventStatus().getEventStatusName() : "Unknown";
        return new EventVO(
                eventEntity.getEventId(),
                eventEntity.getEventName(),
                eventEntity.getEventDate(),
                createdByUsername,
                eventStatusName
        );
    }
}
