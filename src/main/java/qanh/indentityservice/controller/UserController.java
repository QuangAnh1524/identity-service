package qanh.indentityservice.controller;

import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import qanh.indentityservice.dto.request.ApiResponse;
import qanh.indentityservice.dto.request.UserCreationRequest;
import qanh.indentityservice.dto.request.UserUpdateRequest;
import qanh.indentityservice.dto.response.UserResponse;
import qanh.indentityservice.entity.User;
import qanh.indentityservice.service.UserService;

import java.util.List;


@RestController
@Slf4j
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest user) {
        ApiResponse<UserResponse> response = new ApiResponse<>();
        response.setResult(userService.createUser(user));
        return response;
    }

    @GetMapping("/users")
    public ApiResponse<List<UserResponse>> getAllUser() {

        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("User: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority ->
                log.info("Granted Authority: {}", grantedAuthority.getAuthority()));

        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getAllUser())
                .build();
    }

    @GetMapping("/users/{id}")
    public UserResponse getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PutMapping("/users/{id}")
    public UserResponse updateUser(@PathVariable String id, @RequestBody UserUpdateRequest user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }
}
