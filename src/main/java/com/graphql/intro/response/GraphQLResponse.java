package com.graphql.intro.response;

import lombok.Getter;

@Getter
public class GraphQLResponse<T> {
    private boolean success;
    private String message;
    private T data;

    public GraphQLResponse(boolean success, String message, T data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public static <T> GraphQLResponse<T> success(T data) {
        return new GraphQLResponse<>(true, "Operation successful", data);
    }

    public static <T> GraphQLResponse<T> error(String message) {
        return new GraphQLResponse<>(false, message, null);
    }

}