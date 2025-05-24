package qanh.indentityservice.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import qanh.indentityservice.dto.request.ApiResponse;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    ResponseEntity<ApiResponse> handleRuntimeException(RuntimeException e) {
        ApiResponse response = new ApiResponse();
        response.setCode(ErrorCode.UNCATEGORIZED_EXCEPTION.getCode());
        response.setMessage(ErrorCode.UNCATEGORIZED_EXCEPTION.getMessage());
        return ResponseEntity.badRequest().body(response);
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    ResponseEntity<ApiResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        String enumKey = e.getFieldError().getDefaultMessage();
        ErrorCode errorCode = ErrorCode.INVALID_KEY;

        try {
            errorCode = ErrorCode.valueOf(enumKey);
        } catch (IllegalArgumentException ex) {

        }

        ApiResponse response = new ApiResponse();
        response.setCode(errorCode.getCode());
        response.setMessage(errorCode.getMessage());
        return ResponseEntity.badRequest().body(response);
        }

    @ExceptionHandler(value = AppException.class)
    ResponseEntity<ApiResponse> handleAppException(AppException e) {
        ApiResponse response = new ApiResponse();
        response.setCode(e.getErrorCode().getCode());
        response.setMessage(e.getErrorCode().getMessage());
        return ResponseEntity.badRequest().body(response);
    }


}
