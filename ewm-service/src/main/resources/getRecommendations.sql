SELECT r.future_event_id
FROM (SELECT DISTINCT ON (e.category_id)
          --Отбираем одну строку для каждой категории
          e.category_id,
          e.user_id,
          --Лайки больше влияют на рейтинг, чем дизлайки
          ((1.0 + COUNT(DISTINCT likes.mark_id) - COUNT(DISTINCT dislikes.mark_id)) /
           (1.0 + COUNT(DISTINCT pr.participation_request_id))) *
          (COUNT(DISTINCT likes.mark_id))              AS rating,
          e1.event_id                                  AS future_event_id,
          e1.event_date,
          e1.participant_limit,
          COUNT(DISTINCT pr1.participation_request_id) AS future_requests
      FROM events e
               --Все подтверждённые заявки всех событий автора
               LEFT JOIN participation_requests pr
                         ON (
                                     pr.event_id = e.event_id
                                 AND pr.status = 'CONFIRMED'
                             )
          --Все лайки всех событий автора
               LEFT JOIN marks likes
                         ON (
                                     likes.event_id = e.event_id
                                 AND likes.mark_value = true
                             )
          --Все дизлайки всех событий автора
               LEFT JOIN marks dislikes
                         ON (
                                     dislikes.event_id = e.event_id
                                 AND dislikes.mark_value = false
                             )
          --Предстоящие события того же автора
               JOIN events e1
                    ON (
                                e1.user_id = e.user_id
                            AND e1.category_id = e.category_id
                            AND e1.state = 'PUBLISHED'
                            AND e1.event_date > current_timestamp
                        )
          --Подтверждённые заявки на предстоящие события
               LEFT JOIN participation_requests pr1
                         ON (
                                     pr1.event_id = e1.event_id
                                 AND pr1.status = 'CONFIRMED'
                             )
      WHERE e.state = 'PUBLISHED'
        AND e.event_date <= current_timestamp --Рейтинг автора рассчитываем по прошедшим событиям
        AND e.category_id IN (32, 33)         --Категории событий, в которых участвовал пользователь
        AND NOT EXISTS(
          --Отбираем события, на которые пользователь ещё не подавал заявку
              SELECT pr0.participation_request_id
              FROM participation_requests pr0
              WHERE pr0.event_id = e1.event_id
                AND pr0.user_id = 31
          )
      GROUP BY e.user_id, e.category_id, e1.event_id, e1.event_date, e1.participant_limit
               --Отбираем только доступные предстоящие события
      HAVING COUNT(DISTINCT pr1.participation_request_id) <= e1.participant_limit
          OR e1.participant_limit = 0
      ORDER BY e.category_id,
               3 DESC, --Отбираем по одному автору с наивысшим рейтингом в категории
               e1.event_date --Отбираем ближайшее событие
     ) as r;
