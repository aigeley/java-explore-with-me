package ru.practicum.ewm.repository.util;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.BeanPath;
import com.querydsl.core.types.dsl.NumberPath;
import ru.practicum.ewm.model.event.EventLocation;

import javax.annotation.processing.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

/**
 * QEventLocation is a Querydsl query type for EventLocation
 */
@Generated("com.querydsl.codegen.DefaultEmbeddableSerializer")
public class QEventLocation extends BeanPath<EventLocation> {

    private static final long serialVersionUID = 2087566027L;

    public static final QEventLocation eventLocation = new QEventLocation("eventLocation");

    public final NumberPath<Float> lat = createNumber("lat", Float.class);

    public final NumberPath<Float> lon = createNumber("lon", Float.class);

    public QEventLocation(String variable) {
        super(EventLocation.class, forVariable(variable));
    }

    public QEventLocation(Path<? extends EventLocation> path) {
        super(path.getType(), path.getMetadata());
    }

    public QEventLocation(PathMetadata metadata) {
        super(EventLocation.class, metadata);
    }

}

