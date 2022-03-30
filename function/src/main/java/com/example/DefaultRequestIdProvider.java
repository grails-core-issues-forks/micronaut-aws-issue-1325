package com.example;

import com.amazonaws.services.lambda.runtime.Context;
import io.micronaut.context.BeanContext;
import io.micronaut.core.annotation.NonNull;
import jakarta.inject.Singleton;

import java.util.Optional;

@Singleton
public class DefaultRequestIdProvider implements RequestIdProvider {
    private final BeanContext beanContext;

    public DefaultRequestIdProvider(BeanContext beanContext) {
        this.beanContext = beanContext;
    }

    @Override
    @NonNull
    public Optional<String> requestId() {
        return getContext().map(Context::getAwsRequestId);
    }

    @NonNull
    private Optional<Context> getContext() {
        return beanContext.containsBean(Context.class) ?
                Optional.of(beanContext.getBean(Context.class)) : Optional.empty();
    }

}
