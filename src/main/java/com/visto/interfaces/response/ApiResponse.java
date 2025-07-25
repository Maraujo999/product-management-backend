package com.visto.interfaces.response;

public record ApiResponse<T>(T data, String message, int status) {
}
