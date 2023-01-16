package ru.practicum.stats.service;

import ru.practicum.stats.model.ViewStats;

import java.util.List;

public interface StatsService {
    List<ViewStats> getAll(String start, String end, List<String> uris, Boolean unique);
}
