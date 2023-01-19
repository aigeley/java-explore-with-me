package ru.practicum.ewm.repository.util;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import ru.practicum.ewm.model.mark.Mark;

import javax.annotation.processing.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;

/**
 * QMark is a Querydsl query type for Mark
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMark extends EntityPathBase<Mark> {

    private static final long serialVersionUID = -1038824161L;

    public static final QMark mark = new QMark("mark");

    public final DateTimePath<java.time.LocalDateTime> created =
            createDateTime("created", java.time.LocalDateTime.class);

    public final NumberPath<Long> eventId = createNumber("eventId", Long.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Integer> markValue = createNumber("markValue", Integer.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QMark(String variable) {
        super(Mark.class, forVariable(variable));
    }

    public QMark(Path<? extends Mark> path) {
        super(path.getType(), path.getMetadata());
    }

    public QMark(PathMetadata metadata) {
        super(Mark.class, metadata);
    }

}

