package de.alex.blogster_rest_api.user.model.http;

public class CustomResponse<T> {
    private final T response;
    private final String error;

    public CustomResponse(T response) {
        this.response = response;
        this.error = null;
    }

    public CustomResponse(String error) {
        this.response = null;
        this.error = error;
    }

    public T getResponse() {
        return response;
    }

    public String getError() {
        return error;
    }
}
