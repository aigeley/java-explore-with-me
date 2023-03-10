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
import ru.practicum.ewm.model.event.SortEnum;
import ru.practicum.ewm.model.event.dto.EventFullDto;
import ru.practicum.ewm.model.event.dto.EventShortDto;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@Validated
@Tag(name = "Public: События", description = "Публичный API для работы с событиями")
@RequestMapping("/events")
public interface EventsApi {

    /**
     * GET : Получение событий с возможностью фильтрации
     * <p>
     * Обратите внимание:
     * <li> это публичный эндпоинт, соответственно в выдаче должны быть только опубликованные события
     * <li> текстовый поиск (по аннотации и подробному описанию) должен быть без учета регистра букв
     * <li> если в запросе не указан диапазон дат [rangeStart-rangeEnd], то нужно выгружать события,
     * которые произойдут позже текущей даты и времени
     * <li> информация о каждом событии должна включать в себя количество просмотров
     * и количество уже одобренных заявок на участие
     * <li> информацию о том, что по этому эндпоинту был осуществлен и обработан запрос,
     * нужно сохранить в сервисе статистики
     *
     * @param text          текст для поиска в содержимом аннотации и подробном описании события (optional)
     * @param categories    список идентификаторов категорий в которых будет вестись поиск (optional)
     * @param paid          поиск только платных/бесплатных событий (optional)
     * @param rangeStart    дата и время не раньше которых должно произойти событие (optional)
     * @param rangeEnd      дата и время не позже которых должно произойти событие (optional)
     * @param onlyAvailable только события у которых не исчерпан лимит запросов на участие (optional, default to false)
     * @param sort          Вариант сортировки: по дате события или по количеству просмотров (optional)
     * @param from          количество событий, которые нужно пропустить для формирования текущего набора
     *                      (optional, default to 0)
     * @param size          количество событий в наборе (optional, default to 10)
     * @param request       HttpServletRequest для определения IP-адреса
     * @return События найдены (status code 200)
     * or Запрос составлен с ошибкой (status code 400)
     * or Не выполнены условия для совершения операции (status code 403)
     * or Объект не найден (status code 404)
     * or Запрос приводит к нарушению целостности данных (status code 409)
     * or Внутренняя ошибка сервера (status code 500)
     */
    @Operation(
            operationId = "getEvents_1",
            summary = "Получение событий с возможностью фильтрации",
            description = "Обратите внимание:\n" +
                    "- это публичный эндпоинт, соответственно в выдаче должны быть только опубликованные события\n" +
                    "- текстовый поиск (по аннотации и подробному описанию) должен быть без учета регистра букв\n" +
                    "- если в запросе не указан диапазон дат [rangeStart-rangeEnd], то нужно выгружать события, " +
                    "которые произойдут позже текущей даты и времени\n" +
                    "- информация о каждом событии должна включать в себя количество просмотров " +
                    "и количество уже одобренных заявок на участие\n" +
                    "- информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, " +
                    "нужно сохранить в сервисе статистики",
            tags = {"Public: События"},
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
            value = "",
            produces = {"application/json"}
    )
    List<EventShortDto> getAll(
            @Parameter(name = "text",
                    description = "текст для поиска в содержимом аннотации и подробном описании события")
            @Valid @RequestParam(value = "text", required = false) String text,
            @Parameter(name = "categories",
                    description = "список идентификаторов категорий в которых будет вестись поиск")
            @Valid @RequestParam(value = "categories", required = false) List<Long> categories,
            @Parameter(name = "paid", description = "поиск только платных/бесплатных событий")
            @Valid @RequestParam(value = "paid", required = false) Boolean paid,
            @Parameter(name = "rangeStart", description = "дата и время не раньше которых должно произойти событие")
            @Valid @RequestParam(value = "rangeStart", required = false) String rangeStart,
            @Parameter(name = "rangeEnd", description = "дата и время не позже которых должно произойти событие")
            @Valid @RequestParam(value = "rangeEnd", required = false) String rangeEnd,
            @Parameter(name = "onlyAvailable",
                    description = "только события у которых не исчерпан лимит запросов на участие")
            @Valid @RequestParam(value = "onlyAvailable", required = false,
                    defaultValue = "false") Boolean onlyAvailable,
            @Parameter(name = "sort", description = "Вариант сортировки: по дате события или по количеству просмотров")
            @Valid @RequestParam(value = "sort", required = false) SortEnum sort,
            @Parameter(name = "from",
                    description = "количество событий, которые нужно пропустить для формирования текущего набора")
            @Valid @RequestParam(value = "from", required = false, defaultValue = "0") Integer from,
            @Parameter(name = "size", description = "количество событий в наборе")
            @Valid @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
            HttpServletRequest request
    );

    /**
     * GET /{id} : Получение подробной информации об опубликованном событии по его идентификатору
     * <p>
     * Обратите внимание:
     * <li> событие должно быть опубликовано
     * <li> информация о событии должна включать в себя количество просмотров и количество подтвержденных запросов
     * <li> информацию о том, что по этому эндпоинту был осуществлен и обработан запрос,
     * нужно сохранить в сервисе статистики
     *
     * @param id      id события (required)
     * @param request HttpServletRequest для определения IP-адреса
     * @return Событие найдено (status code 200)
     * or Запрос составлен с ошибкой (status code 400)
     * or Не выполнены условия для совершения операции (status code 403)
     * or Объект не найден (status code 404)
     * or Запрос приводит к нарушению целостности данных (status code 409)
     * or Внутренняя ошибка сервера (status code 500)
     */
    @Operation(
            operationId = "getEvent_1",
            summary = "Получение подробной информации об опубликованном событии по его идентификатору",
            description = "Обратите внимание:\n" +
                    "- событие должно быть опубликовано\n" +
                    "- информация о событии должна включать в себя количество просмотров " +
                    "и количество подтвержденных запросов\n" +
                    "- информацию о том, что по этому эндпоинту был осуществлен и обработан запрос, " +
                    "нужно сохранить в сервисе статистики",
            tags = {"Public: События"},
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
            value = "/{id}",
            produces = {"application/json"}
    )
    EventFullDto get(
            @Parameter(name = "id", description = "id события", required = true)
            @PathVariable("id") Long id,
            HttpServletRequest request
    );

}
