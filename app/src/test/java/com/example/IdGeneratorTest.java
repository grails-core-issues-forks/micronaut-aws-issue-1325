package com.example;

import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;

@MicronautTest(startApplication = false)
class IdGeneratorTest {

    @Inject
    IdGenerator idGenerator;

    @Test
    void idGeneratorGeneratesUniqueIds() {
        Set<String> results = new HashSet<>();
        for (int i = 0; i < 20; i++) {
            results.add(idGenerator.generate());
        }
        assertEquals(20, results.size());
    }
}