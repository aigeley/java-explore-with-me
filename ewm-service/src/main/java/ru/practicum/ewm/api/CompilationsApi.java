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
import ru.practicum.ewm.model.compilation.dto.CompilationDto;

import javax.validation.Valid;
import java.util.List;

@Validated
@Tag(name = "Public: Подборки событий", description = "Публичный API для работы с подборками событий")
@RequestMapping("/compilations")
public interface CompilationsApi {

    /**
     * GET : Получение подборок событий
     *
     * @param pinned искать только закрепленные/не закрепленные подборки (optional)
     * @param from   количество элементов, которые нужно пропустить для формирования текущего набора
     *               (optional, default to 0)
     * @param size   количество элементов в наборе (optional, default to 10)
     * @return Найдены подборки событий (status code 200)
     * or Запрос составлен с ошибкой (status code 400)
     * or Не выполнены условия для совершения операции (status code 403)
     * or Объект не найден (status code 404)
     * or Запрос приводит к нарушению целостности данных (status code 409)
     * or Внутренняя ошибка сервера (status code 500)
     */
    @Operation(
            operationId = "getCompilations",
            summary = "Получение подборок событий",
            tags = {"Public: Подборки событий"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Найдены подборки событий", content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = CompilationDto.class)))
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
            value = "",
            produces = {"application/json"}
    )
    List<CompilationDto> getAll(
            @Parameter(name = "pinned", description = "искать только закрепленные/не закрепленные подборки")
            @Valid @RequestParam(value = "pinned", required = false) Boolean pinned,
            @Parameter(name = "from",
                    description = "количество элементов, которые нужно пропустить для формирования текущего набора")
            @Valid @RequestParam(value = "from", required = false, defaultValue = "0") Integer from,
            @Parameter(name = "size", description = "количество элементов в наборе")
            @Valid @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    );

    /**
     * GET /{compId} : Получение подборки событий по его id
     *
     * @param compId id подборки (required)
     * @return Подборка событий найдена (status code 200)
     * or Запрос составлен с ошибкой (status code 400)
     * or Не выполнены условия для совершения операции (status code 403)
     * or Объект не найден (status code 404)
     * or Запрос приводит к нарушению целостности данных (status code 409)
     * or Внутренняя ошибка сервера (status code 500)
     */
    @Operation(
            operationId = "getCompilation",
            summary = "Получение подборки событий по его id",
            tags = {"Public: Подборки событий"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Подборка событий найдена", content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CompilationDto.class))
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
            value = "/{compId}",
            produces = {"application/json"}
    )
    CompilationDto get(
            @Parameter(name = "compId", description = "id подборки", required = true)
            @PathVariable("compId") Long compId
    );

}
