package ru.practicum.ewm.model.event;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.element.model.Identifiable;
import ru.practicum.ewm.model.category.Category;
import ru.practicum.ewm.model.user.User;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "events")
@Getter
@Setter
public class Event implements Identifiable {
    public static final String ELEMENT_NAME = "Событие";

    @Column(name = "annotation", length = 2000, nullable = false)
    private String annotation;

    @ManyToOne
    @JoinColumn(name = "category_id", referencedColumnName = "category_id", nullable = false)
    private Category category;

    @Column(name = "created_on", nullable = false)
    private LocalDateTime createdOn = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

    @Column(name = "description", length = 7000, nullable = false)
    private String description;

    @Column(name = "event_date", nullable = false)
    private LocalDateTime eventDate;

    @Id
    @GeneratedValue(generator = "event_seq")
    @SequenceGenerator(name = "event_seq", sequenceName = "event_seq", allocationSize = 1)
    @Column(name = "event_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User initiator;

    @Embedded
    private EventLocation location;

    @Column(name = "paid", nullable = false)
    private Boolean paid = false;

    @Column(name = "participant_limit", nullable = false)
    private Integer participantLimit = 0;

    @Column(name = "published_on")
    private LocalDateTime publishedOn;

    @Column(name = "request_moderation", nullable = false)
    private Boolean requestModeration = true;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private StateEnum state = StateEnum.PENDING;

    @Column(name = "title", length = 120, nullable = false)
    private String title;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        //сущности с пустым id не равны
        if (id == null || o == null || getClass() != o.getClass()) {
            return false;
        }

        Event other = (Event) o;
        return id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        //константное значение, что бы hash не изменялся после присвоения id
        return 13;
    }

    @Override
    public String toString() {
        return "Event{" +
                "annotation='" + annotation + '\'' +
                ", category=" + category +
                ", createdOn=" + createdOn +
                ", description='" + description + '\'' +
                ", eventDate=" + eventDate +
                ", id=" + id +
                ", initiator=" + initiator +
                ", location=" + location +
                ", paid=" + paid +
                ", participantLimit=" + participantLimit +
                ", publishedOn=" + publishedOn +
                ", requestModeration=" + requestModeration +
                ", state=" + state +
                ", title='" + title + '\'' +
                '}';
    }
}

