package com.stock.main.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReturnCode {
    SUCCESS(1, "success"),
    Exception(0, "exception");

    public final int code;
    private final String message;
}
