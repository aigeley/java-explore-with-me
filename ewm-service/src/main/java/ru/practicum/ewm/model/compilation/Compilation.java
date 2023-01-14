package ru.practicum.ewm.model.compilation;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.element.model.Element;
import ru.practicum.ewm.model.event.Event;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Objects;
import java.util.Set;

@Entity
@ToString
@Table(name = "compilations")
@Getter
@Setter
public class Compilation extends Element {
    public static final String ELEMENT_NAME = "Подборка событий";

    @ManyToMany(fetch = FetchType.EAGER)
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
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Compilation compilationDto = (Compilation) o;
        return Objects.equals(this.events, compilationDto.events) &&
                Objects.equals(this.id, compilationDto.id) &&
                Objects.equals(this.pinned, compilationDto.pinned) &&
                Objects.equals(this.title, compilationDto.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(events, id, pinned, title);
    }
}

