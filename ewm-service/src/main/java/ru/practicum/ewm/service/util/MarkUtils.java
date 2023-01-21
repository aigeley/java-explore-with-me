package ru.practicum.ewm.service.util;

import org.springframework.stereotype.Component;
import ru.practicum.element.util.ElementUtils;
import ru.practicum.ewm.model.mark.Mark;
import ru.practicum.ewm.repository.MarkRepository;

@Component
public class MarkUtils extends ElementUtils<Mark> {
    public MarkUtils(MarkRepository markRepository) {
        super(Mark.ELEMENT_NAME, markRepository);
    }
}
