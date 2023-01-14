package ru.practicum.ewm.api;

import org.springframework.web.bind.annotation.RestController;
import ru.practicum.element.api.ElementApiController;
import ru.practicum.ewm.model.user.dto.NewUserRequest;
import ru.practicum.ewm.model.user.dto.UserDto;
import ru.practicum.ewm.service.AdminUsersService;

import javax.annotation.Generated;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen")
@RestController
public class AdminUsersApiController extends ElementApiController<AdminUsersService> implements AdminUsersApi {
    protected AdminUsersApiController(AdminUsersService service, HttpServletRequest request) {
        super(service, request);
    }

    @Override
    public List<UserDto> getUsers(List<Long> ids, Integer from, Integer size) {
        return service.getUsers(ids, from, size);
    }

    @Override
    public UserDto registerUser(NewUserRequest newUserRequest) {
        return service.registerUser(newUserRequest);
    }

    @Override
    public void delete(Long userId) {
        service.delete(userId);
    }
}

