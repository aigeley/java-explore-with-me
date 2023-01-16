package ru.practicum.stats.repository.util;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.StringPath;
import ru.practicum.stats.model.EndpointHitEntity;

import javax.annotation.processing.Generated;
import java.time.LocalDateTime;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

/**
 * QEndpointHitEntity is a Querydsl query type for EndpointHitEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEndpointHitEntity extends EntityPathBase<EndpointHitEntity> {

    private static final long serialVersionUID = -1046109592L;

    public static final QEndpointHitEntity endpointHitEntity = new QEndpointHitEntity("endpointHitEntity");

    public final StringPath app = createString("app");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath ip = createString("ip");

    public final DateTimePath<LocalDateTime> timestamp = createDateTime("timestamp", LocalDateTime.class);

    public final StringPath uri = createString("uri");

    public QEndpointHitEntity(String variable) {
        super(EndpointHitEntity.class, forVariable(variable));
    }

    public QEndpointHitEntity(Path<? extends EndpointHitEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEndpointHitEntity(PathMetadata metadata) {
        super(EndpointHitEntity.class, metadata);
    }

}

