package qanh.indentityservice.controller;

import com.nimbusds.jose.JOSEException;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import qanh.indentityservice.dto.request.ApiResponse;
import qanh.indentityservice.dto.request.AuthenticationRequest;
import qanh.indentityservice.dto.request.IntrospectRequest;
import qanh.indentityservice.dto.response.AuthenticationResponse;
import qanh.indentityservice.dto.response.IntrospectResponse;
import qanh.indentityservice.service.AuthenticationService;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/token")
    ApiResponse<AuthenticationResponse> authenticate(@RequestBody AuthenticationRequest request) {
        var res = authenticationService.authenticate(request);
        return ApiResponse.<AuthenticationResponse>builder()
                .result(res)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest request) throws ParseException, JOSEException {
        var res = authenticationService.introspect(request);
        return ApiResponse.<IntrospectResponse>builder()
                .result(res)
                .build();
    }
}
