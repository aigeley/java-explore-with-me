package ru.practicum.ewm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.practicum.ewm.model.participation.ParticipationRequest;
import ru.practicum.ewm.model.participation.StatusEnum;

import java.util.List;

public interface ParticipationRequestRepository extends JpaRepository<ParticipationRequest, Long> {
    List<ParticipationRequest> findAllByEvent_IdOrderById(Long eventId);

    List<ParticipationRequest> findAllByRequester_IdOrderById(Long userId);

    ParticipationRequest findByRequester_IdAndEvent_IdAndStatus(Long userId, Long eventId, StatusEnum status);

    @Modifying
    @Query("UPDATE ParticipationRequest pr SET pr.status = :newStatus " +
            "WHERE pr.event.id = :eventId AND pr.status = :oldStatus")
    void updateStatus(@Param("eventId") Long eventId, @Param("oldStatus") StatusEnum oldStatus,
                      @Param("newStatus") StatusEnum newStatus);

    @Query(value = "SELECT DISTINCT e.category_id FROM events e\n" +
            "JOIN participation_requests pr ON e.event_id = pr.event_id\n" +
            "WHERE pr.user_id = ?1\n" +
            "AND pr.status = 'CONFIRMED'",
            nativeQuery = true)
    List<Long> getCategories(Long userId);
}
