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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.element.exception.ApiError;
import ru.practicum.ewm.model.participation.dto.ParticipationRequestDto;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@Tag(name = "Private: Запросы на участие", description = "Закрытый API для работы с запросами текущего пользователя " +
        "на участие в событиях")
@RequestMapping("/users")
public interface UsersRequestsApi {
    /**
     * GET /{userId}/requests : Получение информации о заявках текущего пользователя на участие в чужих событиях
     *
     * @param userId id текущего пользователя (required)
     * @return Найдены запросы на участие (status code 200)
     * or Запрос составлен с ошибкой (status code 400)
     * or Не выполнены условия для совершения операции (status code 403)
     * or Объект не найден (status code 404)
     * or Запрос приводит к нарушению целостности данных (status code 409)
     * or Внутренняя ошибка сервера (status code 500)
     */
    @Operation(
            operationId = "getUserRequests",
            summary = "Получение информации о заявках текущего пользователя на участие в чужих событиях",
            tags = {"Private: Запросы на участие"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Найдены запросы на участие", content = {
                            @Content(mediaType = "application/json", array = @ArraySchema(
                                    schema = @Schema(implementation = ParticipationRequestDto.class)))
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
            value = "/{userId}/requests",
            produces = {"application/json"}
    )
    List<ParticipationRequestDto> getAll(
            @Parameter(name = "userId", description = "id текущего пользователя", required = true)
            @PathVariable("userId") Long userId
    );

    /**
     * POST /{userId}/requests : Добавление запроса от текущего пользователя на участие в событии
     * <p>
     * Обратите внимание:
     * <li> нельзя добавить повторный запрос
     * <li> инициатор события не может добавить запрос на участие в своём событии
     * <li> нельзя участвовать в неопубликованном событии
     * <li> если у события достигнут лимит запросов на участие - необходимо вернуть ошибку
     * <li> если для события отключена пре-модерация запросов на участие,
     * то запрос должен автоматически перейти в состояние подтвержденного
     *
     * @param userId  id текущего пользователя (required)
     * @param eventId id события (required)
     * @return Заявка создана (status code 201)
     * or Запрос составлен с ошибкой (status code 400)
     * or Не выполнены условия для совершения операции (status code 403)
     * or Объект не найден (status code 404)
     * or Запрос приводит к нарушению целостности данных (status code 409)
     * or Внутренняя ошибка сервера (status code 500)
     */
    @Operation(
            operationId = "addParticipationRequest",
            summary = "Добавление запроса от текущего пользователя на участие в событии",
            description = "Обратите внимание:\n" +
                    "- нельзя добавить повторный запрос\n" +
                    "- инициатор события не может добавить запрос на участие в своём событии\n" +
                    "- нельзя участвовать в неопубликованном событии\n" +
                    "- если у события достигнут лимит запросов на участие - необходимо вернуть ошибку\n" +
                    "- если для события отключена пре-модерация запросов на участие, " +
                    "то запрос должен автоматически перейти в состояние подтвержденного",
            tags = {"Private: Запросы на участие"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Заявка создана", content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ParticipationRequestDto.class))
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
            value = "/{userId}/requests",
            produces = {"application/json"}
    )
    ParticipationRequestDto add(
            @Parameter(name = "userId", description = "id текущего пользователя", required = true)
            @PathVariable("userId") Long userId,
            @NotNull @Parameter(name = "eventId", description = "id события", required = true)
            @Valid @RequestParam(value = "eventId", required = true) Long eventId
    );

    /**
     * PATCH /{userId}/requests/{requestId}/cancel : Отмена своего запроса на участие в событии
     *
     * @param userId    id текущего пользователя (required)
     * @param requestId id запроса на участие (required)
     * @return Заявка отменена (status code 200)
     * or Запрос составлен с ошибкой (status code 400)
     * or Не выполнены условия для совершения операции (status code 403)
     * or Объект не найден (status code 404)
     * or Запрос приводит к нарушению целостности данных (status code 409)
     * or Внутренняя ошибка сервера (status code 500)
     */
    @Operation(
            operationId = "cancelRequest",
            summary = "Отмена своего запроса на участие в событии",
            tags = {"Private: Запросы на участие"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Заявка отменена", content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = ParticipationRequestDto.class))
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
            value = "/{userId}/requests/{requestId}/cancel",
            produces = {"application/json"}
    )
    ParticipationRequestDto cancel(
            @Parameter(name = "userId", description = "id текущего пользователя", required = true)
            @PathVariable("userId") Long userId,
            @Parameter(name = "requestId", description = "id запроса на участие", required = true)
            @PathVariable("requestId") Long requestId
    );
}
