package ru.practicum.ewm.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import ru.practicum.element.exception.ApiError;
import ru.practicum.ewm.model.category.dto.CategoryDto;
import ru.practicum.ewm.model.category.dto.NewCategoryDto;

import javax.validation.Valid;

@Validated
@Tag(name = "Admin: Категории", description = "API для работы с категориями")
@RequestMapping("/admin")
public interface AdminCategoriesApi {
    /**
     * PATCH /categories : Изменение категории
     * <p>
     * Обратите внимание: имя категории должно быть уникальным
     *
     * @param categoryDto Данные категории для изменения (required)
     * @return Данные категории изменены (status code 200)
     * or Запрос составлен с ошибкой (status code 400)
     * or Не выполнены условия для совершения операции (status code 403)
     * or Объект не найден (status code 404)
     * or Запрос приводит к нарушению целостности данных (status code 409)
     * or Внутренняя ошибка сервера (status code 500)
     */
    @Operation(
            operationId = "updateCategory",
            summary = "Изменение категории",
            description = "Обратите внимание: имя категории должно быть уникальным",
            tags = {"Admin: Категории"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Данные категории изменены", content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryDto.class))
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
            value = "/categories",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    CategoryDto update(
            @Parameter(name = "CategoryDto", description = "Данные категории для изменения", required = true)
            @Valid @RequestBody CategoryDto categoryDto
    );

    /**
     * POST /categories : Добавление новой категории
     * <p>
     * Обратите внимание: имя категории должно быть уникальным
     *
     * @param newCategoryDto данные добавляемой категории (required)
     * @return Категория добавлена (status code 201)
     * or Запрос составлен с ошибкой (status code 400)
     * or Не выполнены условия для совершения операции (status code 403)
     * or Объект не найден (status code 404)
     * or Запрос приводит к нарушению целостности данных (status code 409)
     * or Внутренняя ошибка сервера (status code 500)
     */
    @Operation(
            operationId = "addCategory",
            summary = "Добавление новой категории",
            description = "Обратите внимание: имя категории должно быть уникальным",
            tags = {"Admin: Категории"},
            responses = {
                    @ApiResponse(responseCode = "201", description = "Категория добавлена", content = {
                            @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CategoryDto.class))
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
            value = "/categories",
            produces = {"application/json"},
            consumes = {"application/json"}
    )
    CategoryDto add(
            @Parameter(name = "NewCategoryDto", description = "данные добавляемой категории", required = true)
            @Valid @RequestBody NewCategoryDto newCategoryDto
    );

    /**
     * DELETE /categories/{catId} : Удаление категории
     * <p>
     * Обратите внимание: с категорией не должно быть связано ни одного события.
     *
     * @param catId id категории (required)
     * @return Категория удалена (status code 200)
     * or Запрос составлен с ошибкой (status code 400)
     * or Не выполнены условия для совершения операции (status code 403)
     * or Объект не найден (status code 404)
     * or Запрос приводит к нарушению целостности данных (status code 409)
     * or Внутренняя ошибка сервера (status code 500)
     */
    @Operation(
            operationId = "deleteCategory",
            summary = "Удаление категории",
            description = "Обратите внимание: с категорией не должно быть связано ни одного события.",
            tags = {"Admin: Категории"},
            responses = {
                    @ApiResponse(responseCode = "200", description = "Категория удалена"),
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
            value = "/categories/{catId}",
            produces = {"application/json"}
    )
    void delete(
            @Parameter(name = "catId", description = "id категории", required = true)
            @PathVariable("catId") Long catId
    );
}
