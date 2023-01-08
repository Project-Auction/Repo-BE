package auctionbe.models;

import org.springframework.http.HttpStatus;

public class ApiError {
    private HttpStatus httpStatus;
    private String message;

    public ApiError() {
    }

    public ApiError(String message) {
        this.message = message;
    }

    public ApiError(HttpStatus httpStatus) {
        this.httpStatus = httpStatus;
    }

    public ApiError(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
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
