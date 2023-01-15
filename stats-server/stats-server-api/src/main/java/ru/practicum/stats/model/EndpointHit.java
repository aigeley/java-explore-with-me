package ru.practicum.stats.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.ToString;
import lombok.Value;

import java.util.Objects;

/**
 * EndpointHit
 */
@Value
@ToString
public class EndpointHit {
    /**
     * Идентификатор записи
     */
    @Schema(name = "id", description = "Идентификатор записи", requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("id")
    Long id;

    /**
     * Идентификатор сервиса для которого записывается информация
     */
    @Schema(name = "app", example = "ewm-main-service",
            description = "Идентификатор сервиса для которого записывается информация",
            requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("app")
    String app;

    /**
     * URI для которого был осуществлен запрос
     */
    @Schema(name = "uri", example = "/events", description = "URI для которого был осуществлен запрос ",
            requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("uri")
    String uri;

    /**
     * IP-адрес пользователя, осуществившего запрос
     */
    @Schema(name = "ip", description = "IP-адрес пользователя, осуществившего запрос",
            requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("ip")
    String ip;

    /**
     * Дата и время, когда был совершен запрос к эндпоинту (в формате \"yyyy-MM-dd HH:mm:ss\")
     */
    @Schema(name = "timestamp", example = "2023-01-09 11:00:23",
            description = "Дата и время, когда был совершен запрос к эндпоинту (в формате \"yyyy-MM-dd HH:mm:ss\")",
            requiredMode = RequiredMode.NOT_REQUIRED
    )
    @JsonProperty("timestamp")
    String timestamp;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EndpointHit endpointHit = (EndpointHit) o;
        return Objects.equals(this.id, endpointHit.id) &&
                Objects.equals(this.app, endpointHit.app) &&
                Objects.equals(this.uri, endpointHit.uri) &&
                Objects.equals(this.ip, endpointHit.ip) &&
                Objects.equals(this.timestamp, endpointHit.timestamp);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, app, uri, ip, timestamp);
    }
}

