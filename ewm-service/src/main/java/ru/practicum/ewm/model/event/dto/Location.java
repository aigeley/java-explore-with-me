package ru.practicum.ewm.model.event.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.media.Schema.RequiredMode;
import lombok.ToString;
import lombok.Value;

import java.util.Objects;

/**
 * Широта и долгота места проведения события
 */
@Schema(name = "Location", description = "Широта и долгота места проведения события")
@Value
@ToString
public class Location {
    /**
     * Широта
     */
    @Schema(name = "lat", example = "55.754167", description = "Широта", requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("lat")
    Float lat;

    /**
     * Долгота
     */
    @Schema(name = "lon", example = "37.62", description = "Долгота", requiredMode = RequiredMode.NOT_REQUIRED)
    @JsonProperty("lon")
    Float lon;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Location location = (Location) o;
        return Objects.equals(this.lat, location.lat) &&
                Objects.equals(this.lon, location.lon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lon);
    }
}

