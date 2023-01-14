package ru.practicum.stats.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.practicum.element.model.Element;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@Entity
@ToString
@Table(name = "endpoint_hits")
@Getter
@Setter
public class EndpointHitEntity extends Element {

    @Id
    @GeneratedValue(generator = "endpoint_hit_seq")
    @SequenceGenerator(name = "endpoint_hit_seq", sequenceName = "endpoint_hit_seq", allocationSize = 1)
    @Column(name = "endpoint_hit_id", nullable = false)
    private Long id;

    @Column(name = "app", nullable = false)
    private String app;

    @Column(name = "uri", length = 512, nullable = false)
    private String uri;

    @Column(name = "ip", nullable = false)
    private String ip;

    @Column(name = "endpoint_hit_timestamp", nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EndpointHitEntity endpointHit = (EndpointHitEntity) o;
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

