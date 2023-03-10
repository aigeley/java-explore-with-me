package ru.practicum.ewm.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.element.exception.ApiError;
import ru.practicum.ewm.model.event.StateEnum;
import ru.practicum.ewm.model.event.dto.AdminUpdateEventRequest;
import ru.practicum.ewm.model.event.dto.EventFullDto;

import javax.validation.Valid;
import java.util.List;

@Validated
@Tag(name = "Admin: События", description = "API для работы с событиями")
@RequestMapping("/admin")
public interface AdminEventsApi {
    /**
     * GET /events : Поиск событий
     * <p>
     * Эндпоинт возвращает полную информацию обо всех событиях подходящих под переданные условия
     *
     * @param users      список id пользователей, чьи события нужно найти (optional)
     * @param states     список состояний в которых находятся искомые события (optional)
     * @param categories список id категорий в которых будет вестись поиск (optional)
     * @param rangeStart дата и время не раньше которых должно произойти событие (optional)
     * @param rangeEnd   дата и время не позже которых должно произойти событие (optional)
     * @param from       количество событий, которые нужно пропустить для формирования текущего набора
     *                   (optional, default to 0)
     * @param size       количество событий в наборе (optional, default to 10)
     * @return События найдены (status code 200)
     * or Запрос составлен с ошибкой (status code 400)
     * or Не выполнены условия для совершения операции (status code 403)
     * or Объект не найден (status code 404)
     * or Запрос приводит к нарушению целостности данных (status code 409)
     * or Внутренняя ошибка сервера (status code 500)
     */
    @Operation(
            operationId = "getEvents_2",
            summary = "Поиск событий",
            description = "Эндпоинт возвращает полную информацию обо всех событиях подходящих под переданные условия",
            tags = {"Admin: События"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "События найдены", content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = EventFullDto.class)))
                    }),
                    @ApiResponse(responseCode = "400", description = "Запрос составлен с ошибкой", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Не выполнены условия для совершения операции",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class))
                            }),
                    @ApiResponse(responseCode = "404", description = "Объект не найден", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    }),
                    @ApiResponse(responseCode = "409", description = "Запрос приводит к нарушению целостности данных",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class))
                            }),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "/events",
            produces = {"application/json"}
    )
    List<EventFullDto> getAll(
            @Parameter(name = "users", description = "список id пользователей, чьи события нужно найти")
            @Valid @RequestParam(value = "users", required = false) List<Long> users,
            @Parameter(name = "states", description = "список состояний в которых находятся искомые события")
            @Valid @RequestParam(value = "states", required = false) List<StateEnum> states,
            @Parameter(name = "categories", description = "список id категорий в которых будет вестись поиск")
            @Valid @RequestParam(value = "categories", required = false) List<Long> categories,
            @Parameter(name = "rangeStart", description = "дата и время не раньше которых должно произойти событие")
            @Valid @RequestParam(value = "rangeStart", required = false) String rangeStart,
            @Parameter(name = "rangeEnd", description = "дата и время не позже которых должно произойти событие")
            @Valid @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
            @Parameter(name = "from",
                    description = "количество событий, которые нужно пропустить для формирования текущего набора")
            @Valid @RequestParam(value = "from", required = false, defaultValue = "0") Integer from,
            @Parameter(name = "size", description = "количество событий в наборе")
            @Valid @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    );

    /**
     * PUT /events/{eventId} : Редактирование события
     * <p>
     * Редактирование данных любого события администратором. Валидация данных не требуется.
     *
     * @param eventId                 id события (required)
     * @param adminUpdateEventRequest Данные для изменения информации о событии (required)
     * @return Событие отредактировано (status code 200)
     * or Запрос составлен с ошибкой (status code 400)
     * or Не выполнены условия для совершения операции (status code 403)
     * or Объект не найден (status code 404)
     * or Запрос приводит к нарушению целостности данных (status code 409)
     * or Внутренняя ошибка сервера (status code 500)
     */
    @Operation(
            operationId = "updateEvent",
            summary = "Редактирование события",
            description = "Редактирование данных любого события администратором. Валидация данных не требуется.",
            tags = {"Admin: События"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Событие отредактировано", content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EventFullDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Запрос составлен с ошибкой", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Не выполнены условия для совершения операции",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class))
                            }),
                    @ApiResponse(responseCode = "404", description = "Объект не найден", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    }),
                    @ApiResponse(responseCode = "409", description = "Запрос приводит к нарушению целостности данных",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class))
                            }),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.PUT,
            value = "/events/{eventId}",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    EventFullDto update(
            @Parameter(name = "eventId", description = "id события", required = true)
            @PathVariable("eventId") Long eventId,
            @Parameter(name = "AdminUpdateEventRequest", description = "Данные для изменения информации о событии",
                    required = true)
            @Valid @RequestBody AdminUpdateEventRequest adminUpdateEventRequest
    );

    /**
     * PATCH /events/{eventId}/publish : Публикация события
     * <p>
     * Обратите внимание:
     * <li> дата начала события должна быть не ранее чем за час от даты публикации.
     * <li> событие должно быть в состоянии ожидания публикации
     *
     * @param eventId id события (required)
     * @return Событие опубликовано (status code 200)
     * or Запрос составлен с ошибкой (status code 400)
     * or Не выполнены условия для совершения операции (status code 403)
     * or Объект не найден (status code 404)
     * or Запрос приводит к нарушению целостности данных (status code 409)
     * or Внутренняя ошибка сервера (status code 500)
     */
    @Operation(
            operationId = "publishEvent",
            summary = "Публикация события",
            description = "Обратите внимание:\n" +
                    "- дата начала события должна быть не ранее чем за час от даты публикации.\n" +
                    "- событие должно быть в состоянии ожидания публикации",
            tags = {"Admin: События"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Событие опубликовано", content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EventFullDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Запрос составлен с ошибкой", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Не выполнены условия для совершения операции",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class))
                            }),
                    @ApiResponse(responseCode = "404", description = "Объект не найден", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    }),
                    @ApiResponse(responseCode = "409", description = "Запрос приводит к нарушению целостности данных",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class))
                            }),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.PATCH,
            value = "/events/{eventId}/publish",
            produces = {"application/json"}
    )
    EventFullDto publish(
            @Parameter(name = "eventId", description = "id события", required = true)
            @PathVariable("eventId") Long eventId
    );

    /**
     * PATCH /events/{eventId}/reject : Отклонение события
     * <p>
     * Обратите внимание: событие не должно быть опубликовано.
     *
     * @param eventId id события (required)
     * @return Событие отклонено (status code 200)
     * or Запрос составлен с ошибкой (status code 400)
     * or Не выполнены условия для совершения операции (status code 403)
     * or Объект не найден (status code 404)
     * or Запрос приводит к нарушению целостности данных (status code 409)
     * or Внутренняя ошибка сервера (status code 500)
     */
    @Operation(
            operationId = "rejectEvent",
            summary = "Отклонение события",
            description = "Обратите внимание: событие не должно быть опубликовано.",
            tags = {"Admin: События"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Событие отклонено", content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = EventFullDto.class))
                    }),
                    @ApiResponse(responseCode = "400", description = "Запрос составлен с ошибкой", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    }),
                    @ApiResponse(responseCode = "403", description = "Не выполнены условия для совершения операции",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class))
                            }),
                    @ApiResponse(responseCode = "404", description = "Объект не найден", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    }),
                    @ApiResponse(responseCode = "409", description = "Запрос приводит к нарушению целостности данных",
                            content = {
                                    @Content(mediaType = "application/json",
                                            schema = @Schema(implementation = ApiError.class))
                            }),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.PATCH,
            value = "/events/{eventId}/reject",
            produces = {"application/json"}
    )
    EventFullDto reject(
            @Parameter(name = "eventId", description = "id события", required = true)
            @PathVariable("eventId") Long eventId
    );
}
