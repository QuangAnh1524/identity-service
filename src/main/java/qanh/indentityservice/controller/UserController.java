package qanh.indentityservice.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import qanh.indentityservice.dto.request.ApiResponse;
import qanh.indentityservice.dto.request.UserCreationRequest;
import qanh.indentityservice.entity.User;
import qanh.indentityservice.service.UserService;


@RestController
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest user) {
        ApiResponse<User> response = new ApiResponse<>();
        response.setResult(userService.createUser(user));
        return response;
    }

    @GetMapping("/users")
    public Iterable<User> getAllUser() {
        return userService.getAllUser();
    }

    @GetMapping("/users/{id}")
    public User getUserById(@PathVariable String id) {
        return userService.getUserById(id);
    }

    @PutMapping("/users/{id}")
    public User updateUser(@PathVariable String id, @RequestBody UserCreationRequest user) {
        return userService.updateUser(id, user);
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
    }
}
