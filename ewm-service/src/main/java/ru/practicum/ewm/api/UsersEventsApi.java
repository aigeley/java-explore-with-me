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
import ru.practicum.ewm.model.event.dto.EventFullDto;
import ru.practicum.ewm.model.event.dto.EventShortDto;
import ru.practicum.ewm.model.event.dto.NewEventDto;
import ru.practicum.ewm.model.event.dto.UpdateEventRequest;

import javax.validation.Valid;
import java.util.List;

@Validated
@Tag(name = "Private: События", description = "Закрытый API для работы с событиями")
@RequestMapping("/users")
public interface UsersEventsApi {
    /**
     * GET /{userId}/events : Получение событий, добавленных текущим пользователем
     *
     * @param userId id текущего пользователя (required)
     * @param from   количество элементов, которые нужно пропустить для формирования текущего набора
     *               (optional, default to 0)
     * @param size   количество элементов в наборе (optional, default to 10)
     * @return События найдены (status code 200)
     * or Запрос составлен с ошибкой (status code 400)
     * or Не выполнены условия для совершения операции (status code 403)
     * or Объект не найден (status code 404)
     * or Запрос приводит к нарушению целостности данных (status code 409)
     * or Внутренняя ошибка сервера (status code 500)
     */
    @Operation(
            operationId = "getEvents",
            summary = "Получение событий, добавленных текущим пользователем",
            tags = {"Private: События"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "События найдены", content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = EventShortDto.class)))
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
            value = "/{userId}/events",
            produces = {"application/json"}
    )
    List<EventShortDto> getAll(
            @Parameter(name = "userId", description = "id текущего пользователя", required = true)
            @PathVariable("userId") Long userId,
            @Parameter(name = "from",
                    description = "количество элементов, которые нужно пропустить для формирования текущего набора")
            @Valid @RequestParam(value = "from", required = false, defaultValue = "0") Integer from,
            @Parameter(name = "size", description = "количество элементов в наборе")
            @Valid @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    );

    /**
     * PATCH /{userId}/events : Изменение события добавленного текущим пользователем
     * <p>
     * Обратите внимание:
     * <li> изменить можно только отмененные события или события в состоянии ожидания модерации
     * <li> если редактируется отменённое событие, то оно автоматически переходит в состояние ожидания модерации
     * <li> дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента
     *
     * @param userId             id текущего пользователя (required)
     * @param updateEventRequest Новые данные события (required)
     * @return Событие обновлено (status code 200)
     * or Запрос составлен с ошибкой (status code 400)
     * or Не выполнены условия для совершения операции (status code 403)
     * or Объект не найден (status code 404)
     * or Запрос приводит к нарушению целостности данных (status code 409)
     * or Внутренняя ошибка сервера (status code 500)
     */
    @Operation(
            operationId = "updateEvent_1",
            summary = "Изменение события добавленного текущим пользователем",
            description = "Обратите внимание:\n" +
                    "- изменить можно только отмененные события или события в состоянии ожидания модерации\n" +
                    "- если редактируется отменённое событие, то оно автоматически переходит в состояние ожидания " +
                    "модерации\n" +
                    "- дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего " +
                    "момента",
            tags = {"Private: События"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Событие обновлено", content = {
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
            value = "/{userId}/events",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    EventFullDto update(
            @Parameter(name = "userId", description = "id текущего пользователя", required = true)
            @PathVariable("userId") Long userId,
            @Parameter(name = "UpdateEventRequest", description = "Новые данные события", required = true)
            @Valid @RequestBody UpdateEventRequest updateEventRequest
    );

    /**
     * POST /{userId}/events : Добавление нового события
     * <p>
     * Обратите внимание:
     * дата и время на которые намечено событие не может быть раньше, чем через два часа от текущего момента
     *
     * @param userId      id текущего пользователя (required)
     * @param newEventDto данные добавляемого события (required)
     * @return Событие добавлено (status code 201)
     * or Запрос составлен с ошибкой (status code 400)
     * or Не выполнены условия для совершения операции (status code 403)
     * or Объект не найден (status code 404)
     * or Запрос приводит к нарушению целостности данных (status code 409)
     * or Внутренняя ошибка сервера (status code 500)
     */
    @Operation(
            operationId = "addEvent",
            summary = "Добавление нового события",
            description = "Обратите внимание: дата и время на которые намечено событие не может быть раньше, " +
                    "чем через два часа от текущего момента",
            tags = {"Private: События"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Событие добавлено", content = {
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
            method = RequestMethod.POST,
            value = "/{userId}/events",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    EventFullDto add(
            @Parameter(name = "userId", description = "id текущего пользователя", required = true)
            @PathVariable("userId") Long userId,
            @Parameter(name = "NewEventDto", description = "данные добавляемого события", required = true)
            @Valid @RequestBody NewEventDto newEventDto
    );

    /**
     * GET /{userId}/events/{eventId} : Получение полной информации о событии добавленном текущим пользователем
     *
     * @param userId  id текущего пользователя (required)
     * @param eventId id события (required)
     * @return Событие найдено (status code 200)
     * or Запрос составлен с ошибкой (status code 400)
     * or Не выполнены условия для совершения операции (status code 403)
     * or Объект не найден (status code 404)
     * or Запрос приводит к нарушению целостности данных (status code 409)
     * or Внутренняя ошибка сервера (status code 500)
     */
    @Operation(
            operationId = "getEvent",
            summary = "Получение полной информации о событии добавленном текущим пользователем",
            tags = {"Private: События"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Событие найдено", content = {
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
            method = RequestMethod.GET,
            value = "/{userId}/events/{eventId}",
            produces = {"application/json"}
    )
    EventFullDto get(
            @Parameter(name = "userId", description = "id текущего пользователя", required = true)
            @PathVariable("userId") Long userId,
            @Parameter(name = "eventId", description = "id события", required = true)
            @PathVariable("eventId") Long eventId
    );

    /**
     * PATCH /{userId}/events/{eventId} : Отмена события добавленного текущим пользователем.
     * <p>
     * Обратите внимание: Отменить можно только событие в состоянии ожидания модерации.
     *
     * @param userId  id текущего пользователя (required)
     * @param eventId id отменяемого события (required)
     * @return Событие обновлено (status code 200)
     * or Запрос составлен с ошибкой (status code 400)
     * or Не выполнены условия для совершения операции (status code 403)
     * or Объект не найден (status code 404)
     * or Запрос приводит к нарушению целостности данных (status code 409)
     * or Внутренняя ошибка сервера (status code 500)
     */
    @Operation(
            operationId = "cancelEvent",
            summary = "Отмена события добавленного текущим пользователем.",
            description = "Обратите внимание: Отменить можно только событие в состоянии ожидания модерации.",
            tags = {"Private: События"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Событие обновлено", content = {
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
            value = "/{userId}/events/{eventId}",
            produces = {"application/json"}
    )
    EventFullDto cancel(
            @Parameter(name = "userId", description = "id текущего пользователя", required = true)
            @PathVariable("userId") Long userId,
            @Parameter(name = "eventId", description = "id отменяемого события", required = true)
            @PathVariable("eventId") Long eventId
    );
}
