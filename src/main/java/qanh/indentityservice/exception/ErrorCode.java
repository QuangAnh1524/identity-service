package qanh.indentityservice.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(998, "Uncategorized exception"),
    USER_EXISTS(1001, "User already exists"),
    USERNAME_INVALID(1002, "Username must be at least 3 characters long"),
    PASSWORD_INVALID(1003, "Password must be at least 8 characters long"),
    INVALID_KEY(1004, "Invalid key"),
    USER_NOT_FOUND(1005, "User not found"),
    ;
    private int code;
    private String message;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
