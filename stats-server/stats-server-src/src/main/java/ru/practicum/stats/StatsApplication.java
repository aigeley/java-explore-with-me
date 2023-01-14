package ru.practicum.stats;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"ru.practicum.stats", "ru.practicum.stats.api", "ru.practicum.stats.config",
        "ru.practicum.element.exception.element"})
public class StatsApplication {
    public static void main(String[] args) {
        SpringApplication.run(StatsApplication.class, args);
    }
}