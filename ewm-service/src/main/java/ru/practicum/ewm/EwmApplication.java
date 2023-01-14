package ru.practicum.ewm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"ru.practicum.ewm", "ru.practicum.ewm.api", "ru.practicum.ewm.config",
        "ru.practicum.stats.model", "ru.practicum.element.exception.element"})
public class EwmApplication {
    public static final String APPLICATION_NAME = "ewm-main-service";

    public static void main(String[] args) {
        SpringApplication.run(EwmApplication.class, args);
    }
}