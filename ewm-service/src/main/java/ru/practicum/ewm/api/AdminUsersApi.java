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
import ru.practicum.ewm.model.user.dto.NewUserRequest;
import ru.practicum.ewm.model.user.dto.UserDto;

import javax.validation.Valid;
import java.util.List;

@Validated
@Tag(name = "Admin: Пользователи", description = "API для работы с пользователями")
@RequestMapping("/admin")
public interface AdminUsersApi {
    /**
     * GET /users : Получение информации о пользователях
     * <p>
     * Возвращает информацию обо всех пользователях (учитываются параметры ограничения выборки),
     * либо о конкретных (учитываются указанные идентификаторы)
     *
     * @param ids  id пользователей (optional)
     * @param from количество элементов, которые нужно пропустить для формирования текущего набора
     *             (optional, default to 0)
     * @param size количество элементов в наборе (optional, default to 10)
     * @return Пользователи найдены (status code 200)
     * or Запрос составлен с ошибкой (status code 400)
     * or Не выполнены условия для совершения операции (status code 403)
     * or Объект не найден (status code 404)
     * or Запрос приводит к нарушению целостности данных (status code 409)
     * or Внутренняя ошибка сервера (status code 500)
     */
    @Operation(
            operationId = "getUsers",
            summary = "Получение информации о пользователях",
            description = "Возвращает информацию обо всех пользователях (учитываются параметры ограничения выборки), " +
                    "либо о конкретных (учитываются указанные идентификаторы)",
            tags = {"Admin: Пользователи"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователи найдены", content = {
                            @Content(mediaType = "application/json",
                                    array = @ArraySchema(schema = @Schema(implementation = UserDto.class)))
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
            value = "/users",
            produces = {"application/json"}
    )
    List<UserDto> getAll(
            @Parameter(name = "ids", description = "id пользователей")
            @Valid @RequestParam(value = "ids", required = false) List<Long> ids,
            @Parameter(name = "from",
                    description = "количество элементов, которые нужно пропустить для формирования текущего набора")
            @Valid @RequestParam(value = "from", required = false, defaultValue = "0") Integer from,
            @Parameter(name = "size", description = "количество элементов в наборе")
            @Valid @RequestParam(value = "size", required = false, defaultValue = "10") Integer size
    );

    /**
     * POST /users : Добавление нового пользователя
     *
     * @param newUserRequest данные добавляемого пользователя (required)
     * @return Пользователь зарегистрирован (status code 200)
     * or Запрос составлен с ошибкой (status code 400)
     * or Не выполнены условия для совершения операции (status code 403)
     * or Объект не найден (status code 404)
     * or Запрос приводит к нарушению целостности данных (status code 409)
     * or Внутренняя ошибка сервера (status code 500)
     */
    @Operation(
            operationId = "registerUser",
            summary = "Добавление нового пользователя",
            tags = {"Admin: Пользователи"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь зарегистрирован", content = {
                            @Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))
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
            value = "/users",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    UserDto register(
            @Parameter(name = "NewUserRequest", description = "данные добавляемого пользователя", required = true)
            @Valid @RequestBody NewUserRequest newUserRequest
    );

    /**
     * DELETE /users/{userId} : Удаление пользователя
     *
     * @param userId id пользователя (required)
     * @return Пользователь удален (status code 200)
     * or Запрос составлен с ошибкой (status code 400)
     * or Не выполнены условия для совершения операции (status code 403)
     * or Объект не найден (status code 404)
     * or Запрос приводит к нарушению целостности данных (status code 409)
     * or Внутренняя ошибка сервера (status code 500)
     */
    @Operation(
            operationId = "delete",
            summary = "Удаление пользователя",
            tags = {"Admin: Пользователи"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Пользователь удален"),
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
            method = RequestMethod.DELETE,
            value = "/users/{userId}",
            produces = {"application/json"}
    )
    void delete(
            @Parameter(name = "userId", description = "id пользователя", required = true)
            @PathVariable("userId") Long userId
    );
}
