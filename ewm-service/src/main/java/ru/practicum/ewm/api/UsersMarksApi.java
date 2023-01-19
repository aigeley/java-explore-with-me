package ru.practicum.ewm.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import ru.practicum.ewm.model.mark.dto.MarkDto;

@Validated
@Tag(name = "Private: Оценки", description = "Закрытый API для работы с оценками")
@RequestMapping("/users")
public interface UsersMarksApi {
    /**
     * POST /{userId}/marks : Проставление оценки событию, в котором принимал участие текущий пользователь
     *
     * @param userId    id текущего пользователя (required)
     * @param eventId   id события (required)
     * @param markValue значение оценки (required)
     * @return Оценка сохранена (status code 200)
     * or Запрос составлен с ошибкой (status code 400)
     * or Не выполнены условия для совершения операции (status code 403)
     * or Объект не найден (status code 404)
     * or Запрос приводит к нарушению целостности данных (status code 409)
     * or Внутренняя ошибка сервера (status code 500)
     */
    @Operation(
            operationId = "addMark",
            summary = "Проставление оценки событию, в котором принимал участие текущий пользователь",
            tags = {"Private: Оценки"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Оценка сохранена", content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MarkDto.class))
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
            value = "/{userId}/marks",
            produces = {"application/json"}
    )
    MarkDto add(
            @Parameter(name = "userId", description = "id текущего пользователя", required = true)
            @PathVariable("userId") Long userId,
            @Parameter(name = "eventId", description = "id события", required = true)
            @RequestParam("eventId") Long eventId,
            @Parameter(name = "markValue", example = "true",
                    description = "значение оценки: true - лайк, false - дизлайк",
                    required = true)
            @RequestParam("markValue") Boolean markValue
    );

    /**
     * GET /{userId}/marks : Получение оценки событию, в котором принимал участие текущий пользователь
     *
     * @param userId  id текущего пользователя (required)
     * @param eventId id события (required)
     * @return Оценка найдена (status code 200)
     * or Запрос составлен с ошибкой (status code 400)
     * or Не выполнены условия для совершения операции (status code 403)
     * or Объект не найден (status code 404)
     * or Запрос приводит к нарушению целостности данных (status code 409)
     * or Внутренняя ошибка сервера (status code 500)
     */
    @Operation(
            operationId = "getMark",
            summary = "Получение оценки событию, в котором принимал участие текущий пользователь",
            tags = {"Private: Оценки"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Оценка найдена", content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = MarkDto.class))
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
            value = "/{userId}/marks",
            produces = {"application/json"}
    )
    MarkDto get(
            @Parameter(name = "userId", description = "id текущего пользователя", required = true)
            @PathVariable("userId") Long userId,
            @Parameter(name = "eventId", description = "id события", required = true)
            @RequestParam("eventId") Long eventId
    );
}
