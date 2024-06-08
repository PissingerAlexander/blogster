package de.alex.blogster_rest_api.user.model.http;

public class CustomResponse<T> {
    private final T data;
    private final String error;

    public CustomResponse(T data) {
        this.data = data;
        this.error = null;
    }

    public CustomResponse(String error) {
        this.data = null;
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public String getError() {
        return error;
    }
}
