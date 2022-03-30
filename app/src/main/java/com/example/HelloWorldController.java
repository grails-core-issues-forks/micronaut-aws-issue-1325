package com.example;

import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.http.annotation.Produces;

@Controller
public class HelloWorldController {

    private final RequestIdProvider requestIdProvider;

    public HelloWorldController(RequestIdProvider requestIdProvider) {
        this.requestIdProvider = requestIdProvider;
    }

    @Produces(MediaType.TEXT_PLAIN)
    @Get
    public String index() {
        return requestIdProvider.requestId().map(requestId -> "AWS Request ID: " + requestId).orElse("unable to retrieve request id");
    }
}
