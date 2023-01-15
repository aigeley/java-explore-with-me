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
import ru.practicum.element.exception.ApiError;
import ru.practicum.ewm.model.participation.dto.ParticipationRequestDto;

import java.util.List;

@Validated
@Tag(name = "Private: События", description = "Закрытый API для работы с событиями")
@RequestMapping("/users")
public interface UsersEventsRequestsApi {
    /**
     * GET /{userId}/events/{eventId}/requests :
     * Получение информации о запросах на участие в событии текущего пользователя
     *
     * @param userId  id текущего пользователя (required)
     * @param eventId id события (required)
     * @return Найдены запросы на участие (status code 200)
     * or Запрос составлен с ошибкой (status code 400)
     * or Не выполнены условия для совершения операции (status code 403)
     * or Объект не найден (status code 404)
     * or Запрос приводит к нарушению целостности данных (status code 409)
     * or Внутренняя ошибка сервера (status code 500)
     */
    @Operation(
            operationId = "getEventParticipants",
            summary = "Получение информации о запросах на участие в событии текущего пользователя",
            description = "Получение информации о запросах на участие в событии текущего пользователя",
            tags = {"Private: События"},
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
            value = "/{userId}/events/{eventId}/requests",
            produces = {"application/json"}
    )
    List<ParticipationRequestDto> getAll(
            @Parameter(name = "userId", description = "id текущего пользователя", required = true)
            @PathVariable("userId") Long userId,
            @Parameter(name = "eventId", description = "id события", required = true)
            @PathVariable("eventId") Long eventId
    );

    /**
     * PATCH /{userId}/events/{eventId}/requests/{reqId}/confirm :
     * Подтверждение чужой заявки на участие в событии текущего пользователя
     * <p>
     * Обратите внимание:
     * <li> если для события лимит заявок равен 0 или отключена пре-модерация заявок,
     * то подтверждение заявок не требуется
     * <li> нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие
     * <li> если при подтверждении данной заявки, лимит заявок для события исчерпан,
     * то все неподтверждённые заявки необходимо отклонить
     *
     * @param userId  id текущего пользователя (required)
     * @param eventId id события текущего пользователя (required)
     * @param reqId   id заявки, которую подтверждает текущий пользователь (required)
     * @return Заявка подтверждена (status code 200)
     * or Запрос составлен с ошибкой (status code 400)
     * or Не выполнены условия для совершения операции (status code 403)
     * or Объект не найден (status code 404)
     * or Запрос приводит к нарушению целостности данных (status code 409)
     * or Внутренняя ошибка сервера (status code 500)
     */
    @Operation(
            operationId = "confirmParticipationRequest",
            summary = "Подтверждение чужой заявки на участие в событии текущего пользователя",
            description = "Подтверждение чужой заявки на участие в событии текущего пользователя\n" +
                    "Обратите внимание:\n" +
                    "- если для события лимит заявок равен 0 или отключена пре-модерация заявок, " +
                    "то подтверждение заявок не требуется\n" +
                    "- нельзя подтвердить заявку, если уже достигнут лимит по заявкам на данное событие\n" +
                    "- если при подтверждении данной заявки, лимит заявок для события исчерпан, " +
                    "то все неподтверждённые заявки необходимо отклонить",
            tags = {"Private: События"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Заявка подтверждена", content = {
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
            value = "/{userId}/events/{eventId}/requests/{reqId}/confirm",
            produces = {"application/json"}
    )
    ParticipationRequestDto confirm(
            @Parameter(name = "userId", description = "id текущего пользователя", required = true)
            @PathVariable("userId") Long userId,
            @Parameter(name = "eventId", description = "id события текущего пользователя", required = true)
            @PathVariable("eventId") Long eventId,
            @Parameter(name = "reqId", description = "id заявки, которую подтверждает текущий пользователь",
                    required = true)
            @PathVariable("reqId") Long reqId
    );

    /**
     * PATCH /{userId}/events/{eventId}/requests/{reqId}/reject :
     * Отклонение чужой заявки на участие в событии текущего пользователя
     *
     * @param userId  id текущего пользователя (required)
     * @param eventId id события текущего пользователя (required)
     * @param reqId   id заявки, которую отменяет текущий пользователь (required)
     * @return Заявка отклонена (status code 200)
     * or Запрос составлен с ошибкой (status code 400)
     * or Не выполнены условия для совершения операции (status code 403)
     * or Объект не найден (status code 404)
     * or Запрос приводит к нарушению целостности данных (status code 409)
     * or Внутренняя ошибка сервера (status code 500)
     */
    @Operation(
            operationId = "cancelParticipationRequest",
            summary = "Отклонение чужой заявки на участие в событии текущего пользователя",
            tags = {"Private: События"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Заявка отклонена", content = {
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
            value = "/{userId}/events/{eventId}/requests/{reqId}/reject",
            produces = {"application/json"}
    )
    ParticipationRequestDto cancel(
            @Parameter(name = "userId", description = "id текущего пользователя", required = true)
            @PathVariable("userId") Long userId,
            @Parameter(name = "eventId", description = "id события текущего пользователя", required = true)
            @PathVariable("eventId") Long eventId,
            @Parameter(name = "reqId",
                    description = "id заявки, которую отменяет текущий пользователь", required = true)
            @PathVariable("reqId") Long reqId
    );
}
