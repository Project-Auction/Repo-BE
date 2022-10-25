package auctionbe.models;

import org.springframework.http.HttpStatus;

public class ApiError {
    private HttpStatus httpStatus;
    private String message;
    private String debugMessage;

    public ApiError() {}

    public ApiError(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public ApiError(HttpStatus httpStatus, String message, String debugMessage) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.debugMessage = debugMessage;
    }

    public String getDebugMessage() {
        return debugMessage;
    }

    public void setDebugMessage(String debugMessage) {
        this.debugMessage = debugMessage;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    public void setHttpStatus(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
