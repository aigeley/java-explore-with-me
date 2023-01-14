package ru.practicum.ewm.model.participation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.element.model.Element;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.user.User;

import javax.persistence.Column;
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
@Table(name = "participation_requests")
@Getter
@Setter
public class ParticipationRequest extends Element {
    public static final String ELEMENT_NAME = "Заявка на участие в событии";

    @Column(name = "created", nullable = false)
    private LocalDateTime created = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

    @ManyToOne
    @JoinColumn(name = "event_id", referencedColumnName = "event_id", nullable = false)
    private Event event;

    @Id
    @GeneratedValue(generator = "participation_request_seq")
    @SequenceGenerator(name = "participation_request_seq", sequenceName = "participation_request_seq", allocationSize = 1)
    @Column(name = "participation_request_id", nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false)
    private User requester;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private StatusEnum status = StatusEnum.PENDING;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ParticipationRequest participationRequest = (ParticipationRequest) o;
        return Objects.equals(this.created, participationRequest.created) &&
                Objects.equals(this.event, participationRequest.event) &&
                Objects.equals(this.id, participationRequest.id) &&
                Objects.equals(this.requester, participationRequest.requester) &&
                Objects.equals(this.status, participationRequest.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(created, event, id, requester, status);
    }
}

