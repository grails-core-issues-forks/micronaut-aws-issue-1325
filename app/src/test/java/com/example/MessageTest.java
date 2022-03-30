package com.example;

import io.micronaut.core.beans.BeanIntrospection;
import io.micronaut.core.beans.BeanProperty;
import io.micronaut.core.beans.BeanWrapper;
import io.micronaut.test.extensions.junit5.annotation.MicronautTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;

import javax.validation.Validator;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@MicronautTest(startApplication = false)
class MessageTest {

    @Inject
    Validator validator;

    @Test
    void messageNameCannotBeBlank() {
        Message msg = new Message("Hello World");
        assertTrue(validator.validate(msg).isEmpty());
        msg = new Message("");
        assertFalse(validator.validate(msg).isEmpty());
    }

    @Test
    void canInstantiateViaIntrospection() {
        BeanIntrospection<Message> introspection = BeanIntrospection.getIntrospection(Message.class);
        Message message = introspection.instantiate("Hello World");
        assertTrue(message.equals(new Message("Hello World")));

        final BeanWrapper<Message> wrapper = BeanWrapper.getWrapper(message);
        System.out.println(wrapper.getBeanProperties().stream()
                .map(prop -> prop.getType() + " " + prop.getName()).collect(Collectors.joining(",")));
    }
}