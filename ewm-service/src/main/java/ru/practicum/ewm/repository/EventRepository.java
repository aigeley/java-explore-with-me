package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.practicum.ewm.model.event.Event;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    /**
     * комментарии в файле \ewm-service\src\main\resources\getRecommendations.sql
     */
    @Query(value = "SELECT r.future_event_id\n" +
            "FROM (SELECT DISTINCT ON (e.category_id)\n" +
            "          e.category_id,\n" +
            "          e.user_id,\n" +
            "          ((1.0 + COUNT(DISTINCT likes.mark_id) - COUNT(DISTINCT dislikes.mark_id)) /\n" +
            "           (1.0 + COUNT(DISTINCT pr.participation_request_id))) *\n" +
            "          (COUNT(DISTINCT likes.mark_id))              AS rating,\n" +
            "          e1.event_id                                  AS future_event_id,\n" +
            "          e1.event_date,\n" +
            "          e1.participant_limit,\n" +
            "          COUNT(DISTINCT pr1.participation_request_id) AS future_requests\n" +
            "      FROM events e\n" +
            "               LEFT JOIN participation_requests pr\n" +
            "                         ON (\n" +
            "                                     pr.event_id = e.event_id\n" +
            "                                 AND pr.status = 'CONFIRMED'\n" +
            "                             )\n" +
            "               LEFT JOIN marks likes\n" +
            "                         ON (\n" +
            "                                     likes.event_id = e.event_id\n" +
            "                                 AND likes.mark_value = 1\n" +
            "                             )\n" +
            "               LEFT JOIN marks dislikes\n" +
            "                         ON (\n" +
            "                                     dislikes.event_id = e.event_id\n" +
            "                                 AND dislikes.mark_value = 0\n" +
            "                             )\n" +
            "               JOIN events e1\n" +
            "                    ON (\n" +
            "                                e1.user_id = e.user_id\n" +
            "                            AND e1.category_id = e.category_id\n" +
            "                            AND e1.state = 'PUBLISHED'\n" +
            "                            AND e1.event_date > current_timestamp\n" +
            "                        )\n" +
            "               LEFT JOIN participation_requests pr1\n" +
            "                         ON (\n" +
            "                                     pr1.event_id = e1.event_id\n" +
            "                                 AND pr1.status = 'CONFIRMED'\n" +
            "                             )\n" +
            "      WHERE e.state = 'PUBLISHED'\n" +
            "        AND e.event_date <= current_timestamp\n" +
            "        AND e.category_id IN (?1)\n" +
            "        AND NOT EXISTS(\n" +
            "              SELECT pr0.participation_request_id\n" +
            "              FROM participation_requests pr0\n" +
            "              WHERE pr0.event_id = e1.event_id\n" +
            "                AND pr0.user_id = ?2\n" +
            "          )\n" +
            "      GROUP BY e.user_id, e.category_id, e1.event_id, e1.event_date, e1.participant_limit\n" +
            "      HAVING COUNT(DISTINCT pr1.participation_request_id) <= e1.participant_limit\n" +
            "          OR e1.participant_limit = 0\n" +
            "      ORDER BY e.category_id,\n" +
            "               3 DESC,\n" +
            "               e1.event_date\n" +
            "     ) as r;\n",
            nativeQuery = true)
    List<Long> getRecommendations(List<Long> categoryIds, Long userId);
}
