package ru.practicum.element.model;

import com.fasterxml.jackson.core.type.TypeReference;
import lombok.Getter;
import org.apache.commons.lang3.NotImplementedException;

import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ElementProjectionMapper<E, P> {
    private static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern(DATE_TIME_FORMAT);
    @Getter
    private final Class<P> projectionClass;
    @Getter
    private final TypeReference<List<P>> projectionListType;

    protected ElementProjectionMapper(Class<P> projectionClass, TypeReference<List<P>> projectionListType) {
        this.projectionClass = projectionClass;
        this.projectionListType = projectionListType;
    }

    protected String getNullIfBlank(String string) {
        return string == null || string.isBlank() ? null : string;
    }

    public abstract P toProjection(E element);

    public List<P> toProjectionList(List<E> elementList) {
        return elementList == null || elementList.size() == 0 ? Collections.emptyList() : elementList.stream()
                .map(this::toProjection)
                .collect(Collectors.toList());
    }

    public E toElement(E element, P elementProjection) {
        throw new NotImplementedException();
    }
}
