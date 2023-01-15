package ru.practicum.stats.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.practicum.element.exception.ApiError;
import ru.practicum.stats.model.EndpointHit;

import javax.validation.Valid;

@Validated
@Tag(name = "StatsController", description = "API для работы со статистикой посещений")
@RequestMapping("/hit")
public interface HitApi {

    /**
     * POST : Сохранение информации о том, что к эндпоинту был запрос
     * <p>
     * Сохранение информации о том, что на uri конкретного сервиса был отправлен запрос пользователем.
     * Название сервиса, uri и ip пользователя указаны в теле запроса.
     *
     * @param endpointHit данные запроса (required)
     * @return Информация сохранена (status code 200)
     */
    @Operation(
            operationId = "hit",
            summary = "Сохранение информации о том, что к эндпоинту был запрос",
            description = "Сохранение информации о том, " +
                    "что на uri конкретного сервиса был отправлен запрос пользователем.\n" +
                    "Название сервиса, uri и ip пользователя указаны в теле запроса.",
            tags = {"StatsController"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Информация сохранена"),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.POST,
            value = "",
            consumes = {"application/json"}
    )
    void add(
            @Parameter(name = "EndpointHit", description = "данные запроса", required = true)
            @Valid @RequestBody EndpointHit endpointHit
    );

}
