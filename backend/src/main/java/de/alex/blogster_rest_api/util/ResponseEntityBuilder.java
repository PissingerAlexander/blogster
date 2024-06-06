package de.alex.blogster_rest_api.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseEntityBuilder {

    public static ResponseEntity<String> buildStringResponse(String message) {
        return new ResponseEntity<>("{ \"message\": \"" + message + "\", \"error\": \"\" }", HttpStatus.OK);
    }

    public static ResponseEntity<String> buildErrorResponse(String error) {
        return new ResponseEntity<>("{ \"message\": \"\", \"error\": \"" + error + "\" }", HttpStatus.CONFLICT);
    }
}
