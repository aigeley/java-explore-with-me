package ru.practicum.ewm.model.compilation;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.element.model.Identifiable;
import ru.practicum.ewm.model.event.Event;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "compilations")
@Getter
@Setter
public class Compilation implements Identifiable {
    public static final String ELEMENT_NAME = "Подборка событий";

    @ManyToMany
    @JoinTable(
            name = "events_compilations",
            joinColumns = @JoinColumn(name = "compilation_id"),
            inverseJoinColumns = @JoinColumn(name = "event_id")
    )
    private Set<Event> events;

    @Id
    @GeneratedValue(generator = "compilation_seq")
    @SequenceGenerator(name = "compilation_seq", sequenceName = "compilation_seq", allocationSize = 1)
    @Column(name = "compilation_id", nullable = false)
    private Long id;

    @Column(name = "pinned", nullable = false)
    private Boolean pinned = false;

    @Column(name = "title", nullable = false)
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

        Compilation other = (Compilation) o;
        return id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        //константное значение, что бы hash не изменялся после присвоения id
        return 13;
    }

    @Override
    public String toString() {
        return "Compilation{" +
                "id=" + id +
                ", pinned=" + pinned +
                ", title='" + title + '\'' +
                '}';
    }
}

