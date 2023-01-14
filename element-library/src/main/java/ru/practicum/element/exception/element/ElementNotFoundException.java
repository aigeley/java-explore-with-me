package ru.practicum.element.exception.element;

public class ElementNotFoundException extends RuntimeException {
    public ElementNotFoundException(String elementName, Long id) {
        super(String.format("%s с id = %d не существует", elementName, id));
    }
}
