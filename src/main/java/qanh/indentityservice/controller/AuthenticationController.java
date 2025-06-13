package qanh.indentityservice.controller;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qanh.indentityservice.dto.request.ApiResponse;
import qanh.indentityservice.dto.request.AuthenticationRequest;
import qanh.indentityservice.dto.response.AuthenticationResponse;
import qanh.indentityservice.service.AuthenticationService;

@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var res = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(res)
                .build();
    }

}
