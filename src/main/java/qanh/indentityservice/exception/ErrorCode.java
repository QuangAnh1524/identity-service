package qanh.indentityservice.exception;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;

@Getter
public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(998, "Uncategorized exception", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_EXISTS(1001, "User already exists", HttpStatus.BAD_REQUEST),
    USERNAME_INVALID(1002, "Username must be at least 3 characters long", HttpStatus.BAD_REQUEST),
    PASSWORD_INVALID(1003, "Password must be at least 8 characters long", HttpStatus.BAD_REQUEST),
    INVALID_KEY(1004, "Invalid key", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1005, "User not found", HttpStatus.NOT_FOUND),
    UNAUTHENTICATED(1006, "User is not authenticated", HttpStatus.UNAUTHORIZED), //401
    UNAUTHORIZED(1007, "You do not have permission", HttpStatus.FORBIDDEN) //403
    ;

    private int code;
    private HttpStatusCode httpStatusCode;
    private String message;

    ErrorCode(int code, String message, HttpStatusCode httpStatusCode) {
        this.code = code;
        this.message = message;
        this.httpStatusCode = httpStatusCode;
    }

}
