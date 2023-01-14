package ru.practicum.ewm.model.event;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.element.model.Element;
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
import java.util.Objects;

@Entity
@ToString
@Table(name = "events")
@Getter
@Setter
public class Event extends Element {
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
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Event event = (Event) o;
        return Objects.equals(this.annotation, event.annotation) &&
                Objects.equals(this.category, event.category) &&
                Objects.equals(this.createdOn, event.createdOn) &&
                Objects.equals(this.description, event.description) &&
                Objects.equals(this.eventDate, event.eventDate) &&
                Objects.equals(this.id, event.id) &&
                Objects.equals(this.initiator, event.initiator) &&
                Objects.equals(this.location, event.location) &&
                Objects.equals(this.paid, event.paid) &&
                Objects.equals(this.participantLimit, event.participantLimit) &&
                Objects.equals(this.publishedOn, event.publishedOn) &&
                Objects.equals(this.requestModeration, event.requestModeration) &&
                Objects.equals(this.state, event.state) &&
                Objects.equals(this.title, event.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(annotation, category, createdOn, description, eventDate, id, initiator, location, paid,
                participantLimit, publishedOn, requestModeration, state, title);
    }
}

