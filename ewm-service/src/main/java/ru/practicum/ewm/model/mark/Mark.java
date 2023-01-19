package ru.practicum.ewm.model.mark;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.element.model.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "marks")
@Getter
@Setter
public class Mark implements Identifiable {
    public static final String ELEMENT_NAME = "Оценка";

    @Id
    @GeneratedValue(generator = "mark_seq")
    @SequenceGenerator(name = "mark_seq", sequenceName = "mark_seq", allocationSize = 1)
    @Column(name = "mark_id", nullable = false)
    private Long id;

    @Column(name = "created", nullable = false)
    private LocalDateTime created = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "mark_value", nullable = false)
    private Integer markValue;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        //сущности с пустым id не равны
        if (id == null || o == null || getClass() != o.getClass()) {
            return false;
        }

        Mark other = (Mark) o;
        return id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        //константное значение, что бы hash не изменялся после присвоения id
        return 13;
    }

    @Override
    public String toString() {
        return "Mark{" +
                "id=" + id +
                ", created=" + created +
                ", eventId=" + eventId +
                ", userId=" + userId +
                ", markValue=" + markValue +
                '}';
    }
}

