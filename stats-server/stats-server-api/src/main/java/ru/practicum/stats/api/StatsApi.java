package ru.practicum.stats.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.practicum.element.exception.ApiError;
import ru.practicum.stats.model.ViewStats;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@Tag(name = "StatsController", description = "API для работы со статистикой посещений")
@RequestMapping("/stats")
public interface StatsApi {

    /**
     * GET : Получение статистики по посещениям.
     * <p>
     * Обратите внимание: значение даты и времени нужно закодировать
     * (например используя java.net.URLEncoder.encode)
     *
     * @param start  Дата и время начала диапазона за который нужно выгрузить статистику
     *               (в формате \&quot;yyyy-MM-dd HH:mm:ss\&quot;) (required)
     * @param end    Дата и время конца диапазона за который нужно выгрузить статистику
     *               (в формате \&quot;yyyy-MM-dd HH:mm:ss\&quot;) (required)
     * @param uris   Список uri для которых нужно выгрузить статистику (optional)
     * @param unique Нужно ли учитывать только уникальные посещения
     *               (только с уникальным ip) (optional, default to false)
     * @return Статистика собрана (status code 200)
     */
    @Operation(
            operationId = "getStats",
            summary = "Получение статистики по посещениям",
            description = "Обратите внимание: значение даты и времени нужно закодировать " +
                    "(например используя java.net.URLEncoder.encode)",
            tags = {"StatsController"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Статистика собрана", content = {
                            @Content(mediaType = "application/json", array =
                            @ArraySchema(schema = @Schema(implementation = ViewStats.class)))
                    }),
                    @ApiResponse(responseCode = "500", description = "Внутренняя ошибка сервера", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))
                    })
            }
    )
    @RequestMapping(
            method = RequestMethod.GET,
            value = "",
            produces = {"application/json"}
    )
    List<ViewStats> getAll(
            @NotNull @Parameter(
                    name = "start",
                    example = "2023-01-09 11:00:23",
                    description = "Дата и время начала диапазона за который нужно выгрузить статистику " +
                            "(в формате \"yyyy-MM-dd HH:mm:ss\")",
                    required = true
            )
            @Valid @RequestParam(value = "start", required = true) String start,
            @NotNull @Parameter(
                    name = "end",
                    example = "2023-02-09 11:00:23",
                    description = "Дата и время конца диапазона за который нужно выгрузить статистику " +
                            "(в формате \"yyyy-MM-dd HH:mm:ss\")",
                    required = true
            )
            @Valid @RequestParam(value = "end", required = true) String end,
            @Parameter(name = "uris", description = "Список uri для которых нужно выгрузить статистику")
            @Valid @RequestParam(value = "uris", required = false) List<String> uris,
            @Parameter(name = "unique",
                    description = "Нужно ли учитывать только уникальные посещения (только с уникальным ip)")
            @Valid @RequestParam(value = "unique", required = false, defaultValue = "false") Boolean unique
    );
}
