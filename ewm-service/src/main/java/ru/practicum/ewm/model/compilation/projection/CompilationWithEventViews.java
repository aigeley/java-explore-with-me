package ru.practicum.ewm.model.compilation.projection;

import lombok.ToString;
import lombok.Value;
import ru.practicum.element.model.Element;
import ru.practicum.ewm.model.compilation.Compilation;
import ru.practicum.ewm.model.event.projection.EventWithViews;

import java.util.Objects;
import java.util.Set;

@Value
@ToString
public class CompilationWithEventViews extends Element {
    Compilation compilation;
    Set<EventWithViews> eventsWithViews;

    @Override
    public Long getId() {
        return compilation.getId();
    }

    @Override
    public void setId(Long id) {
        compilation.setId(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CompilationWithEventViews)) {
            return false;
        }
        CompilationWithEventViews that = (CompilationWithEventViews) o;
        return compilation.equals(that.compilation) && eventsWithViews.equals(that.eventsWithViews);
    }

    @Override
    public int hashCode() {
        return Objects.hash(compilation, eventsWithViews);
    }
}

