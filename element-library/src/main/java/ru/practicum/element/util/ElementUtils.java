package ru.practicum.element.util;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import ru.practicum.element.exception.element.ElementNotFoundException;
import ru.practicum.element.model.Element;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

@AllArgsConstructor
public abstract class ElementUtils<E extends Element> {
    protected final String elementName;
    protected final JpaRepository<E, Long> elementRepository;

    public E getAndCheckElement(Long id) {
        return elementRepository.findById(id)
                .orElseThrow(() -> new ElementNotFoundException(elementName, id));
    }

    public Collection<E> getAndCheckElementCollection(Collection<Long> ids) {
        Map<Long, E> elementsMap = elementRepository.findAllById(ids)
                .stream()
                .collect(Collectors.toMap(Element::getId, element -> element));

        for (Long id : ids) {
            if (elementsMap.get(id) == null) {
                throw new ElementNotFoundException(elementName, id);
            }
        }

        return elementsMap.values();
    }
}
