package ru.practicum.ewm.repository.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.stereotype.Component;
import ru.practicum.stats.client.StatsClient;

@Component
public class EwmStatsClient extends StatsClient {
    public EwmStatsClient(RestTemplateBuilder builder, @Value("${stats-server.url}") String serverUrl) {
        super(builder, serverUrl);
    }
}
