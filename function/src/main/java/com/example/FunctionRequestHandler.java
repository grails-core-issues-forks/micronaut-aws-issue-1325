package com.example;

import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import io.micronaut.core.annotation.Introspected;
import io.micronaut.function.aws.MicronautRequestHandler;
import jakarta.inject.Inject;
import java.util.Collections;

@Introspected
public class FunctionRequestHandler extends MicronautRequestHandler<APIGatewayProxyRequestEvent, APIGatewayProxyResponseEvent> {

    @Inject
    RequestIdProvider requestIdProvider;

    @Override
    public APIGatewayProxyResponseEvent execute(APIGatewayProxyRequestEvent input) {
        return requestIdProvider.requestId().map(requestId -> {
            APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
            response.setStatusCode(200);
            response.setHeaders(Collections.singletonMap("Content-Type", "text/plain"));
            response.setBody("AWS Request ID: " + requestId);
            return response;
        }).orElseGet(() -> notFound());
    }

    private APIGatewayProxyResponseEvent notFound() {
        APIGatewayProxyResponseEvent response = new APIGatewayProxyResponseEvent();
        response.setStatusCode(404);
        return response;
    }
}
