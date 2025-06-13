package qanh.indentityservice.service;

import com.nimbusds.jose.*;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jwt.JWTClaimsSet;
import lombok.experimental.NonFinal;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import qanh.indentityservice.dto.request.AuthenticationRequest;
import qanh.indentityservice.dto.response.AuthenticationResponse;
import qanh.indentityservice.exception.AppException;
import qanh.indentityservice.exception.ErrorCode;
import qanh.indentityservice.repository.UserRepository;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;

@Service
@Slf4j
public class AuthenticationService {
    private final UserRepository userRepository;

    @NonFinal
    protected static final String SIGNER_KEY =
            "so+pBFSOwdS9AaYg7sxpq+x+CWzAu3AanWksJRSioL8Df36gnQAjUVoE4qu/2LLA";

    public AuthenticationService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest) {
        var user = userRepository.findByUsername(authenticationRequest.getUsername()).orElseThrow(()
                -> new AppException(ErrorCode.USER_NOT_FOUND));

        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);
        boolean authenticated = passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword());

        if (!authenticated) {
            throw new AppException(ErrorCode.UNAUTHENTICATED);
        }
        var token = generateToken(authenticationRequest.getUsername());

        return AuthenticationResponse.builder()
                .token(token)
                .authenticated(true)
                .build();
    }

    private String generateToken(String username) {
        JWSHeader header = new JWSHeader(JWSAlgorithm.HS512);
        JWTClaimsSet jwtClaimsSet = new JWTClaimsSet.Builder()
                .subject(username)
                .issuer("indentity-service")
                .issueTime(new Date())
                .expirationTime(new Date(
                        Instant.now().plus(1, java.time.temporal.ChronoUnit.HOURS).toEpochMilli()
                ))
                .build();
        Payload payload = new Payload(jwtClaimsSet.toJSONObject());

        JWSObject jwsObject = new JWSObject(header, payload);

        try {
            jwsObject.sign(new MACSigner(SIGNER_KEY.getBytes()));
            return jwsObject.serialize();
        } catch (JOSEException e) {
            log.error("Error when generate token", e);
            throw new RuntimeException(e);
        }
    }
}
