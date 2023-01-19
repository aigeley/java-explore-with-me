package ru.practicum.ewm.repository.util;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.EnumPath;
import com.querydsl.core.types.dsl.NumberPath;
import com.querydsl.core.types.dsl.PathInits;
import com.querydsl.core.types.dsl.StringPath;
import ru.practicum.ewm.model.event.Event;
import ru.practicum.ewm.model.event.StateEnum;

import javax.annotation.processing.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

/**
 * QEvent is a Querydsl query type for Event
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QEvent extends EntityPathBase<Event> {

    private static final long serialVersionUID = -475675241L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QEvent event = new QEvent("event");

    public final StringPath annotation = createString("annotation");

    public final QCategory category;

    public final DateTimePath<java.time.LocalDateTime> createdOn =
            createDateTime("createdOn", java.time.LocalDateTime.class);

    public final StringPath description = createString("description");

    public final DateTimePath<java.time.LocalDateTime> eventDate =
            createDateTime("eventDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QUser initiator;

    public final QEventLocation location;

    public final BooleanPath paid = createBoolean("paid");

    public final NumberPath<Integer> participantLimit = createNumber("participantLimit", Integer.class);

    public final DateTimePath<java.time.LocalDateTime> publishedOn =
            createDateTime("publishedOn", java.time.LocalDateTime.class);

    public final BooleanPath requestModeration = createBoolean("requestModeration");

    public final EnumPath<StateEnum> state = createEnum("state", StateEnum.class);

    public final StringPath title = createString("title");

    public QEvent(String variable) {
        this(Event.class, forVariable(variable), INITS);
    }

    public QEvent(Path<? extends Event> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QEvent(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QEvent(PathMetadata metadata, PathInits inits) {
        this(Event.class, metadata, inits);
    }

    public QEvent(Class<? extends Event> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.category = inits.isInitialized("category") ? new QCategory(forProperty("category")) : null;
        this.initiator = inits.isInitialized("initiator") ? new QUser(forProperty("initiator")) : null;
        this.location = inits.isInitialized("location") ? new QEventLocation(forProperty("location")) : null;
    }

}

