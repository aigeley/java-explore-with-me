package ru.practicum.element.api;

import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletRequest;

@AllArgsConstructor
public abstract class ElementApiController<T> {
    protected final T service;
    protected final HttpServletRequest request;
}
