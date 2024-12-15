package com.manage.UserSubscription;

import org.springframework.http.HttpStatus;

public class ApiResponse<T> {
    private boolean success;
    private T data;
    private String message;
    private HttpStatus status;

    public ApiResponse(boolean success, T data, String message, HttpStatus status) {
        this.success = success;
        this.data = data;
        this.message = message;
        this.status = status;
    }

    // Getters
    public boolean isSuccess() {
        return success;
    }

    public T getData() {
        return data;
    }

    public String getMessage() {
        return message;
    }

    public HttpStatus getStatus() {
        return status;
    }
}
