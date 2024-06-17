package de.alex.blogster_rest_api.http.model.response;

import java.util.List;

public record GetPage<T>(long itemCount, int pageCount, List<T>pageContent) {
}
