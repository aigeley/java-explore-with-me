package ru.practicum.ewm.repository.http;

import ru.practicum.ewm.service.projection.EventWithRequests;
import ru.practicum.ewm.service.projection.EventWithViews;

import java.util.List;

public interface EventWithViewsHttpRepository {
    void hit(String uri, String ip);

    List<EventWithViews> getAll(List<EventWithRequests> eventWithRequestsList);

    EventWithViews get(EventWithRequests eventWithRequests);
}
