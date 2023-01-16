package ru.practicum.stats.model;

import lombok.Getter;
import lombok.Setter;
import ru.practicum.element.model.Identifiable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "endpoint_hits")
@Getter
@Setter
public class EndpointHitEntity implements Identifiable {

    @Id
    @GeneratedValue(generator = "endpoint_hit_seq")
    @SequenceGenerator(name = "endpoint_hit_seq", sequenceName = "endpoint_hit_seq", allocationSize = 1)
    @Column(name = "endpoint_hit_id", nullable = false)
    private Long id;

    @Column(name = "app")
    private String app;

    @Column(name = "uri", length = 512)
    private String uri;

    @Column(name = "ip")
    private String ip;

    @Column(name = "endpoint_hit_timestamp", nullable = false)
    private LocalDateTime timestamp = LocalDateTime.now().truncatedTo(ChronoUnit.MICROS);

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        //сущности с пустым id не равны
        if (id == null || o == null || getClass() != o.getClass()) {
            return false;
        }

        EndpointHitEntity other = (EndpointHitEntity) o;
        return id.equals(other.getId());
    }

    @Override
    public int hashCode() {
        //константное значение, что бы hash не изменялся после присвоения id
        return 13;
    }

    @Override
    public String toString() {
        return "EndpointHitEntity{" +
                "id=" + id +
                ", app='" + app + '\'' +
                ", uri='" + uri + '\'' +
                ", ip='" + ip + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}

