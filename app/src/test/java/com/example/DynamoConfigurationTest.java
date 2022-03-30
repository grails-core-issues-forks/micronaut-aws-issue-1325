package com.example;

import io.micronaut.context.BeanContext;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@MicronautTest(startApplication = false)
public class DynamoConfigurationTest {

    @Inject
    BeanContext beanContext;

    @Test
    void DynamoConfigurationCanBeSetFromEnvVariable() {
        assertTrue(beanContext.containsBean(DynamoConfiguration.class));
        if (System.getenv("TABLE_NAME") != null) {
            assertEquals(System.getenv("TABLE_NAME"), beanContext.getBean(DynamoConfiguration.class).getName());
        } else {
            assertEquals("products", beanContext.getBean(DynamoConfiguration.class).getName());
        }
    }
}
