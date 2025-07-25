package com.visto.helper;

import java.util.function.Supplier;

public final class ValidationHelper {

    private ValidationHelper() {
    }

    public static <T> T requireNonNull(T value, String message) {
        if (value == null) {
            throw new IllegalArgumentException(message);
        }
        return value;
    }

    public static void require(boolean condition, Supplier<String> messageSupplier) {
        if (!condition) {
            throw new IllegalArgumentException(messageSupplier.get());
        }
    }
}