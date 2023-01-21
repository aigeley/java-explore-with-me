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
import ru.practicum.ewm.model.event.dto.EventShortDto;

import java.util.List;

@Validated
@Tag(name = "Private: Рекомендации", description = "Закрытый API для работы с рекомендациями")
@RequestMapping("/users")
public interface UsersRecommendationsApi {
    /**
     * GET /{userId}/recommendations : Получение рекомендаций для текущего пользователя
     * <p>
     * Если пользователь принимал участие в событиях из каких-либо категорий,
     * то ему предлагаются на выбор по одному ближайшему событию из каждой категории,
     * от авторов с наивысшим рейтингом
     *
     * @param userId id текущего пользователя (required)
     * @return Рекомендации найдены (status code 200)
     * or Запрос составлен с ошибкой (status code 400)
     * or Не выполнены условия для совершения операции (status code 403)
     * or Объект не найден (status code 404)
     * or Запрос приводит к нарушению целостности данных (status code 409)
     * or Внутренняя ошибка сервера (status code 500)
     */
    @Operation(
            operationId = "getRecommendations",
            summary = "Получение рекомендаций для текущего пользователя",
            description = "Если пользователь принимал участие в событиях из каких-либо категорий, " +
                    "то ему предлагается на выбор по одному ближайшему событию из каждой категории, " +
                    "от авторов с наивысшим рейтингом",
            tags = {"Private: Рекомендации"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Рекомендации найдены", content = {
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
            value = "/{userId}/recommendations",
            produces = {"application/json"}
    )
    List<EventShortDto> getAll(
            @Parameter(name = "userId", description = "id текущего пользователя", required = true)
            @PathVariable("userId") Long userId
    );
}
