package com.example;

import io.micronaut.context.annotation.DefaultImplementation;

import java.util.Optional;

@DefaultImplementation(DefaultRequestIdProvider.class)
public interface RequestIdProvider {
    Optional<String> requestId();
}
