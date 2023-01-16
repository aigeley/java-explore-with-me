package ru.practicum.stats.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.ToString;
import lombok.Value;

import java.util.Objects;

/**
 * ViewStats
 */
@Value
@ToString
public class ViewStats {
    /**
     * Название сервиса
     */
    @Schema(name = "app", description = "Название сервиса", requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("app")
    String app;

    /**
     * URI сервиса
     */
    @Schema(name = "uri", description = "URI сервиса", requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("uri")
    String uri;

    /**
     * Количество просмотров
     */
    @Schema(name = "hits", description = "Количество просмотров", requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("hits")
    Long hits;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ViewStats viewStats = (ViewStats) o;
        return Objects.equals(this.app, viewStats.app) &&
                Objects.equals(this.uri, viewStats.uri) &&
                Objects.equals(this.hits, viewStats.hits);
    }

    @Override
    public int hashCode() {
        return Objects.hash(app, uri, hits);
    }
}

