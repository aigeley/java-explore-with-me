package ru.practicum.ewm.service.projection;

import lombok.ToString;
import lombok.Value;
import ru.practicum.element.model.Identifiable;
import ru.practicum.ewm.model.compilation.Compilation;

import java.util.Objects;
import java.util.Set;

@Value
@ToString
public class CompilationWithEventViews implements Identifiable {
    Compilation compilation;
    Set<EventWithViews> eventsWithViews;

    @Override
    public Long getId() {
        return compilation.getId();
    }

    public void setId(Long id) {
        compilation.setId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CompilationWithEventViews that = (CompilationWithEventViews) o;
        return Objects.equals(compilation, that.compilation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(compilation);
    }
}

