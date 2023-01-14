package ru.practicum.stats.client;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.stats.model.EndpointHit;
import ru.practicum.stats.model.ViewStats;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class StatsClient {
    private final RestTemplate rest;
    private final HttpHeaders headers;
    private final ParameterizedTypeReference<List<ViewStats>> viewStatsListType;

    public StatsClient(RestTemplateBuilder builder, String serverUrl) {
        this.rest = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl))
                .requestFactory(HttpComponentsClientHttpRequestFactory::new)
                .build();
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
        viewStatsListType = new ParameterizedTypeReference<>() {
        };
    }

    public void hit(EndpointHit endpointHit) {
        rest.exchange("/hit", HttpMethod.POST, new HttpEntity<>(endpointHit, headers), Void.class);
    }

    public List<ViewStats> getStats(String start, String end, Collection<String> uris, Boolean unique) {
        Map<String, Object> parameters = new HashMap<>();
        Optional.ofNullable(start).ifPresent(p -> parameters.put("start", p));
        Optional.ofNullable(end).ifPresent(p -> parameters.put("end", p));
        Optional.ofNullable(unique).ifPresent(p -> parameters.put("unique", p));

        if (uris != null && !uris.isEmpty()) {
            parameters.put("uris", String.join(",", uris));
        }

        return rest.exchange("/stats?start={start}&end={end}&unique={unique}&uris={uris}", HttpMethod.GET,
                        new HttpEntity<>(null, headers), viewStatsListType, parameters)
                .getBody();
    }
}
